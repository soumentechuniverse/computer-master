package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun CourseCard(
  course: Course,
  onClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val shape = RoundedCornerShape(20.dp)
  val progressAnimated by animateFloatAsState(
    targetValue = course.progressPercent / 100f,
    label = "course_card_progress"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(shape)
      .background(
        brush = Brush.verticalGradient(
          listOf(
            NavyCard,
            NavyCard.copy(alpha = 0.95f)
          )
        )
      )
      .border(
        width = 1.dp,
        brush = Brush.verticalGradient(
          listOf(
            if (course.progressPercent > 0) TechBluePrimary.copy(alpha = 0.5f) else NavyCardBorder,
            NavyCardBorder.copy(alpha = 0.4f)
          )
        ),
        shape = shape
      )
      .clickable(onClick = onClick)
      .padding(16.dp)
      .testTag("course_card_${course.id}")
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Header: Icon + Level + Difficulty + Bookmark
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          CourseIcon(iconName = course.iconName, size = 46.dp, iconSize = 24.dp)

          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              LevelBadge(level = course.level)
              DifficultyIndicator(difficulty = course.difficulty)
            }
          }
        }

        IconButton(
          onClick = onBookmarkClick,
          modifier = Modifier
            .minimumInteractiveComponentSize()
            .testTag("bookmark_btn_${course.id}")
        ) {
          Icon(
            imageVector = if (course.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = if (course.isBookmarked) "Remove bookmark" else "Bookmark course",
            tint = if (course.isBookmarked) TechAmber else TextTertiary
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Title & Description
      Text(
        text = course.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 17.sp
        ),
        color = TextPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = course.description,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, lineHeight = 18.sp),
        color = TextSecondary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Meta: Lessons & Duration
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.MenuBook,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(15.dp)
          )
          Text(
            text = "${course.lessonCount} Lessons",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
            color = TextSecondary
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.AccessTime,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(15.dp)
          )
          Text(
            text = "${course.estimatedHours} hrs",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
            color = TextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Progress Bar & Percentage
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        LinearProgressIndicator(
          progress = { progressAnimated },
          modifier = Modifier
            .weight(1f)
            .height(7.dp)
            .clip(RoundedCornerShape(4.dp))
            .testTag("course_card_progress_bar_${course.id}"),
          color = when {
            course.isCompleted -> TechGreen
            course.progressPercent > 0 -> TechBluePrimary
            else -> NavyCardBorder
          },
          trackColor = NavyDark,
        )

        Text(
          text = if (course.isCompleted) "100% Done" else "${course.progressPercent}%",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
          ),
          color = when {
            course.isCompleted -> TechGreen
            course.progressPercent > 0 -> TechCyanAccent
            else -> TextTertiary
          },
          modifier = Modifier.testTag("course_card_progress_text_${course.id}")
        )
      }
    }
  }
}

@Composable
fun DifficultyIndicator(
  difficulty: Int,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(3.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 1..3) {
      val isFilled = i <= difficulty
      Box(
        modifier = Modifier
          .size(6.dp)
          .clip(CircleShape)
          .background(
            if (isFilled) TechAmber else TextTertiary.copy(alpha = 0.3f)
          )
      )
    }
  }
}
