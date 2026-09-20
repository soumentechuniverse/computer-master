package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PowerOff
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppLanguage

@Composable
fun InteractiveRomIllustration(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "rom_glow")
  val chipGlow by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(2000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "chip_glow"
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
    // Header Row
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
            .background(TechAmber)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "রম চিপ ও বায়োস (BIOS/UEFI)"
            AppLanguage.HINDI -> "रोम चिप और बायोस (BIOS/UEFI)"
            AppLanguage.ENGLISH -> "3D ROM / BIOS FIRMWARE CHIP"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            letterSpacing = 0.8.sp
          ),
          color = TechAmber
        )
      }

      // Permanent Lock Pill
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(TechAmber.copy(alpha = 0.2f))
          .padding(horizontal = 6.dp, vertical = 3.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Lock,
          contentDescription = null,
          tint = TechAmber,
          modifier = Modifier.size(12.dp)
        )
        Text(
          text = "Non-Volatile",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
          ),
          color = TechAmber
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Canvas rendering SOIC-8 BIOS ROM Chip on Motherboard
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(210.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(NavyDarkest)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
        .testTag("rom_3d_canvas_box"),
      contentAlignment = Alignment.Center
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(210.dp)
      ) {
        drawRomChipDiagram(chipGlow = chipGlow)
      }

      // Label overlay
      Box(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(8.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(NavyDark.copy(alpha = 0.9f))
          .border(0.5.dp, TechAmber.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
          .padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        Text(
          text = "Winbond 25Q128 • 128 Mb SPI Flash BIOS ROM",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = TechAmber
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // ROM Key Educational Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(Color(0xFF281C0F))
        .border(1.dp, TechAmber.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
        .padding(12.dp)
    ) {
      Column {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.PowerOff,
            contentDescription = null,
            tint = TechAmber,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "বিদ্যুৎ চলে গেলেও তথ্য চিরকাল সুরক্ষিত থাকে"
              AppLanguage.HINDI -> "बिजली बंद होने पर भी डेटा हमेशा सुरक्षित रहता है"
              AppLanguage.ENGLISH -> "Data Remains Safe Forever Even With Zero Power"
            },
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            ),
            color = TechAmber
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "রমে কারখানায় মুদ্রিত স্থায়ী 'বায়োস (BIOS)' ফার্মওয়্যার থাকে। কম্পিউটার অন করার বোতাম চাপলেই এই রম চিপ জেগে উঠে হার্ডওয়্যার পরীক্ষা (POST) করে এবং উইন্ডোজ/লিনাক্স চালু করে।"
            AppLanguage.HINDI -> "रोम में निर्माण के समय का स्थायी 'बायोस (BIOS)' फर्मवेयर होता है। पावर बटन दबाते ही यह चिप जागती है, हार्डवेयर की जांच (POST) करती है और विंडोज़ को चालू करती है।"
            AppLanguage.ENGLISH -> "ROM holds permanent factory-flashed 'BIOS/UEFI' firmware. The exact millisecond you press the power button, ROM performs the hardware health check (POST) and loads Windows/macOS into RAM."
          },
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 11.sp,
            lineHeight = 16.sp
          ),
          color = TextSecondary
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// CANVAS DRAWING FOR ROM CHIP
// -----------------------------------------------------------------------------
private fun DrawScope.drawRomChipDiagram(chipGlow: Float) {
  val w = size.width
  val h = size.height

  // 1. Motherboard PCB traces
  val moboW = w * 0.76f
  val moboH = h * 0.76f
  val moboLeft = (w - moboW) / 2f
  val moboTop = (h - moboH) / 2f

  drawRoundRect(
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF1B2635), Color(0xFF101720)),
      start = Offset(moboLeft, moboTop),
      end = Offset(moboLeft + moboW, moboTop + moboH)
    ),
    topLeft = Offset(moboLeft, moboTop),
    size = Size(moboW, moboH),
    cornerRadius = CornerRadius(14f, 14f)
  )

  // Gold copper traces entering the chip
  val traceColors = listOf(Color(0xFFFFD54F), Color(0xFF64B5F6), Color(0xFF81C784))
  for (i in 0..4) {
    val yOffset = moboTop + 24f + i * 26f
    drawLine(
      color = traceColors[i % traceColors.size].copy(alpha = 0.4f),
      start = Offset(moboLeft + 10f, yOffset),
      end = Offset(w * 0.36f, yOffset),
      strokeWidth = 2f
    )
    drawLine(
      color = traceColors[i % traceColors.size].copy(alpha = 0.4f),
      start = Offset(w * 0.64f, yOffset),
      end = Offset(moboLeft + moboW - 10f, yOffset),
      strokeWidth = 2f
    )
  }

  // 2. ROM SOIC-8 Chip Body
  val chipW = w * 0.36f
  val chipH = h * 0.52f
  val chipLeft = (w - chipW) / 2f
  val chipTop = (h - chipH) / 2f

  // Glow halo
  drawRoundRect(
    brush = Brush.radialGradient(
      colors = listOf(TechAmber.copy(alpha = chipGlow * 0.35f), Color.Transparent),
      center = Offset(chipLeft + chipW / 2f, chipTop + chipH / 2f),
      radius = chipW
    ),
    topLeft = Offset(chipLeft - 10f, chipTop - 10f),
    size = Size(chipW + 20f, chipH + 20f),
    cornerRadius = CornerRadius(12f, 12f)
  )

  // Chip Base
  drawRoundRect(
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF262D36), Color(0xFF141920)),
      start = Offset(chipLeft, chipTop),
      end = Offset(chipLeft + chipW, chipTop + chipH)
    ),
    topLeft = Offset(chipLeft, chipTop),
    size = Size(chipW, chipH),
    cornerRadius = CornerRadius(8f, 8f)
  )
  drawRoundRect(
    color = TechAmber.copy(alpha = 0.7f),
    topLeft = Offset(chipLeft, chipTop),
    size = Size(chipW, chipH),
    cornerRadius = CornerRadius(8f, 8f),
    style = Stroke(width = 1.5f)
  )

  // Pin 1 Index Dot
  drawCircle(
    color = Color(0xFF5A6678),
    radius = 5f,
    center = Offset(chipLeft + 12f, chipTop + 14f)
  )

  // Chip Pins on left and right (4 on each side = 8-pin SOIC)
  val pinW = 12f
  val pinH = 8f
  val pinSpacing = chipH / 5f
  for (i in 1..4) {
    val py = chipTop + i * pinSpacing - pinH / 2f
    // Left pin
    drawRoundRect(
      color = Color(0xFFFFD54F),
      topLeft = Offset(chipLeft - pinW + 1f, py),
      size = Size(pinW, pinH),
      cornerRadius = CornerRadius(2f, 2f)
    )
    // Right pin
    drawRoundRect(
      color = Color(0xFFFFD54F),
      topLeft = Offset(chipLeft + chipW - 1f, py),
      size = Size(pinW, pinH),
      cornerRadius = CornerRadius(2f, 2f)
    )
  }
}
