package com.example.data.model

data class RealWorldExample(
  val title: String,
  val description: String,
  val iconName: String = "computer"
)

data class HowItWorksStep(
  val stepNumber: Int,
  val title: String,
  val description: String
)

data class CommonMistake(
  val mistake: String,
  val correction: String
)

data class PracticalActivity(
  val title: String,
  val objective: String,
  val steps: List<String>
)

data class LessonQuizQuestion(
  val question: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String
)

data class LessonDetailData(
  val lessonId: String,
  val lessonNumber: Int,
  val totalLessons: Int = 10,
  val courseId: String = "course_basics",
  val courseTitle: String = "Computer Basics",
  val title: String,
  val readingTimeMinutes: Int,
  val level: CourseLevel = CourseLevel.BEGINNER,
  val objectives: List<String>,
  val quickIntro: String,
  val simpleExplanation: String,
  val detailedSections: List<Pair<String, String>>,
  val realWorldExamples: List<RealWorldExample>,
  val howItWorksSteps: List<HowItWorksStep>,
  val visualDiagramType: String,
  val importantPoints: List<String>,
  val commonMistakes: List<CommonMistake>,
  val practicalActivity: PracticalActivity,
  val knowledgeCheck: LessonQuizQuestion,
  val prevLessonId: String? = null,
  val nextLessonId: String? = null
)
