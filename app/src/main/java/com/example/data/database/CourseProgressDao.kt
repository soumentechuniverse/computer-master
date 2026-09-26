package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for course progress and lesson completions.
 * Provides reactive Flow streams and suspend operations for tracking user completion status.
 */
@Dao
interface CourseProgressDao {

  // --- Lesson Completions ---

  @Query("SELECT * FROM lesson_completions WHERE isCompleted = 1")
  fun getAllCompletedLessons(): Flow<List<LessonCompletionEntity>>

  @Query("SELECT * FROM lesson_completions WHERE isCompleted = 1")
  suspend fun getAllCompletedLessonsOnce(): List<LessonCompletionEntity>

  @Query("SELECT * FROM lesson_completions WHERE courseId = :courseId AND isCompleted = 1")
  fun getCompletedLessonsForCourse(courseId: String): Flow<List<LessonCompletionEntity>>

  @Query("SELECT isCompleted FROM lesson_completions WHERE courseId = :courseId AND lessonId = :lessonId")
  fun isLessonCompleted(courseId: String, lessonId: String): Flow<Boolean?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertLessonCompletion(completion: LessonCompletionEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertLessonCompletions(completions: List<LessonCompletionEntity>)

  @Query("UPDATE lesson_completions SET isCompleted = :isCompleted, completedAt = :timestamp WHERE courseId = :courseId AND lessonId = :lessonId")
  suspend fun updateLessonCompletionStatus(courseId: String, lessonId: String, isCompleted: Boolean, timestamp: Long)

  @Query("DELETE FROM lesson_completions WHERE courseId = :courseId AND lessonId = :lessonId")
  suspend fun deleteLessonCompletion(courseId: String, lessonId: String)

  @Query("DELETE FROM lesson_completions WHERE courseId = :courseId")
  suspend fun clearCompletionsForCourse(courseId: String)

  @Query("SELECT COUNT(*) FROM lesson_completions WHERE courseId = :courseId AND isCompleted = 1")
  fun getCompletedCountForCourse(courseId: String): Flow<Int>

  @Query("SELECT COUNT(*) FROM lesson_completions WHERE isCompleted = 1")
  fun getTotalCompletedLessonsCount(): Flow<Int>

  // --- Course Progress ---

  @Query("SELECT * FROM course_progress")
  fun getAllCourseProgress(): Flow<List<CourseProgressEntity>>

  @Query("SELECT * FROM course_progress")
  suspend fun getAllCourseProgressOnce(): List<CourseProgressEntity>

  @Query("SELECT * FROM course_progress WHERE courseId = :courseId")
  fun getCourseProgress(courseId: String): Flow<CourseProgressEntity?>

  @Query("SELECT * FROM course_progress WHERE courseId = :courseId")
  suspend fun getCourseProgressOnce(courseId: String): CourseProgressEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateCourseProgress(progress: CourseProgressEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateCourseProgressList(progressList: List<CourseProgressEntity>)

  @Query("DELETE FROM course_progress WHERE courseId = :courseId")
  suspend fun deleteCourseProgress(courseId: String)

  @Query("DELETE FROM course_progress")
  suspend fun clearAllCourseProgress()
}
