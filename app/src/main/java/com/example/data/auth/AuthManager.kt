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

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

import java.security.MessageDigest
import java.util.UUID

class AuthManager(private val context: Context) {

    private val sessionManager = SessionManager(context)

    private val defaultWebClientId =
        "682580183716-63mrfii4tgboa06u2pm6qudcnpv9bc4p.apps.googleusercontent.com"

    private val firebaseAuth: FirebaseAuth? by lazy {
        try {
            if (isExternalProviderConfigured()) {
                FirebaseAuth.getInstance()
            } else {
                null
            }
        } catch (e: Throwable) {
            Log.w(
                "AuthManager",
                "FirebaseAuth not initialized: ${e.message}"
            )
            null
        }
    }

    private val _authState = MutableStateFlow<AuthState>(
        if (sessionManager.isAuthenticated()) {
            val user = sessionManager.getAuthenticatedUser()

            if (user != null) {
                AuthState.Authenticated(user)
            } else {
                AuthState.Unauthenticated
            }
        } else {
            AuthState.Unauthenticated
        }
    )

    val authState: StateFlow<AuthState> =
        _authState.asStateFlow()

    val isAuthenticated: Boolean
        get() = sessionManager.isAuthenticated()

    val currentUser: AuthUser?
        get() = sessionManager.getAuthenticatedUser()

    init {
        try {
            firebaseAuth?.addAuthStateListener { auth ->

                val fbUser = auth.currentUser

                if (fbUser != null) {

                    val appUser = AuthUser(
                        uid = fbUser.uid,
                        identifier =
                            fbUser.email
                                ?: fbUser.phoneNumber
                                ?: "user_${fbUser.uid.take(6)}",
                        isPhone =
                            fbUser.email == null &&
                                fbUser.phoneNumber != null,
                        displayName =
                            fbUser.displayName
                                ?: "Student Learner",
                        photoUrl =
                            fbUser.photoUrl?.toString(),
                        token = null,
                        sessionCreatedAt =
                            System.currentTimeMillis()
                    )

                    sessionManager.saveSession(appUser)

                    _authState.value =
                        AuthState.Authenticated(appUser)

                } else if (!sessionManager.isAuthenticated()) {

                    _authState.value =
                        AuthState.Unauthenticated
                }
            }

        } catch (e: Throwable) {

            Log.w(
                "AuthManager",
                "Firebase auth listener failed: ${e.message}"
            )
        }
    }

    fun isExternalProviderConfigured(): Boolean {

        return try {

            if (FirebaseApp.getApps(context).isEmpty()) {

                val defaultApp =
                    FirebaseApp.initializeApp(context)

                if (
                    defaultApp == null &&
                    FirebaseApp.getApps(context).isEmpty()
                ) {

                    try {

                        val options =
                            FirebaseOptions.Builder()
                                .setApplicationId(
                                    "1:682580183716:android:772bae14e8ea4ccca67431"
                                )
                                .setApiKey(
                                    "AIzaSyBNIGmaSzzBs1KoQpaUezSvAHrw5P0OCno"
                                )
                                .setProjectId(
                                    "computer-master-8f53d"
                                )
                                .setStorageBucket(
                                    "computer-master-8f53d.firebasestorage.app"
                                )
                                .setGcmSenderId(
                                    "682580183716"
                                )
                                .build()

                        FirebaseApp.initializeApp(
                            context,
                            options
                        )

                    } catch (e: Throwable) {

                        Log.w(
                            "AuthManager",
                            "Firebase initialization failed: ${e.message}"
                        )
                    }
                }
            }

            FirebaseApp.getApps(context).isNotEmpty()

        } catch (e: Throwable) {

            Log.w(
                "AuthManager",
                "Firebase configuration check failed: ${e.message}"
            )

            false
        }
    }

