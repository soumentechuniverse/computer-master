package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertQuizResult(result: QuizResultEntity): Long

  @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
  fun getAllQuizResults(): Flow<List<QuizResultEntity>>

  @Query("SELECT * FROM quiz_results WHERE quizId = :quizId ORDER BY timestamp DESC")
  fun getResultsForQuiz(quizId: String): Flow<List<QuizResultEntity>>

  @Query("SELECT MAX(score) FROM quiz_results WHERE quizId = :quizId")
  suspend fun getBestScoreForQuiz(quizId: String): Int?

  @Query("SELECT MAX(percentage) FROM quiz_results WHERE quizId = :quizId")
  suspend fun getBestPercentageForQuiz(quizId: String): Int?

  @Query("SELECT COUNT(*) FROM quiz_results")
  fun getTotalQuizzesCount(): Flow<Int>

  @Query("SELECT MAX(percentage) FROM quiz_results")
  fun getHighestPercentage(): Flow<Int?>

  @Query("SELECT AVG(percentage) FROM quiz_results")
  fun getAveragePercentage(): Flow<Double?>
}
