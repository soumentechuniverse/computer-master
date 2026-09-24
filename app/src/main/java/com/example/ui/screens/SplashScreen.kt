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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.NavyDark
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

/**
 * Rebuilt Premium Computer Master Landing Screen (STEP 3).
 *
 * Exclusively contains:
 * 1. Premium Animated Educational Visual (3D-style white laptop + white open educational book
 *    with warm golden knowledge rays and soft blue tech particles).
 * 2. Creator Badge: Exactly "Created & Published by Soumen Mondal" with gentle pulse and glow.
 * 3. App Title: "Computer Master" (large premium typography).
 * 4. Subtitle: "Master Every Byte, Bit & Algorithm".
 * 5. Single Primary Button: "GET STARTED →" (with rounded corners, premium blue treatment & glow).
 *
 * Absolutely nothing else appears underneath the button.
 */
@Composable
fun SplashScreen(
  onGetStarted: () -> Unit,
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()

  // Master timeline animation clock (0 to 10 seconds)
  val animSeconds = remember { Animatable(0f) }

  LaunchedEffect(Unit) {
    animSeconds.animateTo(
      targetValue = 10f,
      animationSpec = tween(durationMillis = 10000, easing = LinearEasing)
    )
  }

  // Continuous subtle ambient animations for post-animation floating/breathing
  val infiniteTransition = rememberInfiniteTransition(label = "ambient_anim")

  val floatingOffset by infiniteTransition.animateFloat(
    initialValue = -5f,
    targetValue = 5f,
    animationSpec = infiniteRepeatable(
      animation = tween(2600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "floatingOffset"
  )

  val breathingTilt by infiniteTransition.animateFloat(
    initialValue = -2.5f,
    targetValue = 2.5f,
    animationSpec = infiniteRepeatable(
      animation = tween(3400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "breathingTilt"
  )

  val goldenRayWave by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "goldenRayWave"
  )

  val creatorPulse by infiniteTransition.animateFloat(
    initialValue = 0.98f,
    targetValue = 1.02f,
    animationSpec = infiniteRepeatable(
      animation = tween(1500, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "creatorPulse"
  )

  val buttonGlowPulse by infiniteTransition.animateFloat(
    initialValue = 0.45f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(1600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "buttonGlowPulse"
  )

  // Timeline progress values based on animSeconds (0f to 10f)
  val time = animSeconds.value

  // 0–2 seconds: Dark navy background appears smoothly, subtle particles start moving
  val bgAlpha = (time / 2f).coerceIn(0f, 1f)

  // 2–5 seconds: White laptop & book appear with 3D scale/fade, book opens smoothly
  val hardwareProgress = ((time - 2f) / 3f).coerceIn(0f, 1f)
  val laptopScale = 0.72f + (0.28f * hardwareProgress)
  val laptopAlpha = hardwareProgress
  val bookOpenProgress = hardwareProgress

  // 5–8 seconds: Warm golden knowledge light rises from book, particles gently move upward, circuit elements appear
  val knowledgeProgress = ((time - 5f) / 3f).coerceIn(0f, 1f)
  val goldenLightAlpha = knowledgeProgress
  val circuitAlpha = knowledgeProgress

  // 8–10 seconds: Creator badge animates in, Computer Master title fades/slides in, subtitle appears, GET STARTED button appears last
  val creatorProgress = ((time - 8.0f) / 0.8f).coerceIn(0f, 1f)
  val creatorAlpha = creatorProgress
  val creatorOffsetY = 14.dp * (1f - creatorProgress)

  val titleProgress = ((time - 8.3f) / 0.8f).coerceIn(0f, 1f)
  val titleAlpha = titleProgress
  val titleOffsetY = 16.dp * (1f - titleProgress)

  val subtitleProgress = ((time - 8.7f) / 0.7f).coerceIn(0f, 1f)
  val subtitleAlpha = subtitleProgress

  val buttonProgress = ((time - 9.1f) / 0.9f).coerceIn(0f, 1f)
  val buttonAlpha = buttonProgress
  val buttonScale = 0.85f + (0.15f * buttonProgress)

  // Deep dark navy / royal blue background
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.radialGradient(
          colors = listOf(
            NavyDark,
            NavyDarkest
          ),
          radius = 1100f
        )
      )
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
      ) {
        // Tapping background allows accelerating intro animation to end smoothly
        if (animSeconds.value < 10f) {
          coroutineScope.launch {
            animSeconds.snapTo(10f)
          }
        }
      }
      .testTag("splash_screen"),
    contentAlignment = Alignment.Center
  ) {
    // Ambient subtle floating background stars/particles
    Canvas(modifier = Modifier.fillMaxSize()) {
      val dotColor = TechCyanAccent.copy(alpha = 0.1f * bgAlpha)
      val spacing = 52.dp.toPx()
      var x = 0f
      while (x < size.width) {
        var y = 0f
        while (y < size.height) {
          drawCircle(
            color = dotColor,
            radius = 1.2.dp.toPx(),
            center = Offset(x, y)
          )
          y += spacing
        }
        x += spacing
      }
    }

    // Centered vertical layout for the single focused landing view
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {

      // ==============================================================
      // 1. PREMIUM ANIMATED EDUCATIONAL VISUAL (Computer + Education + Knowledge)
      // ==============================================================
      Box(
        modifier = Modifier
          .size(280.dp, 210.dp)
          .graphicsLayer {
            translationY = floatingOffset
            rotationZ = breathingTilt * 0.4f
          },
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          drawEducationalTechVisual(
            laptopScale = laptopScale,
            laptopAlpha = laptopAlpha,
            bookOpenProgress = bookOpenProgress,
            goldenLightAlpha = goldenLightAlpha,
            circuitAlpha = circuitAlpha,
            rayWave = goldenRayWave
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // ==============================================================
      // 2. CREATOR BADGE: Exactly "Created & Published by Soumen Mondal"
      // ==============================================================
      Box(
        modifier = Modifier
          .graphicsLayer {
            alpha = creatorAlpha
            translationY = creatorOffsetY.toPx()
            scaleX = creatorPulse
            scaleY = creatorPulse
          }
          .clip(RoundedCornerShape(24.dp))
          .background(
            brush = Brush.horizontalGradient(
              listOf(
                Color(0xFF0F172A).copy(alpha = 0.92f),
                Color(0xFF1E293B).copy(alpha = 0.92f)
              )
            )
          )
          .border(
            width = 1.2.dp,
            brush = Brush.horizontalGradient(
              listOf(
                Color(0xFFFFD54F).copy(alpha = 0.65f),
                TechCyanAccent.copy(alpha = 0.75f),
                Color(0xFFFFD54F).copy(alpha = 0.65f)
              )
            ),
            shape = RoundedCornerShape(24.dp)
          )
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .testTag("creator_badge")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Warm golden animated indicator
          Box(
            modifier = Modifier
              .size(7.dp)
              .background(Color(0xFFFFD54F), CircleShape)
          )

          Text(
            text = "Created & Published by Soumen Mondal",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.7.sp,
              fontSize = 12.sp,
              color = Color(0xFFF8FAFC)
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // ==============================================================
      // 3. APP TITLE: "Computer Master" (Large Premium Typography)
      // ==============================================================
      Text(
        text = "Computer Master",
        style = MaterialTheme.typography.headlineLarge.copy(
          fontWeight = FontWeight.Black,
          fontSize = 36.sp,
          letterSpacing = 0.8.sp,
          color = Color.White
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
          .graphicsLayer {
            alpha = titleAlpha
            translationY = titleOffsetY.toPx()
          }
      )

      Spacer(modifier = Modifier.height(6.dp))

      // ==============================================================
      // 4. SUBTITLE: "Master Every Byte, Bit & Algorithm"
      // ==============================================================
      Text(
        text = "Master Every Byte, Bit & Algorithm",
        style = MaterialTheme.typography.bodyMedium.copy(
          fontSize = 14.sp,
          letterSpacing = 0.4.sp,
          fontWeight = FontWeight.Medium
        ),
        color = TechCyanAccent.copy(alpha = 0.95f),
        textAlign = TextAlign.Center,
        modifier = Modifier.graphicsLayer {
          alpha = subtitleAlpha
        }
      )

      Spacer(modifier = Modifier.height(34.dp))

      // ==============================================================
      // 5. ONLY ONE BUTTON: "GET STARTED →"
      // (ABSOLUTELY NOTHING ELSE MAY APPEAR UNDERNEATH THIS BUTTON)
      // ==============================================================
      Box(
        modifier = Modifier
          .fillMaxWidth(0.85f)
          .graphicsLayer {
            alpha = buttonAlpha
            scaleX = buttonScale
            scaleY = buttonScale
          },
        contentAlignment = Alignment.Center
      ) {
        // Subtle animated glow aura around the button
        Box(
          modifier = Modifier
            .matchParentSize()
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .background(
              brush = Brush.horizontalGradient(
                listOf(
                  TechBluePrimary.copy(alpha = 0.35f * buttonGlowPulse),
                  TechCyanAccent.copy(alpha = 0.5f * buttonGlowPulse),
                  TechBluePrimary.copy(alpha = 0.35f * buttonGlowPulse)
                )
              ),
              shape = RoundedCornerShape(18.dp)
            )
        )

        Button(
          onClick = onGetStarted,
          modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .testTag("btn_get_started"),
          colors = ButtonDefaults.buttonColors(
            containerColor = TechBluePrimary,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(16.dp),
          elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
          )
        ) {
          Text(
            text = "GET STARTED →",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 16.sp,
              letterSpacing = 1.3.sp,
              color = Color.White
            )
          )
        }
      }
    }
  }
}

/**
 * Custom Canvas drawing for the 3D-Style Computer Education Visual:
 * - Elegant White/Platinum Laptop (3D perspective screen lid + keyboard chassis)
 * - White Open Educational Book (with smooth open animation & wisdom lines)
 * - Warm Golden Knowledge Light & Rays rising upward from the book
 * - Golden Knowledge Particles & Soft Blue Tech Particles
 * - Subtle Circuit-Board traces in background
 */
private fun DrawScope.drawEducationalTechVisual(
  laptopScale: Float,
  laptopAlpha: Float,
  bookOpenProgress: Float,
  goldenLightAlpha: Float,
  circuitAlpha: Float,
  rayWave: Float
) {
  val cx = size.width / 2f
  val cy = size.height * 0.54f

  // 1. SUBTLE BACKGROUND CIRCUIT-BOARD ELEMENTS
  if (circuitAlpha > 0.05f) {
    val circuitColor = TechCyanAccent.copy(alpha = 0.22f * circuitAlpha)
    val dotColor = TechCyanAccent.copy(alpha = 0.35f * circuitAlpha)

    // Left circuit trace
    val leftPath = Path().apply {
      moveTo(cx - 120.dp.toPx(), cy - 20.dp.toPx())
      lineTo(cx - 85.dp.toPx(), cy - 20.dp.toPx())
      lineTo(cx - 65.dp.toPx(), cy + 10.dp.toPx())
    }
    drawPath(leftPath, circuitColor, style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round))
    drawCircle(dotColor, radius = 2.5.dp.toPx(), center = Offset(cx - 120.dp.toPx(), cy - 20.dp.toPx()))
    drawCircle(dotColor, radius = 3.dp.toPx(), center = Offset(cx - 65.dp.toPx(), cy + 10.dp.toPx()))

    // Right circuit trace
    val rightPath = Path().apply {
      moveTo(cx + 120.dp.toPx(), cy - 20.dp.toPx())
      lineTo(cx + 85.dp.toPx(), cy - 20.dp.toPx())
      lineTo(cx + 65.dp.toPx(), cy + 10.dp.toPx())
    }
    drawPath(rightPath, circuitColor, style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round))
    drawCircle(dotColor, radius = 2.5.dp.toPx(), center = Offset(cx + 120.dp.toPx(), cy - 20.dp.toPx()))
    drawCircle(dotColor, radius = 3.dp.toPx(), center = Offset(cx + 65.dp.toPx(), cy + 10.dp.toPx()))
  }

  // 2. WARM GOLDEN AMBIENT KNOWLEDGE AURA
  if (goldenLightAlpha > 0.05f) {
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          Color(0xFFFFD54F).copy(alpha = 0.38f * goldenLightAlpha),
          Color(0xFFFFB300).copy(alpha = 0.16f * goldenLightAlpha),
          Color.Transparent
        ),
        center = Offset(cx, cy + 5.dp.toPx()),
        radius = 90.dp.toPx()
      )
    )
  }

  // 3. ELEGANT WHITE / PLATINUM LAPTOP (3D Perspective)
  if (laptopAlpha > 0.05f) {
    val laptopWidth = 140.dp.toPx() * laptopScale
    val screenHeight = 84.dp.toPx() * laptopScale
    val screenTop = cy - 42.dp.toPx() - (screenHeight * 0.5f)
    val screenLeft = cx - (laptopWidth / 2f)

    // Laptop Screen Outer Platinum Bezel (tilted upright in 3D)
    drawRoundRect(
      brush = Brush.verticalGradient(
        listOf(
          Color(0xFFF8FAFC).copy(alpha = laptopAlpha),
          Color(0xFFE2E8F0).copy(alpha = laptopAlpha)
        )
      ),
      topLeft = Offset(screenLeft, screenTop),
      size = Size(laptopWidth, screenHeight),
      cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
    )

    // Laptop Display Glass (Dark Deep Navy with subtle code lines)
    val bezelInset = 4.dp.toPx() * laptopScale
    val displayWidth = laptopWidth - (bezelInset * 2f)
    val displayHeight = screenHeight - (bezelInset * 2f) - 3.dp.toPx()

    drawRoundRect(
      brush = Brush.verticalGradient(
        listOf(
          Color(0xFF0F172A).copy(alpha = laptopAlpha),
          Color(0xFF020617).copy(alpha = laptopAlpha)
        )
      ),
      topLeft = Offset(screenLeft + bezelInset, screenTop + bezelInset),
      size = Size(displayWidth, displayHeight),
      cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
    )

    // Faint glowing syntax/code lines on the screen
    val codeLineAlpha = 0.65f * laptopAlpha
    drawRoundRect(
      color = TechCyanAccent.copy(alpha = codeLineAlpha),
      topLeft = Offset(screenLeft + bezelInset + 12.dp.toPx(), screenTop + bezelInset + 14.dp.toPx()),
      size = Size(32.dp.toPx() * laptopScale, 3.dp.toPx()),
      cornerRadius = CornerRadius(1.5f, 1.5f)
    )
    drawRoundRect(
      color = Color(0xFFFFD54F).copy(alpha = 0.55f * laptopAlpha),
      topLeft = Offset(screenLeft + bezelInset + 12.dp.toPx(), screenTop + bezelInset + 22.dp.toPx()),
      size = Size(50.dp.toPx() * laptopScale, 3.dp.toPx()),
      cornerRadius = CornerRadius(1.5f, 1.5f)
    )
    drawRoundRect(
      color = TechBluePrimary.copy(alpha = 0.6f * laptopAlpha),
      topLeft = Offset(screenLeft + bezelInset + 20.dp.toPx(), screenTop + bezelInset + 30.dp.toPx()),
      size = Size(40.dp.toPx() * laptopScale, 3.dp.toPx()),
      cornerRadius = CornerRadius(1.5f, 1.5f)
    )

    // Metallic hinge
    val hingeWidth = 36.dp.toPx() * laptopScale
    drawRoundRect(
      color = Color(0xFF94A3B8).copy(alpha = laptopAlpha),
      topLeft = Offset(cx - (hingeWidth / 2f), screenTop + screenHeight - 2.dp.toPx()),
      size = Size(hingeWidth, 4.dp.toPx()),
      cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
    )

    // Laptop Base Chassis (Isometric forward perspective trapezoid)
    val baseTopY = screenTop + screenHeight
    val baseBottomY = baseTopY + (32.dp.toPx() * laptopScale)
    val baseTopWidth = laptopWidth + 4.dp.toPx()
    val baseBottomWidth = laptopWidth + (28.dp.toPx() * laptopScale)

    val baseChassisPath = Path().apply {
      moveTo(cx - (baseTopWidth / 2f), baseTopY)
      lineTo(cx + (baseTopWidth / 2f), baseTopY)
      lineTo(cx + (baseBottomWidth / 2f), baseBottomY)
      lineTo(cx - (baseBottomWidth / 2f), baseBottomY)
      close()
    }

    drawPath(
      path = baseChassisPath,
      brush = Brush.verticalGradient(
        listOf(
          Color(0xFFE2E8F0).copy(alpha = laptopAlpha),
          Color(0xFFCBD5E1).copy(alpha = laptopAlpha)
        )
      )
    )

    // Keyboard indentation area
    val kbTopWidth = baseTopWidth - 24.dp.toPx()
    val kbBottomWidth = baseBottomWidth - 36.dp.toPx()
    val kbTopY = baseTopY + 4.dp.toPx()
    val kbBottomY = baseBottomY - 10.dp.toPx()

    val kbPath = Path().apply {
      moveTo(cx - (kbTopWidth / 2f), kbTopY)
      lineTo(cx + (kbTopWidth / 2f), kbTopY)
      lineTo(cx + (kbBottomWidth / 2f), kbBottomY)
      lineTo(cx - (kbBottomWidth / 2f), kbBottomY)
      close()
    }
    drawPath(
      path = kbPath,
      color = Color(0xFF64748B).copy(alpha = 0.35f * laptopAlpha)
    )

    // Platinum trackpad
    val tpWidth = 24.dp.toPx() * laptopScale
    val tpHeight = 6.dp.toPx() * laptopScale
    drawRoundRect(
      color = Color(0xFFF8FAFC).copy(alpha = 0.8f * laptopAlpha),
      topLeft = Offset(cx - (tpWidth / 2f), baseBottomY - 7.dp.toPx()),
      size = Size(tpWidth, tpHeight),
      cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
    )
  }

  // 4. WARM GOLDEN KNOWLEDGE RAYS RISING FROM THE BOOK
  if (goldenLightAlpha > 0.05f) {
    val rayAlpha = 0.42f * goldenLightAlpha

    // Central upward knowledge beam
    val centerRay = Path().apply {
      moveTo(cx - 10.dp.toPx(), cy + 14.dp.toPx())
      lineTo(cx - 30.dp.toPx(), cy - 70.dp.toPx())
      lineTo(cx + 30.dp.toPx(), cy - 70.dp.toPx())
      lineTo(cx + 10.dp.toPx(), cy + 14.dp.toPx())
      close()
    }
    drawPath(
      path = centerRay,
      brush = Brush.verticalGradient(
        colors = listOf(
          Color.Transparent,
          Color(0xFFFFD54F).copy(alpha = rayAlpha * 0.7f),
          Color(0xFFFFB300).copy(alpha = rayAlpha)
        ),
        startY = cy - 70.dp.toPx(),
        endY = cy + 14.dp.toPx()
      )
    )

    // Left knowledge ray
    val leftRay = Path().apply {
      moveTo(cx - 8.dp.toPx(), cy + 14.dp.toPx())
      lineTo(cx - 65.dp.toPx(), cy - 55.dp.toPx())
      lineTo(cx - 45.dp.toPx(), cy - 65.dp.toPx())
      lineTo(cx - 2.dp.toPx(), cy + 14.dp.toPx())
      close()
    }
    drawPath(
      path = leftRay,
      brush = Brush.verticalGradient(
        colors = listOf(
          Color.Transparent,
          Color(0xFFFFE082).copy(alpha = rayAlpha * 0.45f)
        ),
        startY = cy - 65.dp.toPx(),
        endY = cy + 14.dp.toPx()
      )
    )

    // Right knowledge ray
    val rightRay = Path().apply {
      moveTo(cx + 8.dp.toPx(), cy + 14.dp.toPx())
      lineTo(cx + 65.dp.toPx(), cy - 55.dp.toPx())
      lineTo(cx + 45.dp.toPx(), cy - 65.dp.toPx())
      lineTo(cx + 2.dp.toPx(), cy + 14.dp.toPx())
      close()
    }
    drawPath(
      path = rightRay,
      brush = Brush.verticalGradient(
        colors = listOf(
          Color.Transparent,
          Color(0xFFFFE082).copy(alpha = rayAlpha * 0.45f)
        ),
        startY = cy - 65.dp.toPx(),
        endY = cy + 14.dp.toPx()
      )
    )
  }

  // 5. WHITE OPEN EDUCATIONAL BOOK (Depth & 3D Perspective)
  if (bookOpenProgress > 0.05f) {
    val bookSpread = 55.dp.toPx() * bookOpenProgress
    val bookHeight = 32.dp.toPx()
    val spineX = cx
    val spineY = cy + 16.dp.toPx()

    // Left Page (Curved 3D surface in pure white & platinum)
    val leftPagePath = Path().apply {
      moveTo(spineX, spineY)
      // Top curve
      quadraticTo(
        spineX - (bookSpread * 0.5f), spineY - 8.dp.toPx(),
        spineX - bookSpread, spineY - 4.dp.toPx()
      )
      // Left outer edge
      lineTo(spineX - bookSpread + 3.dp.toPx(), spineY + bookHeight)
      // Bottom curve
      quadraticTo(
        spineX - (bookSpread * 0.5f), spineY + bookHeight - 4.dp.toPx(),
        spineX, spineY + bookHeight - 2.dp.toPx()
      )
      close()
    }

    // Left Page Shadow / Underlayer
    val leftPageShadow = Path().apply {
      moveTo(spineX - bookSpread + 3.dp.toPx(), spineY + bookHeight)
      lineTo(spineX - bookSpread + 3.dp.toPx(), spineY + bookHeight + 3.dp.toPx())
      quadraticTo(
        spineX - (bookSpread * 0.5f), spineY + bookHeight - 1.dp.toPx(),
        spineX, spineY + bookHeight + 1.dp.toPx()
      )
      lineTo(spineX, spineY + bookHeight - 2.dp.toPx())
      close()
    }
    drawPath(leftPageShadow, Color(0xFF94A3B8).copy(alpha = 0.6f * bookOpenProgress))

    drawPath(
      path = leftPagePath,
      brush = Brush.horizontalGradient(
        colors = listOf(
          Color(0xFFE2E8F0),
          Color(0xFFFFFFFF),
          Color(0xFFF1F5F9)
        ),
        startX = spineX - bookSpread,
        endX = spineX
      )
    )

    // Left Page subtle wisdom lines
    if (bookOpenProgress > 0.6f) {
      val lineAlpha = 0.35f * bookOpenProgress
      val lineXStart = spineX - (bookSpread * 0.8f)
      val lineXEnd = spineX - (bookSpread * 0.2f)
      drawLine(
        color = Color(0xFF94A3B8).copy(alpha = lineAlpha),
        start = Offset(lineXStart, spineY + 8.dp.toPx()),
        end = Offset(lineXEnd, spineY + 8.dp.toPx()),
        strokeWidth = 1.2.dp.toPx()
      )
      drawLine(
        color = Color(0xFF94A3B8).copy(alpha = lineAlpha),
        start = Offset(lineXStart, spineY + 14.dp.toPx()),
        end = Offset(lineXEnd, spineY + 14.dp.toPx()),
        strokeWidth = 1.2.dp.toPx()
      )
      drawLine(
        color = Color(0xFF94A3B8).copy(alpha = lineAlpha),
        start = Offset(lineXStart, spineY + 20.dp.toPx()),
        end = Offset(lineXEnd, spineY + 20.dp.toPx()),
        strokeWidth = 1.2.dp.toPx()
      )
    }

    // Right Page (Curved 3D surface in pure white & platinum)
    val rightPagePath = Path().apply {
      moveTo(spineX, spineY)
      quadraticTo(
        spineX + (bookSpread * 0.5f), spineY - 8.dp.toPx(),
        spineX + bookSpread, spineY - 4.dp.toPx()
      )
      lineTo(spineX + bookSpread - 3.dp.toPx(), spineY + bookHeight)
      quadraticTo(
        spineX + (bookSpread * 0.5f), spineY + bookHeight - 4.dp.toPx(),
        spineX, spineY + bookHeight - 2.dp.toPx()
      )
      close()
    }

    // Right Page Shadow / Underlayer
    val rightPageShadow = Path().apply {
      moveTo(spineX + bookSpread - 3.dp.toPx(), spineY + bookHeight)
      lineTo(spineX + bookSpread - 3.dp.toPx(), spineY + bookHeight + 3.dp.toPx())
      quadraticTo(
        spineX + (bookSpread * 0.5f), spineY + bookHeight - 1.dp.toPx(),
        spineX, spineY + bookHeight + 1.dp.toPx()
      )
      lineTo(spineX, spineY + bookHeight - 2.dp.toPx())
      close()
    }
    drawPath(rightPageShadow, Color(0xFF94A3B8).copy(alpha = 0.6f * bookOpenProgress))

    drawPath(
      path = rightPagePath,
      brush = Brush.horizontalGradient(
        colors = listOf(
          Color(0xFFF1F5F9),
          Color(0xFFFFFFFF),
          Color(0xFFE2E8F0)
        ),
        startX = spineX,
        endX = spineX + bookSpread
      )
    )

    // Right Page subtle wisdom lines
    if (bookOpenProgress > 0.6f) {
      val lineAlpha = 0.35f * bookOpenProgress
      val lineXStart = spineX + (bookSpread * 0.2f)
      val lineXEnd = spineX + (bookSpread * 0.8f)
      drawLine(
        color = Color(0xFF94A3B8).copy(alpha = lineAlpha),
        start = Offset(lineXStart, spineY + 8.dp.toPx()),
        end = Offset(lineXEnd, spineY + 8.dp.toPx()),
        strokeWidth = 1.2.dp.toPx()
      )
      drawLine(
        color = Color(0xFF94A3B8).copy(alpha = lineAlpha),
        start = Offset(lineXStart, spineY + 14.dp.toPx()),
        end = Offset(lineXEnd, spineY + 14.dp.toPx()),
        strokeWidth = 1.2.dp.toPx()
      )
      drawLine(
        color = Color(0xFF94A3B8).copy(alpha = lineAlpha),
        start = Offset(lineXStart, spineY + 20.dp.toPx()),
        end = Offset(lineXEnd, spineY + 20.dp.toPx()),
        strokeWidth = 1.2.dp.toPx()
      )
    }

    // Spine Center divider shadow
    drawLine(
      color = Color(0xFF64748B).copy(alpha = 0.45f * bookOpenProgress),
      start = Offset(spineX, spineY),
      end = Offset(spineX, spineY + bookHeight - 2.dp.toPx()),
      strokeWidth = 1.5.dp.toPx()
    )

    // Golden Core Bulb/Glow at the Book Spine
    if (goldenLightAlpha > 0.1f) {
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            Color(0xFFFFF59D).copy(alpha = 0.9f * goldenLightAlpha),
            Color(0xFFFFB300).copy(alpha = 0.55f * goldenLightAlpha),
            Color.Transparent
          ),
          center = Offset(spineX, spineY + 10.dp.toPx()),
          radius = 18.dp.toPx()
        )
      )
    }
  }

  // 6. FLOATING GOLDEN KNOWLEDGE PARTICLES & SOFT BLUE TECH PARTICLES
  if (goldenLightAlpha > 0.1f) {
    val radWave = Math.toRadians(rayWave.toDouble())

    // 6.1 Golden Knowledge Particles (rising up from the book)
    val goldenParticles = listOf(
      Offset(cx - 24.dp.toPx(), cy - 20.dp.toPx() - (sin(radWave).toFloat() * 10.dp.toPx())),
      Offset(cx + 28.dp.toPx(), cy - 35.dp.toPx() - (cos(radWave).toFloat() * 8.dp.toPx())),
      Offset(cx - 8.dp.toPx(), cy - 50.dp.toPx() - (sin(radWave * 1.3).toFloat() * 12.dp.toPx())),
      Offset(cx + 12.dp.toPx(), cy - 65.dp.toPx() - (cos(radWave * 1.1).toFloat() * 9.dp.toPx())),
      Offset(cx - 38.dp.toPx(), cy - 40.dp.toPx() - (sin(radWave * 0.9).toFloat() * 7.dp.toPx())),
      Offset(cx + 45.dp.toPx(), cy - 25.dp.toPx() - (cos(radWave * 1.2).toFloat() * 11.dp.toPx()))
    )

    goldenParticles.forEachIndexed { i, pos ->
      val radius = if (i % 2 == 0) 2.5.dp.toPx() else 1.8.dp.toPx()
      drawCircle(
        color = Color(0xFFFFD54F).copy(alpha = 0.75f * goldenLightAlpha),
        radius = radius,
        center = pos
      )
    }

    // 6.2 Soft Blue Technology Particles (subtly orbiting)
    val orbitAngle1 = Math.toRadians((rayWave * 1.2).toDouble())
    val orbitAngle2 = Math.toRadians((-rayWave * 0.9).toDouble())

    val techNode1 = Offset(
      cx + (90.dp.toPx() * cos(orbitAngle1)).toFloat(),
      cy - 10.dp.toPx() + (45.dp.toPx() * sin(orbitAngle1)).toFloat()
    )
    val techNode2 = Offset(
      cx + (75.dp.toPx() * cos(orbitAngle2)).toFloat(),
      cy + 15.dp.toPx() + (35.dp.toPx() * sin(orbitAngle2)).toFloat()
    )

    drawCircle(
      color = TechCyanAccent.copy(alpha = 0.7f * goldenLightAlpha),
      radius = 2.8.dp.toPx(),
      center = techNode1
    )
    drawCircle(
      color = TechBluePrimary.copy(alpha = 0.7f * goldenLightAlpha),
      radius = 2.4.dp.toPx(),
      center = techNode2
    )
  }
}
