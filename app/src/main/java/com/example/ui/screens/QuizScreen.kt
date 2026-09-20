package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Quiz
import com.example.data.model.QuizAttempt
import com.example.data.model.QuizOption
import com.example.data.model.QuizType
import com.example.data.model.QuizUserAnswer
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
import com.example.ui.viewmodel.ActiveQuizState
import com.example.ui.viewmodel.ComputerMasterViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QuizScreen(
  viewModel: ComputerMasterViewModel,
  modifier: Modifier = Modifier,
) {
  val activeState by viewModel.activeQuizState.collectAsState()
  val quizzes by viewModel.quizzes.collectAsState()
  val recentAttempts by viewModel.recentAttempts.collectAsState()

  if (activeState.activeQuiz != null) {
    if (activeState.isReviewMode) {
      QuizReviewView(
        state = activeState,
        onBackToResults = { viewModel.closeReviewMode() },
        onExit = { viewModel.exitQuiz() }
      )
    } else {
      ActiveQuizSession(
        state = activeState,
        onSelectOption = { viewModel.selectQuizOption(it) },
        onNextQuestion = { viewModel.nextQuizQuestion() },
        onExit = { viewModel.exitQuiz() },
        onRetry = { viewModel.retryQuiz() },
        onReviewAnswers = { viewModel.openReviewMode() }
      )
    }
  } else {
    QuizDashboard(
      quizzes = quizzes,
      recentAttempts = recentAttempts,
      onStartQuiz = { viewModel.startQuiz(it) },
      modifier = modifier
    )
  }
}

