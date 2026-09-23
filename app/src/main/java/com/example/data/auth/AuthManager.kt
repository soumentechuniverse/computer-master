  suspend fun signInWithGoogle(activityContext: Context) {

    _authState.value = AuthState.Authenticating

    if (!isExternalProviderConfigured()) {
      _authState.value = AuthState.ProviderConfigRequired(
        title = "Firebase Configuration Required",
        description =
          "Google Sign-In requires Firebase Authentication backend initialization.",
        setupInstructions =
          "Please verify google-services.json is present in app/ and FirebaseApp is initialized."
      )
      return
    }

    val auth = firebaseAuth

    if (auth == null) {
      _authState.value = AuthState.AuthError(
        "Firebase Authentication is not available on this device."
      )
      return
    }

    val resolvedActivity: Activity? =
      activityContext.findActivity()

    val invocationContext: Context =
      resolvedActivity ?: activityContext

    var authStage = "Starting Google Sign-In"

    try {

      Log.d(
        "AUTH_DEBUG",
        "START Google Sign-In"
      )

      // STEP 1: Create nonce
      authStage = "Creating security nonce"

      val rawNonce = UUID.randomUUID().toString()

      val digest = MessageDigest
        .getInstance("SHA-256")
        .digest(rawNonce.toByteArray())

      val hashedNonce =
        digest.joinToString("") {
          "%02x".format(it)
        }

      Log.d(
        "AUTH_DEBUG",
        "STEP 1: Nonce created"
      )

      // STEP 2: Create Google Sign-In option
      authStage = "Creating Google Sign-In option"

      val googleSignInOption =
        GetSignInWithGoogleOption.Builder(
          defaultWebClientId
        )
          .setNonce(hashedNonce)
          .build()

      Log.d(
        "AUTH_DEBUG",
        "STEP 2: Google Sign-In option created"
      )

      // STEP 3: Create credential request
      authStage = "Creating Credential request"

      val request =
        GetCredentialRequest.Builder()
          .addCredentialOption(googleSignInOption)
          .build()

      Log.d(
        "AUTH_DEBUG",
        "STEP 3: Credential request created"
      )

      // STEP 4: Open Google account picker
      authStage = "Opening Google account picker"

      val result =
        CredentialManager
          .create(invocationContext)
          .getCredential(
            request = request,
            context = invocationContext
          )

      Log.d(
        "AUTH_DEBUG",
        "STEP 4: Google account picker returned"
      )

      // STEP 5: Read returned credential
      authStage = "Reading Google credential"

      val credential = result.credential

      Log.d(
        "AUTH_DEBUG",
        "STEP 5: Credential received type=${credential.type}"
      )

      if (
        credential is CustomCredential &&
        credential.type ==
        GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
      ) {

        // STEP 6: Create Google ID token credential
        authStage = "Creating Google ID token credential"

        val googleIdTokenCredential =
          try {

            GoogleIdTokenCredential.createFrom(
              credential.data
            )

          } catch (e: Exception) {

            Log.e(
              "AUTH_DEBUG",
              "STEP 6 FAILED: Could not create Google ID token",
              e
            )

            _authState.value =
              AuthState.AuthError(
                "Google Sign-In failed at Step 6.\n\n" +
                  "Could not read the Google account information.\n\n" +
                  "${e.message ?: "Unknown error"}"
              )

            return
          }

        Log.d(
          "AUTH_DEBUG",
          "STEP 6: Google ID token created successfully"
        )

        val idToken =
          googleIdTokenCredential.idToken

        Log.d(
          "AUTH_DEBUG",
          "STEP 6: ID token received"
        )

        // STEP 7: Create Firebase credential
        authStage = "Creating Firebase credential"

        val firebaseCredential =
          try {

            GoogleAuthProvider.getCredential(
              idToken,
              null
            )

          } catch (e: Exception) {

            Log.e(
              "AUTH_DEBUG",
              "STEP 7 FAILED: Could not create Firebase credential",
              e
            )

            _authState.value =
              AuthState.AuthError(
                "Google Sign-In failed at Step 7.\n\n" +
                  "Could not create the Firebase credential.\n\n" +
                  "${e.message ?: "Unknown error"}"
              )

            return
          }

        Log.d(
          "AUTH_DEBUG",
          "STEP 7: Firebase credential created"
        )

        // STEP 8: Sign in to Firebase
        authStage = "Signing in to Firebase"

        val authResult =
          try {

            auth.signInWithCredential(
              firebaseCredential
            ).await()

          } catch (e: Exception) {

            Log.e(
              "AUTH_DEBUG",
              "STEP 8 FAILED: Firebase sign-in failed",
              e
            )

            _authState.value =
              AuthState.AuthError(
                "Google Sign-In failed at Step 8.\n\n" +
                  "Firebase rejected the Google account.\n\n" +
                  "${e.message ?: "Unknown Firebase error"}"
              )

            return
          }

        Log.d(
          "AUTH_DEBUG",
          "STEP 8: Firebase sign-in successful"
        )

        // STEP 9: Get Firebase user
        authStage = "Reading Firebase user"

        val fbUser = authResult.user

        if (fbUser == null) {

          _authState.value =
            AuthState.AuthError(
              "Authentication succeeded, but Firebase did not return a user."
            )

          return
        }

        Log.d(
          "AUTH_DEBUG",
          "STEP 9: Firebase user received"
        )

        // STEP 10: Create Computer Master user
        authStage = "Creating Computer Master user session"

        val appUser = AuthUser(
          uid = fbUser.uid,
          identifier =
            fbUser.email
              ?: googleIdTokenCredential.id,
          isPhone = false,
          displayName =
            fbUser.displayName
              ?: googleIdTokenCredential.displayName
              ?: "Computer Master Student",
          photoUrl =
            fbUser.photoUrl?.toString()
              ?: googleIdTokenCredential
                .profilePictureUri
                ?.toString(),
          token = idToken,
          sessionCreatedAt =
            System.currentTimeMillis()
        )

        Log.d(
          "AUTH_DEBUG",
          "STEP 10: App user created"
        )

        // STEP 11: Save session
        authStage = "Saving user session"

        sessionManager.saveSession(appUser)

        Log.d(
          "AUTH_DEBUG",
          "STEP 11: Session saved"
        )

        // STEP 12: Set authenticated state
        authStage = "Setting Authenticated state"

        _authState.value =
          AuthState.Authenticated(appUser)

        Log.d(
          "AUTH_DEBUG",
          "STEP 12: Authenticated state SET uid=${appUser.uid}"
        )

      } else {

        Log.e(
          "AUTH_DEBUG",
          "Invalid Google credential type: ${credential.type}"
        )

        _authState.value =
          AuthState.AuthError(
            "Google did not return a valid Google ID credential.\n\n" +
              "Credential type: ${credential.type}"
          )
      }

    } catch (e: GetCredentialCancellationException) {

      Log.e(
        "AUTH_DEBUG",
        "Google Sign-In CANCELLED at stage: $authStage",
        e
      )

      // Do not silently reset the state.
      // Show the actual stage so the problem can be identified.

      _authState.value =
        AuthState.AuthError(
          "Google Sign-In was cancelled or interrupted.\n\n" +
            "Stage: $authStage\n\n" +
            "${e.message ?: "No additional information"}"
        )

    } catch (e: GetCredentialException) {

      Log.e(
        "AUTH_DEBUG",
        "Credential Manager FAILED at stage: $authStage",
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
        "Google Sign-In FAILED at stage: $authStage",
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
