package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Achievement
import com.example.data.model.Course
import com.example.data.model.CourseLevel
import com.example.data.model.DailyActivity
import com.example.data.model.Quiz
import com.example.data.model.QuizAttempt
import com.example.data.model.QuizUserAnswer
import com.example.data.model.StudyNote
import com.example.data.model.UserProfile
import com.example.data.model.AppUpdateInfo
import com.example.data.repository.ComputerBasicsQuizRepository
import com.example.data.repository.ComputerMasterRepository
import com.example.data.update.InAppUpdateManager
import com.example.data.update.UpdateUiState
import com.example.ui.theme.AppThemeMode
import com.example.util.AppLanguage
import java.io.File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class AccountState(
  val isLoggedIn: Boolean = false,
  val userEmail: String? = null,
  val userName: String? = null,
  val isGuest: Boolean = true,
)

data class ActiveQuizState(
  val activeQuiz: Quiz? = null,
  val currentQuestionIndex: Int = 0,
  val selectedOptionIndex: Int? = null,
  val isAnswerSubmitted: Boolean = false,
  val correctAnswersCount: Int = 0,
  val isQuizCompleted: Boolean = false,
  val scorePercentage: Int = 0,
  val userAnswers: List<QuizUserAnswer> = emptyList(),
  val isReviewMode: Boolean = false,
)

class ComputerMasterViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = ComputerMasterRepository(application.applicationContext)
  val updateManager = InAppUpdateManager(application.applicationContext)

  val allCourses: StateFlow<List<Course>> = repository.courses
  val quizzes: StateFlow<List<Quiz>> = repository.quizzes
  val userProfile: StateFlow<UserProfile> = repository.userProfile
  val achievements: StateFlow<List<Achievement>> = repository.achievements
  val notes: StateFlow<List<StudyNote>> = repository.notes
  val dailyActivities: StateFlow<List<DailyActivity>> = repository.dailyActivities
  val recentAttempts: StateFlow<List<QuizAttempt>> = repository.recentAttempts
  val bookmarkedLessons: StateFlow<Set<String>> = repository.bookmarkedLessons

  // In-App Update states
  val updateState: StateFlow<UpdateUiState> = updateManager.updateState
  val showUpdateDialog: StateFlow<Boolean> = updateManager.showDialog

  // App Language System
  private val _currentLanguage = MutableStateFlow(repository.getSavedLanguage())
  val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

  // App Theme Mode
  private val _themeMode = MutableStateFlow(repository.getSavedThemeMode())
  val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

  // Update Notifications Setting
  private val _updateNotificationsEnabled = MutableStateFlow(repository.getUpdateNotificationsEnabled())
  val updateNotificationsEnabled: StateFlow<Boolean> = _updateNotificationsEnabled.asStateFlow()

  // Account State Architecture
  private val _accountState = MutableStateFlow(AccountState())
  val accountState: StateFlow<AccountState> = _accountState.asStateFlow()

  init {
    // Automatically check for newer version when app launches
    checkForUpdates(isManual = false)
  }

  fun setLanguage(language: AppLanguage) {
    _currentLanguage.value = language
    repository.saveLanguage(language)
  }

  fun setThemeMode(mode: AppThemeMode) {
    _themeMode.value = mode
    repository.saveThemeMode(mode)
  }

  fun setUpdateNotificationsEnabled(enabled: Boolean) {
    _updateNotificationsEnabled.value = enabled
    repository.saveUpdateNotificationsEnabled(enabled)
  }

  fun logout() {
    _accountState.value = AccountState(
      isLoggedIn = false,
      userEmail = null,
      userName = null,
      isGuest = true
    )
  }

  // UI state for search & level filtering
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedLevel = MutableStateFlow(CourseLevel.ALL)
  val selectedLevel: StateFlow<CourseLevel> = _selectedLevel.asStateFlow()

  // Filtered courses
  val filteredCourses: StateFlow<List<Course>> =
    combine(allCourses, _searchQuery, _selectedLevel) { courses, query, level ->
      val trimmed = query.trim()
      courses.filter { course ->
        val matchesLevel = (level == CourseLevel.ALL || course.level == level)
        val matchesQuery = trimmed.isBlank() ||
            course.title.contains(trimmed, ignoreCase = true) ||
            course.description.contains(trimmed, ignoreCase = true) ||
            course.level.label.contains(trimmed, ignoreCase = true) ||
            course.tags.any { it.contains(trimmed, ignoreCase = true) }
        matchesLevel && matchesQuery
      }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Quiz active session state
  private val _activeQuizState = MutableStateFlow(ActiveQuizState())
  val activeQuizState: StateFlow<ActiveQuizState> = _activeQuizState.asStateFlow()

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setSelectedLevel(level: CourseLevel) {
    _selectedLevel.value = level
  }

  fun toggleBookmark(courseId: String) {
    repository.toggleBookmark(courseId)
  }

  fun toggleLessonBookmark(lessonId: String) {
    repository.toggleLessonBookmark(lessonId)
  }

  fun isLessonBookmarked(lessonId: String): Boolean {
    return repository.isLessonBookmarked(lessonId)
  }

  fun setLessonCompleted(courseId: String, lessonId: String, completed: Boolean) {
    repository.setLessonCompleted(courseId, lessonId, completed)
  }

  fun toggleLessonCompletion(courseId: String, lessonId: String) {
    repository.toggleLessonCompletion(courseId, lessonId)
  }

  fun startCourse(courseId: String) {
    repository.startCourse(courseId)
  }

  fun getCourseById(courseId: String): Course? {
    return allCourses.value.find { it.id == courseId }
  }

  fun addNote(courseId: String, courseTitle: String, title: String, content: String) {
    repository.addNote(courseId, courseTitle, title, content)
  }

  fun deleteNote(noteId: String) {
    repository.deleteNote(noteId)
  }

  fun resetAllProgress() {
    repository.resetAllProgress()
  }

  // Quiz Actions
  fun startQuiz(quiz: Quiz) {
    _activeQuizState.value = ActiveQuizState(
      activeQuiz = quiz,
      currentQuestionIndex = 0,
      selectedOptionIndex = null,
      isAnswerSubmitted = false,
      correctAnswersCount = 0,
      isQuizCompleted = false,
      scorePercentage = 0,
      userAnswers = emptyList(),
      isReviewMode = false,
    )
  }

  fun startQuizForLesson(lessonId: String) {
    val quiz = ComputerBasicsQuizRepository.getQuizForLesson(lessonId)
      ?: quizzes.value.find { it.lessonId == lessonId }
    if (quiz != null) {
      startQuiz(quiz)
    }
  }

  fun startQuizById(quizId: String) {
    val quiz = quizzes.value.find { it.id == quizId }
      ?: ComputerBasicsQuizRepository.getQuizById(quizId)
    if (quiz != null) {
      startQuiz(quiz)
    }
  }

  fun selectQuizOption(index: Int) {
    val state = _activeQuizState.value
    val quiz = state.activeQuiz ?: return
    if (state.isAnswerSubmitted || state.isQuizCompleted) return

    val currentQ = quiz.questions.getOrNull(state.currentQuestionIndex) ?: return
    val isCorrect = (index == currentQ.correctOptionIndex)
    val newCorrectCount = if (isCorrect) state.correctAnswersCount + 1 else state.correctAnswersCount
    val recordedAnswer = QuizUserAnswer(
      questionIndex = state.currentQuestionIndex,
      question = currentQ,
      selectedOptionIndex = index,
      isCorrect = isCorrect
    )

    _activeQuizState.value = state.copy(
      selectedOptionIndex = index,
      isAnswerSubmitted = true,
      correctAnswersCount = newCorrectCount,
      userAnswers = state.userAnswers + recordedAnswer
    )
  }

  fun submitCurrentAnswer() {
    // If not submitted yet but an option is selected, submit it
    val state = _activeQuizState.value
    if (state.selectedOptionIndex != null && !state.isAnswerSubmitted) {
      selectQuizOption(state.selectedOptionIndex)
    }
  }

  fun nextQuizQuestion() {
    val state = _activeQuizState.value
    val quiz = state.activeQuiz ?: return

    val nextIndex = state.currentQuestionIndex + 1
    if (nextIndex < quiz.questions.size) {
      _activeQuizState.value = state.copy(
        currentQuestionIndex = nextIndex,
        selectedOptionIndex = null,
        isAnswerSubmitted = false
      )
    } else {
      // Quiz finished
      val total = quiz.questions.size
      val pct = if (total > 0) ((state.correctAnswersCount.toFloat() / total) * 100).toInt() else 0
      _activeQuizState.value = state.copy(
        isQuizCompleted = true,
        scorePercentage = pct
      )
      // Record attempt in repository with Room DB persistence
      repository.recordQuizAttempt(
        quiz = quiz,
        scorePercentage = pct,
        correct = state.correctAnswersCount,
        total = total
      )
    }
  }

  fun openReviewMode() {
    _activeQuizState.value = _activeQuizState.value.copy(isReviewMode = true)
  }

  fun closeReviewMode() {
    _activeQuizState.value = _activeQuizState.value.copy(isReviewMode = false)
  }

  fun retryQuiz() {
    val quiz = _activeQuizState.value.activeQuiz
    if (quiz != null) {
      startQuiz(quiz)
    }
  }

  fun resetQuiz() {
    val quiz = _activeQuizState.value.activeQuiz
    if (quiz != null) {
      startQuiz(quiz)
    } else {
      _activeQuizState.value = ActiveQuizState()
    }
  }

  fun exitQuiz() {
    _activeQuizState.value = ActiveQuizState()
  }

  // --- In-App Update Helper Functions ---

  fun checkForUpdates(isManual: Boolean = false) {
    updateManager.checkForUpdates(
      scope = viewModelScope,
      isManual = isManual,
      language = _currentLanguage.value
    )
  }

  fun downloadUpdateApk(info: AppUpdateInfo) {
    updateManager.downloadApk(viewModelScope, info)
  }

  fun launchPackageInstaller(apkFile: File) {
    updateManager.launchPackageInstaller(apkFile)
  }

  fun dismissUpdateDialog(versionCode: Int? = null) {
    updateManager.dismissDialog(versionCode)
  }

  fun openUpdateDialog() {
    updateManager.openDialog()
  }

  fun getUpdateMetadataUrl(): String = updateManager.getUpdateMetadataUrl()

  fun setUpdateMetadataUrl(url: String) {
    updateManager.setUpdateMetadataUrl(url)
  }

  fun resetDefaultUpdateUrl() {
    updateManager.resetToDefaultUrl()
  }

  fun getCustomApkUrl(): String? = updateManager.getCustomApkUrl()

  fun setCustomApkUrl(url: String?) {
    updateManager.setCustomApkUrl(url)
  }

  fun resetCustomApkUrl() {
    updateManager.resetCustomApkUrl()
  }
}
