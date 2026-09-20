package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CourseLevel
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechPurple

@Composable
fun LevelBadge(
  level: CourseLevel,
  modifier: Modifier = Modifier,
) {
  val (bgColor, textColor, borderColor) = when (level) {
    CourseLevel.BEGINNER -> Triple(
      TechGreen.copy(alpha = 0.15f),
      TechGreen,
      TechGreen.copy(alpha = 0.4f)
    )
    CourseLevel.INTERMEDIATE -> Triple(
      TechCyanAccent.copy(alpha = 0.15f),
      TechCyanAccent,
      TechCyanAccent.copy(alpha = 0.4f)
    )
    CourseLevel.ADVANCED -> Triple(
      TechPurple.copy(alpha = 0.15f),
      TechPurple,
      TechPurple.copy(alpha = 0.4f)
    )
    CourseLevel.ALL -> Triple(
      Color.White.copy(alpha = 0.1f),
      Color.White,
      Color.White.copy(alpha = 0.3f)
    )
  }

  Box(
    modifier = modifier
      .background(color = bgColor, shape = RoundedCornerShape(8.dp))
      .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
      .padding(horizontal = 8.dp, vertical = 3.dp)
  ) {
    Text(
      text = level.label.uppercase(),
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.8.sp,
        fontSize = 10.sp
      ),
      color = textColor
    )
  }
}
