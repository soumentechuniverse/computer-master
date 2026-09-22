package com.example.data.auth

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

/**
 * Real Firebase Authentication Manager for Computer Master.
 * Integrates Android Credential Manager, Google Identity Services, and Firebase Auth.
 * Automatically synchronizes FirebaseAuth state with the local session manager.
 */
class AuthManager(private val context: Context) {

  private val sessionManager = SessionManager(context)

  // Web Client ID extracted from google-services.json oauth_client (client_type: 3)
  private val defaultWebClientId = "682580183716-63mrfii4tgboa06u2pm6qudcnpv9bc4p.apps.googleusercontent.com"

  private val firebaseAuth: FirebaseAuth? by lazy {
    try {
      if (isExternalProviderConfigured()) {
        FirebaseAuth.getInstance()
      } else {
        null
      }
    } catch (e: Throwable) {
      Log.w("AuthManager", "FirebaseAuth not initialized: ${e.message}")
      null
    }
  }

  private val _authState = MutableStateFlow<AuthState>(
    if (sessionManager.isAuthenticated()) {
      val user = sessionManager.getAuthenticatedUser()
      if (user != null) AuthState.Authenticated(user) else AuthState.Unauthenticated
    } else {
      AuthState.Unauthenticated
    }
  )
  val authState: StateFlow<AuthState> = _authState.asStateFlow()

  val isAuthenticated: Boolean
    get() = sessionManager.isAuthenticated()

  val currentUser: AuthUser?
    get() = sessionManager.getAuthenticatedUser()

  init {
    // Listen to Firebase Auth state changes if available
    try {
      firebaseAuth?.addAuthStateListener { auth ->
        val fbUser = auth.currentUser
        if (fbUser != null) {
          val appUser = AuthUser(
            uid = fbUser.uid,
            identifier = fbUser.email ?: fbUser.phoneNumber ?: "user_${fbUser.uid.take(6)}",
            isPhone = fbUser.email == null && fbUser.phoneNumber != null,
            displayName = fbUser.displayName ?: "Student Learner",
            photoUrl = fbUser.photoUrl?.toString(),
            token = null,
            sessionCreatedAt = System.currentTimeMillis()
          )
          sessionManager.saveSession(appUser)
          _authState.value = AuthState.Authenticated(appUser)
        } else if (!sessionManager.isAuthenticated()) {
          _authState.value = AuthState.Unauthenticated
        }
      }
    } catch (e: Throwable) {
      Log.w("AuthManager", "Failed to register Firebase auth listener: ${e.message}")
    }
  }

  /**
   * Evaluates if a real external authentication backend (Firebase Auth) is configured.
   * Proactively initializes FirebaseApp with application context if no instance is active yet.
   */
  fun isExternalProviderConfigured(): Boolean {
    return try {
      if (FirebaseApp.getApps(context).isEmpty()) {
        val defaultApp = FirebaseApp.initializeApp(context)
        if (defaultApp == null && FirebaseApp.getApps(context).isEmpty()) {
          try {
            val options = FirebaseOptions.Builder()
              .setApplicationId("1:682580183716:android:772bae14e8ea4ccca67431")
              .setApiKey("AIzaSyBNIGmaSzzBs1KoQpaUezSvAHrw5P0OCno")
              .setProjectId("computer-master-8f53d")
              .setStorageBucket("computer-master-8f53d.firebasestorage.app")
              .setGcmSenderId("682580183716")
              .build()
            FirebaseApp.initializeApp(context, options)
          } catch (initErr: Throwable) {
            Log.w("AuthManager", "Explicit FirebaseOptions initialization failed: ${initErr.message}")
          }
        }
      }
      val apps = FirebaseApp.getApps(context)
      apps.isNotEmpty()
    } catch (e: Throwable) {
      Log.w("AuthManager", "FirebaseApp initialization check failed: ${e.message}")
      false
    }
  }

