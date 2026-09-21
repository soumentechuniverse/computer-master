package com.example.data.update

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import com.example.MainActivity
import com.example.R
import com.example.data.model.AppUpdateInfo
import com.example.util.AppLanguage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

/**
 * Sealed class representing in-app update UI state.
 */
sealed class UpdateUiState {
  object Idle : UpdateUiState()
  data class Checking(val isManual: Boolean) : UpdateUiState()
  data class UpdateAvailable(val info: AppUpdateInfo, val isManual: Boolean = false) : UpdateUiState()
  data class UpToDate(val versionName: String, val isManual: Boolean) : UpdateUiState()
  data class Downloading(
    val info: AppUpdateInfo,
    val progress: Float,
    val downloadedBytes: Long,
    val totalBytes: Long
  ) : UpdateUiState()
  data class ReadyToInstall(val info: AppUpdateInfo, val apkFile: File) : UpdateUiState()
  data class Error(val message: String, val isManual: Boolean) : UpdateUiState()
}

class InAppUpdateManager(private val context: Context) {

  companion object {
    const val DEFAULT_GITHUB_UPDATE_URL =
      "https://raw.githubusercontent.com/SoumenTechUniverse/Computer-Master/main/app_update.json"
    private const val PREFS_NAME = "computer_master_update_prefs"
    private const val KEY_CUSTOM_URL = "custom_github_update_url"
    private const val KEY_CUSTOM_APK_URL = "custom_apk_download_url"
    private const val KEY_LAST_CHECK_TIME = "last_update_check_time_ms"
    private const val KEY_DISMISSED_VERSION = "dismissed_update_version_code"

    const val CHANNEL_ID = "computer_master_updates_channel"
    const val NOTIFICATION_ID = 2026
    const val EXTRA_LAUNCH_UPDATE = "extra_launch_update_dialog"

    // Throttle automatic checks to once every 4 hours to respect battery and bandwidth
    private const val AUTO_CHECK_INTERVAL_MS = 4 * 60 * 60 * 1000L
  }

  private val prefs: SharedPreferences =
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

  private val httpClient = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .followRedirects(true)
    .followSslRedirects(true)
    .build()

  private val _updateState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
  val updateState: StateFlow<UpdateUiState> = _updateState.asStateFlow()

  private val _showDialog = MutableStateFlow(false)
  val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

