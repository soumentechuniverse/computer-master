package com.example.data.database

import androidx.room.Entity
import androidx.room.Index

/**
 * Room entity representing an individual lesson's user completion status.
 * Persists whether a user has finished studying and practicing each lesson.
 */
@Entity(
  tableName = "lesson_completions",
  primaryKeys = ["courseId", "lessonId"],
  indices = [
    Index(value = ["courseId"]),
    Index(value = ["lessonId"])
  ]
)
data class LessonCompletionEntity(
  val courseId: String,
  val lessonId: String,
  val isCompleted: Boolean = true,
  val completedAt: Long = System.currentTimeMillis()
)
