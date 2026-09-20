package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.data.model.CommonMistake
import com.example.data.model.HowItWorksStep
import com.example.data.model.LessonDetailData
import com.example.data.model.LessonQuizQuestion
import com.example.data.model.PracticalActivity
import com.example.data.model.RealWorldExample
import com.example.data.repository.ComputerBasicsLessonRepository
import com.example.ui.components.CourseIcon
import com.example.ui.components.EducationalVisualDiagram
import com.example.ui.components.LessonNotesDialog
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
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
  courseId: String,
  lessonId: String,
  viewModel: ComputerMasterViewModel,
  onBackClick: () -> Unit,
  onNavigateToLesson: (String) -> Unit,
  onNavigateToQuiz: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val lesson: LessonDetailData? = remember(lessonId) {
    ComputerBasicsLessonRepository.getLessonById(lessonId)
      ?: ComputerBasicsLessonRepository.getAllLessons().firstOrNull()
  }

  val allCourses by viewModel.allCourses.collectAsState()
  val course = allCourses.find { it.id == courseId }
  val isLessonCompleted = course?.allLessons?.find { it.id == lessonId }?.isCompleted ?: false

  val bookmarkedLessons by viewModel.bookmarkedLessons.collectAsState()
  val isBookmarked = bookmarkedLessons.contains(lessonId)

  var showNotesDialog by remember { mutableStateOf(false) }

  if (lesson == null) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .background(NavyDarkest),
      contentAlignment = Alignment.Center
    ) {
      Text(text = "Lesson not found", color = TextPrimary)
    }
    return
  }

  // Notes Dialog
  if (showNotesDialog) {
    LessonNotesDialog(
      courseId = courseId,
      courseTitle = course?.title ?: lesson.courseTitle,
      lessonTitle = lesson.title,
      viewModel = viewModel,
      onDismiss = { showNotesDialog = false }
    )
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "Lesson ${lesson.lessonNumber} of ${lesson.totalLessons}",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = TextPrimary
            )
            Text(
              text = lesson.courseTitle,
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = TechCyanAccent
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier.testTag("lesson_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = TextPrimary
            )
          }
        },
        actions = {
          // Study Notes Action
          IconButton(
            onClick = { showNotesDialog = true },
            modifier = Modifier.testTag("lesson_notes_button")
          ) {
            Icon(
              imageVector = Icons.Default.EditNote,
              contentDescription = "My Notes",
              tint = TechCyanAccent,
              modifier = Modifier.size(26.dp)
            )
          }

          // Bookmark Action
          IconButton(
            onClick = { viewModel.toggleLessonBookmark(lesson.lessonId) },
            modifier = Modifier.testTag("lesson_bookmark_button")
          ) {
            Icon(
              imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark",
              tint = if (isBookmarked) TechAmber else TextSecondary,
              modifier = Modifier.size(24.dp)
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = NavyDarkest,
          titleContentColor = TextPrimary
        )
      )
    },
    bottomBar = {
      // 13. Lesson Completion & Navigation Bar
      LessonBottomNavigationBar(
        lesson = lesson,
        isCompleted = isLessonCompleted,
        onToggleCompletion = {
          viewModel.toggleLessonCompletion(courseId, lesson.lessonId)
        },
        onPrevClick = {
          lesson.prevLessonId?.let { onNavigateToLesson(it) }
        },
        onNextClick = {
          lesson.nextLessonId?.let { onNavigateToLesson(it) }
        }
      )
    }
  ) { innerPadding ->
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .background(NavyDarkest)
        .padding(innerPadding)
        .testTag("lesson_detail_content"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      // 1. LESSON HEADER SECTION
      item {
        LessonHeaderSection(
          lesson = lesson,
          isCompleted = isLessonCompleted
        )
      }

      // 2. LEARNING OBJECTIVES
      item {
        LearningObjectivesCard(objectives = lesson.objectives)
      }

      // 3. QUICK INTRODUCTION
      item {
        QuickIntroductionCard(intro = lesson.quickIntro)
      }

      // 4. SIMPLE EXPLANATION
      item {
        SimpleExplanationCard(explanation = lesson.simpleExplanation)
      }

      // 5. DETAILED EXPLANATION SECTIONS
      item {
        DetailedExplanationSection(sections = lesson.detailedSections)
      }

      // 6. REAL-WORLD EXAMPLES
      item {
        RealWorldExamplesSection(examples = lesson.realWorldExamples)
      }

      // 7. HOW IT WORKS (STEP-BY-STEP)
      item {
        HowItWorksSection(steps = lesson.howItWorksSteps)
      }

      // 8. EDUCATIONAL VISUAL DIAGRAM
      item {
        EducationalVisualSection(
          diagramType = lesson.visualDiagramType,
          lessonTitle = lesson.title
        )
      }

      // 9. IMPORTANT POINTS
      item {
        ImportantPointsCard(points = lesson.importantPoints)
      }

      // 10. COMMON MISTAKES
      item {
        CommonMistakesSection(mistakes = lesson.commonMistakes)
      }

      // 11. PRACTICAL ACTIVITY
      item {
        PracticalActivityCard(activity = lesson.practicalActivity)
      }

      // 12. QUICK KNOWLEDGE CHECK
      item {
        KnowledgeCheckCard(
          question = lesson.knowledgeCheck,
          lessonId = lesson.lessonId
        )
      }

      // 13. TAKE LESSON QUIZ (5 Questions)
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = NavyCard),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
              listOf(TechCyanAccent.copy(alpha = 0.5f), TechBluePrimary.copy(alpha = 0.3f))
            )
          )
        ) {
          Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(TechCyanAccent.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Quiz,
                  contentDescription = null,
                  tint = TechCyanAccent,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(
                  text = "Lesson Knowledge Quiz",
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = TextPrimary
                )
                Text(
                  text = "5 questions to verify your understanding & earn XP",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                  color = TextSecondary
                )
              }
            }

            Button(
              onClick = {
                viewModel.startQuizForLesson(lesson.lessonId)
                onNavigateToQuiz()
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .testTag("take_lesson_quiz_button"),
              colors = ButtonDefaults.buttonColors(containerColor = TechCyanAccent, contentColor = NavyDarkest),
              shape = RoundedCornerShape(12.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                Text("Take Lesson Quiz (5 Questions)", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
              }
            }
          }
        }
      }

      // Spacing for Bottom Navigation
      item {
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

// 1. Header Section
@Composable
private fun LessonHeaderSection(
  lesson: LessonDetailData,
  isCompleted: Boolean
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(
        Brush.verticalGradient(
          colors = listOf(
            TechBluePrimary.copy(alpha = 0.22f),
            NavyCard
          )
        )
      )
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(18.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Badges Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          LevelBadge(level = lesson.level)

          // Reading Time Pill
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(NavyDarkest.copy(alpha = 0.6f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Icon(
              imageVector = Icons.Default.AccessTime,
              contentDescription = null,
              tint = TechCyanAccent,
              modifier = Modifier.size(12.dp)
            )
            Text(
              text = "${lesson.readingTimeMinutes} mins read",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = TextSecondary
            )
          }
        }

        // Completion Status Pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isCompleted) TechGreen.copy(alpha = 0.15f) else TechIndigo.copy(alpha = 0.15f))
            .border(
              1.dp,
              if (isCompleted) TechGreen.copy(alpha = 0.4f) else TechIndigo.copy(alpha = 0.4f),
              RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.School,
              contentDescription = null,
              tint = if (isCompleted) TechGreen else TechCyanAccent,
              modifier = Modifier.size(12.dp)
            )
            Text(
              text = if (isCompleted) "Completed ✓" else "In Progress",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              ),
              color = if (isCompleted) TechGreen else TechCyanAccent
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = lesson.title,
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 22.sp
        ),
        color = TextPrimary
      )
    }
  }
}

