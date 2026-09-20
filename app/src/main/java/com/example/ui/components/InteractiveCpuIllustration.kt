package com.example.ui.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CpuInternalPart
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppLanguage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveCpuIllustration(
  selectedPart: CpuInternalPart,
  onSelectPart: (CpuInternalPart) -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  // Clock pulse infinite animation (smooth, lightweight)
  val infiniteTransition = rememberInfiniteTransition(label = "cpu_pulse")
  val pulseProgress by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(2200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "pulse_progress"
  )

  val heatGlow by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "heat_glow"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Header Row with 3D Status
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(TechCyanAccent)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "সিপিইউ ৩ডি ডায়াগ্রাম (ইন্টারেক্টিভ)"
            AppLanguage.HINDI -> "सीपीयू 3D आरेख (इंटरएक्टिव)"
            AppLanguage.ENGLISH -> "3D CPU ARCHITECTURE"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            letterSpacing = 0.8.sp
          ),
          color = TechCyanAccent
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.TouchApp,
          contentDescription = null,
          tint = TechAmber,
          modifier = Modifier.size(14.dp)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "অংশ স্পর্শ করুন"
            AppLanguage.HINDI -> "घटक स्पर्श करें"
            AppLanguage.ENGLISH -> "Tap any part"
          },
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = TextSecondary
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 3D Canvas Box
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(260.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(NavyDarkest)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
        .testTag("cpu_3d_canvas_box"),
      contentAlignment = Alignment.Center
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(260.dp)
      ) {
        drawCpuChipDiagram(
          selectedPart = selectedPart,
          pulseProgress = pulseProgress,
          heatGlow = heatGlow
        )
      }

      // Overlay Mini Legend Indicator
      Box(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(8.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(NavyDark.copy(alpha = 0.85f))
          .border(0.5.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = "Clock: 3.8 GHz • 8 Cores • 16 Threads",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
          color = TechCyanAccent
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Interactive Quick Part Selector Chips (8 subcomponents)
    Text(
      text = when (currentLanguage) {
        AppLanguage.BENGALI -> "নিচের ৮টি যন্ত্রাংশে স্পর্শ করে গভীরভাবে জানুন:"
        AppLanguage.HINDI -> "गहराई से समझने के लिए नीचे दिए गए 8 भागों को स्पर्श करें:"
        AppLanguage.ENGLISH -> "Select a CPU part below to inspect details:"
      },
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
      color = TextSecondary,
      modifier = Modifier.align(Alignment.Start)
    )

    Spacer(modifier = Modifier.height(8.dp))

    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      CpuInternalPart.entries.forEach { part ->
        val isSelected = part == selectedPart
        val partLabel = when (part) {
          CpuInternalPart.CHIP_DIE -> when (currentLanguage) {
            AppLanguage.BENGALI -> "১. সিলিকন ডাই"
            AppLanguage.HINDI -> "1. सिलिकॉन डाई"
            AppLanguage.ENGLISH -> "1. Silicon Die"
          }
          CpuInternalPart.CONTROL_UNIT -> when (currentLanguage) {
            AppLanguage.BENGALI -> "২. কন্ট্রোল ইউনিট (CU)"
            AppLanguage.HINDI -> "2. कंट्रोल यूनिट (CU)"
            AppLanguage.ENGLISH -> "2. Control Unit"
          }
          CpuInternalPart.ALU -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৩. এএলইউ (ALU)"
            AppLanguage.HINDI -> "3. एएलयू (ALU)"
            AppLanguage.ENGLISH -> "3. ALU (Math)"
          }
          CpuInternalPart.REGISTERS -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৪. রেজিস্টারস"
            AppLanguage.HINDI -> "4. रजिस्टर्स"
            AppLanguage.ENGLISH -> "4. Registers"
          }
          CpuInternalPart.CACHE -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৫. ক্যাশ (L1/L2/L3)"
            AppLanguage.HINDI -> "5. कैश (L1-L3)"
            AppLanguage.ENGLISH -> "5. Cache"
          }
          CpuInternalPart.CORES -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৬. মাল্টি-কোর"
            AppLanguage.HINDI -> "6. मल्टी-कोर"
            AppLanguage.ENGLISH -> "6. Cores"
          }
          CpuInternalPart.CLOCK -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৭. ক্লক (GHz)"
            AppLanguage.HINDI -> "7. क्लॉक (GHz)"
            AppLanguage.ENGLISH -> "7. Clock (GHz)"
          }
          CpuInternalPart.HEAT_COOLING -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৮. তাপ ও কুলিং"
            AppLanguage.HINDI -> "8. हीट और कूलिंग"
            AppLanguage.ENGLISH -> "8. Heat & Cooler"
          }
        }

        val chipBg = if (isSelected) TechCyanAccent else NavyCard
        val chipBorder = if (isSelected) TechCyanAccent else NavyCardBorder
        val textColor = if (isSelected) NavyDarkest else TextPrimary

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(chipBg)
            .border(1.dp, chipBorder, RoundedCornerShape(8.dp))
            .clickable { onSelectPart(part) }
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("cpu_chip_${part.name.lowercase()}")
        ) {
          Text(
            text = partLabel,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
              fontSize = 11.sp
            ),
            color = textColor
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Pipeline Data Pulse Indicator (Input -> CPU -> Result)
    CpuFlowPipelineBar(
      pulseProgress = pulseProgress,
      currentLanguage = currentLanguage
    )
  }
}

