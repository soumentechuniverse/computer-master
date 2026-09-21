package com.example

import com.example.data.model.AppUpdateInfo
import com.example.data.update.InAppUpdateManager
import com.example.util.AppLanguage
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class InAppUpdateSystemTest {

  private fun resolveFile(relativePath: String): File {
    val candidates = listOf(
      File(relativePath),
      File("..", relativePath),
      File(relativePath.removePrefix("app/")),
      File("../app", relativePath.removePrefix("app/")),
      File("app", relativePath)
    )
    return candidates.firstOrNull { it.exists() } ?: File(relativePath)
  }

  @Test
  fun githubUpdateMetadataUrl_isCorrect() {
    val defaultUrl = InAppUpdateManager.DEFAULT_GITHUB_UPDATE_URL

    // 1. Verify scheme and host
    assertTrue("URL must be secure HTTPS", defaultUrl.startsWith("https://"))
    assertTrue("URL must point to raw.githubusercontent.com", defaultUrl.contains("raw.githubusercontent.com"))

    // 2. Verify repository owner and repo name
    assertTrue("URL must point to SoumenTechUniverse repository", defaultUrl.contains("SoumenTechUniverse/Computer-Master"))

    // 3. Verify target file and branch
    assertEquals(
      "https://raw.githubusercontent.com/SoumenTechUniverse/Computer-Master/main/app_update.json",
      defaultUrl
    )
  }

  @Test
  fun appUpdateJson_hasGenuineApkUrlAndValidMetadata() {
    val updateJsonFile = resolveFile("app_update.json")
    assertTrue("app_update.json must exist in project (${updateJsonFile.absolutePath})", updateJsonFile.exists())

    val jsonContent = updateJsonFile.readText()
    val json = JSONObject(jsonContent)

    // Verify versioning metadata
    val versionCode = json.getInt("versionCode")
    val versionName = json.getString("versionName")
    assertTrue("Version code must be positive", versionCode > 0)
    assertTrue("Version name must not be empty", versionName.isNotBlank())

    // Verify APK download URL
    val apkUrl = json.getString("apkUrl")
    assertTrue("APK URL must not be blank", apkUrl.isNotBlank())
    assertTrue("APK URL must use HTTPS", apkUrl.startsWith("https://"))
    assertTrue("APK URL must point to GitHub Releases", apkUrl.contains("github.com/SoumenTechUniverse/Computer-Master/releases/download/"))
    assertTrue("APK URL must end with .apk", apkUrl.endsWith(".apk"))

    // Ensure it is not a dummy or placeholder URL
    assertFalse("APK URL must not contain placeholder", apkUrl.contains("placeholder", ignoreCase = true))
    assertFalse("APK URL must not contain example.com", apkUrl.contains("example.com", ignoreCase = true))
    assertFalse("APK URL must not contain fake", apkUrl.contains("fake", ignoreCase = true))

    // Verify trilingual release notes are present
    val enMessage = json.getString("updateMessage")
    val bnMessage = json.optString("updateMessageBn")
    val hiMessage = json.optString("updateMessageHi")

    assertTrue("English release notes must be populated", enMessage.isNotBlank())
    assertTrue("Bengali release notes must be populated", bnMessage.isNotBlank())
    assertTrue("Hindi release notes must be populated", hiMessage.isNotBlank())
  }

  @Test
  fun appUpdateInfo_newerVersionDetectionAndLocalization() {
    val hiText = "हिंदी अपडेट विवरण"
    val bnText = "বাংলা আপডেট বিবরণ"
    val enText = "English changelog"

    val info = AppUpdateInfo(
      versionCode = 2,
      versionName = "1.1.0",
      updateMessage = enText,
      apkUrl = "https://github.com/SoumenTechUniverse/Computer-Master/releases/download/v1.1.0/app-debug.apk",
      updateMessageBn = bnText,
      updateMessageHi = hiText,
      minSupportedVersion = 1,
      releaseDate = "2026-09-20",
      fileSize = "14.8 MB"
    )

    // Test version comparison
    assertTrue("v2 is newer than installed v1", info.isNewerThan(1L))
    assertFalse("v2 is not newer than installed v2", info.isNewerThan(2L))
    assertFalse("v2 is not newer than installed v3", info.isNewerThan(3L))

    // Test localization
    assertEquals(enText, info.getLocalizedMessage(AppLanguage.ENGLISH))
    assertEquals(bnText, info.getLocalizedMessage(AppLanguage.BENGALI))
    assertEquals(hiText, info.getLocalizedMessage(AppLanguage.HINDI))
  }

  @Test
  fun appDataPreservation_configurationIntegrity() {
    // 1. Verify AndroidManifest file provider authority matches package specification
    val manifestFile = resolveFile("app/src/main/AndroidManifest.xml")
    assertTrue("AndroidManifest.xml must exist (${manifestFile.absolutePath})", manifestFile.exists())
    val manifestContent = manifestFile.readText()

    assertTrue(
      "FileProvider authority must use \${applicationId}.fileprovider to prevent package collision",
      manifestContent.contains("android:authorities=\"\${applicationId}.fileprovider\"")
    )
    assertTrue(
      "FileProvider must grant URI permissions",
      manifestContent.contains("android:grantUriPermissions=\"true\"")
    )
    assertTrue(
      "Backup must be enabled for user data safety",
      manifestContent.contains("android:allowBackup=\"true\"")
    )
    assertTrue(
      "Install packages permission must be declared",
      manifestContent.contains("android.permission.REQUEST_INSTALL_PACKAGES")
    )

    // 2. Verify file_paths.xml uses cache directory so APKs don't touch database files
    val filePaths = resolveFile("app/src/main/res/xml/file_paths.xml")
    assertTrue("file_paths.xml must exist (${filePaths.absolutePath})", filePaths.exists())
    val filePathsContent = filePaths.readText()
    assertTrue(
      "Cache path updates directory must be configured in file_paths.xml",
      filePathsContent.contains("path=\"updates/\"")
    )
  }
}
