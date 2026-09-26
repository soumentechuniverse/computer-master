package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.database.AppDatabase
import com.example.data.database.CourseProgressEntity
import com.example.data.database.LessonCompletionEntity
import com.example.data.database.QuizResultEntity
import com.example.data.model.Achievement
import com.example.data.model.Course
import com.example.data.model.DailyActivity
import com.example.data.model.Quiz
import com.example.data.model.QuizAttempt
import com.example.data.model.QuizOption
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizType
import com.example.data.model.StudyNote
import com.example.data.model.UserProfile
import com.example.ui.theme.AppThemeMode
import com.example.util.AppLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ComputerMasterRepository(context: Context) {

  private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
  private val database = AppDatabase.getDatabase(context)
  private val quizDao = database.quizResultDao()
  private val courseProgressDao = database.courseProgressDao()

  val allCourseProgress: Flow<List<CourseProgressEntity>> = courseProgressDao.getAllCourseProgress()
  val allCompletedLessons: Flow<List<LessonCompletionEntity>> = courseProgressDao.getAllCompletedLessons()
  val totalCompletedLessonsCount: Flow<Int> = courseProgressDao.getTotalCompletedLessonsCount()

  fun getCourseProgressFlow(courseId: String): Flow<CourseProgressEntity?> =
    courseProgressDao.getCourseProgress(courseId)

  private val prefs: SharedPreferences =
    context.getSharedPreferences("computer_master_prefs", Context.MODE_PRIVATE)

  // In-memory reactive state flows
  private val _courses = MutableStateFlow<List<Course>>(emptyList())
  val courses: StateFlow<List<Course>> = _courses.asStateFlow()

  private val _quizzes = MutableStateFlow<List<Quiz>>(emptyList())
  val quizzes: StateFlow<List<Quiz>> = _quizzes.asStateFlow()

  private val _recentAttempts = MutableStateFlow<List<QuizAttempt>>(emptyList())
  val recentAttempts: StateFlow<List<QuizAttempt>> = _recentAttempts.asStateFlow()

  private val _userProfile = MutableStateFlow(
    UserProfile(
      name = "Tech Master Student",
      title = "Byte Apprentice",
      levelNumber = 4,
      currentXp = 1850,
      maxXp = 2500,
      streakDays = 5,
      lessonsCompleted = 24,
      totalLessons = 160,
      coursesStarted = 4,
      coursesCompleted = 1,
      quizAverage = 88,
      totalQuizzesCompleted = 0,
      highestQuizScore = 0,
    )
  )
  val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

  private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
  val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

  private val _notes = MutableStateFlow<List<StudyNote>>(emptyList())
  val notes: StateFlow<List<StudyNote>> = _notes.asStateFlow()

  private val _dailyActivities = MutableStateFlow<List<DailyActivity>>(emptyList())
  val dailyActivities: StateFlow<List<DailyActivity>> = _dailyActivities.asStateFlow()

  private val _bookmarkedLessons = MutableStateFlow<Set<String>>(emptySet())
  val bookmarkedLessons: StateFlow<Set<String>> = _bookmarkedLessons.asStateFlow()

  init {
    loadSeedData()
    loadPersistedState()
  }

  private fun loadSeedData() {
    _achievements.value = listOf(
      Achievement("ach_1", "First Boot", "Completed your very first computer lesson", "power", true, "Sep 15"),
      Achievement("ach_2", "Key Tactician", "Practiced touch typing for 3 consecutive days", "keyboard", true, "Sep 17"),
      Achievement("ach_3", "Algorithm Mind", "Scored 100% on a Programming Fundamentals quiz", "code", true, "Sep 18"),
      Achievement("ach_quiz_starter", "Quiz Starter", "Complete your first quiz", "quiz", false),
      Achievement("ach_quiz_master", "Quiz Master", "Score 90% or higher on a quiz", "trophy", false),
      Achievement("ach_4", "5-Day Streak", "Maintained an unbroken 5-day daily learning streak", "flame", true, "Today"),
      Achievement("ach_5", "System Admin", "Mastered Windows File Management directory commands", "terminal", false),
      Achievement("ach_6", "Cyber Shield", "Passed the Cybersecurity Fundamentals exam", "shield", false),
      Achievement("ach_7", "AI Explorer", "Completed your first Neural Network architecture module", "brain", false)
    )

    _dailyActivities.value = listOf(
      DailyActivity("Mon", 1.5f, true, lessonsCount = 4),
      DailyActivity("Tue", 2.0f, true, lessonsCount = 6),
      DailyActivity("Wed", 1.0f, true, lessonsCount = 3),
      DailyActivity("Thu", 2.5f, true, lessonsCount = 7),
      DailyActivity("Fri", 1.8f, true, lessonsCount = 5),
      DailyActivity("Sat", 0.5f, false, lessonsCount = 2),
      DailyActivity("Sun", 0.0f, false, lessonsCount = 1)
    )

    // Seed Quizzes: Daily Knowledge Check + 10 Computer Basics Lesson Quizzes + Final Exam + Chapter Quizzes
    val seededQuizzes = mutableListOf<Quiz>()
    seededQuizzes.add(ComputerBasicsQuizRepository.dailyKnowledgeCheck)
    seededQuizzes.addAll(ComputerBasicsQuizRepository.allLessonQuizzes)
    seededQuizzes.add(ComputerBasicsQuizRepository.finalCourseQuiz)
    seededQuizzes.addAll(
      listOf(
        Quiz(
          id = "quiz_python",
          title = "Python Core Mechanics Assessment",
          category = "Programming",
          type = QuizType.CHAPTER,
          durationMinutes = 5,
          courseId = "course_python",
          courseTitle = "Python Programming",
          questions = listOf(
            QuizQuestion(
              id = "qp1",
              question = "What is the output of: len([x for x in range(10) if x % 2 == 0])?",
              options = listOf(
                QuizOption(0, "4"),
                QuizOption(1, "5"),
                QuizOption(2, "10"),
                QuizOption(3, "6")
              ),
              correctOptionIndex = 1,
              explanation = "The even numbers in range(10) are 0, 2, 4, 6, 8, which equals 5 elements in total."
            ),
            QuizQuestion(
              id = "qp2",
              question = "Which Python data structure is unordered, mutable, and does not allow duplicate items?",
              options = listOf(
                QuizOption(0, "List"),
                QuizOption(1, "Tuple"),
                QuizOption(2, "Set"),
                QuizOption(3, "Dictionary")
              ),
              correctOptionIndex = 2,
              explanation = "A set contains unique elements with O(1) membership lookups and rejects duplicates."
            ),
            QuizQuestion(
              id = "qp3",
              question = "In Python, which statement safely handles file I/O and guarantees closing files?",
              options = listOf(
                QuizOption(0, "try / finally"),
                QuizOption(1, "with open(...) as f:"),
                QuizOption(2, "file.close() manually"),
                QuizOption(3, "Both 0 and 1 are correct, but 'with' is idiomatic")
              ),
              correctOptionIndex = 3,
              explanation = "The 'with' context manager uses the context protocol (__enter__ and __exit__) to automatically close files."
            )
          )
        ),
        Quiz(
          id = "quiz_networking",
          title = "Networking & OSI Model Challenge",
          category = "Networking",
          type = QuizType.CHAPTER,
          durationMinutes = 5,
          courseId = "course_network",
          courseTitle = "Computer Networking",
          questions = listOf(
            QuizQuestion(
              id = "qn1",
              question = "Which layer of the OSI model does HTTP/HTTPS operate on?",
              options = listOf(
                QuizOption(0, "Layer 3 (Network)"),
                QuizOption(1, "Layer 4 (Transport)"),
                QuizOption(2, "Layer 7 (Application)"),
                QuizOption(3, "Layer 2 (Data Link)")
              ),
              correctOptionIndex = 2,
              explanation = "HTTP/HTTPS are application layer protocols (Layer 7) in the OSI 7-layer reference model."
            ),
            QuizQuestion(
              id = "qn2",
              question = "What is the primary function of a DNS server?",
              options = listOf(
                QuizOption(0, "Encrypt network packets"),
                QuizOption(1, "Translate human-readable domain names into IP addresses"),
                QuizOption(2, "Assign DHCP IP leases locally"),
                QuizOption(3, "Filter unwanted spam emails")
              ),
              correctOptionIndex = 1,
              explanation = "DNS translates friendly domains (like google.com) into numerical IP addresses computers route to."
            )
          )
        ),
        Quiz(
          id = "quiz_cyber",
          title = "Cybersecurity Defense Fundamentals",
          category = "Cybersecurity",
          type = QuizType.FINAL_EXAM,
          durationMinutes = 10,
          courseId = "course_cyber",
          courseTitle = "Cybersecurity Fundamentals",
          questions = listOf(
            QuizQuestion(
              id = "qc1",
              question = "What is Multi-Factor Authentication (MFA)?",
              options = listOf(
                QuizOption(0, "Using two different passwords"),
                QuizOption(1, "A security system requiring two or more distinct evidence types to verify identity"),
                QuizOption(2, "An antivirus scanning engine"),
                QuizOption(3, "A firewall hardware module")
              ),
              correctOptionIndex = 1,
              explanation = "MFA combines something you know (password), something you have (phone/key), or something you are (biometrics)."
            ),
            QuizQuestion(
              id = "qc2",
              question = "Which encryption standard is widely used to secure Wi-Fi networks today?",
              options = listOf(
                QuizOption(0, "WEP"),
                QuizOption(1, "WPA3"),
                QuizOption(2, "DES"),
                QuizOption(3, "MD5")
              ),
              correctOptionIndex = 1,
              explanation = "WPA3 is the latest modern Wi-Fi security protocol replacing legacy WEP and WPA2."
            )
          )
        )
      )
    )
    _quizzes.value = seededQuizzes

    // Load initial 20 complete courses with Module -> Chapter -> Lesson hierarchy
    _courses.value = CourseSeedData.getInitialCourses()
  }

  private fun loadPersistedState() {
    val completedLessonsString = prefs.getString("completed_lessons", null)
    val completedSet: Set<String> = if (completedLessonsString != null) {
      completedLessonsString.split(",").filter { it.isNotEmpty() }.toSet()
    } else {
      // First run: persist default initial completed lessons from seed data
      val initialCompleted = _courses.value.flatMap { it.allLessons }.filter { it.isCompleted }.map { it.id }.toSet()
      prefs.edit().putString("completed_lessons", initialCompleted.joinToString(",")).apply()
      initialCompleted
    }

    val bookmarkedString = prefs.getString("bookmarked_courses", "course_basics,course_python,course_ai") ?: ""
    val bookmarkedSet = bookmarkedString.split(",").filter { it.isNotEmpty() }.toSet()

    // Update courses with persisted completion and bookmark status
    val updatedList = _courses.value.map { course ->
      val isBookmarked = bookmarkedSet.contains(course.id)
      val updatedModules = course.modules.map { mod ->
        val updatedChapters = mod.chapters.map { chap ->
          val updatedLessons = chap.lessons.map { les ->
            les.copy(isCompleted = completedSet.contains(les.id))
          }
          chap.copy(lessons = updatedLessons)
        }
        mod.copy(chapters = updatedChapters)
      }
      val totalLessons = updatedModules.sumOf { it.lessons.size }
      val completedCount = updatedModules.sumOf { m -> m.lessons.count { it.isCompleted } }
      val calculatedProgress = if (totalLessons > 0) ((completedCount.toFloat() / totalLessons) * 100).toInt() else 0

      course.copy(
        isBookmarked = isBookmarked,
        modules = updatedModules,
        progressPercent = calculatedProgress
      )
    }
    _courses.value = updatedList

    val bookmarkedLessonsString = prefs.getString("bookmarked_lessons", "") ?: ""
    _bookmarkedLessons.value = bookmarkedLessonsString.split(",").filter { it.isNotEmpty() }.toSet()

    loadNotesFromPrefs()
    loadAchievementsFromPrefs()
    loadRoomQuizResults()
    loadRoomCourseProgress()
    recalculateProfile()
  }

  private fun loadRoomCourseProgress() {
    scope.launch {
      val existingCompletions = courseProgressDao.getAllCompletedLessonsOnce()
      if (existingCompletions.isNotEmpty()) {
        val completedSet = existingCompletions.map { it.lessonId }.toSet()
        val bookmarkedString = prefs.getString("bookmarked_courses", "course_basics,course_python,course_ai") ?: ""
        val bookmarkedSet = bookmarkedString.split(",").filter { it.isNotEmpty() }.toSet()
        applyCompletedLessons(completedSet, bookmarkedSet)
      } else {
        // First run: save existing course completion state into Room
        val initialEntities = _courses.value.flatMap { course ->
          course.allLessons.filter { it.isCompleted }.map { lesson ->
            LessonCompletionEntity(
              courseId = course.id,
              lessonId = lesson.id,
              isCompleted = true,
              completedAt = System.currentTimeMillis()
            )
          }
        }
        if (initialEntities.isNotEmpty()) {
          courseProgressDao.insertLessonCompletions(initialEntities)
        }
        updateAllCourseProgressInRoom()
      }

      // Continuously observe Room completions for reactive UI updates
      courseProgressDao.getAllCompletedLessons().collect { completions ->
        val completedSet = completions.map { it.lessonId }.toSet()
        val bookmarkedString = prefs.getString("bookmarked_courses", "course_basics,course_python,course_ai") ?: ""
        val bookmarkedSet = bookmarkedString.split(",").filter { it.isNotEmpty() }.toSet()
        applyCompletedLessons(completedSet, bookmarkedSet)
      }
    }
  }

  private fun applyCompletedLessons(completedSet: Set<String>, bookmarkedSet: Set<String>) {
    val updatedList = _courses.value.map { course ->
      val isBookmarked = bookmarkedSet.contains(course.id)
      val updatedModules = course.modules.map { mod ->
        val updatedChapters = mod.chapters.map { chap ->
          val updatedLessons = chap.lessons.map { les ->
            les.copy(isCompleted = completedSet.contains(les.id))
          }
          chap.copy(lessons = updatedLessons)
        }
        mod.copy(chapters = updatedChapters)
      }
      val totalLessons = updatedModules.sumOf { it.lessons.size }
      val completedCount = updatedModules.sumOf { m -> m.lessons.count { it.isCompleted } }
      val calculatedProgress = if (totalLessons > 0) ((completedCount.toFloat() / totalLessons) * 100).toInt() else 0

      course.copy(
        isBookmarked = isBookmarked,
        modules = updatedModules,
        progressPercent = calculatedProgress
      )
    }
    _courses.value = updatedList
    recalculateProfile()
  }

  private suspend fun updateAllCourseProgressInRoom() {
    val progressList = _courses.value.map { course ->
      val total = course.allLessons.size
      val completed = course.completedLessonsCount
      val percent = if (total > 0) ((completed.toFloat() / total) * 100).toInt() else 0
      CourseProgressEntity(
        courseId = course.id,
        completedLessonsCount = completed,
        totalLessonsCount = total,
        progressPercent = percent,
        isCompleted = percent >= 100,
        lastUpdated = System.currentTimeMillis()
      )
    }
    courseProgressDao.insertOrUpdateCourseProgressList(progressList)
  }

  private fun loadAchievementsFromPrefs() {
    val unlockedIds = prefs.getString("unlocked_achievements", "")
      ?.split(",")?.filter { it.isNotEmpty() }?.toSet() ?: emptySet()
    if (unlockedIds.isNotEmpty()) {
      _achievements.value = _achievements.value.map { ach ->
        if (unlockedIds.contains(ach.id)) {
          ach.copy(isUnlocked = true, unlockedDate = ach.unlockedDate ?: "Recently")
        } else ach
      }
    }
  }

  private fun loadRoomQuizResults() {
    scope.launch {
      quizDao.getAllQuizResults().collect { entities ->
        if (entities.isNotEmpty()) {
          val attempts = entities.map { e ->
            QuizAttempt(
              quizId = e.quizId,
              quizTitle = e.lessonName ?: e.courseName,
              scorePercentage = e.percentage,
              correctAnswers = e.score,
              totalQuestions = e.totalQuestions,
              isPassed = e.isPassed,
              timestamp = e.timestamp,
              courseTitle = e.courseName,
              lessonTitle = e.lessonName
            )
          }
          _recentAttempts.value = attempts.take(20)

          val allPercentages = entities.map { it.percentage }
          val avg = if (allPercentages.isNotEmpty()) allPercentages.average().toInt() else 0
          val highest = if (allPercentages.isNotEmpty()) allPercentages.maxOrNull() ?: 0 else 0
          val totalCompleted = entities.size

          val cur = _userProfile.value
          _userProfile.value = cur.copy(
            quizAverage = if (totalCompleted > 0) avg else cur.quizAverage,
            totalQuizzesCompleted = totalCompleted,
            highestQuizScore = highest
          )

          // Update best scores on loaded quizzes
          val bestScoresByQuizId = entities.groupBy { it.quizId }
            .mapValues { (_, list) -> list.maxOf { it.percentage } }

          _quizzes.value = _quizzes.value.map { q ->
            val best = bestScoresByQuizId[q.id]
            if (best != null) q.copy(bestScore = best) else q
          }

          unlockQuizAchievements(totalCompleted, highest)
        }
      }
    }
  }

  private fun unlockQuizAchievements(totalQuizzes: Int, scorePercentage: Int) {
    val unlockedIds = prefs.getString("unlocked_achievements", "")
      ?.split(",")?.filter { it.isNotEmpty() }?.toSet() ?: emptySet()
    val newUnlockedIds = unlockedIds.toMutableSet()

    if (totalQuizzes >= 1) {
      newUnlockedIds.add("ach_quiz_starter")
    }
    if (scorePercentage >= 90) {
      newUnlockedIds.add("ach_quiz_master")
    }
    if (scorePercentage == 100) {
      newUnlockedIds.add("ach_3")
    }

    val updated = _achievements.value.map { ach ->
      if (newUnlockedIds.contains(ach.id) && !ach.isUnlocked) {
        ach.copy(isUnlocked = true, unlockedDate = "Today")
      } else ach
    }
    _achievements.value = updated
    prefs.edit().putString("unlocked_achievements", newUnlockedIds.joinToString(",")).apply()
  }

  private fun loadNotesFromPrefs() {
    val notesJson = prefs.getString("study_notes", null)
    if (notesJson != null) {
      try {
        val array = JSONArray(notesJson)
        val loadedList = mutableListOf<StudyNote>()
        for (i in 0 until array.length()) {
          val obj = array.getJSONObject(i)
          loadedList.add(
            StudyNote(
              id = obj.getString("id"),
              courseId = obj.getString("courseId"),
              courseTitle = obj.getString("courseTitle"),
              title = obj.getString("title"),
              content = obj.getString("content"),
              dateAdded = obj.getLong("dateAdded")
            )
          )
        }
        _notes.value = loadedList
      } catch (_: Exception) {
        setInitialNotes()
      }
    } else {
      setInitialNotes()
    }
  }

  private fun setInitialNotes() {
    _notes.value = listOf(
      StudyNote(
        id = "note_1",
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "Von Neumann Architecture",
        content = "Consists of CPU (Control Unit + ALU), Memory Unit (RAM/ROM), Registers, and Input/Output interfaces. Bus architecture connects them.",
      ),
      StudyNote(
        id = "note_2",
        courseId = "course_python",
        courseTitle = "Python Programming",
        title = "List Comprehensions & Lambdas",
        content = "Syntax: [x * 2 for x in items if x > 0]. Fast, readable and pythonic way to filter and transform sequences.",
      )
    )
  }

  private fun recalculateProfile() {
    val currentCourses = _courses.value
    var completedLessons = 0
    var totalLessons = 0
    var startedCount = 0
    var finishedCount = 0

    currentCourses.forEach { c ->
      val countInCourse = c.modules.sumOf { it.lessons.size }
      val completedInCourse = c.modules.sumOf { m -> m.lessons.count { it.isCompleted } }
      totalLessons += countInCourse
      completedLessons += completedInCourse

      if (c.progressPercent > 0) startedCount++
      if (c.progressPercent == 100) finishedCount++
    }

    val currentStats = _userProfile.value
    _userProfile.value = currentStats.copy(
      lessonsCompleted = if (completedLessons > 0) completedLessons else 24,
      totalLessons = if (totalLessons > 0) totalLessons else 160,
      coursesStarted = if (startedCount > 0) startedCount else 4,
      coursesCompleted = finishedCount
    )
  }

  fun toggleBookmark(courseId: String) {
    val current = _courses.value
    val updated = current.map {
      if (it.id == courseId) it.copy(isBookmarked = !it.isBookmarked) else it
    }
    _courses.value = updated

    val bookmarkedIds = updated.filter { it.isBookmarked }.map { it.id }.joinToString(",")
    prefs.edit().putString("bookmarked_courses", bookmarkedIds).apply()
  }

  fun toggleLessonBookmark(lessonId: String) {
    val current = _bookmarkedLessons.value
    val updated = if (current.contains(lessonId)) current - lessonId else current + lessonId
    _bookmarkedLessons.value = updated
    prefs.edit().putString("bookmarked_lessons", updated.joinToString(",")).apply()
  }

  fun isLessonBookmarked(lessonId: String): Boolean {
    return _bookmarkedLessons.value.contains(lessonId)
  }

  fun setLessonCompleted(courseId: String, lessonId: String, completed: Boolean) {
    val current = _courses.value
    val course = current.find { it.id == courseId } ?: return
    val currentLesson = course.allLessons.find { it.id == lessonId }
    if (currentLesson != null && currentLesson.isCompleted != completed) {
      toggleLessonCompletion(courseId, lessonId)
    }
  }

  fun toggleLessonCompletion(courseId: String, lessonId: String) {
    val current = _courses.value
    var newIsCompleted = false
    var currentCourseTotal = 0
    var currentCourseCompleted = 0
    var calculatedPercent = 0

    val updated = current.map { course ->
      if (course.id == courseId) {
        val newModules = course.modules.map { mod ->
          val newChapters = mod.chapters.map { chap ->
            val newLessons = chap.lessons.map { les ->
              if (les.id == lessonId) {
                newIsCompleted = !les.isCompleted
                les.copy(isCompleted = newIsCompleted)
              } else les
            }
            chap.copy(lessons = newLessons)
          }
          mod.copy(chapters = newChapters)
        }
        val total = newModules.sumOf { it.lessons.size }
        val completed = newModules.sumOf { m -> m.lessons.count { it.isCompleted } }
        val percent = if (total > 0) ((completed.toFloat() / total) * 100).toInt() else 0
        currentCourseTotal = total
        currentCourseCompleted = completed
        calculatedPercent = percent
        course.copy(modules = newModules, progressPercent = percent)
      } else {
        course
      }
    }
    _courses.value = updated

    // Persist to Room Database asynchronously
    scope.launch {
      if (newIsCompleted) {
        courseProgressDao.insertLessonCompletion(
          LessonCompletionEntity(
            courseId = courseId,
            lessonId = lessonId,
            isCompleted = true,
            completedAt = System.currentTimeMillis()
          )
        )
      } else {
        courseProgressDao.deleteLessonCompletion(courseId, lessonId)
      }

      // Persist aggregated CourseProgressEntity in Room
      courseProgressDao.insertOrUpdateCourseProgress(
        CourseProgressEntity(
          courseId = courseId,
          completedLessonsCount = currentCourseCompleted,
          totalLessonsCount = currentCourseTotal,
          progressPercent = calculatedPercent,
          isCompleted = calculatedPercent >= 100,
          lastUpdated = System.currentTimeMillis()
        )
      )
    }

    // Persist all completed lesson ids to prefs as fallback
    val allCompletedIds = updated.flatMap { c ->
      c.modules.flatMap { m -> m.lessons.filter { it.isCompleted }.map { it.id } }
    }.joinToString(",")

    prefs.edit().putString("completed_lessons", allCompletedIds).apply()
    recalculateProfile()
  }

  fun startCourse(courseId: String) {
    val course = _courses.value.find { it.id == courseId } ?: return
    val firstLesson = course.modules.firstOrNull()?.chapters?.firstOrNull()?.lessons?.firstOrNull()
    if (firstLesson != null && !firstLesson.isCompleted) {
      toggleLessonCompletion(courseId, firstLesson.id)
    }
  }

  fun recordQuizAttempt(quiz: Quiz, scorePercentage: Int, correct: Int, total: Int) {
    val isPassed = scorePercentage >= 70
    val attempt = QuizAttempt(
      quizId = quiz.id,
      quizTitle = quiz.lessonTitle ?: quiz.title,
      scorePercentage = scorePercentage,
      correctAnswers = correct,
      totalQuestions = total,
      isPassed = isPassed,
      courseTitle = quiz.courseTitle,
      lessonTitle = quiz.lessonTitle,
      timestamp = System.currentTimeMillis()
    )

    // Save to Room Database asynchronously
    scope.launch {
      val entity = QuizResultEntity(
        quizId = quiz.id,
        quizType = quiz.type.name,
        courseId = quiz.courseId,
        courseName = quiz.courseTitle,
        lessonId = quiz.lessonId,
        lessonName = quiz.lessonTitle,
        score = correct,
        totalQuestions = total,
        percentage = scorePercentage,
        isPassed = isPassed,
        timestamp = attempt.timestamp
      )
      quizDao.insertQuizResult(entity)
    }

    // Update in-memory attempts
    val list = listOf(attempt) + _recentAttempts.value.filterNot { it.quizId == quiz.id && it.timestamp == attempt.timestamp }
    _recentAttempts.value = list.take(20)

    // Calculate new average and stats
    val allScores = list.map { it.scorePercentage }
    val avg = if (allScores.isNotEmpty()) allScores.average().toInt() else 0
    val highest = if (allScores.isNotEmpty()) allScores.maxOrNull() ?: 0 else 0
    val cur = _userProfile.value
    val gainedXp = scorePercentage * 2
    _userProfile.value = cur.copy(
      quizAverage = avg,
      totalQuizzesCompleted = list.size,
      highestQuizScore = maxOf(cur.highestQuizScore, highest),
      currentXp = cur.currentXp + gainedXp
    )

    // Check and unlock achievements
    unlockQuizAchievements(list.size, scorePercentage)

    // Update best score in quiz list
    _quizzes.value = _quizzes.value.map { q ->
      if (q.id == quiz.id) {
        val currentBest = q.bestScore ?: 0
        q.copy(bestScore = maxOf(currentBest, scorePercentage))
      } else q
    }
  }

  fun recordQuizAttempt(quizId: String, quizTitle: String, scorePercentage: Int, correct: Int, total: Int) {
    val existingQuiz = _quizzes.value.find { it.id == quizId }
    if (existingQuiz != null) {
      recordQuizAttempt(existingQuiz, scorePercentage, correct, total)
    } else {
      val fallbackQuiz = Quiz(
        id = quizId,
        title = quizTitle,
        category = "General",
        type = QuizType.LESSON,
        durationMinutes = 5,
        questions = emptyList()
      )
      recordQuizAttempt(fallbackQuiz, scorePercentage, correct, total)
    }
  }

  fun addNote(courseId: String, courseTitle: String, title: String, content: String) {
    val newNote = StudyNote(
      id = "note_${System.currentTimeMillis()}",
      courseId = courseId,
      courseTitle = courseTitle,
      title = title,
      content = content
    )
    val updated = listOf(newNote) + _notes.value
    _notes.value = updated
    saveNotesToPrefs(updated)
  }

  fun deleteNote(noteId: String) {
    val updated = _notes.value.filterNot { it.id == noteId }
    _notes.value = updated
    saveNotesToPrefs(updated)
  }

  private fun saveNotesToPrefs(list: List<StudyNote>) {
    val array = JSONArray()
    list.forEach { note ->
      val obj = JSONObject()
      obj.put("id", note.id)
      obj.put("courseId", note.courseId)
      obj.put("courseTitle", note.courseTitle)
      obj.put("title", note.title)
      obj.put("content", note.content)
      obj.put("dateAdded", note.dateAdded)
      array.put(obj)
    }
    prefs.edit().putString("study_notes", array.toString()).apply()
  }

  fun getSavedLanguage(): AppLanguage {
    val code = prefs.getString("selected_language", AppLanguage.ENGLISH.code) ?: AppLanguage.ENGLISH.code
    return AppLanguage.fromCode(code)
  }

  fun saveLanguage(language: AppLanguage) {
    prefs.edit().putString("selected_language", language.code).apply()
  }

  fun getSavedThemeMode(): AppThemeMode {
    val key = prefs.getString("theme_mode", AppThemeMode.SYSTEM.key) ?: AppThemeMode.SYSTEM.key
    return AppThemeMode.fromKey(key)
  }

  fun saveThemeMode(mode: AppThemeMode) {
    prefs.edit().putString("theme_mode", mode.key).apply()
  }

  fun getUpdateNotificationsEnabled(): Boolean {
    return prefs.getBoolean("update_notifications_enabled", true)
  }

  fun saveUpdateNotificationsEnabled(enabled: Boolean) {
    prefs.edit().putBoolean("update_notifications_enabled", enabled).apply()
  }

  fun getSoundEffectsEnabled(): Boolean {
    return prefs.getBoolean("sound_effects_enabled", true)
  }

  fun saveSoundEffectsEnabled(enabled: Boolean) {
    prefs.edit().putBoolean("sound_effects_enabled", enabled).apply()
  }

  fun resetAllProgress() {
    val currentLang = getSavedLanguage()
    prefs.edit().clear().apply()
    saveLanguage(currentLang)
    loadSeedData()
    loadPersistedState()
  }
}
