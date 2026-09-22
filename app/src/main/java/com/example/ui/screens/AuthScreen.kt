package com.example.ui.screens

import android.app.Activity
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.auth.AuthState
import com.example.ui.components.GoogleLogoIcon
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

/**
 * Modern Authentication screen for Computer Master.
 * Replaces legacy OTP flows with official Firebase Google Sign-In via Android Credential Manager.
 */
@Composable
fun AuthScreen(
  viewModel: ComputerMasterViewModel,
  onAuthSuccess: () -> Unit,
  onBackToIntro: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  val authState by viewModel.authState.collectAsState()

  var showProviderModal by remember { mutableStateOf<AuthState.ProviderConfigRequired?>(null) }
  var localErrorMessage by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(authState) {
    when (val state = authState) {
      is AuthState.Authenticated -> {
        onAuthSuccess()
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
          onClick = onBackToIntro,
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

      Spacer(modifier = Modifier.height(20.dp))

      // Brand Logo & Header
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(RoundedCornerShape(22.dp))
          .background(
            brush = Brush.linearGradient(
              listOf(TechBluePrimary.copy(alpha = 0.35f), NavyCardElevated)
            )
          )
          .border(1.5.dp, TechCyanAccent.copy(alpha = 0.7f), RoundedCornerShape(22.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Computer,
          contentDescription = "Computer Master",
          tint = TechCyanAccent,
          modifier = Modifier.size(42.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

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

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = AppStrings.authSubtitle(currentLanguage),
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 18.sp),
        color = TextSecondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(32.dp))

      // Google Sign-In Action Card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
          .padding(24.dp)
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Feature highlights
          Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            AuthBenefitRow(
              icon = Icons.Default.Shield,
              title = "Secure Firebase Authentication",
              subtitle = "Verified with Google Identity Services"
            )
            AuthBenefitRow(
              icon = Icons.Default.CheckCircle,
              title = "Instant Progress Sync",
              subtitle = "Preserve lessons, quizzes & XP across devices"
            )
            AuthBenefitRow(
              icon = Icons.Default.Lock,
              title = "Privacy Protected",
              subtitle = "Zero tracking • No passwords stored"
            )
          }

          Spacer(modifier = Modifier.height(24.dp))

          localErrorMessage?.let { err ->
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(TechRed.copy(alpha = 0.15f))
                .border(1.dp, TechRed.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                .padding(12.dp)
            ) {
              Text(
                text = err,
                style = MaterialTheme.typography.bodySmall.copy(color = TechRed, fontSize = 12.sp)
              )
            }
            Spacer(modifier = Modifier.height(16.dp))
          }

          // Polished "Continue with Google" Button
          Button(
            onClick = {
              localErrorMessage = null
              viewModel.signInWithGoogle(context)
            },
            enabled = authState !is AuthState.Authenticating,
            modifier = Modifier
              .fillMaxWidth()
              .height(54.dp)
              .testTag("btn_continue_with_google"),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color.White,
              contentColor = Color(0xFF1F2937),
              disabledContainerColor = Color.White.copy(alpha = 0.7f),
              disabledContentColor = Color(0xFF1F2937).copy(alpha = 0.7f)
            ),
            shape = RoundedCornerShape(14.dp),
            elevation = ButtonDefaults.buttonElevation(
              defaultElevation = 2.dp,
              pressedElevation = 4.dp
            )
          ) {
            if (authState is AuthState.Authenticating) {
              CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = TechBluePrimary,
                strokeWidth = 2.5.dp
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = AppStrings.signingInWithGoogle(currentLanguage),
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = Color(0xFF1F2937)
                )
              )
            } else {
              GoogleLogoIcon(modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(12.dp))
              Text(
                text = AppStrings.continueWithGoogle(currentLanguage),
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  letterSpacing = 0.2.sp,
                  color = Color(0xFF1F2937)
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = AppStrings.googleAuthBenefit(currentLanguage),
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              color = TextTertiary
            ),
            textAlign = TextAlign.Center
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

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
            text = "Official Firebase Authentication: Industry-standard OAuth 2.0 and Credential Manager token exchange.",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              color = TextSecondary,
              lineHeight = 16.sp
            )
          )
        }
      }
    }

    // Modal when external provider config is needed
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
            Text(modal.title, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
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
            Text("OK")
          }
        },
        containerColor = NavyCardElevated
      )
    }
  }
}

@Composable
private fun AuthBenefitRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  subtitle: String
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Box(
      modifier = Modifier
        .size(36.dp)
        .clip(CircleShape)
        .background(TechBluePrimary.copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = TechCyanAccent,
        modifier = Modifier.size(18.dp)
      )
    }
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = TextPrimary
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = TextSecondary
      )
    }
  }
}
