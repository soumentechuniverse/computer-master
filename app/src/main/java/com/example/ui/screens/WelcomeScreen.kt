package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.util.AppLanguage
import com.example.util.AppStrings
import com.example.util.LocalAppLanguage
import kotlinx.coroutines.delay

/**
 * Modern, responsive Welcome Screen for Computer Master.
 * Features:
 * - Prominently displays "Welcome to Computer Master" in selected language
 * - Prominently displays "Created by Soumen Mondal" preserving creator's name
 * - Multi-language support (English, বাংলা, हिन्दी)
 * - Subtle staggered entrance animations
 * - Responsive layout for phones, foldables, and tablets
 * - High accessibility & readable typography
 */
@Composable
fun WelcomeScreen(
  onGetStarted: () -> Unit,
  onLanguageChange: ((AppLanguage) -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val currentLanguage = LocalAppLanguage.current
  var isVisible by remember { mutableStateOf(false) }

  LaunchedEffect(Unit) {
    delay(100)
    isVisible = true
  }

  val scrollState = rememberScrollState()

  BoxWithConstraints(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(
            NavyDarkest,
            NavyCard.copy(alpha = 0.6f),
            NavyDarkest
          )
        )
      )
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("welcome_screen"),
    contentAlignment = Alignment.Center
  ) {
    val isTablet = maxWidth >= 600.dp

    AnimatedVisibility(
      visible = isVisible,
      enter = fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
          slideInVertically(
            initialOffsetY = { 60 },
            animationSpec = tween(600, easing = FastOutSlowInEasing)
          )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(scrollState)
          .padding(horizontal = if (isTablet) 36.dp else 22.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        // TOP & MAIN CONTENT CONTAINER
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 560.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Language selector bar at the top of welcome screen
          if (onLanguageChange != null) {
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(NavyCard)
                .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .testTag("welcome_language_selector"),
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              AppLanguage.entries.forEach { lang ->
                val isSelected = currentLanguage == lang
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) TechBluePrimary else Color.Transparent)
                    .clickable { onLanguageChange(lang) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .testTag("welcome_lang_${lang.code}"),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = lang.nativeName,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      fontSize = 12.sp
                    ),
                    color = if (isSelected) Color.White else TextSecondary
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))
          } else {
            Spacer(modifier = Modifier.height(16.dp))
          }

          // 1. BRAND LOGO EMBLEM
          Box(
            modifier = Modifier
              .size(80.dp)
              .clip(RoundedCornerShape(22.dp))
              .background(
                brush = Brush.linearGradient(
                  listOf(
                    TechBluePrimary.copy(alpha = 0.25f),
                    TechCyanAccent.copy(alpha = 0.12f)
                  )
                )
              )
              .border(
                1.5.dp,
                brush = Brush.linearGradient(
                  listOf(
                    TechCyanAccent.copy(alpha = 0.8f),
                    TechIndigo.copy(alpha = 0.4f)
                  )
                ),
                shape = RoundedCornerShape(22.dp)
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Computer,
              contentDescription = "Computer Master",
              tint = TechCyanAccent,
              modifier = Modifier.size(42.dp)
            )
          }

          Spacer(modifier = Modifier.height(20.dp))

          // 2. WELCOME TITLE (LOCALIZED)
          Text(
            text = AppStrings.welcomeTitle(currentLanguage),
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Black,
              fontSize = if (isTablet) 32.sp else 26.sp,
              letterSpacing = 0.2.sp,
              lineHeight = 34.sp
            ),
            color = TextPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("welcome_title_text")
          )

          Spacer(modifier = Modifier.height(10.dp))

          // 3. CREATOR BADGE (Prominently displaying "Created by Soumen Mondal" preserving creator's name)
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(24.dp))
              .background(NavyCardElevated)
              .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                  listOf(
                    TechCyanAccent.copy(alpha = 0.6f),
                    TechIndigo.copy(alpha = 0.5f)
                  )
                ),
                shape = RoundedCornerShape(24.dp)
              )
              .padding(horizontal = 16.dp, vertical = 7.dp)
              .testTag("creator_badge")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified Creator",
                tint = TechCyanAccent,
                modifier = Modifier.size(16.dp)
              )

              Text(
                text = AppStrings.welcomeCreatedBy(currentLanguage),
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  letterSpacing = 0.4.sp
                ),
                color = TextPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // 4. SUBTITLE DESCRIPTION (LOCALIZED)
          Text(
            text = AppStrings.welcomeSubtitle(currentLanguage),
            style = MaterialTheme.typography.bodyMedium.copy(
              fontSize = 14.sp,
              lineHeight = 21.sp,
              fontWeight = FontWeight.Normal
            ),
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
          )

          Spacer(modifier = Modifier.height(26.dp))

          // 5. FEATURE HIGHLIGHTS PILLARS (LOCALIZED)
          Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            FeatureHighlightRow(
              icon = Icons.Default.MenuBook,
              iconColor = TechCyanAccent,
              title = AppStrings.welcomeFeature1Title(currentLanguage),
              subtitle = AppStrings.welcomeFeature1Desc(currentLanguage)
            )

            FeatureHighlightRow(
              icon = Icons.Default.Quiz,
              iconColor = TechIndigo,
              title = AppStrings.welcomeFeature2Title(currentLanguage),
              subtitle = AppStrings.welcomeFeature2Desc(currentLanguage)
            )

            FeatureHighlightRow(
              icon = Icons.Default.AutoAwesome,
              iconColor = TechGreen,
              title = AppStrings.welcomeFeature3Title(currentLanguage),
              subtitle = AppStrings.welcomeFeature3Desc(currentLanguage)
            )
          }
        }

        // BOTTOM ACTION CONTAINER
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 560.dp)
            .padding(top = 28.dp, bottom = 12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Primary Action CTA Button (LOCALIZED)
          Button(
            onClick = onGetStarted,
            modifier = Modifier
              .fillMaxWidth()
              .height(54.dp)
              .testTag("get_started_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = TechBluePrimary,
              contentColor = Color.White
            )
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = AppStrings.getStarted(currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  letterSpacing = 0.3.sp
                )
              )
              Spacer(modifier = Modifier.width(8.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Subtle educational guarantee notice (LOCALIZED)
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = TechGreen.copy(alpha = 0.8f),
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = AppStrings.welcomeBadgeFree(currentLanguage),
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              ),
              color = TextTertiary
            )
          }
        }
      }
    }
  }
}

@Composable
private fun FeatureHighlightRow(
  icon: ImageVector,
  iconColor: Color,
  title: String,
  subtitle: String,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(
        listOf(
          iconColor.copy(alpha = 0.35f),
          NavyCardBorder.copy(alpha = 0.5f)
        )
      )
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 13.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(iconColor.copy(alpha = 0.15f))
          .border(1.dp, iconColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = iconColor,
          modifier = Modifier.size(22.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          ),
          color = TextPrimary
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 12.sp,
            lineHeight = 16.sp
          ),
          color = TextSecondary
        )
      }
    }
  }
}