// 2. Learning Objectives Card
@Composable
private fun LearningObjectivesCard(objectives: List<String>) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(TechCyanAccent.copy(alpha = 0.4f), NavyCardBorder)))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.CheckCircleOutline,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = "What You Will Learn",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TechCyanAccent
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      objectives.forEach { objective ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .padding(top = 4.dp)
              .size(6.dp)
              .clip(CircleShape)
              .background(TechCyanAccent)
          )
          Text(
            text = objective,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
            color = TextPrimary
          )
        }
      }
    }
  }
}

// 3. Quick Introduction Card
@Composable
private fun QuickIntroductionCard(intro: String) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(NavyCardElevated)
      .border(1.dp, TechBluePrimary.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
      .padding(16.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Lightbulb,
          contentDescription = null,
          tint = TechAmber,
          modifier = Modifier.size(18.dp)
        )
        Text(
          text = "Quick Introduction",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = TechAmber
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = intro,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontSize = 14.sp,
          lineHeight = 20.sp,
          fontWeight = FontWeight.Medium
        ),
        color = TextPrimary
      )
    }
  }
}

// 4. Simple Explanation Card
@Composable
private fun SimpleExplanationCard(explanation: String) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(
        Brush.horizontalGradient(
          colors = listOf(
            NavyDark,
            NavyCard
          )
        )
      )
      .border(1.dp, TechIndigo.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
      .padding(16.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.TipsAndUpdates,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(18.dp)
        )
        Text(
          text = "Plain English Analogy",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = TechCyanAccent
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = explanation,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontSize = 13.sp,
          lineHeight = 19.sp
        ),
        color = TextSecondary
      )
    }
  }
}