// -------------------------------------------------------------
// 1. QUIZ DASHBOARD
// -------------------------------------------------------------
@Composable
private fun QuizDashboard(
  quizzes: List<Quiz>,
  recentAttempts: List<QuizAttempt>,
  onStartQuiz: (Quiz) -> Unit,
  modifier: Modifier = Modifier,
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: All Quizzes, 1: History

  val dailyQuiz = quizzes.find { it.type == QuizType.DAILY_CHECK }
  val finalQuiz = quizzes.find { it.type == QuizType.FINAL_EXAM && it.courseId == "course_comp_basics" }
  val lessonQuizzes = quizzes.filter { it.type == QuizType.LESSON && it.courseId == "course_comp_basics" }
  val otherQuizzes = quizzes.filter { it.courseId != "course_comp_basics" }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("quiz_screen"),
    contentPadding = PaddingValues(bottom = 96.dp)
  ) {
    // Header
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
          .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Quiz & Exams",
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
              ),
              color = TextPrimary
            )
            Text(
              text = "Master your computer skills with active recall",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
              color = TextSecondary
            )
          }

          Box(
            modifier = Modifier
              .size(42.dp)
              .background(TechIndigo.copy(alpha = 0.2f), CircleShape)
              .border(1.dp, TechIndigo.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.EmojiEvents,
              contentDescription = null,
              tint = TechAmber,
              modifier = Modifier.size(22.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Segmented Tab Switcher: Quizzes vs History
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
            .padding(4.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (selectedTab == 0) TechBluePrimary else Color.Transparent)
              .clickable { selectedTab = 0 }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Quizzes & Tests",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                color = if (selectedTab == 0) Color.White else TextSecondary
              )
            )
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (selectedTab == 1) TechBluePrimary else Color.Transparent)
              .clickable { selectedTab = 1 }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "History & Results (${recentAttempts.size})",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                color = if (selectedTab == 1) Color.White else TextSecondary
              )
            )
          }
        }
      }
    }

    if (selectedTab == 0) {
      // 1. Featured Daily Knowledge Check
      if (dailyQuiz != null) {
        item {
          QuizFeaturedCard(
            badge = "DAILY KNOWLEDGE CHECK",
            badgeColor = TechGreen,
            title = "Daily Knowledge Check",
            subtitle = "Test your computer knowledge today.",
            metaText = "${dailyQuiz.questions.size} Questions • Instant Feedback",
            icon = Icons.Default.Timer,
            buttonText = "Start Daily Check",
            bestScore = dailyQuiz.bestScore,
            onClick = { onStartQuiz(dailyQuiz) }
          )
        }
      }

      // 2. Featured Final Course Certification Exam
      if (finalQuiz != null) {
        item {
          QuizFeaturedCard(
            badge = "FINAL COURSE QUIZ",
            badgeColor = TechPurple,
            title = "Computer Basics Final Exam",
            subtitle = "Comprehensive 20-question test covering all 10 lessons. Pass score: 70%.",
            metaText = "20 Questions • 15 Mins • Certificate Level",
            icon = Icons.Default.EmojiEvents,
            buttonText = "Take Final Exam",
            bestScore = finalQuiz.bestScore,
            onClick = { onStartQuiz(finalQuiz) }
          )
        }
      }

      // 3. Lesson Quizzes Header
      item {
        Spacer(modifier = Modifier.height(14.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Computer Basics Lesson Quizzes",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            ),
            color = TextPrimary
          )
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(TechCyanAccent.copy(alpha = 0.15f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = "10 Quizzes",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TechCyanAccent
              )
            )
          }
        }
      }

      // 10 Lesson Quiz Items
      itemsIndexed(lessonQuizzes) { index, quiz ->
        LessonQuizRowItem(
          index = index + 1,
          quiz = quiz,
          onStart = { onStartQuiz(quiz) }
        )
      }

      // 4. Other Chapter Quizzes
      if (otherQuizzes.isNotEmpty()) {
        item {
          Spacer(modifier = Modifier.height(18.dp))
          Text(
            text = "Additional Subject Quizzes",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            ),
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
          )
        }

        items(otherQuizzes) { quiz ->
          OtherQuizRowItem(
            quiz = quiz,
            onStart = { onStartQuiz(quiz) }
          )
        }
      }
    } else {
      // HISTORY TAB: Show saved attempts from Room database
      if (recentAttempts.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = "No Quiz Attempts Yet",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextSecondary
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Take a lesson quiz or daily check to build your score history!",
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiary,
                textAlign = TextAlign.Center
              )
            }
          }
        }
      } else {
        item {
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Recorded Attempts (${recentAttempts.size})",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TechCyanAccent
            ),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
          )
        }

        items(recentAttempts) { attempt ->
          QuizAttemptHistoryCard(attempt = attempt)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// FEATURED QUIZ CARD (Daily Check & Final Exam)
// -------------------------------------------------------------
@Composable
private fun QuizFeaturedCard(
  badge: String,
  badgeColor: Color,
  title: String,
  subtitle: String,
  metaText: String,
  icon: ImageVector,
  buttonText: String,
  bestScore: Int?,
  onClick: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.verticalGradient(
        listOf(badgeColor.copy(alpha = 0.5f), NavyCardBorder)
      )
    )
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(badgeColor.copy(alpha = 0.15f))
            .border(1.dp, badgeColor.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = badge,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 10.sp,
              letterSpacing = 0.8.sp
            ),
            color = badgeColor
          )
        }

        if (bestScore != null) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.EmojiEvents,
              contentDescription = null,
              tint = TechAmber,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "Best: $bestScore%",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (bestScore >= 70) TechGreen else TechAmber
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(badgeColor.copy(alpha = 0.15f))
            .border(1.dp, badgeColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = badgeColor,
            modifier = Modifier.size(24.dp)
          )
        }

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            ),
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = TextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = metaText,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
        color = TextTertiary
      )

      Spacer(modifier = Modifier.height(14.dp))

      Button(
        onClick = onClick,
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .testTag("quiz_start_button_${badge.lowercase().replace(" ", "_")}"),
        colors = ButtonDefaults.buttonColors(
          containerColor = badgeColor,
          contentColor = if (badgeColor == TechCyanAccent) NavyDarkest else Color.White
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = buttonText,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
    }
  }
}

