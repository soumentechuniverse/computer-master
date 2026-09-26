package com.example.data.model

data class Achievement(
  val id: String,
  val title: String,
  val description: String,
  val iconName: String,
  val isUnlocked: Boolean,
  val unlockedDate: String? = null,
  val category: String = "General",
  val currentProgress: Int = 0,
  val maxProgress: Int = 1,
  val xpReward: Int = 50,
  val rarity: String = "Common",
  val relatedCourseId: String? = null,
) {
  val progressPercent: Int
    get() = if (maxProgress > 0) ((currentProgress.toFloat() / maxProgress) * 100).toInt().coerceIn(0, 100) else if (isUnlocked) 100 else 0
}

data class StudyNote(
  val id: String,
  val courseId: String,
  val courseTitle: String,
  val title: String,
  val content: String,
  val dateAdded: Long = System.currentTimeMillis(),
)

data class DailyActivity(
  val day: String,
  val hoursLearned: Float,
  val isCompleted: Boolean,
  val lessonsCount: Int = 0,
)

data class UserProfile(
  val name: String,
  val title: String,
  val levelNumber: Int,
  val currentXp: Int,
  val maxXp: Int,
  val streakDays: Int,
  val lessonsCompleted: Int,
  val totalLessons: Int,
  val coursesStarted: Int,
  val coursesCompleted: Int,
  val quizAverage: Int,
  val totalQuizzesCompleted: Int = 0,
  val highestQuizScore: Int = 0,
)
