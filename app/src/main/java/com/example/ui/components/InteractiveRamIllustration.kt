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
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Speed
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
import com.example.data.model.RamPartType
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppLanguage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveRamIllustration(
  selectedPart: RamPartType,
  onSelectPart: (RamPartType) -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "ram_data_stream")
  val dataBusPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(1800, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "data_bus_phase"
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
            .background(TechGreen)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "র‍্যাম মডিউল (ইন্টারেক্টিভ ৩ডি)"
            AppLanguage.HINDI -> "रैम मॉड्यूल (इंटरएक्टिव 3D)"
            AppLanguage.ENGLISH -> "3D RAM MODULE & DIMM"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            letterSpacing = 0.8.sp
          ),
          color = TechGreen
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
            AppLanguage.ENGLISH -> "Tap part"
          },
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = TextSecondary
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Canvas rendering RAM Stick, IC Chips, Golden Contacts & Motherboard Slot
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(230.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(NavyDarkest)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
        .testTag("ram_3d_canvas_box"),
      contentAlignment = Alignment.Center
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(230.dp)
      ) {
        drawRamModuleDiagram(
          selectedPart = selectedPart,
          dataBusPhase = dataBusPhase
        )
      }

      // Live Speed & DDR Badge
      Box(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(8.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(NavyDark.copy(alpha = 0.85f))
          .border(0.5.dp, TechGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = "DDR5 • 6000 MT/s • Dual Channel",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
          color = TechGreen
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 4 RAM Component Chips
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      RamPartType.entries.forEach { part ->
        val isSelected = part == selectedPart
        val label = when (part) {
          RamPartType.RAM_STICK -> when (currentLanguage) {
            AppLanguage.BENGALI -> "১. র‍্যাম স্টিক"
            AppLanguage.HINDI -> "1. रैम स्टिक"
            AppLanguage.ENGLISH -> "1. Stick (PCB)"
          }
          RamPartType.MEMORY_CHIPS -> when (currentLanguage) {
            AppLanguage.BENGALI -> "২. মেমরি চিপস"
            AppLanguage.HINDI -> "2. मेमोरी चिप्स"
            AppLanguage.ENGLISH -> "2. DRAM Chips"
          }
          RamPartType.DIMM_SLOTS -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৩. মাদারবোর্ড স্লট"
            AppLanguage.HINDI -> "3. DIMM स्लॉट"
            AppLanguage.ENGLISH -> "3. DIMM Slot"
          }
          RamPartType.DATA_BUS -> when (currentLanguage) {
            AppLanguage.BENGALI -> "৪. ডেটা বাস"
            AppLanguage.HINDI -> "4. डेटा बस"
            AppLanguage.ENGLISH -> "4. Data Bus"
          }
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) TechGreen else NavyCard)
            .border(
              1.dp,
              if (isSelected) TechGreen else NavyCardBorder,
              RoundedCornerShape(8.dp)
            )
            .clickable { onSelectPart(part) }
            .padding(vertical = 8.dp, horizontal = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 10.sp
            ),
            color = if (isSelected) NavyDarkest else TextPrimary,
            maxLines = 1
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // RAM Volatile Alert & Multitasking Work Desk Banner
    RamDeskAnalogyBanner(currentLanguage = currentLanguage)
  }
}