// 5. Detailed Explanation Section
@Composable
private fun DetailedExplanationSection(sections: List<Pair<String, String>>) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Text(
      text = "In-Depth Exploration",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
      color = TextPrimary
    )

    sections.forEach { (subHeading, body) ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
          .padding(16.dp)
      ) {
        Column {
          Text(
            text = subHeading,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            ),
            color = TechCyanAccent
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontSize = 13.sp,
              lineHeight = 19.sp
            ),
            color = TextSecondary
          )
        }
      }
    }
  }
}

// 6. Real-World Examples Section
@Composable
private fun RealWorldExamplesSection(examples: List<RealWorldExample>) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Real-World Applications",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
      color = TextPrimary
    )

    examples.forEach { example ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(NavyCardElevated)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
          .padding(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          CourseIcon(
            iconName = example.iconName,
            size = 40.dp,
            iconSize = 20.dp
          )

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = example.title,
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
              color = TechCyanAccent
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = example.description,
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                lineHeight = 17.sp
              ),
              color = TextPrimary
            )
          }
        }
      }
    }
  }
}

// 7. How It Works Section
@Composable
private fun HowItWorksSection(steps: List<HowItWorksStep>) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "How It Works: Step-by-Step",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
      color = TextPrimary
    )

    steps.forEachIndexed { index, step ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Step Badge
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(TechBluePrimary.copy(alpha = 0.2f))
            .border(1.dp, TechBluePrimary, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "${step.stepNumber}",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = TechCyanAccent
          )
        }

        // Step Content
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
            .padding(12.dp)
        ) {
          Column {
            Text(
              text = step.title,
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = step.description,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 16.sp),
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}

// 8. Educational Visual Diagram Section
@Composable
private fun EducationalVisualSection(
  diagramType: String,
  lessonTitle: String
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = "Interactive Visual Architecture",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
      color = TextPrimary
    )

    EducationalVisualDiagram(diagramType = diagramType)
  }
}

// 9. Important Points Card
@Composable
private fun ImportantPointsCard(points: List<String>) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(TechAmber.copy(alpha = 0.4f), NavyCardBorder)))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = null,
          tint = TechAmber,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = "Key Takeaways",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TechAmber
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      points.forEach { point ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Done,
            contentDescription = null,
            tint = TechGreen,
            modifier = Modifier
              .size(16.dp)
              .padding(top = 2.dp)
          )
          Text(
            text = point,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
            color = TextPrimary
          )
        }
      }
    }
  }
}

// 10. Common Mistakes Section
@Composable
private fun CommonMistakesSection(mistakes: List<CommonMistake>) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Icon(
        imageVector = Icons.Default.ErrorOutline,
        contentDescription = null,
        tint = TechRed,
        modifier = Modifier.size(20.dp)
      )
      Text(
        text = "Common Beginner Misunderstandings",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = TextPrimary
      )
    }

    mistakes.forEach { item ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(NavyCard)
          .border(1.dp, TechRed.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
          .padding(14.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          // Mistake row
          Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "❌ Myth:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = TechRed
            )
            Text(
              text = item.mistake,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 16.sp),
              color = TextPrimary
            )
          }

          // Correction row
          Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "✓ Reality:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = TechGreen
            )
            Text(
              text = item.correction,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 16.sp),
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}

// 11. Practical Activity Card
@Composable
private fun PracticalActivityCard(activity: PracticalActivity) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(
        Brush.verticalGradient(
          colors = listOf(
            NavyDark,
            NavyCardElevated
          )
        )
      )
      .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
      .padding(16.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.FitnessCenter,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = "Practical Hands-On Activity",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TechCyanAccent
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = activity.title,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = "Objective: ${activity.objective}",
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(10.dp))

      activity.steps.forEachIndexed { idx, step ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = "${idx + 1}.",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = TechCyanAccent
          )
          Text(
            text = step,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
            color = TextPrimary
          )
        }
      }
    }
  }
}