// -----------------------------------------------------------------------------
// CPU FLOW PIPELINE BAR: Input -> CPU -> Processing -> Output
// -----------------------------------------------------------------------------
@Composable
private fun CpuFlowPipelineBar(
  pulseProgress: Float,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(NavyDarkest)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
      .padding(horizontal = 12.dp, vertical = 10.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "ডেটা চক্র: নির্দেশ → প্রসেসিং → ফলাফল"
            AppLanguage.HINDI -> "डेटा चक्र: निर्देश → प्रोसेसिंग → परिणाम"
            AppLanguage.ENGLISH -> "Data Cycle: Instruction → Processing → Result"
          },
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          ),
          color = TechCyanAccent
        )

        Text(
          text = "Fetch → Decode → Execute",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = TechAmber
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // 4 Flow Steps Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        val steps = listOf(
          when (currentLanguage) { AppLanguage.BENGALI -> "১. ইনপুট/ডেটা"; AppLanguage.HINDI -> "1. इनपुट/डेटा"; else -> "1. Input" },
          when (currentLanguage) { AppLanguage.BENGALI -> "২. ফেচ (র‍্যাম)"; AppLanguage.HINDI -> "2. फेच (रैम)"; else -> "2. Fetch" },
          when (currentLanguage) { AppLanguage.BENGALI -> "৩. ডিকোড (CU)"; AppLanguage.HINDI -> "3. डिकोड (CU)"; else -> "3. Decode" },
          when (currentLanguage) { AppLanguage.BENGALI -> "৪. এক্সিকিউট (ALU)"; AppLanguage.HINDI -> "4. एक्ज़ीक्यूट"; else -> "4. Execute" }
        )

        steps.forEachIndexed { index, stepName ->
          val stepRangeStart = index * 0.25f
          val stepRangeEnd = (index + 1) * 0.25f
          val isActive = pulseProgress >= stepRangeStart && pulseProgress < stepRangeEnd

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (isActive) TechCyanAccent.copy(alpha = 0.25f) else NavyCard)
              .border(
                1.dp,
                if (isActive) TechCyanAccent else NavyCardBorder,
                RoundedCornerShape(6.dp)
              )
              .padding(horizontal = 6.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = stepName,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                fontSize = 9.sp
              ),
              color = if (isActive) TechCyanAccent else TextSecondary
            )
          }

          if (index < steps.size - 1) {
            Text(
              text = "→",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
              color = if (pulseProgress >= stepRangeEnd - 0.05f) TechCyanAccent else TextSecondary.copy(alpha = 0.4f)
            )
          }
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// CANVAS DRAWING LOGIC FOR CPU 3D ISOMETRIC/PERSPECTIVE CHIP
// -----------------------------------------------------------------------------
private fun DrawScope.drawCpuChipDiagram(
  selectedPart: CpuInternalPart,
  pulseProgress: Float,
  heatGlow: Float
) {
  val w = size.width
  val h = size.height

  // 1. Draw subtle cyber background matrix grid
  val gridSpacing = 24f
  var curX = 0f
  while (curX < w) {
    drawLine(
      color = Color(0xFF132B45).copy(alpha = 0.35f),
      start = Offset(curX, 0f),
      end = Offset(curX, h),
      strokeWidth = 1f
    )
    curX += gridSpacing
  }
  var curY = 0f
  while (curY < h) {
    drawLine(
      color = Color(0xFF132B45).copy(alpha = 0.35f),
      start = Offset(0f, curY),
      end = Offset(w, curY),
      strokeWidth = 1f
    )
    curY += gridSpacing
  }

  // 2. Motherboard Green/Dark PCB Base (Socket AM5 / LGA1700 style)
  val pcbWidth = w * 0.78f
  val pcbHeight = h * 0.78f
  val pcbLeft = (w - pcbWidth) / 2f
  val pcbTop = (h - pcbHeight) / 2f

  // PCB Drop Shadow
  drawRoundRect(
    color = Color.Black.copy(alpha = 0.45f),
    topLeft = Offset(pcbLeft + 6f, pcbTop + 8f),
    size = Size(pcbWidth, pcbHeight),
    cornerRadius = CornerRadius(16f, 16f)
  )

  // PCB Substrate Board (Fiberglass dark green/slate)
  drawRoundRect(
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF0F382E), Color(0xFF081C16)),
      start = Offset(pcbLeft, pcbTop),
      end = Offset(pcbLeft + pcbWidth, pcbTop + pcbHeight)
    ),
    topLeft = Offset(pcbLeft, pcbTop),
    size = Size(pcbWidth, pcbHeight),
    cornerRadius = CornerRadius(16f, 16f)
  )
  drawRoundRect(
    color = Color(0xFF1F6B56),
    topLeft = Offset(pcbLeft, pcbTop),
    size = Size(pcbWidth, pcbHeight),
    cornerRadius = CornerRadius(16f, 16f),
    style = Stroke(width = 1.5f)
  )

  // Gold Pins / Alignment notch in top-left
  val notchPath = Path().apply {
    moveTo(pcbLeft + 8f, pcbTop)
    lineTo(pcbLeft, pcbTop + 8f)
    lineTo(pcbLeft + 16f, pcbTop + 16f)
    close()
  }
  drawPath(notchPath, color = Color(0xFFFFD54F))

  // 3. Central Metallic Heatspreader (IHS) / Silicon Core Cutaway
  val ihsWidth = pcbWidth * 0.74f
  val ihsHeight = pcbHeight * 0.74f
  val ihsLeft = pcbLeft + (pcbWidth - ihsWidth) / 2f
  val ihsTop = pcbTop + (pcbHeight - ihsHeight) / 2f

  // Heat glow if HEAT_COOLING selected
  if (selectedPart == CpuInternalPart.HEAT_COOLING) {
    drawRoundRect(
      brush = Brush.radialGradient(
        colors = listOf(TechRed.copy(alpha = heatGlow * 0.6f), Color.Transparent),
        center = Offset(ihsLeft + ihsWidth / 2f, ihsTop + ihsHeight / 2f),
        radius = ihsWidth * 0.8f
      ),
      topLeft = Offset(ihsLeft - 14f, ihsTop - 14f),
      size = Size(ihsWidth + 28f, ihsHeight + 28f),
      cornerRadius = CornerRadius(20f, 20f)
    )
  }

  // Metallic IHS base
  drawRoundRect(
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF2A3645), Color(0xFF16202D)),
      start = Offset(ihsLeft, ihsTop),
      end = Offset(ihsLeft + ihsWidth, ihsTop + ihsHeight)
    ),
    topLeft = Offset(ihsLeft, ihsTop),
    size = Size(ihsWidth, ihsHeight),
    cornerRadius = CornerRadius(12f, 12f)
  )
  drawRoundRect(
    color = Color(0xFF4B6079),
    topLeft = Offset(ihsLeft, ihsTop),
    size = Size(ihsWidth, ihsHeight),
    cornerRadius = CornerRadius(12f, 12f),
    style = Stroke(width = 2f)
  )

  // 4. Silicon Die Cutaway Interior
  val dieWidth = ihsWidth * 0.86f
  val dieHeight = ihsHeight * 0.86f
  val dieLeft = ihsLeft + (ihsWidth - dieWidth) / 2f
  val dieTop = ihsTop + (ihsHeight - dieHeight) / 2f

  // Die background
  drawRoundRect(
    color = Color(0xFF0C141F),
    topLeft = Offset(dieLeft, dieTop),
    size = Size(dieWidth, dieHeight),
    cornerRadius = CornerRadius(8f, 8f)
  )

  // Sub-blocks inside the die:
  // TOP ROW: Control Unit (left 45%) & Clock/Oscillator (right 55%)
  val topRowHeight = dieHeight * 0.28f
  val cuWidth = dieWidth * 0.48f

  // 4.1 CONTROL UNIT (CU) Block
  val isCuSelected = selectedPart == CpuInternalPart.CONTROL_UNIT
  drawRoundRect(
    color = if (isCuSelected) TechCyanAccent.copy(alpha = 0.45f) else Color(0xFF152A3F),
    topLeft = Offset(dieLeft + 4f, dieTop + 4f),
    size = Size(cuWidth, topRowHeight),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawRoundRect(
    color = if (isCuSelected) TechCyanAccent else Color(0xFF234B70),
    topLeft = Offset(dieLeft + 4f, dieTop + 4f),
    size = Size(cuWidth, topRowHeight),
    cornerRadius = CornerRadius(6f, 6f),
    style = Stroke(width = if (isCuSelected) 2f else 1f)
  )

  // 4.2 CLOCK & REGISTERS Block (Top-Right)
  val isRegSelected = selectedPart == CpuInternalPart.REGISTERS
  val isClockSelected = selectedPart == CpuInternalPart.CLOCK
  val clockLeft = dieLeft + cuWidth + 8f
  val clockWidth = dieWidth - cuWidth - 12f

  drawRoundRect(
    color = if (isRegSelected || isClockSelected) TechAmber.copy(alpha = 0.4f) else Color(0xFF2B2215),
    topLeft = Offset(clockLeft, dieTop + 4f),
    size = Size(clockWidth, topRowHeight),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawRoundRect(
    color = if (isRegSelected || isClockSelected) TechAmber else Color(0xFF6B4F1B),
    topLeft = Offset(clockLeft, dieTop + 4f),
    size = Size(clockWidth, topRowHeight),
    cornerRadius = CornerRadius(6f, 6f),
    style = Stroke(width = if (isRegSelected || isClockSelected) 2f else 1f)
  )

  // Pulsing clock oscillator ring
  val clockCenterX = clockLeft + clockWidth * 0.75f
  val clockCenterY = dieTop + 4f + topRowHeight * 0.5f
  drawCircle(
    color = TechAmber.copy(alpha = 0.8f),
    radius = 6f,
    center = Offset(clockCenterX, clockCenterY)
  )
  drawCircle(
    color = TechAmber.copy(alpha = (1f - pulseProgress) * 0.7f),
    radius = 6f + pulseProgress * 14f,
    center = Offset(clockCenterX, clockCenterY),
    style = Stroke(width = 1.5f)
  )

  // 4.3 ALU (Arithmetic Logic Unit) Block (Middle-Left)
  val midRowTop = dieTop + topRowHeight + 8f
  val midRowHeight = dieHeight * 0.32f
  val aluWidth = dieWidth * 0.48f
  val isAluSelected = selectedPart == CpuInternalPart.ALU

  drawRoundRect(
    color = if (isAluSelected) TechGreen.copy(alpha = 0.45f) else Color(0xFF143026),
    topLeft = Offset(dieLeft + 4f, midRowTop),
    size = Size(aluWidth, midRowHeight),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawRoundRect(
    color = if (isAluSelected) TechGreen else Color(0xFF1F5E48),
    topLeft = Offset(dieLeft + 4f, midRowTop),
    size = Size(aluWidth, midRowHeight),
    cornerRadius = CornerRadius(6f, 6f),
    style = Stroke(width = if (isAluSelected) 2f else 1f)
  )

  // 4.4 CORES (Core 0 to Core 3 Multi-Core Grid) (Middle-Right)
  val isCoresSelected = selectedPart == CpuInternalPart.CORES
  val coresLeft = dieLeft + aluWidth + 8f
  val coresWidth = dieWidth - aluWidth - 12f

  drawRoundRect(
    color = if (isCoresSelected) TechPurple.copy(alpha = 0.45f) else Color(0xFF28183B),
    topLeft = Offset(coresLeft, midRowTop),
    size = Size(coresWidth, midRowHeight),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawRoundRect(
    color = if (isCoresSelected) TechPurple else Color(0xFF5D2E8E),
    topLeft = Offset(coresLeft, midRowTop),
    size = Size(coresWidth, midRowHeight),
    cornerRadius = CornerRadius(6f, 6f),
    style = Stroke(width = if (isCoresSelected) 2f else 1f)
  )

  // Draw 4 mini core sub-boxes inside Cores area
  val subCoreW = (coresWidth - 8f) / 2f
  val subCoreH = (midRowHeight - 8f) / 2f
  for (r in 0..1) {
    for (c in 0..1) {
      val cX = coresLeft + 3f + c * (subCoreW + 2f)
      val cY = midRowTop + 3f + r * (subCoreH + 2f)
      drawRoundRect(
        color = Color(0xFF422165).copy(alpha = 0.7f),
        topLeft = Offset(cX, cY),
        size = Size(subCoreW, subCoreH),
        cornerRadius = CornerRadius(3f, 3f)
      )
    }
  }

  // 4.5 CACHE Block (L1/L2/L3) (Bottom Wide Strip)
  val botRowTop = midRowTop + midRowHeight + 8f
  val botRowHeight = dieHeight - (botRowTop - dieTop) - 4f
  val isCacheSelected = selectedPart == CpuInternalPart.CACHE

  drawRoundRect(
    color = if (isCacheSelected) TechCyanAccent.copy(alpha = 0.35f) else Color(0xFF0F2636),
    topLeft = Offset(dieLeft + 4f, botRowTop),
    size = Size(dieWidth - 8f, botRowHeight),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawRoundRect(
    color = if (isCacheSelected) TechCyanAccent else Color(0xFF1E4663),
    topLeft = Offset(dieLeft + 4f, botRowTop),
    size = Size(dieWidth - 8f, botRowHeight),
    cornerRadius = CornerRadius(6f, 6f),
    style = Stroke(width = if (isCacheSelected) 2f else 1f)
  )

  // Cache lines texture
  val cacheLineCount = 10
  val cacheLineStep = (dieWidth - 16f) / cacheLineCount
  for (i in 0 until cacheLineCount) {
    val lx = dieLeft + 8f + i * cacheLineStep
    drawLine(
      color = Color(0xFF225B82).copy(alpha = 0.5f),
      start = Offset(lx, botRowTop + 3f),
      end = Offset(lx, botRowTop + botRowHeight - 3f),
      strokeWidth = 1f
    )
  }

  // Silicon Die Outline
  val isDieSelected = selectedPart == CpuInternalPart.CHIP_DIE
  drawRoundRect(
    color = if (isDieSelected) TechCyanAccent else Color(0xFF4A688A),
    topLeft = Offset(dieLeft, dieTop),
    size = Size(dieWidth, dieHeight),
    cornerRadius = CornerRadius(8f, 8f),
    style = Stroke(width = if (isDieSelected) 2.5f else 1f)
  )
}