// -------------------------------------------------------------
// LESSON QUIZ ROW ITEM (For 10 Computer Basics Lessons)
// -------------------------------------------------------------
@Composable
private fun LessonQuizRowItem(
  index: Int,
  quiz: Quiz,
  onStart: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 5.dp)
      .clickable { onStart() }
      .testTag("lesson_quiz_item_$index"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(
        listOf(TechBluePrimary.copy(alpha = 0.3f), NavyCardBorder)
      )
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.weight(1f)
      ) {
        // Number badge
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(NavyCardElevated)
            .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "$index",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TechCyanAccent
            )
          )
        }

        Column {
          Text(
            text = quiz.lessonTitle ?: quiz.title,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 14.sp
            ),
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "5 Questions • 4 mins",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextSecondary
            )
            if (quiz.bestScore != null) {
              Text(
                text = "•",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TextTertiary
              )
              Text(
                text = "Best: ${quiz.bestScore}%",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (quiz.bestScore >= 70) TechGreen else TechAmber
                )
              )
            }
          }
        }
      }

      Button(
        onClick = onStart,
        modifier = Modifier.height(34.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (quiz.bestScore != null && quiz.bestScore >= 70) TechGreen.copy(alpha = 0.8f) else TechBluePrimary
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
      ) {
        Text(
          text = if (quiz.bestScore != null && quiz.bestScore >= 70) "Retake" else "Start",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}

// -------------------------------------------------------------
// OTHER QUIZ ROW ITEM (Python, Networking, Cyber)
// -------------------------------------------------------------
@Composable
private fun OtherQuizRowItem(
  quiz: Quiz,
  onStart: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 5.dp)
      .clickable { onStart() },
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(
        listOf(TechIndigo.copy(alpha = 0.3f), NavyCardBorder)
      )
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = quiz.title,
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
          color = TextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "${quiz.category} • ${quiz.questions.size} Questions • ${quiz.durationMinutes} mins",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = TextSecondary
        )
      }

      Button(
        onClick = onStart,
        modifier = Modifier.height(34.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TechIndigo),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
      ) {
        Text(
          text = "Take Quiz",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}

// -------------------------------------------------------------
// QUIZ ATTEMPT HISTORY CARD
// -------------------------------------------------------------
@Composable
private fun QuizAttemptHistoryCard(attempt: QuizAttempt) {
  val isPassed = attempt.scorePercentage >= 70
  val dateFormatted = remember(attempt.timestamp) {
    SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault()).format(Date(attempt.timestamp))
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 5.dp)
      .clip(RoundedCornerShape(14.dp))
      .background(NavyCard)
      .border(
        width = 1.dp,
        color = if (isPassed) TechGreen.copy(alpha = 0.3f) else TechRed.copy(alpha = 0.3f),
        shape = RoundedCornerShape(14.dp)
      )
      .padding(14.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = attempt.quizTitle,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            ),
            color = TextPrimary
          )

          // Pass / Fail chip
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (isPassed) TechGreen.copy(alpha = 0.15f) else TechRed.copy(alpha = 0.15f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = if (isPassed) "PASSED" else "NOT PASSED",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 9.sp,
                color = if (isPassed) TechGreen else TechRed
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "${attempt.courseTitle} • ${attempt.correctAnswers}/${attempt.totalQuestions} Correct",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
          color = TextSecondary
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = dateFormatted,
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = TextTertiary
        )
      }

      // Score percentage bubble
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(if (isPassed) TechGreen.copy(alpha = 0.15f) else TechAmber.copy(alpha = 0.15f))
          .border(
            width = 1.5.dp,
            color = if (isPassed) TechGreen else TechAmber,
            shape = CircleShape
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "${attempt.scorePercentage}%",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = if (isPassed) TechGreen else TechAmber
          )
        )
      }
    }
  }
}