// 12. Quick Knowledge Check Card
@Composable
private fun KnowledgeCheckCard(
  question: LessonQuizQuestion,
  lessonId: String
) {
  var selectedIndex by remember(lessonId) { mutableStateOf<Int?>(null) }
  val isAnswered = selectedIndex != null

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("lesson_quiz_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(TechPurple.copy(alpha = 0.5f), NavyCardBorder)))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.HelpOutline,
          contentDescription = null,
          tint = TechPurple,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = "Quick Knowledge Check",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TechPurple
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = question.question,
        style = MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 14.sp
        ),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(14.dp))

      question.options.forEachIndexed { index, optionText ->
        val isSelected = selectedIndex == index
        val isCorrect = index == question.correctOptionIndex

        val optionBg by animateColorAsState(
          when {
            !isAnswered -> NavyDark
            isSelected && isCorrect -> TechGreen.copy(alpha = 0.2f)
            isSelected && !isCorrect -> TechRed.copy(alpha = 0.2f)
            isCorrect -> TechGreen.copy(alpha = 0.15f)
            else -> NavyDark
          },
          label = "option_bg"
        )

        val optionBorder by animateColorAsState(
          when {
            !isAnswered -> NavyCardBorder
            isSelected && isCorrect -> TechGreen
            isSelected && !isCorrect -> TechRed
            isCorrect -> TechGreen.copy(alpha = 0.6f)
            else -> NavyCardBorder
          },
          label = "option_border"
        )

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(optionBg)
            .border(1.dp, optionBorder, RoundedCornerShape(10.dp))
            .clickable {
              if (!isAnswered) {
                selectedIndex = index
              }
            }
            .padding(12.dp)
            .testTag("quiz_option_$index")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Icon(
              imageVector = when {
                isSelected && isCorrect -> Icons.Default.CheckCircle
                isSelected && !isCorrect -> Icons.Default.ErrorOutline
                isSelected -> Icons.Default.RadioButtonChecked
                else -> Icons.Default.RadioButtonUnchecked
              },
              contentDescription = null,
              tint = when {
                isSelected && isCorrect -> TechGreen
                isSelected && !isCorrect -> TechRed
                isAnswered && isCorrect -> TechGreen
                else -> TextTertiary
              },
              modifier = Modifier.size(18.dp)
            )

            Text(
              text = optionText,
              style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
              color = TextPrimary
            )
          }
        }
      }

      // Feedback explanation banner
      AnimatedVisibility(visible = isAnswered) {
        val isCorrect = selectedIndex == question.correctOptionIndex
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isCorrect) TechGreen.copy(alpha = 0.15f) else TechIndigo.copy(alpha = 0.15f))
            .border(
              1.dp,
              if (isCorrect) TechGreen.copy(alpha = 0.4f) else TechIndigo.copy(alpha = 0.4f),
              RoundedCornerShape(10.dp)
            )
            .padding(12.dp)
        ) {
          Column {
            Text(
              text = if (isCorrect) "✓ Correct!" else "Explanation:",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = if (isCorrect) TechGreen else TechCyanAccent
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = question.explanation,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
              color = TextPrimary
            )
          }
        }
      }
    }
  }
}

// 13. Lesson Bottom Navigation Bar
@Composable
private fun LessonBottomNavigationBar(
  lesson: LessonDetailData,
  isCompleted: Boolean,
  onToggleCompletion: () -> Unit,
  onPrevClick: () -> Unit,
  onNextClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(NavyDarkest)
      .border(1.dp, NavyCardBorder)
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Previous Lesson Button
      if (lesson.prevLessonId != null) {
        Button(
          onClick = onPrevClick,
          colors = ButtonDefaults.buttonColors(containerColor = NavyCard),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .height(44.dp)
            .testTag("prev_lesson_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Previous",
            modifier = Modifier.size(16.dp)
          )
        }
      }

      // Mark Complete Primary CTA
      Button(
        onClick = onToggleCompletion,
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isCompleted) TechGreen else TechBluePrimary
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .weight(1f)
          .height(44.dp)
          .testTag("mark_complete_button")
      ) {
        Icon(
          imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = if (isCompleted) "Completed ✓" else "Mark Complete",
          style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
      }

      // Next Lesson Button
      if (lesson.nextLessonId != null) {
        Button(
          onClick = onNextClick,
          colors = ButtonDefaults.buttonColors(containerColor = TechCyanAccent, contentColor = NavyDarkest),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .height(44.dp)
            .testTag("next_lesson_button")
        ) {
          Text(
            text = "Next",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
