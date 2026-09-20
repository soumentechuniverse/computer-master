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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun StatCard(
  title: String,
  value: String,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier,
  subtitle: String? = null,
) {
  val shape = RoundedCornerShape(16.dp)

  Box(
    modifier = modifier
      .clip(shape)
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(
            NavyCard,
            NavyCard.copy(alpha = 0.85f)
          )
        )
      )
      .border(
        width = 1.dp,
        brush = Brush.verticalGradient(
          colors = listOf(
            NavyCardBorder.copy(alpha = 0.9f),
            NavyCardBorder.copy(alpha = 0.4f)
          )
        ),
        shape = shape
      )
      .padding(14.dp)
      .testTag("stat_card_${title.lowercase().replace(" ", "_")}")
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .background(
              color = accentColor.copy(alpha = 0.15f),
              shape = RoundedCornerShape(10.dp)
            )
            .border(
              width = 1.dp,
              color = accentColor.copy(alpha = 0.3f),
              shape = RoundedCornerShape(10.dp)
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(20.dp)
          )
        }

        if (subtitle != null) {
          Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = accentColor
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = value,
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.ExtraBold,
          fontSize = 22.sp
        ),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        color = TextSecondary,
        maxLines = 1
      )
    }
  }
}
