package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.data.model.CourseModule
import com.example.data.model.Lesson
import com.example.ui.components.CourseIcon
import com.example.ui.components.DifficultyIndicator
import com.example.ui.components.LevelBadge
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel

@Composable
fun CourseDetailScreen(
  courseId: String,
  viewModel: ComputerMasterViewModel,
  onBackClick: () -> Unit,
  onNavigateToQuiz: () -> Unit,
  onNavigateToLesson: (courseId: String, lessonId: String) -> Unit = { _, _ -> },
  modifier: Modifier = Modifier,
) {
  val allCourses by viewModel.allCourses.collectAsState()
  val course = allCourses.find { it.id == courseId }

  if (course == null) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .background(NavyDarkest),
      contentAlignment = Alignment.Center
    ) {
      Text(text = "Course not found", color = TextPrimary)
    }
    return
  }

  val progressAnimated by animateFloatAsState(
    targetValue = course.progressPercent / 100f,
    label = "detail_progress"
  )

  // Track expanded modules and chapters (default: expand all for easy reading)
  var expandedModuleIds by remember(course.id) {
    mutableStateOf(course.modules.map { it.id }.toSet())
  }
  var expandedChapterIds by remember(course.id) {
    mutableStateOf(course.modules.flatMap { it.chapters.map { chap -> chap.id } }.toSet())
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("course_detail_screen"),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // 1. Top Navigation Bar with Back & Bookmark Buttons
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
          .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .minimumInteractiveComponentSize()
            .background(NavyCard, CircleShape)
            .border(1.dp, NavyCardBorder, CircleShape)
            .testTag("course_detail_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = TextPrimary
          )
        }

        Text(
          text = "Course Details",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
          ),
          color = TextPrimary
        )

        IconButton(
          onClick = { viewModel.toggleBookmark(course.id) },
          modifier = Modifier
            .minimumInteractiveComponentSize()
            .background(NavyCard, CircleShape)
            .border(1.dp, NavyCardBorder, CircleShape)
            .testTag("course_detail_bookmark_button")
        ) {
          Icon(
            imageVector = if (course.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = if (course.isBookmarked) "Remove bookmark" else "Bookmark",
            tint = if (course.isBookmarked) TechAmber else TextSecondary
          )
        }
      }
    }

    // 2. Large Course Hero Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 8.dp)
          .clip(RoundedCornerShape(24.dp))
          .background(
            brush = Brush.verticalGradient(
              listOf(NavyCardElevated, NavyCard)
            )
          )
          .border(
            width = 1.dp,
            brush = Brush.verticalGradient(
              listOf(TechBluePrimary.copy(alpha = 0.5f), NavyCardBorder)
            ),
            shape = RoundedCornerShape(24.dp)
          )
          .padding(20.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          // Top Row: Large Icon + Badges
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            CourseIcon(
              iconName = course.iconName,
              size = 64.dp,
              iconSize = 34.dp
            )

            Column(horizontalAlignment = Alignment.End) {
              Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                LevelBadge(level = course.level)
                DifficultyIndicator(difficulty = course.difficulty)
              }

              Spacer(modifier = Modifier.height(6.dp))

              // Status Pill
              val statusText = when {
                course.isCompleted -> "Completed"
                course.isStarted -> "Started"
                else -> "Not Started"
              }
              val statusColor = when {
                course.isCompleted -> TechGreen
                course.isStarted -> TechCyanAccent
                else -> TextTertiary
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(statusColor.copy(alpha = 0.15f))
                  .border(1.dp, statusColor.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Text(
                  text = statusText,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                  ),
                  color = statusColor
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Course Title
          Text(
            text = course.title,
            style = MaterialTheme.typography.headlineSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 22.sp
            ),
            color = TextPrimary
          )

          Spacer(modifier = Modifier.height(6.dp))

          // Course Description
          Text(
            text = course.description,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontSize = 14.sp,
              lineHeight = 21.sp
            ),
            color = TextSecondary
          )

          Spacer(modifier = Modifier.height(18.dp))

          // Meta Info: Lessons, Estimated Time, Difficulty
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                tint = TechCyanAccent,
                modifier = Modifier.size(16.dp)
              )
              Text(
                text = "${course.lessonCount} Lessons",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp),
                color = TextSecondary
              )
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = TechCyanAccent,
                modifier = Modifier.size(16.dp)
              )
              Text(
                text = "${course.estimatedHours} hrs",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp),
                color = TextSecondary
              )
            }

            Text(
              text = "• ${course.difficultyLabel}",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
              ),
              color = TechAmber
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Progress Bar & Percentage
          Column(modifier = Modifier.fillMaxWidth()) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Course Progress",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 13.sp
                ),
                color = TextPrimary
              )
              Text(
                text = "${course.progressPercent}%",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                ),
                color = if (course.progressPercent > 0) TechCyanAccent else TextTertiary
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
              progress = { progressAnimated },
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
              color = if (course.progressPercent >= 100) TechGreen else TechCyanAccent,
              trackColor = NavyDark
            )
          }

          Spacer(modifier = Modifier.height(20.dp))

          // 3. Action Buttons: Start Course & Continue Learning
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            if (!course.isStarted) {
              // Start Course Primary CTA
              Button(
                onClick = {
                  viewModel.startCourse(course.id)
                  val firstLesson = course.allLessons.firstOrNull()
                  if (firstLesson != null) {
                    onNavigateToLesson(course.id, firstLesson.id)
                  }
                },
                modifier = Modifier
                  .weight(1f)
                  .height(48.dp)
                  .testTag("start_course_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = TechBluePrimary
                )
              ) {
                Icon(
                  imageVector = Icons.Default.PlayArrow,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Start Course",
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                  )
                )
              }
            } else if (!course.isCompleted) {
              // Continue Learning CTA
              Button(
                onClick = {
                  val nextLesson = course.allLessons.firstOrNull { !it.isCompleted } ?: course.allLessons.firstOrNull()
                  if (nextLesson != null) {
                    onNavigateToLesson(course.id, nextLesson.id)
                  }
                },
                modifier = Modifier
                  .weight(1f)
                  .height(48.dp)
                  .testTag("continue_learning_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = TechCyanAccent,
                  contentColor = NavyDarkest
                )
              ) {
                Icon(
                  imageVector = Icons.Default.PlayArrow,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Continue Learning",
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                  )
                )
              }
            } else {
              // Completed Banner CTA
              Button(
                onClick = onNavigateToQuiz,
                modifier = Modifier
                  .weight(1f)
                  .height(48.dp)
                  .testTag("course_completed_quiz_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = TechGreen
                )
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Completed • Take Quiz",
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                  )
                )
              }
            }

            // Secondary Bookmark / Quiz button
            OutlinedButton(
              onClick = { viewModel.toggleBookmark(course.id) },
              modifier = Modifier
                .height(48.dp)
                .testTag("hero_bookmark_button"),
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = if (course.isBookmarked) TechAmber else TextSecondary
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (course.isBookmarked) TechAmber.copy(alpha = 0.6f) else NavyCardBorder
              )
            ) {
              Icon(
                imageVector = if (course.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (course.isBookmarked) "Saved" else "Save",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
              )
            }
          }
        }
      }
    }

    // 4. Curriculum Header with Hierarchy Details
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "COURSE CONTENT",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              letterSpacing = 1.sp
            ),
            color = TextPrimary
          )
          Text(
            text = "${course.modules.size} Modules • ${course.modules.sumOf { it.chapters.size }} Chapters • ${course.lessonCount} Lessons",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = TextSecondary
          )
        }

        // Toggle Expand All / Collapse All
        val allExpanded = expandedModuleIds.size == course.modules.size
        Text(
          text = if (allExpanded) "Collapse All" else "Expand All",
          style = MaterialTheme.typography.labelSmall.copy(
            color = TechCyanAccent,
            fontWeight = FontWeight.SemiBold
          ),
          modifier = Modifier
            .clickable {
              if (allExpanded) {
                expandedModuleIds = emptySet()
                expandedChapterIds = emptySet()
              } else {
                expandedModuleIds = course.modules.map { it.id }.toSet()
                expandedChapterIds = course.modules.flatMap { it.chapters.map { chap -> chap.id } }.toSet()
              }
            }
            .padding(4.dp)
        )
      }
    }

    // 5. Hierarchy: Module -> Chapter -> Lesson
    course.modules.forEachIndexed { modIndex, module ->
      val isModuleExpanded = expandedModuleIds.contains(module.id)

      // Module Header Card
      item(key = "module_${module.id}") {
        ModuleHeaderCard(
          moduleNumber = modIndex + 1,
          module = module,
          isExpanded = isModuleExpanded,
          onToggleExpand = {
            expandedModuleIds = if (isModuleExpanded) {
              expandedModuleIds - module.id
            } else {
              expandedModuleIds + module.id
            }
          }
        )
      }

      // Expandable Chapters & Lessons inside this Module
      if (isModuleExpanded) {
        module.chapters.forEachIndexed { chapIndex, chapter ->
          val isChapterExpanded = expandedChapterIds.contains(chapter.id)

          // Chapter Header
          item(key = "chapter_${chapter.id}") {
            ChapterHeaderCard(
              chapterIndex = "${modIndex + 1}.${chapIndex + 1}",
              chapter = chapter,
              isExpanded = isChapterExpanded,
              onToggleExpand = {
                expandedChapterIds = if (isChapterExpanded) {
                  expandedChapterIds - chapter.id
                } else {
                  expandedChapterIds + chapter.id
                }
              }
            )
          }

          // Lessons inside this Chapter
          if (isChapterExpanded) {
            items(chapter.lessons, key = { it.id }) { lesson ->
              LessonItemRow(
                lesson = lesson,
                onLessonClick = {
                  onNavigateToLesson(course.id, lesson.id)
                },
                onToggleCompletion = {
                  viewModel.toggleLessonCompletion(course.id, lesson.id)
                },
                onQuizClick = {
                  viewModel.startQuizForLesson(lesson.id)
                  onNavigateToQuiz()
                }
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ModuleHeaderCard(
  moduleNumber: Int,
  module: CourseModule,
  isExpanded: Boolean,
  onToggleExpand: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val completedCount = module.lessons.count { it.isCompleted }
  val totalCount = module.lessons.size

  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 6.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(NavyCard)
      .border(
        width = 1.dp,
        color = if (isExpanded) TechBluePrimary.copy(alpha = 0.5f) else NavyCardBorder,
        shape = RoundedCornerShape(16.dp)
      )
      .clickable(onClick = onToggleExpand)
      .padding(horizontal = 16.dp, vertical = 14.dp)
      .testTag("module_header_${module.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(TechBluePrimary.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "M$moduleNumber",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            ),
            color = TechCyanAccent
          )
        }

        Column {
          Text(
            text = module.title,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            ),
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "${module.chapters.size} Chapters • $completedCount/$totalCount Lessons Completed",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = if (completedCount == totalCount && totalCount > 0) TechGreen else TextSecondary
          )
        }
      }

      Icon(
        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
        contentDescription = if (isExpanded) "Collapse module" else "Expand module",
        tint = TextSecondary,
        modifier = Modifier.size(22.dp)
      )
    }
  }
}

@Composable
private fun ChapterHeaderCard(
  chapterIndex: String,
  chapter: Chapter,
  isExpanded: Boolean,
  onToggleExpand: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = 32.dp, end = 20.dp, top = 4.dp, bottom = 4.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(NavyDark)
      .border(
        width = 1.dp,
        color = NavyCardBorder.copy(alpha = 0.6f),
        shape = RoundedCornerShape(12.dp)
      )
      .clickable(onClick = onToggleExpand)
      .padding(horizontal = 14.dp, vertical = 10.dp)
      .testTag("chapter_header_${chapter.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Folder,
          contentDescription = null,
          tint = TechCyanAccent.copy(alpha = 0.8f),
          modifier = Modifier.size(16.dp)
        )

        Column {
          Text(
            text = "Chapter $chapterIndex: ${chapter.title}",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp
            ),
            color = TextPrimary
          )
          Text(
            text = "${chapter.lessons.size} Lessons",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = TextSecondary
          )
        }
      }

      Icon(
        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
        contentDescription = if (isExpanded) "Collapse chapter" else "Expand chapter",
        tint = TextTertiary,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}

