package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.auth.AuthMode
import com.example.data.auth.AuthState
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel
import com.example.util.AppLanguage
import com.example.util.AppStrings
import kotlinx.coroutines.delay

/**
 * Authentication screen featuring Register and Login with real OTP verification architecture.
 * Strictly avoids fake OTPs, hardcoded verification bypasses, and plain-text secrets.
 */
@Composable
fun AuthScreen(
  viewModel: ComputerMasterViewModel,
  onAuthSuccess: () -> Unit,
  onBackToIntro: () -> Unit,
  modifier: Modifier = Modifier
) {
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  val authState by viewModel.authState.collectAsState()

  var selectedMode by remember { mutableStateOf(AuthMode.REGISTER) }
  var fullName by remember { mutableStateOf("") }
  var identifier by remember { mutableStateOf("") }
  var otpCode by remember { mutableStateOf("") }
  var activeVerificationId by remember { mutableStateOf("") }
  var isOtpStep by remember { mutableStateOf(false) }

  // Countdown for resend
  var resendTimer by remember { mutableIntStateOf(60) }
  var showProviderModal by remember { mutableStateOf<AuthState.ProviderConfigRequired?>(null) }
  var localErrorMessage by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(authState) {
    when (val state = authState) {
      is AuthState.Authenticated -> {
        onAuthSuccess()
      }
      is AuthState.OtpSent -> {
        isOtpStep = true
        activeVerificationId = state.verificationId
        resendTimer = state.resendCountdown
      }
      is AuthState.ProviderConfigRequired -> {
        showProviderModal = state
      }
      is AuthState.AuthError -> {
        localErrorMessage = state.message
      }
      else -> Unit
    }
  }

  // Timer countdown
  LaunchedEffect(isOtpStep, resendTimer) {
    if (isOtpStep && resendTimer > 0) {
      delay(1000)
      resendTimer -= 1
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(NavyDarkest, NavyDark, NavyDarkest)
        )
      )
      .testTag("auth_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Top Navigation / Back to 3D Intro
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = {
            if (isOtpStep) {
              isOtpStep = false
              otpCode = ""
              viewModel.resetAuthState()
            } else {
              onBackToIntro()
            }
          },
          modifier = Modifier.testTag("auth_back_btn")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = TextSecondary
          )
        }

        // Language Pill
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
          AppLanguage.entries.forEach { lang ->
            val isSelected = currentLanguage == lang
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) TechCyanAccent.copy(alpha = 0.2f) else Color.Transparent)
                .clickable { viewModel.setLanguage(lang) }
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = lang.symbol,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) TechCyanAccent else TextTertiary
                )
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Brand Logo & Header
      Box(
        modifier = Modifier
          .size(72.dp)
          .clip(RoundedCornerShape(18.dp))
          .background(
            brush = Brush.linearGradient(
              listOf(TechBluePrimary.copy(alpha = 0.3f), NavyCardElevated)
            )
          )
          .border(1.5.dp, TechCyanAccent.copy(alpha = 0.6f), RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Computer,
          contentDescription = "Computer Master",
          tint = TechCyanAccent,
          modifier = Modifier.size(38.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Computer Master",
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          letterSpacing = 0.5.sp
        ),
        color = TextPrimary
      )

      Text(
        text = "Created by Soumen Mondal",
        style = MaterialTheme.typography.labelMedium.copy(
          color = TechCyanAccent,
          fontWeight = FontWeight.SemiBold
        )
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = AppStrings.authSubtitle(currentLanguage),
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        color = TextSecondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(24.dp))

      if (!isOtpStep) {
        // TAB SELECTOR: REGISTER vs LOGIN
        TabRow(
          selectedTabIndex = if (selectedMode == AuthMode.REGISTER) 0 else 1,
          containerColor = NavyCard,
          contentColor = TechCyanAccent,
          indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
              Modifier.tabIndicatorOffset(tabPositions[if (selectedMode == AuthMode.REGISTER) 0 else 1]),
              color = TechCyanAccent,
              height = 3.dp
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
        ) {
          Tab(
            selected = selectedMode == AuthMode.REGISTER,
            onClick = {
              selectedMode = AuthMode.REGISTER
              localErrorMessage = null
            },
            text = {
              Text(
                text = AppStrings.authRegisterTab(currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = if (selectedMode == AuthMode.REGISTER) TechCyanAccent else TextSecondary
              )
            },
            modifier = Modifier.testTag("auth_tab_register")
          )

          Tab(
            selected = selectedMode == AuthMode.LOGIN,
            onClick = {
              selectedMode = AuthMode.LOGIN
              localErrorMessage = null
            },
            text = {
              Text(
                text = AppStrings.authLoginTab(currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = if (selectedMode == AuthMode.LOGIN) TechCyanAccent else TextSecondary
              )
            },
            modifier = Modifier.testTag("auth_tab_login")
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Input Card
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
            .padding(20.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Full name field only in register mode
            if (selectedMode == AuthMode.REGISTER) {
              OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text(AppStrings.authFullName(currentLanguage)) },
                leadingIcon = {
                  Icon(Icons.Default.Person, contentDescription = null, tint = TechCyanAccent)
                },
                singleLine = true,
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("input_full_name"),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = TechCyanAccent,
                  unfocusedBorderColor = NavyCardBorder,
                  focusedTextColor = TextPrimary,
                  unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
              )
            }

            // Phone or Email Field
            OutlinedTextField(
              value = identifier,
              onValueChange = { identifier = it },
              label = { Text(AppStrings.authPhoneOrEmail(currentLanguage)) },
              placeholder = { Text("+91 9876543210 or user@example.com") },
              leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = null, tint = TechCyanAccent)
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
              ),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_identifier"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TechCyanAccent,
                unfocusedBorderColor = NavyCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              ),
              shape = RoundedCornerShape(12.dp)
            )

            localErrorMessage?.let { err ->
              Text(
                text = err,
                style = MaterialTheme.typography.bodySmall.copy(color = TechRed),
                modifier = Modifier.padding(horizontal = 4.dp)
              )
            }

            Button(
              onClick = {
                localErrorMessage = null
                if (identifier.isBlank()) {
                  localErrorMessage = "Please enter a valid phone number or email address."
                  return@Button
                }
                viewModel.requestAuthOtp(
                  identifier = identifier,
                  isRegister = selectedMode == AuthMode.REGISTER,
                  displayName = if (selectedMode == AuthMode.REGISTER) fullName.ifBlank { null } else null
                )
              },
              enabled = authState !is AuthState.SendingOtp,
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_send_otp"),
              colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
              shape = RoundedCornerShape(12.dp)
            ) {
              if (authState is AuthState.SendingOtp) {
                CircularProgressIndicator(
                  modifier = Modifier.size(20.dp),
                  color = TechCyanAccent,
                  strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sending OTP...")
              } else {
                Icon(Icons.Default.Pin, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = AppStrings.authSendOtp(currentLanguage),
                  style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
              }
            }
          }
        }
      } else {
        // STEP 2: OTP VERIFICATION VIEW
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
            .padding(20.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(TechCyanAccent.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.VpnKey,
                  contentDescription = null,
                  tint = TechCyanAccent,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(
                  text = AppStrings.authOtpVerification(currentLanguage),
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = TextPrimary
                )
                Text(
                  text = "Sent to $identifier",
                  style = MaterialTheme.typography.bodySmall,
                  color = TextSecondary
                )
              }
            }

            OutlinedTextField(
              value = otpCode,
              onValueChange = { if (it.length <= 6) otpCode = it },
              label = { Text(AppStrings.authEnterOtp(currentLanguage)) },
              placeholder = { Text("123456") },
              leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = TechCyanAccent)
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_otp_code"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TechCyanAccent,
                unfocusedBorderColor = NavyCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              ),
              shape = RoundedCornerShape(12.dp)
            )

            localErrorMessage?.let { err ->
              Text(
                text = err,
                style = MaterialTheme.typography.bodySmall.copy(color = TechRed),
                modifier = Modifier.padding(horizontal = 4.dp)
              )
            }

            Button(
              onClick = {
                localErrorMessage = null
                if (otpCode.trim().length < 6) {
                  localErrorMessage = "Please enter the 6-digit verification code."
                  return@Button
                }
                viewModel.verifyAuthOtp(
                  verificationId = activeVerificationId,
                  otpCode = otpCode,
                  targetIdentifier = identifier,
                  isRegister = selectedMode == AuthMode.REGISTER,
                  displayName = fullName.ifBlank { null }
                )
              },
              enabled = authState !is AuthState.VerifyingOtp,
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_verify_otp"),
              colors = ButtonDefaults.buttonColors(containerColor = TechCyanAccent),
              shape = RoundedCornerShape(12.dp)
            ) {
              if (authState is AuthState.VerifyingOtp) {
                CircularProgressIndicator(
                  modifier = Modifier.size(20.dp),
                  color = NavyDarkest,
                  strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Verifying...", color = NavyDarkest)
              } else {
                Text(
                  text = AppStrings.authVerifyAndContinue(currentLanguage),
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = NavyDarkest
                  )
                )
              }
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = if (resendTimer > 0) "Resend in ${resendTimer}s" else "Didn't receive code?",
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiary
              )

              if (resendTimer == 0) {
                OutlinedButton(
                  onClick = {
                    resendTimer = 60
                    viewModel.requestAuthOtp(
                      identifier = identifier,
                      isRegister = selectedMode == AuthMode.REGISTER,
                      displayName = fullName.ifBlank { null }
                    )
                  },
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = TechCyanAccent),
                  modifier = Modifier.testTag("btn_resend_otp")
                ) {
                  Text(AppStrings.authResendOtp(currentLanguage), fontSize = 12.sp)
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Security Notice Card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(NavyCard.copy(alpha = 0.6f))
          .border(1.dp, NavyCardBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
          .padding(14.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            tint = TechGreen,
            modifier = Modifier.size(20.dp)
          )
          Text(
            text = AppStrings.authSecurityNote(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              color = TextSecondary,
              lineHeight = 16.sp
            )
          )
        }
      }
    }

    // Modal when external authentication provider is not configured
    showProviderModal?.let { modal ->
      AlertDialog(
        onDismissRequest = {
          showProviderModal = null
          viewModel.resetAuthState()
        },
        title = {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = TechAmber)
            Text(modal.title, style = MaterialTheme.typography.titleMedium)
          }
        },
        text = {
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(modal.description, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(NavyDarkest)
                .border(1.dp, NavyCardBorder, RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Text(
                modal.setupInstructions,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.sp,
                  color = TechCyanAccent,
                  lineHeight = 16.sp
                )
              )
            }
            Text(
              "Security Policy: In accordance with security mandates, Computer Master strictly prohibits dummy bypasses or fake hardcoded OTPs.",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, color = TextTertiary)
            )
          }
        },
        confirmButton = {
          Button(
            onClick = {
              showProviderModal = null
              viewModel.resetAuthState()
            },
            colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
          ) {
            Text("Understand")
          }
        },
        containerColor = NavyCardElevated
      )
    }
  }
}