// -------------------------------------------------------------
// 2. ACTIVE QUIZ SESSION
// -------------------------------------------------------------
@Composable
private fun ActiveQuizSession(
  state: ActiveQuizState,
  onSelectOption: (Int) -> Unit,
  onNextQuestion: () -> Unit,
  onExit: () -> Unit,
  onRetry: () -> Unit,
  onReviewAnswers: () -> Unit,
) {
  val quiz = state.activeQuiz ?: return

  if (state.isQuizCompleted) {
    QuizResultView(
      quiz = quiz,
      scorePercentage = state.scorePercentage,
      correctAnswers = state.correctAnswersCount,
      totalQuestions = quiz.questions.size,
      onReview = onReviewAnswers,
      onRetry = onRetry,
      onExit = onExit
    )
    return
  }

  val currentQuestion = quiz.questions.getOrNull(state.currentQuestionIndex) ?: return
  val totalQuestions = quiz.questions.size
  val progress = (state.currentQuestionIndex + 1).toFloat() / totalQuestions

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .statusBarsPadding()
      .padding(horizontal = 20.dp, vertical = 10.dp)
      .testTag("active_quiz_session"),
    contentPadding = PaddingValues(bottom = 96.dp)
  ) {
    // Top Bar: Exit + Quiz Title + Counter
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onExit,
          modifier = Modifier
            .size(38.dp)
            .background(NavyCard, CircleShape)
            .border(1.dp, NavyCardBorder, CircleShape)
            .testTag("quiz_exit_button")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Exit Quiz",
            tint = TextSecondary,
            modifier = Modifier.size(18.dp)
          )
        }

        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .weight(1f)
            .padding(horizontal = 12.dp)
        ) {
          Text(
            text = quiz.courseTitle,
            style = MaterialTheme.typography.labelSmall.copy(
              color = TechCyanAccent,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          )
          Text(
            text = quiz.lessonTitle ?: quiz.title,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            ),
            color = TextPrimary,
            maxLines = 1
          )
        }

        // Question pill
        Box(
          modifier = Modifier
            .background(TechBluePrimary.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
            .border(1.dp, TechBluePrimary.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "${state.currentQuestionIndex + 1} / $totalQuestions",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TechCyanAccent
            )
          )
        }
      }
    }

    // Linear Progress Bar
    item {
      Spacer(modifier = Modifier.height(14.dp))
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = TechCyanAccent,
        trackColor = NavyCard
      )
    }

    // Question Card
    item {
      Spacer(modifier = Modifier.height(18.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(NavyCardElevated)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
          .padding(20.dp)
      ) {
        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(TechCyanAccent.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = "QUESTION ${state.currentQuestionIndex + 1} OF $totalQuestions",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.ExtraBold,
                  letterSpacing = 0.8.sp,
                  color = TechCyanAccent,
                  fontSize = 10.sp
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = currentQuestion.question,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              lineHeight = 23.sp
            ),
            color = TextPrimary
          )
        }
      }
    }

    // Instruction Prompt
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = if (state.isAnswerSubmitted) "Answer Recorded — Immediate Feedback:" else "Select the correct answer:",
        style = MaterialTheme.typography.labelMedium.copy(
          color = if (state.isAnswerSubmitted) TechCyanAccent else TextSecondary,
          fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(bottom = 6.dp)
      )
    }

    // 4 Answer Options (Instant feedback when selected)
    itemsIndexed(currentQuestion.options) { optIndex, option ->
      val optionLetter = when (optIndex) {
        0 -> "A"
        1 -> "B"
        2 -> "C"
        3 -> "D"
        else -> "${optIndex + 1}"
      }
      val isSelected = (state.selectedOptionIndex == option.id)
      val isSubmitted = state.isAnswerSubmitted
      val isCorrect = (option.id == currentQuestion.correctOptionIndex)

      val (bgColor, borderColor, textColor) = when {
        isSubmitted && isCorrect -> Triple(TechGreen.copy(alpha = 0.2f), TechGreen, TechGreen)
        isSubmitted && isSelected && !isCorrect -> Triple(TechRed.copy(alpha = 0.2f), TechRed, TechRed)
        isSelected -> Triple(TechBluePrimary.copy(alpha = 0.25f), TechCyanAccent, TextPrimary)
        else -> Triple(NavyCard, NavyCardBorder, TextPrimary)
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 5.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(bgColor)
          .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
          .clickable(enabled = !isSubmitted) { onSelectOption(option.id) }
          .padding(14.dp)
          .testTag("quiz_option_${option.id}")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
          ) {
            // Letter Badge: A, B, C, D
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(
                  when {
                    isSubmitted && isCorrect -> TechGreen
                    isSubmitted && isSelected && !isCorrect -> TechRed
                    isSelected -> TechCyanAccent
                    else -> NavyCardElevated
                  }
                ),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = optionLetter,
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = when {
                    isSubmitted && isCorrect -> Color.White
                    isSubmitted && isSelected && !isCorrect -> Color.White
                    isSelected -> NavyDarkest
                    else -> TextSecondary
                  }
                )
              )
            }

            Text(
              text = option.text,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected || (isSubmitted && isCorrect)) FontWeight.Bold else FontWeight.Medium,
                fontSize = 14.sp
              ),
              color = textColor
            )
          }

          if (isSubmitted) {
            if (isCorrect) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Correct",
                tint = TechGreen,
                modifier = Modifier.size(22.dp)
              )
            } else if (isSelected) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Incorrect",
                tint = TechRed,
                modifier = Modifier.size(22.dp)
              )
            }
          }
        }
      }
    }

    // Explanation Box (Revealed smoothly immediately upon selecting an answer)
    item {
      AnimatedVisibility(
        visible = state.isAnswerSubmitted,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        val selectedOption = currentQuestion.options.find { it.id == state.selectedOptionIndex }
        val isUserCorrect = (state.selectedOptionIndex == currentQuestion.correctOptionIndex)

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(NavyCard)
            .border(
              width = 1.dp,
              color = if (isUserCorrect) TechGreen.copy(alpha = 0.5f) else TechAmber.copy(alpha = 0.5f),
              shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp)
        ) {
          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(
                imageVector = if (isUserCorrect) Icons.Default.CheckCircle else Icons.Default.HelpOutline,
                contentDescription = null,
                tint = if (isUserCorrect) TechGreen else TechAmber,
                modifier = Modifier.size(18.dp)
              )
              Text(
                text = if (isUserCorrect) "Correct! Explanation:" else "Incorrect. Here's why:",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isUserCorrect) TechGreen else TechAmber
                )
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = currentQuestion.explanation,
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 13.sp,
                lineHeight = 18.sp
              ),
              color = TextPrimary
            )
          }
        }
      }
    }

    // Bottom Action Button: Next Question or View Results
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Button(
        onClick = onNextQuestion,
        enabled = state.isAnswerSubmitted,
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("next_question_button"),
        colors = ButtonDefaults.buttonColors(
          containerColor = TechCyanAccent,
          contentColor = NavyDarkest,
          disabledContainerColor = NavyCard,
          disabledContentColor = TextTertiary
        ),
        shape = RoundedCornerShape(14.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = if (!state.isAnswerSubmitted) {
              "Select an option to continue"
            } else if (state.currentQuestionIndex + 1 < totalQuestions) {
              "Next Question"
            } else {
              "View Results"
            },
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          )
          if (state.isAnswerSubmitted) {
            Icon(
              imageVector = Icons.Default.ArrowForward,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 3. QUIZ RESULT VIEW (Pass / Fail, Stats, Review, Retry, Exit)
// -------------------------------------------------------------
@Composable
private fun QuizResultView(
  quiz: Quiz,
  scorePercentage: Int,
  correctAnswers: Int,
  totalQuestions: Int,
  onReview: () -> Unit,
  onRetry: () -> Unit,
  onExit: () -> Unit,
) {
  val isPassed = scorePercentage >= 70
  val incorrectAnswers = totalQuestions - correctAnswers

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .statusBarsPadding()
      .padding(horizontal = 24.dp, vertical = 16.dp)
      .testTag("quiz_result_view"),
    horizontalAlignment = Alignment.CenterHorizontally,
    contentPadding = PaddingValues(bottom = 96.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(10.dp))

      // Radial Score Badge
      Box(
        modifier = Modifier
          .size(110.dp)
          .background(
            brush = Brush.radialGradient(
              listOf(
                if (isPassed) TechGreen.copy(alpha = 0.35f) else TechAmber.copy(alpha = 0.35f),
                Color.Transparent
              )
            ),
            shape = CircleShape
          )
          .border(
            width = 3.dp,
            color = if (isPassed) TechGreen else TechAmber,
            shape = CircleShape
          ),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "$scorePercentage%",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 28.sp,
              color = if (isPassed) TechGreen else TechAmber
            )
          )
          Text(
            text = "$correctAnswers / $totalQuestions",
            style = MaterialTheme.typography.labelSmall.copy(
              color = TextSecondary,
              fontSize = 11.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Status Badge: PASSED vs NOT PASSED (Threshold: 70%)
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(if (isPassed) TechGreen.copy(alpha = 0.2f) else TechAmber.copy(alpha = 0.2f))
          .border(
            width = 1.dp,
            color = if (isPassed) TechGreen else TechAmber,
            shape = RoundedCornerShape(20.dp)
          )
          .padding(horizontal = 16.dp, vertical = 6.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = if (isPassed) Icons.Default.CheckCircle else Icons.Default.Close,
            contentDescription = null,
            tint = if (isPassed) TechGreen else TechAmber,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = if (isPassed) "PASSED (70% REQUIRED)" else "NOT PASSED (70% REQUIRED)",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = if (isPassed) TechGreen else TechAmber,
              letterSpacing = 0.5.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = if (scorePercentage >= 90) {
          "Mastery Demonstrated!"
        } else if (isPassed) {
          "Great Job, You Passed!"
        } else {
          "Keep Studying, You Can Do It!"
        },
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        ),
        color = TextPrimary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "${quiz.lessonTitle ?: quiz.title} • ${quiz.courseTitle}",
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
        color = TextSecondary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Detailed Score Summary Card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
          .padding(18.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Score", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              "$scorePercentage%",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = TechCyanAccent
            )
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Correct", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              "$correctAnswers",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = TechGreen
            )
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Incorrect", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              "$incorrectAnswers",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = if (incorrectAnswers > 0) TechRed else TextSecondary
            )
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("XP Earned", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              "+${scorePercentage * 2} XP",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = TechAmber
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 1. Primary Action: Review Answers
      Button(
        onClick = onReview,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("review_answers_button"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TechCyanAccent, contentColor = NavyDarkest)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
          Text(
            text = "Review Answers",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 2. Retry Quiz Button
      OutlinedButton(
        onClick = onRetry,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("retry_quiz_button"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(TechBluePrimary, NavyCardBorder)))
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
          Text(
            text = "Retry Quiz",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 3. Continue Learning Button (Exits back)
      Button(
        onClick = onExit,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("continue_learning_button"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NavyCard, contentColor = TextPrimary)
      ) {
        Text(
          text = "Continue Learning",
          style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}

// -------------------------------------------------------------
// 4. QUIZ REVIEW VIEW (Review Answers Screen)
// -------------------------------------------------------------
@Composable
private fun QuizReviewView(
  state: ActiveQuizState,
  onBackToResults: () -> Unit,
  onExit: () -> Unit,
) {
  val quiz = state.activeQuiz ?: return
  val totalQuestions = quiz.questions.size

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .statusBarsPadding()
      .padding(horizontal = 20.dp, vertical = 10.dp)
      .testTag("quiz_review_view"),
    contentPadding = PaddingValues(bottom = 96.dp)
  ) {
    // Top Bar
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBackToResults,
          modifier = Modifier
            .size(38.dp)
            .background(NavyCard, CircleShape)
            .border(1.dp, NavyCardBorder, CircleShape)
            .testTag("review_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = TextPrimary,
            modifier = Modifier.size(18.dp)
          )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "Review Answers",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = TextPrimary
          )
          Text(
            text = "Score: ${state.scorePercentage}% (${state.correctAnswersCount}/$totalQuestions)",
            style = MaterialTheme.typography.labelSmall.copy(color = TechCyanAccent)
          )
        }

        IconButton(
          onClick = onExit,
          modifier = Modifier
            .size(38.dp)
            .background(NavyCard, CircleShape)
            .border(1.dp, NavyCardBorder, CircleShape)
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = TextSecondary,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(14.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
          .padding(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = quiz.lessonTitle ?: quiz.title,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = TextPrimary
            )
            Text(
              text = "Carefully review each question and its detailed explanation below.",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
              color = TextSecondary
            )
          }
        }
      }
    }

    // List of each question reviewed
    itemsIndexed(quiz.questions) { qIndex, question ->
      val userAnswer = state.userAnswers.find { it.questionIndex == qIndex }
      val userSelectedOptionId = userAnswer?.selectedOptionIndex
      val isCorrect = (userSelectedOptionId == question.correctOptionIndex)

      Spacer(modifier = Modifier.height(14.dp))

      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavyCard),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = Brush.horizontalGradient(
            if (isCorrect) {
              listOf(TechGreen.copy(alpha = 0.5f), NavyCardBorder)
            } else {
              listOf(TechRed.copy(alpha = 0.5f), NavyCardBorder)
            }
          )
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Question Header: Number + Result Chip
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Question ${qIndex + 1} of $totalQuestions",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TechCyanAccent
              )
            )

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(if (isCorrect) TechGreen.copy(alpha = 0.15f) else TechRed.copy(alpha = 0.15f))
                .border(
                  width = 1.dp,
                  color = if (isCorrect) TechGreen.copy(alpha = 0.4f) else TechRed.copy(alpha = 0.4f),
                  shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                  contentDescription = null,
                  tint = if (isCorrect) TechGreen else TechRed,
                  modifier = Modifier.size(12.dp)
                )
                Text(
                  text = if (isCorrect) "Correct" else "Incorrect",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    color = if (isCorrect) TechGreen else TechRed
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = question.question,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              lineHeight = 21.sp
            ),
            color = TextPrimary
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Options Breakdown
          question.options.forEachIndexed { optIdx, opt ->
            val isUserChoice = (opt.id == userSelectedOptionId)
            val isCorrectChoice = (opt.id == question.correctOptionIndex)

            val (optBg, optBorder, optText) = when {
              isCorrectChoice -> Triple(TechGreen.copy(alpha = 0.18f), TechGreen, TechGreen)
              isUserChoice && !isCorrectChoice -> Triple(TechRed.copy(alpha = 0.18f), TechRed, TechRed)
              else -> Triple(NavyCardElevated, NavyCardBorder, TextSecondary)
            }

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(optBg)
                .border(1.dp, optBorder, RoundedCornerShape(10.dp))
                .padding(10.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = opt.text,
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = if (isUserChoice || isCorrectChoice) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 13.sp
                  ),
                  color = optText,
                  modifier = Modifier.weight(1f)
                )

                if (isCorrectChoice) {
                  Text(
                    text = "Correct Answer ✓",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = TechGreen,
                      fontSize = 11.sp
                    )
                  )
                } else if (isUserChoice) {
                  Text(
                    text = "Your Choice ✗",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = TechRed,
                      fontSize = 11.sp
                    )
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Explanation Box
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(NavyDarkest)
              .border(1.dp, TechCyanAccent.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
              .padding(12.dp)
          ) {
            Column {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.HelpOutline,
                  contentDescription = null,
                  tint = TechCyanAccent,
                  modifier = Modifier.size(14.dp)
                )
                Text(
                  text = "Explanation",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TechCyanAccent
                  )
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = question.explanation,
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

    // Bottom Finish Review Button
    item {
      Spacer(modifier = Modifier.height(20.dp))
      Button(
        onClick = onBackToResults,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("done_review_button"),
        colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text(
          text = "Back to Results",
          style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}
