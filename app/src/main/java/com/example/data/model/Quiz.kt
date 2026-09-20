package com.example.data.model

enum class QuizType(val displayName: String) {
  DAILY_CHECK("Daily Knowledge Check"),
  LESSON("Lesson Quiz"),
  CHAPTER("Chapter Quiz"),
  FINAL_EXAM("Final Course Quiz"),
}

data class QuizOption(
  val id: Int,
  val text: String,
)

data class QuizQuestion(
  val id: String,
  val question: String,
  val options: List<QuizOption>,
  val correctOptionIndex: Int,
  val explanation: String,
)

data class Quiz(
  val id: String,
  val title: String,
  val category: String,
  val type: QuizType,
  val durationMinutes: Int,
  val questions: List<QuizQuestion>,
  val bestScore: Int? = null,
  val courseId: String = "course_comp_basics",
  val courseTitle: String = "Computer Basics",
  val lessonId: String? = null,
  val lessonTitle: String? = null,
)

data class QuizUserAnswer(
  val questionIndex: Int,
  val question: QuizQuestion,
  val selectedOptionIndex: Int,
  val isCorrect: Boolean,
)

data class QuizAttempt(
  val quizId: String,
  val quizTitle: String,
  val scorePercentage: Int,
  val correctAnswers: Int,
  val totalQuestions: Int,
  val isPassed: Boolean = scorePercentage >= 70,
  val timestamp: Long = System.currentTimeMillis(),
  val courseTitle: String = "Computer Basics",
  val lessonTitle: String? = null,
)
