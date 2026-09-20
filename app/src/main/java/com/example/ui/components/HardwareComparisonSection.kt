package com.example.ui.components

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
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CpuRamRomLessonRepository
import com.example.data.model.HardwareComparisonRow
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppLanguage

@Composable
fun HardwareComparisonSection(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  val rows = CpuRamRomLessonRepository.comparisons

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp)
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
            .background(TechCyanAccent)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "সিপিইউ বনাম র‍্যাম বনাম রম তুলনা"
            AppLanguage.HINDI -> "सीपीयू बनाम रैम बनाम रोम तुलना"
            AppLanguage.ENGLISH -> "CPU vs RAM vs ROM COMPARISON"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            letterSpacing = 0.8.sp
          ),
          color = TechCyanAccent
        )
      }

      Icon(
        imageVector = Icons.Default.Balance,
        contentDescription = null,
        tint = TechAmber,
        modifier = Modifier.size(18.dp)
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Comparison Column Badges
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(TechCyanAccent.copy(alpha = 0.15f))
          .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
          .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "CPU",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
          color = TechCyanAccent
        )
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(TechGreen.copy(alpha = 0.15f))
          .border(1.dp, TechGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
          .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "RAM",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
          color = TechGreen
        )
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(TechAmber.copy(alpha = 0.15f))
          .border(1.dp, TechAmber.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
          .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "ROM",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
          color = TechAmber
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Comparison Attribute Cards
    rows.forEach { row ->
      ComparisonFeatureCard(
        row = row,
        currentLanguage = currentLanguage
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

@Composable
private fun ComparisonFeatureCard(
  row: HardwareComparisonRow,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(NavyDarkest)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
      .padding(10.dp)
  ) {
    Column {
      Text(
        text = row.getFeature(currentLanguage),
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp
        ),
        color = TechCyanAccent
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // CPU Value
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(NavyCard)
            .padding(6.dp)
        ) {
          Text(
            text = row.getCpu(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
            color = TextPrimary
          )
        }

        // RAM Value
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(NavyCard)
            .padding(6.dp)
        ) {
          Text(
            text = row.getRam(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
            color = TextPrimary
          )
        }

        // ROM Value
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(NavyCard)
            .padding(6.dp)
        ) {
          Text(
            text = row.getRom(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
            color = TextPrimary
          )
        }
      }
    }
  }
}
