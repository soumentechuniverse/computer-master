package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CpuRamRomLessonRepository
import com.example.data.model.PipelineStage
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

@Composable
fun MemoryPipelineDiagram(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  var selectedStageIndex by remember { mutableIntStateOf(0) }
  val stages = CpuRamRomLessonRepository.pipelineStages

  val infiniteTransition = rememberInfiniteTransition(label = "pipeline_glow")
  val arrowGlow by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "arrow_glow"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp)
  ) {
    // Section Header
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
            AppLanguage.BENGALI -> "ডেটা মেমরি ফ্লো ডায়াগ্রাম"
            AppLanguage.HINDI -> "डेटा मेमोरी फ्लो आरेख"
            AppLanguage.ENGLISH -> "DATA MEMORY PIPELINE FLOW"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            letterSpacing = 0.8.sp
          ),
          color = TechCyanAccent
        )
      }

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "ধাপ স্পর্শ করুন"
          AppLanguage.HINDI -> "स्टेप स्पर्श करें"
          AppLanguage.ENGLISH -> "Tap any step"
        },
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = TextSecondary
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Interactive 5-Stage Vertical Pipeline
    stages.forEachIndexed { index, stageInfo ->
      val isSelected = index == selectedStageIndex
      val stageIcon: ImageVector = when (stageInfo.stage) {
        PipelineStage.USER_INPUT -> Icons.Default.TouchApp
        PipelineStage.STORAGE -> Icons.Default.Save
        PipelineStage.RAM -> Icons.Default.Memory
        PipelineStage.CPU -> Icons.Default.Computer
        PipelineStage.OUTPUT -> Icons.Default.Tv
      }

      val accentColor = when (stageInfo.stage) {
        PipelineStage.USER_INPUT -> TechAmber
        PipelineStage.STORAGE -> TechPurple
        PipelineStage.RAM -> TechGreen
        PipelineStage.CPU -> TechCyanAccent
        PipelineStage.OUTPUT -> Color(0xFF64B5F6)
      }

      PipelineStepCard(
        stepNumber = stageInfo.stepNumber,
        title = stageInfo.getTitle(currentLanguage),
        subtitle = stageInfo.getSubtitle(currentLanguage),
        icon = stageIcon,
        accentColor = accentColor,
        isSelected = isSelected,
        onClick = { selectedStageIndex = index }
      )

      // Animated Arrow between steps
      if (index < stages.size - 1) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = null,
            tint = if (index == selectedStageIndex || index == selectedStageIndex - 1) {
              accentColor.copy(alpha = arrowGlow)
            } else {
              NavyCardBorder
            },
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Detailed Stage Explanation Card
    val currentStage = stages[selectedStageIndex]
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(NavyDarkest)
        .border(1.dp, TechCyanAccent.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .background(TechCyanAccent),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${currentStage.stepNumber}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              ),
              color = NavyDarkest
            )
          }

          Text(
            text = currentStage.getTitle(currentLanguage),
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            ),
            color = TechCyanAccent
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = currentStage.getDesc(currentLanguage),
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 12.sp,
            lineHeight = 18.sp
          ),
          color = TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Real-Life Example walkthrough (e.g. Opening Chrome browser)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(NavyCard)
            .border(0.5.dp, TechAmber.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(10.dp)
        ) {
          Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Language,
              contentDescription = null,
              tint = TechAmber,
              modifier = Modifier.size(18.dp)
            )
            Column {
              Text(
                text = when (currentLanguage) {
                  AppLanguage.BENGALI -> "বাস্তব উদাহরণ (ক্রোম ব্রাউজার চালু করার সময়):"
                  AppLanguage.HINDI -> "वास्तविक उदाहरण (क्रोम ब्राउज़र खोलते समय):"
                  AppLanguage.ENGLISH -> "Real-life Case (When opening Chrome Browser):"
                },
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp
                ),
                color = TechAmber
              )
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                text = currentStage.getBrowserAction(currentLanguage),
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
    }
  }
}

// -----------------------------------------------------------------------------
// STEP ROW COMPONENT
// -----------------------------------------------------------------------------
@Composable
private fun PipelineStepCard(
  stepNumber: Int,
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val bg = if (isSelected) NavyCard else NavyDarkest
  val borderCol = if (isSelected) accentColor else NavyCardBorder

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(bg)
      .border(if (isSelected) 1.5.dp else 1.dp, borderCol, RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(accentColor.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(20.dp)
        )
      }

      Column {
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          ),
          color = if (isSelected) accentColor else TextPrimary
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
          color = TextSecondary
        )
      }
    }

    if (isSelected) {
      Box(
        modifier = Modifier
          .size(8.dp)
          .clip(CircleShape)
          .background(accentColor)
      )
    }
  }
}
