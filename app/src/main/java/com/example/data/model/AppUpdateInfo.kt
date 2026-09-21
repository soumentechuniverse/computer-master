package com.example.data.model

import com.example.util.AppLanguage

/**
 * Metadata representation of an in-app update payload hosted on GitHub.
 */
data class AppUpdateInfo(
  val versionCode: Int,
  val versionName: String,
  val updateMessage: String,
  val apkUrl: String,
  val updateMessageBn: String? = null,
  val updateMessageHi: String? = null,
  val minSupportedVersion: Int? = null,
  val releaseDate: String? = null,
  val fileSize: String? = null
) {
  /**
   * Returns release notes formatted for the active language.
   */
  fun getLocalizedMessage(language: AppLanguage): String {
    return when (language) {
      AppLanguage.BENGALI -> updateMessageBn?.takeIf { it.isNotBlank() } ?: updateMessage
      AppLanguage.HINDI -> updateMessageHi?.takeIf { it.isNotBlank() } ?: updateMessage
      AppLanguage.ENGLISH -> updateMessage
    }
  }

  /**
   * Evaluates if this remote version is newer than the locally installed app version.
   */
  fun isNewerThan(currentVersionCode: Long): Boolean {
    return versionCode > currentVersionCode
  }
}