  val currentVersionCode: Long by lazy {
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode
      } else {
        @Suppress("DEPRECATION")
        context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toLong()
      }
    } catch (_: Exception) {
      1L
    }
  }

  val currentVersionName: String by lazy {
    try {
      context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0.0"
    } catch (_: Exception) {
      "1.0.0"
    }
  }

  init {
    createNotificationChannel()
  }

  fun getUpdateMetadataUrl(): String {
    return prefs.getString(KEY_CUSTOM_URL, DEFAULT_GITHUB_UPDATE_URL) ?: DEFAULT_GITHUB_UPDATE_URL
  }

  fun setUpdateMetadataUrl(url: String) {
    prefs.edit().putString(KEY_CUSTOM_URL, url.trim()).apply()
  }

  fun resetToDefaultUrl() {
    prefs.edit().remove(KEY_CUSTOM_URL).apply()
  }

  fun getCustomApkUrl(): String? {
    return prefs.getString(KEY_CUSTOM_APK_URL, null)?.takeIf { it.isNotBlank() }
  }

  fun setCustomApkUrl(url: String?) {
    if (url.isNullOrBlank()) {
      prefs.edit().remove(KEY_CUSTOM_APK_URL).apply()
    } else {
      prefs.edit().putString(KEY_CUSTOM_APK_URL, url.trim()).apply()
    }
  }

  fun resetCustomApkUrl() {
    prefs.edit().remove(KEY_CUSTOM_APK_URL).apply()
  }

  fun getEffectiveApkUrl(infoApkUrl: String): String {
    return getCustomApkUrl() ?: infoApkUrl
  }

  /**
   * Checks for updates from the remote GitHub JSON file.
   */
  fun checkForUpdates(
    scope: CoroutineScope,
    isManual: Boolean = false,
    language: AppLanguage = AppLanguage.ENGLISH
  ) {
    val now = System.currentTimeMillis()
    val lastCheck = prefs.getLong(KEY_LAST_CHECK_TIME, 0L)

    // Skip auto-check if recently checked within throttle window unless manual
    if (!isManual && (now - lastCheck) < AUTO_CHECK_INTERVAL_MS) {
      return
    }

    _updateState.value = UpdateUiState.Checking(isManual)

    scope.launch {
      val result = withContext(Dispatchers.IO) {
        fetchRemoteUpdateInfo()
      }

      prefs.edit().putLong(KEY_LAST_CHECK_TIME, now).apply()

      result.fold(
        onSuccess = { info ->
          if (info != null && info.isNewerThan(currentVersionCode)) {
            val dismissedVersion = prefs.getInt(KEY_DISMISSED_VERSION, -1)
            _updateState.value = UpdateUiState.UpdateAvailable(info, isManual)

            // If manual check or newer than previously dismissed, pop dialog immediately
            if (isManual || info.versionCode > dismissedVersion) {
              _showDialog.value = true
            }

            // Also post notification if permitted and appropriate
            showUpdateNotification(info, language)
          } else {
            _updateState.value = UpdateUiState.UpToDate(currentVersionName, isManual)
          }
        },
        onFailure = { error ->
          val errorMessage = error.localizedMessage ?: "Failed to connect to update server"
          _updateState.value = UpdateUiState.Error(errorMessage, isManual)
        }
      )
    }
  }

  /**
   * Fetches and parses GitHub JSON metadata.
   */
  private fun fetchRemoteUpdateInfo(): Result<AppUpdateInfo?> {
    val url = getUpdateMetadataUrl()
    return try {
      val request = Request.Builder()
        .url(url)
        .header("Cache-Control", "no-cache")
        .build()

      val response = httpClient.newCall(request).execute()
      if (!response.isSuccessful) {
        return Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
      }

      val jsonString = response.body?.string().orEmpty()
      if (jsonString.isBlank()) {
        return Result.failure(Exception("Empty update response received from server"))
      }

      val json = JSONObject(jsonString)
      val versionCode = json.optInt("versionCode", 0)
      val versionName = json.optString("versionName", "")
      val updateMessage = json.optString("updateMessage", "")
      val apkUrl = json.optString("apkUrl", "")
      val updateMessageBn = if (json.has("updateMessageBn") && !json.isNull("updateMessageBn")) json.getString("updateMessageBn") else null
      val updateMessageHi = if (json.has("updateMessageHi") && !json.isNull("updateMessageHi")) json.getString("updateMessageHi") else null
      val minSupportedVersion = if (json.has("minSupportedVersion")) json.getInt("minSupportedVersion") else null
      val releaseDate = if (json.has("releaseDate") && !json.isNull("releaseDate")) json.getString("releaseDate") else null
      val fileSize = if (json.has("fileSize") && !json.isNull("fileSize")) json.getString("fileSize") else null

      if (versionCode > 0 && apkUrl.isNotBlank()) {
        // Verify APK URL is a genuine HTTP/HTTPS URL and not a dummy placeholder
        val isHttpUrl = apkUrl.startsWith("https://", ignoreCase = true) || apkUrl.startsWith("http://", ignoreCase = true)
        val isPlaceholder = apkUrl.contains("example.com") || apkUrl.contains("placeholder") || apkUrl.contains("fake.url")
        if (!isHttpUrl || isPlaceholder) {
          return Result.failure(Exception("Invalid APK download URL in update metadata: $apkUrl"))
        }

        val effectiveApkUrl = getEffectiveApkUrl(apkUrl)
        val info = AppUpdateInfo(
          versionCode = versionCode,
          versionName = versionName.ifBlank { "v$versionCode" },
          updateMessage = updateMessage,
          apkUrl = effectiveApkUrl,
          updateMessageBn = updateMessageBn,
          updateMessageHi = updateMessageHi,
          minSupportedVersion = minSupportedVersion,
          releaseDate = releaseDate,
          fileSize = fileSize
        )
        Result.success(info)
      } else {
        Result.failure(Exception("Invalid update format: missing versionCode or apkUrl"))
      }
    } catch (e: Exception) {
      if (e is CancellationException) throw e
      Result.failure(e)
    }
  }

  /**
   * Downloads the remote APK file and updates state with download progress.
   */
  fun downloadApk(
    scope: CoroutineScope,
    info: AppUpdateInfo
  ) {
    scope.launch {
      _updateState.value = UpdateUiState.Downloading(
        info = info,
        progress = 0f,
        downloadedBytes = 0L,
        totalBytes = 0L
      )

      val result = withContext(Dispatchers.IO) {
        runCatching {
          val updatesDir = File(context.cacheDir, "updates").apply { mkdirs() }
          val safeVersion = info.versionName.replace(Regex("[^a-zA-Z0-9._-]"), "_")
          val destinationFile = File(updatesDir, "ComputerMaster_$safeVersion.apk")

          val downloadUrl = getEffectiveApkUrl(info.apkUrl)
          val request = Request.Builder()
            .url(downloadUrl)
            .build()

          httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
              throw Exception("Download failed with HTTP ${response.code}")
            }

            val body = response.body ?: throw Exception("Response body is empty")
            val contentLength = body.contentLength()

            body.byteStream().use { input ->
              FileOutputStream(destinationFile).use { output ->
                val buffer = ByteArray(8 * 1024)
                var bytesRead: Int
                var totalBytesRead = 0L
                var lastReportTime = System.currentTimeMillis()

                while (input.read(buffer).also { bytesRead = it } != -1) {
                  output.write(buffer, 0, bytesRead)
                  totalBytesRead += bytesRead

                  val now = System.currentTimeMillis()
                  if (now - lastReportTime > 150L || totalBytesRead == contentLength) {
                    lastReportTime = now
                    val progress = if (contentLength > 0) {
                      (totalBytesRead.toFloat() / contentLength.toFloat()).coerceIn(0f, 1f)
                    } else {
                      0f
                    }
                    _updateState.value = UpdateUiState.Downloading(
                      info = info,
                      progress = progress,
                      downloadedBytes = totalBytesRead,
                      totalBytes = contentLength
                    )
                  }
                }
                output.flush()
              }
            }
          }
          destinationFile
        }
      }

      result.fold(
        onSuccess = { apkFile ->
          _updateState.value = UpdateUiState.ReadyToInstall(info, apkFile)
          // Automatically launch installer once downloaded
          launchPackageInstaller(apkFile)
        },
        onFailure = { error ->
          _updateState.value = UpdateUiState.Error(
            message = error.localizedMessage ?: "APK download failed",
            isManual = true
          )
        }
      )
    }
  }

  /**
   * Safely invokes the Android Package Installer using FileProvider.
   */
  fun launchPackageInstaller(apkFile: File) {
    try {
      if (!apkFile.exists() || apkFile.length() == 0L) {
        _updateState.value = UpdateUiState.Error("Downloaded update file is missing or empty.", true)
        return
      }

      val apkUri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        apkFile
      )

      val installIntent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(apkUri, "application/vnd.android.package-archive")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }

      // Grant explicit URI read permission to any resolving installer activities
      val resolveActivities = context.packageManager.queryIntentActivities(installIntent, 0)
      for (resolveInfo in resolveActivities) {
        val packageName = resolveInfo.activityInfo.packageName
        context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
      }

      // Check for unknown source permission if Android 8.0+
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (!context.packageManager.canRequestPackageInstalls()) {
          // Launch unknown sources settings specifically for our app
          val manageSourcesIntent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
            data = Uri.parse("package:${context.packageName}")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
          }
          context.startActivity(manageSourcesIntent)
          // Also invoke installer; user can proceed once allowed
          context.startActivity(installIntent)
          return
        }
      }

      context.startActivity(installIntent)
    } catch (e: Exception) {
      _updateState.value = UpdateUiState.Error("Unable to launch installer: ${e.message}", true)
    }
  }

  fun dismissDialog(versionCode: Int? = null) {
    _showDialog.value = false
    if (versionCode != null) {
      prefs.edit().putInt(KEY_DISMISSED_VERSION, versionCode).apply()
    }
  }

  fun openDialog() {
    _showDialog.value = true
  }

  /**
   * Initializes notification channel for updates on Android 8.0+.
   */
  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "Computer Master Updates"
      val descriptionText = "Notifications for newly available Computer Master app updates"
      val importance = NotificationManager.IMPORTANCE_DEFAULT
      val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
      }
      val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }

  /**
   * Posts an Android system notification alerting the user to the update.
   */
  private fun showUpdateNotification(info: AppUpdateInfo, language: AppLanguage) {
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
          return
        }
      }

      val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        putExtra(EXTRA_LAUNCH_UPDATE, true)
      }

      val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
      )

      val title = when (language) {
        AppLanguage.BENGALI -> "কম্পিউটার মাস্টারের নতুন সংস্করণ এসেছে! (${info.versionName})"
        AppLanguage.HINDI -> "कंप्यूटर मास्टर का नया संस्करण उपलब्ध है! (${info.versionName})"
        AppLanguage.ENGLISH -> "New Computer Master Update Available! (${info.versionName})"
      }

      val body = info.getLocalizedMessage(language).lines().firstOrNull()
        ?: "Tap to download and update to ${info.versionName}"

      val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(title)
        .setContentText(body)
        .setStyle(NotificationCompat.BigTextStyle().bigText(info.getLocalizedMessage(language)))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

      NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    } catch (_: Exception) {
      // Gracefully ignore notification errors
    }
  }
}
