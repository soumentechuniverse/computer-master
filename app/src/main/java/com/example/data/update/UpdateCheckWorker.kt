package com.example.data.update

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.util.AppLanguage
import kotlinx.coroutines.CancellationException
import java.util.Locale

/**
 * Periodic update checker. WorkManager can run this when the app is closed
 * and reschedules it across device restarts, subject to Android battery rules.
 */
class UpdateCheckWorker(
  appContext: Context,
  params: WorkerParameters
) : CoroutineWorker(appContext, params) {

  override suspend fun doWork(): Result {
    return try {
      val language = when (Locale.getDefault().language.lowercase(Locale.ROOT)) {
        "bn" -> AppLanguage.BENGALI
        "hi" -> AppLanguage.HINDI
        else -> AppLanguage.ENGLISH
      }
      val checked = InAppUpdateManager(applicationContext).checkForUpdatesInBackground(language)
      if (checked) Result.success() else Result.retry()
    } catch (cancelled: CancellationException) {
      throw cancelled
    } catch (_: Exception) {
      Result.retry()
    }
  }
}
