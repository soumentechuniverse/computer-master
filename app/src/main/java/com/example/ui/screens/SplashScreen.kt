package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

/**
 * Modern 3D-style animated splash screen for Computer Master.
 * Features a dynamic 3D perspective holographic CPU core with neon circuit traces,
 * rotational orbital data rings, and smooth entrance transitions (~2.4s total).
 */
@Composable
fun SplashScreen(
  onSplashFinished: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Main entrance animation progress (0f -> 1f)
  val entranceProgress = remember { Animatable(0f) }
  val logoScale = remember { Animatable(0.7f) }
  val logoAlpha = remember { Animatable(0f) }

  // Continuous infinite 3D rotations for tech orbital rings and circuits
  val infiniteTransition = rememberInfiniteTransition(label = "tech_spin")
  val rotation3DY by infiniteTransition.animateFloat(
    initialValue = -25f,
    targetValue = 25f,
    animationSpec = infiniteRepeatable(
      animation = tween(2200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "rotation3DY"
  )

  val rotation3DX by infiniteTransition.animateFloat(
    initialValue = 18f,
    targetValue = -18f,
    animationSpec = infiniteRepeatable(
      animation = tween(1900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "rotation3DX"
  )

  val ringOrbitAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "ringOrbitAngle"
  )

  val pulseGlow by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseGlow"
  )

  LaunchedEffect(Unit) {
    // Stage 1: Reveal logo and scale up smoothly
    logoAlpha.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
    logoScale.animateTo(1f, animationSpec = tween(700, easing = FastOutSlowInEasing))
    entranceProgress.animateTo(1f, animationSpec = tween(1600, easing = FastOutSlowInEasing))

    // Hold briefly to complete ~2.4s total duration
    delay(200)
    onSplashFinished()
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.radialGradient(
          colors = listOf(
            NavyCard.copy(alpha = 0.85f),
            NavyDarkest
          ),
          radius = 1200f
        )
      )
      .testTag("splash_screen"),
    contentAlignment = Alignment.Center
  ) {
    // Ambient background cyber grid particles
    Canvas(modifier = Modifier.fillMaxSize()) {
      val gridSpacing = 48.dp.toPx()
      val dotColor = TechCyanAccent.copy(alpha = 0.07f * pulseGlow)
      var x = 0f
      while (x < size.width) {
        var y = 0f
        while (y < size.height) {
          drawCircle(
            color = dotColor,
            radius = 1.5.dp.toPx(),
            center = Offset(x, y)
          )
          y += gridSpacing
        }
        x += gridSpacing
      }
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // 3D-STYLE ROTATING HOLOGRAPHIC TECH LOGO
      Box(
        modifier = Modifier
          .size(200.dp)
          .graphicsLayer {
            // Apply 3D perspective transformation
            rotationX = rotation3DX * entranceProgress.value
            rotationY = rotation3DY * entranceProgress.value
            cameraDistance = 16f * density
            scaleX = logoScale.value
            scaleY = logoScale.value
            alpha = logoAlpha.value
          },
        contentAlignment = Alignment.Center
      ) {
        // Glowing halo behind 3D core
        Box(
          modifier = Modifier
            .size(170.dp)
            .background(
              brush = Brush.radialGradient(
                colors = listOf(
                  TechCyanAccent.copy(alpha = 0.35f * pulseGlow),
                  TechIndigo.copy(alpha = 0.15f * pulseGlow),
                  Color.Transparent
                )
              ),
              shape = CircleShape
            )
        )

        // Custom Canvas for 3D Orbital Rings & Hologram Circuit Traces
        Canvas(modifier = Modifier.size(190.dp)) {
          val centerOffset = Offset(size.width / 2f, size.height / 2f)
          val outerRadius = size.width / 2f - 10.dp.toPx()

          // Orbital Ring 1 (Cyan)
          drawCircle(
            brush = Brush.sweepGradient(
              listOf(
                TechCyanAccent.copy(alpha = 0.8f),
                TechBluePrimary.copy(alpha = 0.2f),
                TechCyanAccent.copy(alpha = 0.8f)
              )
            ),
            radius = outerRadius,
            center = centerOffset,
            style = Stroke(width = 2.dp.toPx())
          )

          // Orbiting data node 1
          val rad1 = Math.toRadians(ringOrbitAngle.toDouble())
          val node1X = centerOffset.x + (outerRadius * cos(rad1)).toFloat()
          val node1Y = centerOffset.y + (outerRadius * sin(rad1)).toFloat()
          drawCircle(
            color = TechCyanAccent,
            radius = 4.dp.toPx(),
            center = Offset(node1X, node1Y)
          )

          // Inner Concentric Tech Ring 2 (Indigo)
          val innerRadius = outerRadius - 16.dp.toPx()
          drawCircle(
            color = TechIndigo.copy(alpha = 0.45f),
            radius = innerRadius,
            center = centerOffset,
            style = Stroke(
              width = 1.5.dp.toPx(),
              cap = StrokeCap.Round
            )
          )

          // Orbiting data node 2 (counter-clockwise)
          val rad2 = Math.toRadians(-ringOrbitAngle.toDouble() * 1.4)
          val node2X = centerOffset.x + (innerRadius * cos(rad2)).toFloat()
          val node2Y = centerOffset.y + (innerRadius * sin(rad2)).toFloat()
          drawCircle(
            color = TechBluePrimary,
            radius = 3.dp.toPx(),
            center = Offset(node2X, node2Y)
          )

          // Microchip circuit corner brackets
          val bracketSize = 22.dp.toPx()
          val bracketStroke = 2.5.dp.toPx()
          val bracketMargin = 38.dp.toPx()

          // Top-Left bracket
          drawLine(
            color = TechCyanAccent.copy(alpha = 0.7f),
            start = Offset(bracketMargin, bracketMargin + bracketSize),
            end = Offset(bracketMargin, bracketMargin),
            strokeWidth = bracketStroke
          )
          drawLine(
            color = TechCyanAccent.copy(alpha = 0.7f),
            start = Offset(bracketMargin, bracketMargin),
            end = Offset(bracketMargin + bracketSize, bracketMargin),
            strokeWidth = bracketStroke
          )

          // Bottom-Right bracket
          val rightEdge = size.width - bracketMargin
          val bottomEdge = size.height - bracketMargin
          drawLine(
            color = TechCyanAccent.copy(alpha = 0.7f),
            start = Offset(rightEdge, bottomEdge - bracketSize),
            end = Offset(rightEdge, bottomEdge),
            strokeWidth = bracketStroke
          )
          drawLine(
            color = TechCyanAccent.copy(alpha = 0.7f),
            start = Offset(rightEdge - bracketSize, bottomEdge),
            end = Offset(rightEdge, bottomEdge),
            strokeWidth = bracketStroke
          )
        }

        // Central 3D Layered Microprocessor Core
        Box(
          modifier = Modifier
            .size(92.dp)
            .graphicsLayer {
              // Subtle opposite 3D tilt for depth layering effect
              rotationX = -rotation3DX * 0.4f
              rotationY = -rotation3DY * 0.4f
              shadowElevation = 16.dp.toPx()
            }
            .clip(RoundedCornerShape(22.dp))
            .background(
              brush = Brush.linearGradient(
                colors = listOf(
                  NavyCardElevated,
                  NavyDarkest
                )
              )
            )
            .border(
              width = 2.dp,
              brush = Brush.linearGradient(
                colors = listOf(
                  TechCyanAccent,
                  TechIndigo
                )
              ),
              shape = RoundedCornerShape(22.dp)
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Computer,
            contentDescription = "Computer Master Emblem",
            tint = TechCyanAccent,
            modifier = Modifier.size(46.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // APP TITLE & BRANDING (With smooth staggered reveal)
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.alpha(logoAlpha.value)
      ) {
        // Futuristic Tech Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(TechCyanAccent.copy(alpha = 0.12f))
            .border(1.dp, TechCyanAccent.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 5.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .background(TechGreen, CircleShape)
            )
            Text(
              text = "NEXT-GEN COMPUTING ACADEMY",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.5.sp,
                fontSize = 10.sp,
                color = TechCyanAccent
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Main Title
        Text(
          text = "Computer Master",
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Black,
            fontSize = 32.sp,
            letterSpacing = 0.5.sp
          ),
          color = TextPrimary,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "Master Every Byte, Bit & Algorithm",
          style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 13.sp,
            letterSpacing = 0.3.sp
          ),
          color = TextSecondary,
          textAlign = TextAlign.Center
        )
      }

      Spacer(modifier = Modifier.height(42.dp))

      // SYSTEM INITIALIZATION STATUS & PROGRESS BAR
      Column(
        modifier = Modifier
          .fillMaxWidth(0.75f)
          .alpha(logoAlpha.value),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "INITIALIZING CORE ENGINE",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp,
              fontSize = 9.sp
            ),
            color = TechCyanAccent.copy(alpha = 0.85f)
          )
          Text(
            text = "${(entranceProgress.value * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 9.sp
            ),
            color = TechCyanAccent
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cyber Progress Bar
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(NavyCard)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(fraction = entranceProgress.value)
              .height(4.dp)
              .clip(RoundedCornerShape(2.dp))
              .background(
                brush = Brush.horizontalGradient(
                  colors = listOf(
                    TechBluePrimary,
                    TechCyanAccent
                  )
                )
              )
          )
        }
      }
    }
  }
}
