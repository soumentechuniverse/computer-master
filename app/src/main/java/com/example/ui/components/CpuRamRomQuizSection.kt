package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CpuRamRomLessonRepository
import com.example.data.model.CpuRamRomQuizQuestion
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppLanguage

@Composable
fun CpuRamRomQuizSection(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  val questions = CpuRamRomLessonRepository.quizQuestions
  val userAnswers = remember { mutableStateMapOf<Int, Int>() }

  val answeredCount = userAnswers.size
  val correctCount = userAnswers.count { (qId, selectedIdx) ->
    val q = questions.find { it.id == qId }
    q?.correctOptionIndex == selectedIdx
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp)
  ) {
    // Header & Score Tracker
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
            .background(TechAmber)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "কুইজ: সিপিইউ, র‍্যাম ও রম (৮টি প্রশ্ন)"
            AppLanguage.HINDI -> "प्रश्नोत्तरी: सीपीयू, रैम और रोम (8 प्रश्न)"
            AppLanguage.ENGLISH -> "QUIZ: CPU, RAM & ROM (8 QUESTIONS)"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            letterSpacing = 0.8.sp
          ),
          color = TechAmber
        )
      }

      // Score Pill
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(TechCyanAccent.copy(alpha = 0.2f))
          .border(1.dp, TechCyanAccent.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = "$correctCount / ${questions.size}",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          ),
          color = TechCyanAccent
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // List of 8 Questions
    questions.forEachIndexed { qIdx, question ->
      QuizQuestionCard(
        questionNumber = qIdx + 1,
        question = question,
        selectedOption = userAnswers[question.id],
        onSelectOption = { chosenIdx ->
          if (userAnswers[question.id] == null) {
            userAnswers[question.id] = chosenIdx
          }
        },
        currentLanguage = currentLanguage
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Reset / Retry Button
    if (answeredCount > 0) {
      Button(
        onClick = { userAnswers.clear() },
        colors = ButtonDefaults.buttonColors(
          containerColor = NavyCard,
          contentColor = TechAmber
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, TechAmber.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
          .testTag("retry_quiz_button")
      ) {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "কুইজ পুনরায় শুরু করুন (Retry Quiz)"
            AppLanguage.HINDI -> "प्रश्नोत्तरी दोबारा शुरू करें (Retry Quiz)"
            AppLanguage.ENGLISH -> "Retry Quiz"
          },
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// SINGLE QUESTION CARD
// -----------------------------------------------------------------------------
@Composable
private fun QuizQuestionCard(
  questionNumber: Int,
  question: CpuRamRomQuizQuestion,
  selectedOption: Int?,
  onSelectOption: (Int) -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  val options = question.getOptions(currentLanguage)
  val isAnswered = selectedOption != null
  val isCorrect = selectedOption == question.correctOptionIndex

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(NavyDarkest)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
      .padding(12.dp)
  ) {
    Column {
      // Question Title
      Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Text(
          text = "Q$questionNumber.",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          ),
          color = TechCyanAccent
        )
        Text(
          text = question.getQuestion(currentLanguage),
          style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp
          ),
          color = TextPrimary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 4 Options
      options.forEachIndexed { optIdx, optText ->
        val isThisOptionChosen = selectedOption == optIdx
        val isThisTheCorrectAnswer = optIdx == question.correctOptionIndex

        val optionBg: Color
        val optionBorder: Color
        val optionTextCol: Color

        if (!isAnswered) {
          optionBg = NavyCard
          optionBorder = NavyCardBorder
          optionTextCol = TextPrimary
        } else {
          if (isThisTheCorrectAnswer) {
            optionBg = TechGreen.copy(alpha = 0.2f)
            optionBorder = TechGreen
            optionTextCol = TechGreen
          } else if (isThisOptionChosen) {
            optionBg = TechRed.copy(alpha = 0.2f)
            optionBorder = TechRed
            optionTextCol = TechRed
          } else {
            optionBg = NavyCard.copy(alpha = 0.5f)
            optionBorder = NavyCardBorder
            optionTextCol = TextSecondary.copy(alpha = 0.6f)
          }
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(optionBg)
            .border(1.dp, optionBorder, RoundedCornerShape(8.dp))
            .clickable(enabled = !isAnswered) { onSelectOption(optIdx) }
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .testTag("quiz_q${question.id}_opt$optIdx")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              val letter = ('A' + optIdx).toString()
              Text(
                text = "$letter.",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = optionTextCol
              )
              Text(
                text = optText,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = optionTextCol
              )
            }

            if (isAnswered) {
              if (isThisTheCorrectAnswer) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = TechGreen,
                  modifier = Modifier.size(16.dp)
                )
              } else if (isThisOptionChosen) {
                Icon(
                  imageVector = Icons.Default.Warning,
                  contentDescription = null,
                  tint = TechRed,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }
        }
      }

      // Feedback & Explanation
      AnimatedVisibility(
        visible = isAnswered,
        enter = fadeIn() + expandVertically()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isCorrect) TechGreen.copy(alpha = 0.15f) else TechAmber.copy(alpha = 0.15f))
            .border(
              1.dp,
              if (isCorrect) TechGreen.copy(alpha = 0.5f) else TechAmber.copy(alpha = 0.5f),
              RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
        ) {
          Text(
            text = if (isCorrect) {
              when (currentLanguage) {
                AppLanguage.BENGALI -> "✓ সঠিক উত্তর!"
                AppLanguage.HINDI -> "✓ सही उत्तर!"
                AppLanguage.ENGLISH -> "✓ Correct Answer!"
              }
            } else {
              when (currentLanguage) {
                AppLanguage.BENGALI -> "✗ ভুল হয়েছে! ব্যাখ্যা দেখে নিন:"
                AppLanguage.HINDI -> "✗ गलत उत्तर! स्पष्टीकरण देखें:"
                AppLanguage.ENGLISH -> "✗ Incorrect! Explanation:"
              }
            },
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            ),
            color = if (isCorrect) TechGreen else TechAmber
          )

          Spacer(modifier = Modifier.height(3.dp))

          Text(
            text = question.getExplanation(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 10.sp,
              lineHeight = 15.sp
            ),
            color = TextPrimary
          )
        }
      }
    }
  }
}
