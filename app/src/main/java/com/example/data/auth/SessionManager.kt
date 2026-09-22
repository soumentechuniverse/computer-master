package com.example.data.auth

import android.content.Context
import android.content.SharedPreferences

/**
 * Secure session manager for Computer Master.
 * Persists only authorized session tokens, user IDs, and timestamps.
 * Passwords, OTP codes, or verification secrets are strictly NEVER persisted.
 */
class SessionManager(context: Context) {

  private val prefs: SharedPreferences =
    context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

  companion object {
    private const val PREFS_NAME = "cm_auth_secure_session"
    private const val KEY_IS_AUTHENTICATED = "is_authenticated"
    private const val KEY_UID = "user_uid"
    private const val KEY_IDENTIFIER = "user_identifier"
    private const val KEY_DISPLAY_NAME = "user_display_name"
    private const val KEY_PHOTO_URL = "user_photo_url"
    private const val KEY_IS_PHONE = "user_is_phone"
    private const val KEY_TOKEN = "session_token"
    private const val KEY_CREATED_AT = "session_created_at"
    private const val KEY_EXPIRES_AT = "session_expires_at"
  }

  fun isAuthenticated(): Boolean {
    val isAuth = prefs.getBoolean(KEY_IS_AUTHENTICATED, false)
    if (!isAuth) return false

    val expiresAt = prefs.getLong(KEY_EXPIRES_AT, 0L)
    if (expiresAt > 0L && System.currentTimeMillis() > expiresAt) {
      clearSession()
      return false
    }
    return true
  }

  fun getAuthenticatedUser(): AuthUser? {
    if (!isAuthenticated()) return null

    val uid = prefs.getString(KEY_UID, null) ?: return null
    val identifier = prefs.getString(KEY_IDENTIFIER, "") ?: ""
    val isPhone = prefs.getBoolean(KEY_IS_PHONE, true)
    val displayName = prefs.getString(KEY_DISPLAY_NAME, null)
    val photoUrl = prefs.getString(KEY_PHOTO_URL, null)
    val token = prefs.getString(KEY_TOKEN, null)
    val createdAt = prefs.getLong(KEY_CREATED_AT, System.currentTimeMillis())
    val expiresAt = prefs.getLong(KEY_EXPIRES_AT, 0L)

    return AuthUser(
      uid = uid,
      identifier = identifier,
      isPhone = isPhone,
      displayName = displayName,
      photoUrl = photoUrl,
      token = token,
      sessionCreatedAt = createdAt,
      sessionExpiresAt = expiresAt
    )
  }

  fun saveSession(user: AuthUser) {
    prefs.edit()
      .putBoolean(KEY_IS_AUTHENTICATED, true)
      .putString(KEY_UID, user.uid)
      .putString(KEY_IDENTIFIER, user.identifier)
      .putBoolean(KEY_IS_PHONE, user.isPhone)
      .putString(KEY_DISPLAY_NAME, user.displayName)
      .putString(KEY_PHOTO_URL, user.photoUrl)
      .putString(KEY_TOKEN, user.token)
      .putLong(KEY_CREATED_AT, user.sessionCreatedAt)
      .putLong(KEY_EXPIRES_AT, user.sessionExpiresAt)
      .apply()
  }

  fun clearSession() {
    prefs.edit()
      .clear()
      .apply()
  }
}
