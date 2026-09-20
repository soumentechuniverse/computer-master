package com.example.data.model

data class Achievement(
  val id: String,
  val title: String,
  val description: String,
  val iconName: String,
  val isUnlocked: Boolean,
  val unlockedDate: String? = null,
)

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
