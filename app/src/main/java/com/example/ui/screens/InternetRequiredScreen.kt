package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppLanguage
import com.example.util.AppStrings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Screen displayed when an active internet connection is required but missing.
 * Strict requirement: does not allow bypassing or viewing main app content offline.
 */
@Composable
fun InternetRequiredScreen(
  currentLanguage: AppLanguage,
  onRetry: () -> Boolean,
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  var isChecking by remember { mutableStateOf(false) }
  var connectionFailedNotice by remember { mutableStateOf(false) }

  val infiniteTransition = rememberInfiniteTransition(label = "signal_pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.35f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.radialGradient(
          colors = listOf(NavyCard.copy(alpha = 0.85f), NavyDarkest),
          radius = 1200f
        )
      )
      .testTag("internet_required_screen"),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 28.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Offline Animated Radar Graphic
      Box(
        modifier = Modifier.size(130.dp),
        contentAlignment = Alignment.Center
      ) {
        // Outer pulsing ring
        Box(
          modifier = Modifier
            .size(130.dp)
            .border(
              width = 2.dp,
              color = TechRed.copy(alpha = pulseAlpha * 0.5f),
              shape = CircleShape
            )
        )
        // Mid ring
        Box(
          modifier = Modifier
            .size(96.dp)
            .border(
              width = 2.dp,
              color = TechAmber.copy(alpha = pulseAlpha * 0.7f),
              shape = CircleShape
            )
        )
        // Core icon
        Box(
          modifier = Modifier
            .size(68.dp)
            .clip(CircleShape)
            .background(NavyCardElevated)
            .border(1.5.dp, TechRed.copy(alpha = 0.8f), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.WifiOff,
            contentDescription = "No Internet Connection",
            tint = TechRed,
            modifier = Modifier.size(36.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(26.dp))

      // Requirement Title
      Text(
        text = AppStrings.internetRequiredTitle(currentLanguage),
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 22.sp
        ),
        color = TextPrimary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Localized Mandate Description
      Text(
        text = AppStrings.internetRequiredMessage(currentLanguage),
        style = MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.Medium,
          fontSize = 15.sp,
          lineHeight = 22.sp
        ),
        color = TextSecondary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = AppStrings.internetOfflineSubtext(currentLanguage),
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 12.sp,
          color = TechAmber.copy(alpha = 0.9f)
        ),
        textAlign = TextAlign.Center
      )

      if (connectionFailedNotice) {
        Spacer(modifier = Modifier.height(14.dp))
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(TechRed.copy(alpha = 0.12f))
            .border(1.dp, TechRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
          Text(
            text = "Connection test failed. Still offline.",
            style = MaterialTheme.typography.labelSmall.copy(
              color = TechRed,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(32.dp))

      // Retry Button
      Button(
        onClick = {
          if (!isChecking) {
            isChecking = true
            connectionFailedNotice = false
            coroutineScope.launch {
              delay(600) // Brief delay for network scan feel
              val isNowOnline = onRetry()
              isChecking = false
              if (!isNowOnline) {
                connectionFailedNotice = true
              }
            }
          }
        },
        enabled = !isChecking,
        modifier = Modifier
          .fillMaxWidth(0.8f)
          .height(50.dp)
          .testTag("btn_retry_internet"),
        colors = ButtonDefaults.buttonColors(
          containerColor = TechBluePrimary,
          contentColor = TextPrimary
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        if (isChecking) {
          CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            strokeWidth = 2.dp,
            color = TechCyanAccent
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Checking...",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        } else {
          Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = AppStrings.internetRetryBtn(currentLanguage),
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          )
        }
      }
    }
  }
}
