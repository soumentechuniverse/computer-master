package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val quizId: String,
  val quizType: String,
  val courseId: String,
  val courseName: String,
  val lessonId: String? = null,
  val lessonName: String? = null,
  val score: Int,
  val totalQuestions: Int,
  val percentage: Int,
  val isPassed: Boolean,
  val timestamp: Long = System.currentTimeMillis()
)
