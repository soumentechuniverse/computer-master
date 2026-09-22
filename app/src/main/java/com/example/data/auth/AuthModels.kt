package com.example.data.auth

/**
 * Authenticated user profile representation.
 * Secrets, passwords, or plain OTPs are NEVER stored here.
 */
data class AuthUser(
  val uid: String,
  val identifier: String, // Phone number (e.g. +91 9876543210) or Email
  val isPhone: Boolean,
  val displayName: String? = null,
  val photoUrl: String? = null,
  val token: String? = null,
  val sessionCreatedAt: Long = System.currentTimeMillis(),
  val sessionExpiresAt: Long = 0L // 0L means persistent session until explicit logout
)

enum class AuthMode {
  LOGIN,
  REGISTER
}

sealed class AuthState {
  object Unauthenticated : AuthState()
  object Authenticating : AuthState()
  object SendingOtp : AuthState()
  data class OtpSent(
    val verificationId: String,
    val targetIdentifier: String,
    val isRegister: Boolean,
    val resendCountdown: Int = 60
  ) : AuthState()
  object VerifyingOtp : AuthState()
  data class Authenticated(val user: AuthUser) : AuthState()
  data class ProviderConfigRequired(
    val title: String,
    val description: String,
    val setupInstructions: String
  ) : AuthState()
  data class AuthError(val message: String) : AuthState()
}