@Composable
private fun LessonItemRow(
  lesson: Lesson,
  onLessonClick: () -> Unit,
  onToggleCompletion: () -> Unit,
  onQuizClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = 44.dp, end = 20.dp, top = 3.dp, bottom = 3.dp)
      .clip(RoundedCornerShape(14.dp))
      .background(NavyCard)
      .border(
        width = 1.dp,
        color = if (lesson.isCompleted) TechGreen.copy(alpha = 0.4f) else NavyCardBorder.copy(alpha = 0.7f),
        shape = RoundedCornerShape(14.dp)
      )
      .clickable(onClick = onLessonClick)
      .padding(12.dp)
      .testTag("lesson_row_${lesson.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Tappable checkmark
      IconButton(
        onClick = onToggleCompletion,
        modifier = Modifier
          .size(32.dp)
          .minimumInteractiveComponentSize()
          .testTag("lesson_check_${lesson.id}")
      ) {
        Icon(
          imageVector = if (lesson.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
          contentDescription = if (lesson.isCompleted) "Completed" else "Incomplete",
          tint = if (lesson.isCompleted) TechGreen else TextTertiary,
          modifier = Modifier.size(22.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = lesson.title,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
          ),
          color = TextPrimary
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = lesson.description,
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 11.sp,
            lineHeight = 15.sp
          ),
          color = TextSecondary,
          maxLines = 2
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = "${lesson.durationMinutes} mins",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = TechCyanAccent
          )

          if (lesson.id == "cb_lesson_3") {
            Text(
              text = "• 3D Visual",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              ),
              color = TechCyanAccent
            )
          }

          if (lesson.hasQuiz) {
            Text(
              text = "• Quiz Ready",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TechIndigo
            )
          }

          if (lesson.isCompleted) {
            Text(
              text = "• Completed",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp
              ),
              color = TechGreen
            )
          }
        }
      }

      if (lesson.hasQuiz) {
        IconButton(
          onClick = onQuizClick,
          modifier = Modifier
            .size(34.dp)
            .background(TechBluePrimary.copy(alpha = 0.2f), CircleShape)
            .border(1.dp, TechCyanAccent.copy(alpha = 0.3f), CircleShape)
            .testTag("lesson_quiz_button_${lesson.id}")
        ) {
          Icon(
            imageVector = Icons.Default.Quiz,
            contentDescription = "Take Quiz",
            tint = TechCyanAccent,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
