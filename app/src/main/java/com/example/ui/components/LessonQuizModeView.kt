package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizOption
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizUserAnswer
import com.example.data.repository.LessonQuizGenerator
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel

/**
 * Dedicated 'Quiz Mode' for individual lessons.
 * Generates 5 multiple-choice questions based on the lesson content, allowing users
 * to test their understanding, receive instant explanations, and track mastery.
 */
@Composable
fun LessonQuizModeView(
  lessonId: String,
  lessonTitle: String,
  courseId: String,
  courseTitle: String,
  viewModel: ComputerMasterViewModel,
  onExitQuizMode: () -> Unit,
  modifier: Modifier = Modifier
) {
  val quiz = remember(lessonId) {
    LessonQuizGenerator.generateQuizForLesson(
      lessonId = lessonId,
      lessonTitle = lessonTitle,
      courseId = courseId,
      courseTitle = courseTitle
    )
  }

  val totalQuestions = quiz.questions.size.coerceAtLeast(5)
  var currentQuestionIndex by remember { mutableIntStateOf(0) }
  var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
  var isAnswerSubmitted by remember { mutableStateOf(false) }
  var correctCount by remember { mutableIntStateOf(0) }
  var isQuizCompleted by remember { mutableStateOf(false) }
  var showReviewAll by remember { mutableStateOf(false) }

  val userAnswers = remember { mutableStateListOf<QuizUserAnswer>() }

  val currentQuestion: QuizQuestion? = quiz.questions.getOrNull(currentQuestionIndex)

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("lesson_quiz_mode_view")
  ) {
    // 1. Top Header Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(NavyDark)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.weight(1f)
      ) {
        IconButton(
          onClick = onExitQuizMode,
          modifier = Modifier
            .size(36.dp)
            .testTag("quiz_mode_exit_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Exit Quiz Mode",
            tint = TextPrimary
          )
        }

        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(TechCyanAccent.copy(alpha = 0.15f))
                .border(0.5.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "QUIZ MODE",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 9.sp
                ),
                color = TechCyanAccent
              )
            }
            Text(
              text = lessonTitle,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = TextPrimary,
              maxLines = 1
            )
          }
          Text(
            text = "5 Multiple-Choice Questions • Test Understanding",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = TextSecondary
          )
        }
      }

      if (!isQuizCompleted) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(
            text = "${currentQuestionIndex + 1} / $totalQuestions",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            ),
            color = TechCyanAccent
          )
        }
      }
    }

    // 2. Progress Bar
    if (!isQuizCompleted) {
      val progress = (currentQuestionIndex.toFloat() / totalQuestions.toFloat()).coerceIn(0f, 1f)
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(3.dp),
        color = TechCyanAccent,
        trackColor = NavyDark
      )
    }

    // 3. Main Content: Question Card or Results Screen
    if (!isQuizCompleted && currentQuestion != null) {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Question Container
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = NavyCard),
            border = BorderStroke(1.dp, NavyCardBorder)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(TechBluePrimary.copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "Q${currentQuestionIndex + 1}",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.sp
                    ),
                    color = TechCyanAccent
                  )
                }

                Text(
                  text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
                  style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                  color = TextSecondary
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = currentQuestion.question,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  lineHeight = 22.sp
                ),
                color = TextPrimary
              )
            }
          }
        }

        // 4 Options
        itemsIndexed(currentQuestion.options) { index, option ->
          QuizOptionItem(
            option = option,
            index = index,
            isSelected = selectedOptionIndex == index,
            isSubmitted = isAnswerSubmitted,
            isCorrect = index == currentQuestion.correctOptionIndex,
            enabled = !isAnswerSubmitted,
            onClick = {
              if (!isAnswerSubmitted) {
                selectedOptionIndex = index
              }
            }
          )
        }

        // Explanation Card (Expands when answer is submitted)
        if (isAnswerSubmitted) {
          item {
            val isUserCorrect = selectedOptionIndex == currentQuestion.correctOptionIndex
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .testTag("quiz_explanation_card"),
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isUserCorrect) TechGreen.copy(alpha = 0.12f) else TechAmber.copy(alpha = 0.12f)
              ),
              border = BorderStroke(
                1.dp,
                if (isUserCorrect) TechGreen.copy(alpha = 0.45f) else TechAmber.copy(alpha = 0.45f)
              )
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Icon(
                    imageVector = if (isUserCorrect) Icons.Default.Check else Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = if (isUserCorrect) TechGreen else TechAmber,
                    modifier = Modifier.size(18.dp)
                  )
                  Text(
                    text = if (isUserCorrect) "Correct! Explanation:" else "Incorrect. Here's why:",
                    style = MaterialTheme.typography.labelMedium.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 13.sp
                    ),
                    color = if (isUserCorrect) TechGreen else TechAmber
                  )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                  text = currentQuestion.explanation,
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp
                  ),
                  color = TextPrimary
                )
              }
            }
          }
        }
      }

      // Bottom Action Button (Submit Answer or Next Question)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(NavyDark)
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        if (!isAnswerSubmitted) {
          Button(
            onClick = {
              if (selectedOptionIndex != null) {
                isAnswerSubmitted = true
                val isCorrect = selectedOptionIndex == currentQuestion.correctOptionIndex
                if (isCorrect) {
                  correctCount++
                  viewModel.soundManager.playQuizCorrect()
                } else {
                  viewModel.soundManager.playQuizWrong()
                }

                userAnswers.add(
                  QuizUserAnswer(
                    questionIndex = currentQuestionIndex,
                    question = currentQuestion,
                    selectedOptionIndex = selectedOptionIndex!!,
                    isCorrect = isCorrect
                  )
                )
              }
            },
            enabled = selectedOptionIndex != null,
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("submit_lesson_quiz_answer_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = TechCyanAccent,
              contentColor = NavyDarkest,
              disabledContainerColor = NavyCardBorder,
              disabledContentColor = TextTertiary
            ),
            shape = RoundedCornerShape(14.dp)
          ) {
            Text(
              text = "Check Answer",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
          }
        } else {
          Button(
            onClick = {
              if (currentQuestionIndex + 1 < totalQuestions) {
                currentQuestionIndex++
                selectedOptionIndex = null
                isAnswerSubmitted = false
              } else {
                isQuizCompleted = true
                // Persist score & XP reward
                val scorePercent = ((correctCount.toFloat() / totalQuestions) * 100).toInt()
                viewModel.submitCompletedQuiz(
                  quiz = quiz,
                  userAnswers = userAnswers.toList(),
                  scorePercentage = scorePercent,
                  correctCount = correctCount,
                  totalQuestions = totalQuestions
                )
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("next_lesson_quiz_question_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = TechBluePrimary,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Text(
                text = if (currentQuestionIndex + 1 < totalQuestions) "Next Question" else "See Lesson Mastery Results",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
              )
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    } else if (isQuizCompleted) {
      // 4. Results View
      LessonQuizResultsView(
        lessonTitle = lessonTitle,
        correctCount = correctCount,
        totalQuestions = totalQuestions,
        userAnswers = userAnswers,
        showReviewAll = showReviewAll,
        onToggleReview = { showReviewAll = !showReviewAll },
        onRetake = {
          currentQuestionIndex = 0
          selectedOptionIndex = null
          isAnswerSubmitted = false
          correctCount = 0
          isQuizCompleted = false
          showReviewAll = false
          userAnswers.clear()
        },
        onExit = onExitQuizMode
      )
    }
  }
}

/**
 * Reusable Option Card for Lesson Quiz Mode questions.
 */
@Composable
private fun QuizOptionItem(
  option: QuizOption,
  index: Int,
  isSelected: Boolean,
  isSubmitted: Boolean,
  isCorrect: Boolean,
  enabled: Boolean,
  onClick: () -> Unit
) {
  val letter = ('A' + index).toString()

  val backgroundColor by animateColorAsState(
    targetValue = when {
      isSubmitted && isCorrect -> TechGreen.copy(alpha = 0.18f)
      isSubmitted && isSelected && !isCorrect -> TechRed.copy(alpha = 0.18f)
      isSelected -> TechBluePrimary.copy(alpha = 0.22f)
      else -> NavyCard
    },
    label = "optionBgColor"
  )

  val borderColor by animateColorAsState(
    targetValue = when {
      isSubmitted && isCorrect -> TechGreen
      isSubmitted && isSelected && !isCorrect -> TechRed
      isSelected -> TechCyanAccent
      else -> NavyCardBorder
    },
    label = "optionBorderColor"
  )

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable(enabled = enabled) { onClick() }
      .testTag("quiz_mode_option_$index"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = backgroundColor),
    border = BorderStroke(1.dp, borderColor)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Option Letter Badge (A, B, C, D)
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(
            when {
              isSubmitted && isCorrect -> TechGreen
              isSubmitted && isSelected && !isCorrect -> TechRed
              isSelected -> TechCyanAccent
              else -> NavyDark
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        if (isSubmitted && isCorrect) {
          Icon(Icons.Default.Check, contentDescription = null, tint = NavyDarkest, modifier = Modifier.size(16.dp))
        } else if (isSubmitted && isSelected && !isCorrect) {
          Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        } else {
          Text(
            text = letter,
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            ),
            color = if (isSelected) NavyDarkest else TextSecondary
          )
        }
      }

      Text(
        text = option.text,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
          fontSize = 14.sp,
          lineHeight = 19.sp
        ),
        color = if (isSelected || (isSubmitted && isCorrect)) TextPrimary else TextSecondary,
        modifier = Modifier.weight(1f)
      )
    }
  }
}