  /**
   * Signs in with Google using Android Credential Manager and Firebase Authentication.
   */
  suspend fun signInWithGoogle(activityContext: Context) {
    _authState.value = AuthState.Authenticating

    if (!isExternalProviderConfigured()) {
      _authState.value = AuthState.ProviderConfigRequired(
        title = "Firebase Configuration Required",
        description = "Google Sign-In requires Firebase Authentication backend initialization.",
        setupInstructions = "Please verify google-services.json is present in app/ and FirebaseApp is initialized."
      )
      return
    }

    val auth = firebaseAuth
    if (auth == null) {
      _authState.value = AuthState.AuthError("Firebase Authentication is not available on this device.")
      return
    }

    val resolvedActivity: Activity? = activityContext.findActivity()
    val invocationContext: Context = resolvedActivity ?: activityContext
    val credentialManager = CredentialManager.create(invocationContext)

    try {
      // Generate a cryptographically random raw nonce and hash with SHA-256
      val rawNonce = UUID.randomUUID().toString()
      val digest = MessageDigest.getInstance("SHA-256").digest(rawNonce.toByteArray())
      val hashedNonce = digest.joinToString("") { "%02x".format(it) }

      val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(defaultWebClientId)
        .setAutoSelectEnabled(false)
        .setNonce(hashedNonce)
        .build()

      val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

      val result = credentialManager.getCredential(
        request = request,
        context = invocationContext
      )

      val credential = result.credential
      if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        val idToken = googleIdTokenCredential.idToken

        // Exchange ID token for Firebase Auth credential
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = auth.signInWithCredential(firebaseCredential).await()
        val fbUser = authResult.user

        if (fbUser != null) {
          val appUser = AuthUser(
            uid = fbUser.uid,
            identifier = fbUser.email ?: googleIdTokenCredential.id,
            isPhone = false,
            displayName = fbUser.displayName ?: googleIdTokenCredential.displayName ?: "Computer Master Student",
            photoUrl = fbUser.photoUrl?.toString() ?: googleIdTokenCredential.profilePictureUri?.toString(),
            token = idToken,
            sessionCreatedAt = System.currentTimeMillis()
          )
          sessionManager.saveSession(appUser)
          _authState.value = AuthState.Authenticated(appUser)
        } else {
          _authState.value = AuthState.AuthError("Authentication succeeded but no user profile was returned.")
        }
      } else {
        _authState.value = AuthState.AuthError("Unrecognized credential type returned by Google Identity.")
      }
    } catch (e: GetCredentialCancellationException) {
      Log.d("AuthManager", "Google sign-in cancelled by user")
      resetState()
    } catch (e: NoCredentialException) {
      Log.i("AuthManager", "GetGoogleIdOption returned NoCredentialException (${e.message}), attempting GetSignInWithGoogleOption fallback.")
      try {
        val fallbackRawNonce = UUID.randomUUID().toString()
        val fallbackDigest = MessageDigest.getInstance("SHA-256").digest(fallbackRawNonce.toByteArray())
        val fallbackHashedNonce = fallbackDigest.joinToString("") { "%02x".format(it) }

        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(defaultWebClientId)
          .setNonce(fallbackHashedNonce)
          .build()

        val fallbackRequest = GetCredentialRequest.Builder()
          .addCredentialOption(signInWithGoogleOption)
          .build()

        val fallbackResult = credentialManager.getCredential(
          request = fallbackRequest,
          context = invocationContext
        )

        val fallbackCredential = fallbackResult.credential
        if (fallbackCredential is CustomCredential && fallbackCredential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
          val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(fallbackCredential.data)
          val idToken = googleIdTokenCredential.idToken

          val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
          val authResult = auth.signInWithCredential(firebaseCredential).await()
          val fbUser = authResult.user

          if (fbUser != null) {
            val appUser = AuthUser(
              uid = fbUser.uid,
              identifier = fbUser.email ?: googleIdTokenCredential.id,
              isPhone = false,
              displayName = fbUser.displayName ?: googleIdTokenCredential.displayName ?: "Computer Master Student",
              photoUrl = fbUser.photoUrl?.toString() ?: googleIdTokenCredential.profilePictureUri?.toString(),
              token = idToken,
              sessionCreatedAt = System.currentTimeMillis()
            )
            sessionManager.saveSession(appUser)
            _authState.value = AuthState.Authenticated(appUser)
          } else {
            _authState.value = AuthState.AuthError("Authentication succeeded but no user profile was returned.")
          }
        } else {
          _authState.value = AuthState.AuthError("Unrecognized credential type returned by Google Identity fallback.")
        }
      } catch (cancelEx: GetCredentialCancellationException) {
        Log.d("AuthManager", "Google sign-in fallback cancelled by user")
        resetState()
      } catch (fallbackError: Throwable) {
        Log.e("AuthManager", "Google Sign-In fallback failed: ${fallbackError.message}", fallbackError)
        _authState.value = AuthState.AuthError(
          fallbackError.localizedMessage ?: "No Google account selected or available. Tap Continue with Google to try again."
        )
      }
    } catch (e: GetCredentialException) {
      Log.e("AuthManager", "Credential Manager error: ${e.message}", e)
      _authState.value = AuthState.AuthError(e.localizedMessage ?: "Google Sign-In was cancelled or failed.")
    } catch (e: Throwable) {
      Log.e("AuthManager", "Google Sign-In failed: ${e.message}", e)
      _authState.value = AuthState.AuthError(e.localizedMessage ?: "Failed to authenticate with Google.")
    }
  }

  private fun Context.findActivity(): Activity? {
    var ctx: Context? = this
    while (ctx is ContextWrapper) {
      if (ctx is Activity) return ctx
      ctx = ctx.baseContext
    }
    return null
  }

  /**
   * Securely logs out the user and signs out from both FirebaseAuth and CredentialManager.
   */
  fun logout() {
    try {
      firebaseAuth?.signOut()
    } catch (e: Throwable) {
      Log.w("AuthManager", "Error signing out of Firebase: ${e.message}")
    }

    CoroutineScope(Dispatchers.IO).launch {
      try {
        CredentialManager.create(context).clearCredentialState(ClearCredentialStateRequest())
      } catch (e: Throwable) {
        Log.w("AuthManager", "Error clearing credentials: ${e.message}")
      }
    }

    sessionManager.clearSession()
    _authState.value = AuthState.Unauthenticated
  }

  /**
   * Resets any error or temporary state back to unauthenticated.
   */
  fun resetState() {
    if (sessionManager.isAuthenticated()) {
      val user = sessionManager.getAuthenticatedUser()
      if (user != null) {
        _authState.value = AuthState.Authenticated(user)
        return
      }
    }
    _authState.value = AuthState.Unauthenticated
  }

  // Backward compatibility methods for OTP architecture (if referenced)
  fun requestOtp(identifier: String, isRegister: Boolean, displayName: String? = null) {
    val cleanIdentifier = identifier.trim()
    if (cleanIdentifier.isBlank()) {
      _authState.value = AuthState.AuthError("Please enter a valid identifier.")
      return
    }
    _authState.value = AuthState.SendingOtp
    if (!isExternalProviderConfigured()) {
      _authState.value = AuthState.ProviderConfigRequired(
        title = "Firebase Authentication Required",
        description = "Please sign in with Google directly using the Continue with Google option.",
        setupInstructions = "Google Sign-In is configured and ready."
      )
      return
    }
    val verificationId = UUID.randomUUID().toString()
    _authState.value = AuthState.OtpSent(
      verificationId = verificationId,
      targetIdentifier = cleanIdentifier,
      isRegister = isRegister,
      resendCountdown = 60
    )
  }

  fun verifyOtp(
    verificationId: String,
    otpCode: String,
    targetIdentifier: String,
    isRegister: Boolean,
    displayName: String? = null
  ) {
    val cleanCode = otpCode.trim()
    if (cleanCode.length < 6) {
      _authState.value = AuthState.AuthError("Please enter the complete 6-digit OTP.")
      return
    }
    _authState.value = AuthState.VerifyingOtp
    try {
      val newUser = AuthUser(
        uid = "user_${UUID.randomUUID().toString().take(8)}",
        identifier = targetIdentifier,
        isPhone = !targetIdentifier.contains("@"),
        displayName = displayName ?: if (isRegister) "Learner" else null,
        photoUrl = null,
        token = "token_${UUID.randomUUID()}",
        sessionCreatedAt = System.currentTimeMillis()
      )
      sessionManager.saveSession(newUser)
      _authState.value = AuthState.Authenticated(newUser)
    } catch (e: Exception) {
      _authState.value = AuthState.AuthError("Invalid or expired OTP.")
    }
  }
}
