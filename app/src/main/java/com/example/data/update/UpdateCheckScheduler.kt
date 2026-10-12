package com.example.data.update

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

/**
 * Registers a persistent, network-aware background update check.
 * Android controls the exact run time to preserve battery life.
 */
object UpdateCheckScheduler {
  private const val UNIQUE_WORK_NAME = "computer_master_periodic_update_check"

  fun schedule(context: Context) {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val request = PeriodicWorkRequestBuilder<UpdateCheckWorker>(12, TimeUnit.HOURS)
      .setConstraints(constraints)
      .build()

    WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
      UNIQUE_WORK_NAME,
      ExistingPeriodicWorkPolicy.KEEP,
      request
    )
  }
}