    suspend fun signInWithGoogle(
        activityContext: Context
    ) {

        _authState.value =
            AuthState.Authenticating

        if (!isExternalProviderConfigured()) {

            _authState.value =
                AuthState.ProviderConfigRequired(
                    title =
                        "Firebase Configuration Required",
                    description =
                        "Google Sign-In requires Firebase Authentication backend initialization.",
                    setupInstructions =
                        "Please verify google-services.json is present in app/ and FirebaseApp is initialized."
                )

            return
        }

        val auth = firebaseAuth

        if (auth == null) {

            _authState.value =
                AuthState.AuthError(
                    "Firebase Authentication is not available on this device."
                )

            return
        }

        val activity =
            activityContext.findActivity()

        val invocationContext =
            activity ?: activityContext

        var authStage =
            "Starting Google Sign-In"

        try {

            Log.d(
                "AUTH_DEBUG",
                "Google Sign-In started"
            )

            authStage =
                "Creating security nonce"

            val rawNonce =
                UUID.randomUUID().toString()

            val digest =
                MessageDigest
                    .getInstance("SHA-256")
                    .digest(
                        rawNonce.toByteArray()
                    )

            val hashedNonce =
                digest.joinToString("") {
                    "%02x".format(it)
                }

            authStage =
                "Creating Google Sign-In option"

            val googleSignInOption =
                GetSignInWithGoogleOption.Builder(
                    defaultWebClientId
                )
                    .setNonce(hashedNonce)
                    .build()

            authStage =
                "Creating credential request"

            val request =
                GetCredentialRequest.Builder()
                    .addCredentialOption(
                        googleSignInOption
                    )
                    .build()

            authStage =
                "Opening Google account picker"

            val credentialManager =
                CredentialManager.create(
                    invocationContext
                )

            val result =
                credentialManager.getCredential(
                    request = request,
                    context = invocationContext
                )

            authStage =
                "Reading Google credential"

            val credential =
                result.credential

            if (
                credential is CustomCredential &&
                credential.type ==
                    GoogleIdTokenCredential
                        .TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {

                authStage =
                    "Creating Google ID token"

                val googleCredential =
                    GoogleIdTokenCredential.createFrom(
                        credential.data
                    )

                val idToken =
                    googleCredential.idToken

                if (idToken.isBlank()) {

                    _authState.value =
                        AuthState.AuthError(
                            "Google did not return a valid ID token."
                        )

                    return
                }

                authStage =
                    "Creating Firebase credential"

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(
                        idToken,
                        null
                    )

                authStage =
                    "Signing in to Firebase"

                val authResult =
                    auth.signInWithCredential(
                        firebaseCredential
                    ).await()

                val fbUser =
                    authResult.user

                if (fbUser == null) {

                    _authState.value =
                        AuthState.AuthError(
                            "Firebase sign-in succeeded, but no user was returned."
                        )

                    return
                }

                authStage =
                    "Creating Computer Master session"

                val appUser =
                    AuthUser(
                        uid = fbUser.uid,

                        identifier =
                            fbUser.email
                                ?: googleCredential.id,

                        isPhone = false,

                        displayName =
                            fbUser.displayName
                                ?: googleCredential.displayName
                                ?: "Computer Master Student",

                        photoUrl =
                            fbUser.photoUrl?.toString()
                                ?: googleCredential
                                    .profilePictureUri
                                    ?.toString(),

                        token = idToken,

                        sessionCreatedAt =
                            System.currentTimeMillis()
                    )

                authStage =
                    "Saving user session"

                sessionManager.saveSession(
                    appUser
                )

                authStage =
                    "Setting authenticated state"

                _authState.value =
                    AuthState.Authenticated(
                        appUser
                    )

                Log.d(
                    "AUTH_DEBUG",
                    "Google Sign-In successful: ${appUser.uid}"
                )

            } else {

                _authState.value =
                    AuthState.AuthError(
                        "Google did not return a valid Google ID credential."
                    )
            }

        } catch (
            e: GetCredentialCancellationException
        ) {

            Log.e(
                "AUTH_DEBUG",
                "Google Sign-In cancelled at: $authStage",
                e
            )

            _authState.value =
                AuthState.AuthError(
                    "Google Sign-In was cancelled or interrupted.\n\n" +
                        "Stage: $authStage"
                )

        } catch (
            e: GetCredentialException
        ) {

            Log.e(
                "AUTH_DEBUG",
                "Credential Manager error at: $authStage",
                e
            )

            _authState.value =
                AuthState.AuthError(
                    "Google Sign-In failed.\n\n" +
                        "Stage: $authStage\n\n" +
                        "${e.message ?: "Credential Manager error"}"
                )

        } catch (e: Throwable) {

            Log.e(
                "AUTH_DEBUG",
                "Google Sign-In failed at: $authStage",
                e
            )

            _authState.value =
                AuthState.AuthError(
                    "Google Sign-In failed.\n\n" +
                        "Stage: $authStage\n\n" +
                        "${e::class.simpleName}: " +
                        "${e.message ?: "Unknown error"}"
                )
        }
    }

    fun resetState() {

        if (sessionManager.isAuthenticated()) {

            val user =
                sessionManager.getAuthenticatedUser()

            if (user != null) {

                _authState.value =
                    AuthState.Authenticated(user)

                return
            }
        }

        _authState.value =
            AuthState.Unauthenticated
    }

    fun logout() {

        try {

            firebaseAuth?.signOut()

        } catch (e: Throwable) {

            Log.w(
                "AuthManager",
                "Firebase logout failed: ${e.message}"
            )
        }

        try {

            sessionManager.clearSession()

        } catch (e: Throwable) {

            Log.w(
                "AuthManager",
                "Session clear failed: ${e.message}"
            )
        }

        _authState.value =
            AuthState.Unauthenticated
    }

    suspend fun clearGoogleCredentialState() {

        try {

            val credentialManager =
                CredentialManager.create(context)

            credentialManager.clearCredentialState(
                ClearCredentialStateRequest()
            )

        } catch (e: Throwable) {

            Log.w(
                "AuthManager",
                "Could not clear Google credential state: ${e.message}"
            )
        }
    }

    private fun Context.findActivity(): Activity? {

        var currentContext =
            this

        while (currentContext is ContextWrapper) {

            if (currentContext is Activity) {
                return currentContext
            }

            currentContext =
                currentContext.baseContext
        }

        return null
    }
}