/**
 * Quiz Completion Results View with score, feedback, review answers, and retry actions.
 */
@Composable
private fun LessonQuizResultsView(
  lessonTitle: String,
  correctCount: Int,
  totalQuestions: Int,
  userAnswers: List<QuizUserAnswer>,
  showReviewAll: Boolean,
  onToggleReview: () -> Unit,
  onRetake: () -> Unit,
  onExit: () -> Unit
) {
  val scorePercentage = ((correctCount.toFloat() / totalQuestions) * 100).toInt()

  val (statusTitle, statusSubtitle, statusColor) = when {
    scorePercentage >= 90 -> Triple("Mastery Achieved! 🌟", "Outstanding! You have thoroughly grasped all concepts in this lesson.", TechGreen)
    scorePercentage >= 70 -> Triple("Concept Understood! 🎉", "Great work! You demonstrated solid understanding of this lesson's key ideas.", TechCyanAccent)
    scorePercentage >= 50 -> Triple("Good Effort! 👍", "You've got the basics down, but reviewing a few concepts will solidify your mastery.", TechAmber)
    else -> Triple("Keep Learning! 📚", "Review the lesson material and try again to master these concepts.", TechRed)
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 16.dp)
      .testTag("lesson_quiz_results_view"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = NavyCard),
        border = BorderStroke(1.dp, statusColor.copy(alpha = 0.5f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(64.dp)
              .clip(CircleShape)
              .background(statusColor.copy(alpha = 0.15f))
              .border(1.dp, statusColor.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (scorePercentage >= 70) Icons.Default.EmojiEvents else Icons.Default.School,
              contentDescription = null,
              tint = statusColor,
              modifier = Modifier.size(34.dp)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = statusTitle,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            ),
            color = TextPrimary,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = statusSubtitle,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp),
            color = TextSecondary,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(18.dp))

          // Score Badge
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .background(NavyDark)
              .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
              .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "Score",
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiary
              )
              Text(
                text = "$scorePercentage%",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 26.sp
                ),
                color = statusColor
              )
            }

            Box(
              modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(NavyCardBorder)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "Correct",
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiary
              )
              Text(
                text = "$correctCount / $totalQuestions",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 20.sp
                ),
                color = TextPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // XP Gained Banner
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(TechAmber.copy(alpha = 0.12f))
              .border(1.dp, TechAmber.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Icon(Icons.Default.Star, contentDescription = null, tint = TechAmber, modifier = Modifier.size(16.dp))
            Text(
              text = "+${if (scorePercentage >= 70) 50 else 25} XP Earned",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = TechAmber
            )
          }
        }
      }
    }

    // Toggle Review Questions Button
    item {
      OutlinedButton(
        onClick = onToggleReview,
        modifier = Modifier
          .fillMaxWidth()
          .height(46.dp)
          .testTag("toggle_quiz_review_button"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TechCyanAccent)
      ) {
        Text(
          text = if (showReviewAll) "Hide Question Explanations" else "Review All 5 Questions & Explanations"
        )
      }
    }

    // Review List of all 5 Questions
    if (showReviewAll) {
      itemsIndexed(userAnswers) { idx, answer ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = NavyCard),
          border = BorderStroke(
            1.dp,
            if (answer.isCorrect) TechGreen.copy(alpha = 0.45f) else TechRed.copy(alpha = 0.45f)
          )
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = if (answer.isCorrect) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                tint = if (answer.isCorrect) TechGreen else TechRed,
                modifier = Modifier.size(16.dp)
              )
              Text(
                text = "Question ${idx + 1}: ${if (answer.isCorrect) "Correct" else "Incorrect"}",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = if (answer.isCorrect) TechGreen else TechRed
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = answer.question.question,
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
              color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            val correctOptionText = answer.question.options.getOrNull(answer.question.correctOptionIndex)?.text ?: ""
            Text(
              text = "Correct Answer: $correctOptionText",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
              color = TechGreen
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = "Explanation: ${answer.question.explanation}",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
              color = TextSecondary
            )
          }
        }
      }
    }

    // Action Buttons: Retake Quiz or Back to Lesson
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = onRetake,
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("retake_lesson_quiz_button"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
        ) {
          Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Retake Quiz")
        }

        Button(
          onClick = onExit,
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("back_to_lesson_button"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TechCyanAccent, contentColor = NavyDarkest)
        ) {
          Text("Back to Lesson", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
        }
      }
    }
  }
}
