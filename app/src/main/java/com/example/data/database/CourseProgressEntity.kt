package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing aggregated progress for each course.
 * Tracks completion metrics including completed lesson count, total lessons,
 * progress percentage, and whether the course has been fully mastered.
 */
@Entity(tableName = "course_progress")
data class CourseProgressEntity(
  @PrimaryKey
  val courseId: String,
  val completedLessonsCount: Int,
  val totalLessonsCount: Int,
  val progressPercent: Int,
  val isCompleted: Boolean = false,
  val lastUpdated: Long = System.currentTimeMillis()
)