// -----------------------------------------------------------------------------
// ANALOGY BANNER: Working Desk & Volatile Nature
// -----------------------------------------------------------------------------
@Composable
private fun RamDeskAnalogyBanner(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(Color(0xFF0D251C))
      .border(1.dp, TechGreen.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
      .padding(12.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.ElectricBolt,
          contentDescription = null,
          tint = TechAmber,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "বাস্তব তুলনা: র‍্যাম হলো আপনার কাজের টেবিল"
            AppLanguage.HINDI -> "सरल सादृश्य: रैम आपकी काम करने की मेज है"
            AppLanguage.ENGLISH -> "Real-Life Analogy: RAM is your Working Desk"
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
          AppLanguage.BENGALI -> "টেবিল যত বড় (8GB vs 16GB vs 32GB), একসাথে তত বেশি বই-খাতা (ব্রাউজার ট্যাব, গেম, ফটোশপ) খুলে কাজ করা সম্ভব। কিন্তু কাজ শেষে কম্পিউটার বন্ধ করলে টেবিলের সব কাগজ এক নিমেষে মুছে সাফ হয়ে যায় (Volatile)!"
          AppLanguage.HINDI -> "मेज जितनी बड़ी होगी (8GB vs 16GB vs 32GB), आप एक साथ उतनी ही अधिक फाइलें और ऐप्स खोल सकेंगे। लेकिन कंप्यूटर बंद होते ही मेज पूरी तरह खाली हो जाती है (अस्थायी/Volatile)!"
          AppLanguage.ENGLISH -> "The wider your desk (8GB vs 16GB vs 32GB), the more open apps & browser tabs you can juggle at once without slowdown. When power switches off, everything on the desk vanishes instantly (Volatile)!"
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

// -----------------------------------------------------------------------------
// CANVAS DRAWING LOGIC FOR 3D RAM STICK, DRAM CHIPS & MOTHERBOARD BUS
// -----------------------------------------------------------------------------
private fun DrawScope.drawRamModuleDiagram(
  selectedPart: RamPartType,
  dataBusPhase: Float
) {
  val w = size.width
  val h = size.height

  // 1. Motherboard PCB backdrop (Top area)
  val moboSlotTop = h * 0.72f
  val moboSlotHeight = h * 0.22f
  val isSlotSelected = selectedPart == RamPartType.DIMM_SLOTS

  // DIMM Socket Base (Black plastic socket with latch tabs)
  drawRoundRect(
    color = if (isSlotSelected) TechGreen.copy(alpha = 0.35f) else Color(0xFF131A22),
    topLeft = Offset(w * 0.06f, moboSlotTop),
    size = Size(w * 0.88f, moboSlotHeight),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawRoundRect(
    color = if (isSlotSelected) TechGreen else Color(0xFF283647),
    topLeft = Offset(w * 0.06f, moboSlotTop),
    size = Size(w * 0.88f, moboSlotHeight),
    cornerRadius = CornerRadius(6f, 6f),
    style = Stroke(width = if (isSlotSelected) 2f else 1.2f)
  )

  // DIMM Lock Latches (Left & Right)
  val latchWidth = 14f
  val latchHeight = moboSlotHeight + 16f
  // Left latch
  drawRoundRect(
    color = if (isSlotSelected) TechGreen else Color(0xFF384E66),
    topLeft = Offset(w * 0.04f, moboSlotTop - 8f),
    size = Size(latchWidth, latchHeight),
    cornerRadius = CornerRadius(4f, 4f)
  )
  // Right latch
  drawRoundRect(
    color = if (isSlotSelected) TechGreen else Color(0xFF384E66),
    topLeft = Offset(w * 0.94f - latchWidth, moboSlotTop - 8f),
    size = Size(latchWidth, latchHeight),
    cornerRadius = CornerRadius(4f, 4f)
  )

  // 2. RAM STICK PCB (Vertical angled or horizontal module)
  val stickLeft = w * 0.10f
  val stickWidth = w * 0.80f
  val stickTop = h * 0.15f
  val stickHeight = h * 0.58f

  val isStickSelected = selectedPart == RamPartType.RAM_STICK

  // RAM Stick Body (Modern stealth matte dark green / cyber black PCB)
  drawRoundRect(
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF0F3628), Color(0xFF081C15)),
      start = Offset(stickLeft, stickTop),
      end = Offset(stickLeft + stickWidth, stickTop + stickHeight)
    ),
    topLeft = Offset(stickLeft, stickTop),
    size = Size(stickWidth, stickHeight),
    cornerRadius = CornerRadius(10f, 10f)
  )

  // RAM Stick Border
  drawRoundRect(
    color = if (isStickSelected) TechGreen else Color(0xFF1B6047),
    topLeft = Offset(stickLeft, stickTop),
    size = Size(stickWidth, stickHeight),
    cornerRadius = CornerRadius(10f, 10f),
    style = Stroke(width = if (isStickSelected) 2.5f else 1.5f)
  )

  // Key Notch cutout at bottom center (DDR5 asymmetrical alignment)
  val notchX = stickLeft + stickWidth * 0.54f
  val notchWidth = 14f
  val notchHeight = 16f
  drawRect(
    color = Color(0xFF131A22),
    topLeft = Offset(notchX, stickTop + stickHeight - notchHeight),
    size = Size(notchWidth, notchHeight)
  )

  // 3. Golden Connector Contacts / Edge Pins (Bottom row of RAM stick)
  val pinAreaTop = stickTop + stickHeight - 12f
  val pinCount = 38
  val pinStep = (stickWidth - 8f) / pinCount
  for (i in 0 until pinCount) {
    val px = stickLeft + 4f + i * pinStep
    if (px < notchX - 2f || px > notchX + notchWidth + 2f) {
      drawLine(
        color = Color(0xFFFFD54F),
        start = Offset(px, pinAreaTop),
        end = Offset(px, pinAreaTop + 10f),
        strokeWidth = 2f
      )
    }
  }

  // 4. DRAM Memory IC Chips (Row of black rectangle silicon chips)
  val isChipSelected = selectedPart == RamPartType.MEMORY_CHIPS
  val chipCount = 8
  val chipTotalWidth = stickWidth * 0.88f
  val chipStep = chipTotalWidth / chipCount
  val chipW = chipStep * 0.75f
  val chipH = stickHeight * 0.42f
  val chipTop = stickTop + (stickHeight - chipH) * 0.38f
  val chipStartLeft = stickLeft + (stickWidth - chipTotalWidth) / 2f

  for (i in 0 until chipCount) {
    val cx = chipStartLeft + i * chipStep
    // Chip Shadow
    drawRoundRect(
      color = Color.Black.copy(alpha = 0.5f),
      topLeft = Offset(cx + 2f, chipTop + 3f),
      size = Size(chipW, chipH),
      cornerRadius = CornerRadius(4f, 4f)
    )
    // Chip Base
    drawRoundRect(
      brush = Brush.linearGradient(
        colors = listOf(Color(0xFF22272E), Color(0xFF14171C)),
        start = Offset(cx, chipTop),
        end = Offset(cx + chipW, chipTop + chipH)
      ),
      topLeft = Offset(cx, chipTop),
      size = Size(chipW, chipH),
      cornerRadius = CornerRadius(4f, 4f)
    )
    // Chip Border
    drawRoundRect(
      color = if (isChipSelected) TechCyanAccent else Color(0xFF3B4654),
      topLeft = Offset(cx, chipTop),
      size = Size(chipW, chipH),
      cornerRadius = CornerRadius(4f, 4f),
      style = Stroke(width = if (isChipSelected) 1.8f else 0.8f)
    )

    // Pin legs on sides of IC
    drawLine(
      color = Color(0xFF9EABB8),
      start = Offset(cx + chipW * 0.2f, chipTop - 2f),
      end = Offset(cx + chipW * 0.2f, chipTop),
      strokeWidth = 1f
    )
    drawLine(
      color = Color(0xFF9EABB8),
      start = Offset(cx + chipW * 0.8f, chipTop - 2f),
      end = Offset(cx + chipW * 0.8f, chipTop),
      strokeWidth = 1f
    )
  }

  // 5. Data Bus / Signal Traces (Active animated pulse to CPU)
  val isBusSelected = selectedPart == RamPartType.DATA_BUS
  val busTraceY = moboSlotTop + moboSlotHeight * 0.5f
  val busStart = w * 0.12f
  val busEnd = w * 0.88f

  drawLine(
    color = if (isBusSelected) TechCyanAccent else Color(0xFF1E394A),
    start = Offset(busStart, busTraceY),
    end = Offset(busEnd, busTraceY),
    strokeWidth = if (isBusSelected) 2.5f else 1.5f
  )

  // Animated pulse packets travelling along the bus
  val pulseX = busStart + (busEnd - busStart) * dataBusPhase
  drawCircle(
    color = TechCyanAccent,
    radius = 4f,
    center = Offset(pulseX, busTraceY)
  )
  drawCircle(
    color = TechCyanAccent.copy(alpha = 0.4f),
    radius = 8f,
    center = Offset(pulseX, busTraceY)
  )
}
