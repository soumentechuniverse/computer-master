package com.example.data.model

enum class CourseLevel(val label: String) {
  ALL("All"),
  BEGINNER("Beginner"),
  INTERMEDIATE("Intermediate"),
  ADVANCED("Advanced");

  companion object {
    fun fromString(value: String): CourseLevel {
      return entries.find { it.name.equals(value, ignoreCase = true) || it.label.equals(value, ignoreCase = true) }
        ?: ALL
    }
  }
}

data class Lesson(
  val id: String,
  val title: String,
  val durationMinutes: Int,
  val description: String,
  val isCompleted: Boolean = false,
  val hasQuiz: Boolean = true,
)

data class Chapter(
  val id: String,
  val title: String,
  val lessons: List<Lesson> = emptyList(),
)

data class CourseModule(
  val id: String,
  val title: String,
  val chapters: List<Chapter> = emptyList(),
) {
  // Convenient accessor to get all lessons in the module
  val lessons: List<Lesson>
    get() = chapters.flatMap { it.lessons }
}

data class Course(
  val id: String,
  val title: String,
  val description: String,
  val level: CourseLevel,
  val difficulty: Int, // 1 to 3 dots (e.g. 1=Beginner, 2=Medium, 3=Hard)
  val lessonCount: Int,
  val estimatedHours: Double,
  val iconName: String,
  val modules: List<CourseModule> = emptyList(),
  val progressPercent: Int = 0,
  val isBookmarked: Boolean = false,
  val tags: List<String> = emptyList(),
) {
  val isStarted: Boolean
    get() = progressPercent > 0

  val isCompleted: Boolean
    get() = progressPercent >= 100

  val difficultyLabel: String
    get() = when (difficulty) {
      1 -> "Beginner"
      2 -> "Intermediate"
      else -> "Advanced"
    }

  val estimatedTimeDisplay: String
    get() = "$estimatedHours hrs"

  val allLessons: List<Lesson>
    get() = modules.flatMap { it.lessons }

  val completedLessonsCount: Int
    get() = allLessons.count { it.isCompleted }
}
