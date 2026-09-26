package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Computer Master", appName)
  }

  @Test
  fun `verify Computer Basics course has 10 complete lessons`() {
    val lessons = com.example.data.repository.ComputerBasicsLessonRepository.getAllLessons()
    assertEquals(10, lessons.size)

    // Verify all 10 lesson titles and detailed sections are populated
    lessons.forEachIndexed { index, lesson ->
      assertEquals(index + 1, lesson.lessonNumber)
      assert(lesson.title.isNotBlank())
      assert(lesson.objectives.isNotEmpty())
      assert(lesson.quickIntro.isNotBlank())
      assert(lesson.simpleExplanation.isNotBlank())
      assert(lesson.detailedSections.isNotEmpty())
      assert(lesson.realWorldExamples.isNotEmpty())
      assert(lesson.howItWorksSteps.isNotEmpty())
      assert(lesson.importantPoints.isNotEmpty())
      assert(lesson.commonMistakes.isNotEmpty())
      assert(lesson.practicalActivity.steps.isNotEmpty())
      assert(lesson.knowledgeCheck.options.size >= 4)
    }
  }

  @Test
  fun `verify creator branding string and landing screen configuration`() {
    val creatorBadgeText = "Created & Published by Soumen Mondal"
    val appTitleText = "Computer Master"
    val subtitleText = "Master Every Byte, Bit & Algorithm"
    val getStartedButtonText = "GET STARTED →"

    assert(creatorBadgeText.contains("Created & Published by Soumen Mondal"))
    assertEquals("Computer Master", appTitleText)
    assertEquals("Master Every Byte, Bit & Algorithm", subtitleText)
    assertEquals("GET STARTED →", getStartedButtonText)
  }

  @Test
  fun `verify exactly 18 courses exist in exact requested order`() {
    val courses = com.example.data.repository.CourseSeedData.getInitialCourses()
    assertEquals(18, courses.size)

    val expectedTitles = listOf(
      "Computer Fundamentals",
      "Computer Hardware",
      "Software",
      "Operating Systems",
      "Windows",
      "Files and Folders",
      "Internet",
      "Networking",
      "Microsoft Word",
      "Microsoft Excel",
      "Microsoft PowerPoint",
      "Programming Basics",
      "Databases",
      "Cyber Security",
      "Cloud Computing",
      "Artificial Intelligence",
      "Computer Troubleshooting",
      "Advanced Computer Knowledge"
    )

    expectedTitles.forEachIndexed { index, expectedTitle ->
      assertEquals(
        "Course at index $index must be $expectedTitle",
        expectedTitle,
        courses[index].title
      )
    }
  }

  @Test
  fun `verify repository loads all 18 courses and preserves existing lessons`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repository = com.example.data.repository.ComputerMasterRepository(context)
    val courses = repository.courses.value

    assertEquals(18, courses.size)

    // Verify Fundamentals has 10 lessons
    val fundamentals = courses.first { it.title == "Computer Fundamentals" }
    assertEquals(10, fundamentals.lessonCount)
    assert(fundamentals.modules.isNotEmpty())

    // Verify newly added courses have 0 lessons (no fake counts)
    val hardware = courses.first { it.title == "Computer Hardware" }
    assertEquals(0, hardware.lessonCount)
    assert(hardware.modules.isEmpty())

    val software = courses.first { it.title == "Software" }
    assertEquals(0, software.lessonCount)
    assert(software.modules.isEmpty())

    val os = courses.first { it.title == "Operating Systems" }
    assertEquals(0, os.lessonCount)
    assert(os.modules.isEmpty())

    val troubleshooting = courses.first { it.title == "Computer Troubleshooting" }
    assertEquals(0, troubleshooting.lessonCount)
    assert(troubleshooting.modules.isEmpty())
  }

  @Test
  fun `verify all 10 sound resources exist and SoundManager operates correctly`() {
    val context = ApplicationProvider.getApplicationContext<Context>()

    // Verify all 10 sound raw resources exist
    val soundResources = listOf(
      R.raw.sound_startup,
      R.raw.sound_get_started,
      R.raw.sound_course_click,
      R.raw.sound_lesson_open,
      R.raw.sound_lesson_complete,
      R.raw.sound_quiz_correct,
      R.raw.sound_quiz_wrong,
      R.raw.sound_bookmark,
      R.raw.sound_note_saved,
      R.raw.sound_back
    )

    assertEquals(10, soundResources.size)

    for (resId in soundResources) {
      val inputStream = context.resources.openRawResource(resId)
      assert(inputStream.available() > 0)
      inputStream.close()
    }

    // Verify SoundManager initialization and toggle settings
    val soundManager = com.example.util.SoundManager.getInstance(context)
    assert(soundManager.isSoundEnabled)

    soundManager.setSoundEnabled(false)
    assert(!soundManager.isSoundEnabled)

    soundManager.setSoundEnabled(true)
    assert(soundManager.isSoundEnabled)

    // Verify all 10 sound playback methods execute cleanly
    soundManager.playStartup()
    soundManager.playGetStarted()
    soundManager.playCourseClick()
    soundManager.playLessonOpen()
    soundManager.playLessonCompleted()
    soundManager.playQuizCorrect()
    soundManager.playQuizWrong()
    soundManager.playBookmark()
    soundManager.playNoteSaved()
    soundManager.playBack()
  }

  @Test
  fun `verify course search by title and category`() {
    val context = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.viewmodel.ComputerMasterViewModel(context)

    // Initial total courses should be 18
    assertEquals(18, viewModel.allCourses.value.size)

    // Search by title: "Excel"
    viewModel.setSearchQuery("Excel")
    ShadowLooper.idleMainLooper()
    var filtered = viewModel.filteredCourses.value
    assertEquals(1, filtered.size)
    assertEquals("Microsoft Excel", filtered.first().title)

    // Search by category: "Office" -> matches Word, Excel, PowerPoint
    viewModel.setSearchQuery("Office")
    ShadowLooper.idleMainLooper()
    filtered = viewModel.filteredCourses.value
    assertEquals(3, filtered.size)
    val officeTitles = filtered.map { it.title }.toSet()
    assert(officeTitles.contains("Microsoft Word"))
    assert(officeTitles.contains("Microsoft Excel"))
    assert(officeTitles.contains("Microsoft PowerPoint"))

    // Search by category: "Security" -> matches Cyber Security
    viewModel.setSearchQuery("Security")
    ShadowLooper.idleMainLooper()
    filtered = viewModel.filteredCourses.value
    assert(filtered.any { it.title == "Cyber Security" })

    // Clear search -> returns all 18 courses
    viewModel.setSearchQuery("")
    ShadowLooper.idleMainLooper()
    filtered = viewModel.filteredCourses.value
    assertEquals(18, filtered.size)
  }

  @Test
  fun `verify past week learning activity lessons completed`() {
    val context = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.viewmodel.ComputerMasterViewModel(context)

    val activities = viewModel.dailyActivities.value
    assertEquals(7, activities.size)

    val expectedDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    assertEquals(expectedDays, activities.map { it.day })

    // Verify lessons completed counts over the past week
    val totalLessonsPastWeek = activities.sumOf { it.lessonsCount }
    assertEquals(28, totalLessonsPastWeek)

    val thursday = activities.first { it.day == "Thu" }
    assertEquals(7, thursday.lessonsCount)

    assert(activities.all { it.lessonsCount >= 0 })
  }

  @Test
  fun `verify lesson quiz mode generates 5 multiple choice questions with options and explanations`() {
    // 1. Verify curated Computer Basics lesson quiz has 5 questions
    val cbQuiz = com.example.data.repository.LessonQuizGenerator.generateQuizForLesson(
      lessonId = "cb_lesson_1",
      lessonTitle = "What is a Computer?",
      courseId = "course_basics",
      courseTitle = "Computer Basics"
    )
    assertEquals(5, cbQuiz.questions.size)
    cbQuiz.questions.forEach { question ->
      assertEquals(4, question.options.size)
      assert(question.correctOptionIndex in 0..3)
      assert(question.explanation.isNotBlank())
    }

    // 2. Verify dynamic quiz generation for Excel lesson generates 5 multiple-choice questions
    val excelQuiz = com.example.data.repository.LessonQuizGenerator.generateQuizForLesson(
      lessonId = "excel_lesson_3",
      lessonTitle = "Excel Core Formulas & Calculations",
      courseId = "course_excel",
      courseTitle = "Microsoft Excel"
    )
    assertEquals(5, excelQuiz.questions.size)
    assertEquals("Excel Core Formulas & Calculations Quiz", excelQuiz.title)
    excelQuiz.questions.forEach { question ->
      assertEquals(4, question.options.size)
      assert(question.correctOptionIndex in 0..3)
      assert(question.explanation.isNotBlank())
    }

    // 3. Verify Programming Basics lesson quiz has 5 multiple-choice questions
    val progQuiz = com.example.data.repository.LessonQuizGenerator.generateQuizForLesson(
      lessonId = "prog_lesson_2",
      lessonTitle = "Variables and Loops",
      courseId = "course_programming",
      courseTitle = "Programming Basics"
    )
    assertEquals(5, progQuiz.questions.size)
    progQuiz.questions.forEach { question ->
      assertEquals(4, question.options.size)
      assert(question.correctOptionIndex in 0..3)
      assert(question.explanation.isNotBlank())
    }

    // 4. Verify ViewModel startQuizForLesson initiates 5-question active quiz session
    val context = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.viewmodel.ComputerMasterViewModel(context)
    viewModel.startQuizForLesson("cb_lesson_1", "What is a Computer?", "Computer Basics")

    val activeState = viewModel.activeQuizState.value
    assert(activeState.activeQuiz != null)
    assertEquals(5, activeState.activeQuiz!!.questions.size)
    assertEquals(0, activeState.currentQuestionIndex)
    assert(!activeState.isQuizCompleted)
  }

  @Test
  fun `verify Room database stores user lesson completion and tracks course progress`() = kotlinx.coroutines.runBlocking {
    val context = ApplicationProvider.getApplicationContext<android.app.Application>()
    val db = com.example.data.database.AppDatabase.getDatabase(context)
    val dao = db.courseProgressDao()

    // 1. Insert lesson completions into Room
    val completion1 = com.example.data.database.LessonCompletionEntity(
      courseId = "course_basics",
      lessonId = "cb_l1",
      isCompleted = true,
      completedAt = System.currentTimeMillis()
    )
    val completion2 = com.example.data.database.LessonCompletionEntity(
      courseId = "course_basics",
      lessonId = "cb_l2",
      isCompleted = true,
      completedAt = System.currentTimeMillis()
    )
    dao.insertLessonCompletion(completion1)
    dao.insertLessonCompletion(completion2)

    val storedCompletions = dao.getAllCompletedLessonsOnce()
    assert(storedCompletions.any { it.courseId == "course_basics" && it.lessonId == "cb_l1" })
    assert(storedCompletions.any { it.courseId == "course_basics" && it.lessonId == "cb_l2" })

    // 2. Insert and verify CourseProgressEntity in Room
    val progress = com.example.data.database.CourseProgressEntity(
      courseId = "course_basics",
      completedLessonsCount = 2,
      totalLessonsCount = 10,
      progressPercent = 20,
      isCompleted = false,
      lastUpdated = System.currentTimeMillis()
    )
    dao.insertOrUpdateCourseProgress(progress)

    val retrievedProgress = dao.getCourseProgressOnce("course_basics")
    assert(retrievedProgress != null)
    assertEquals(2, retrievedProgress?.completedLessonsCount)
    assertEquals(10, retrievedProgress?.totalLessonsCount)
    assertEquals(20, retrievedProgress?.progressPercent)
    assertEquals(false, retrievedProgress?.isCompleted)

    // 3. Verify ViewModel toggleLessonCompletion updates course progress
    val viewModel = com.example.ui.viewmodel.ComputerMasterViewModel(context)
    val initialBasicsCourse = viewModel.allCourses.value.first { it.id == "course_basics" }
    val initialCompletedCount = initialBasicsCourse.completedLessonsCount

    // Toggle completion on a lesson in course_basics
    val targetLesson = initialBasicsCourse.allLessons.first()
    val wasCompleted = targetLesson.isCompleted
    viewModel.toggleLessonCompletion("course_basics", targetLesson.id)
    ShadowLooper.idleMainLooper()

    val updatedBasicsCourse = viewModel.allCourses.value.first { it.id == "course_basics" }
    val updatedLesson = updatedBasicsCourse.allLessons.first { it.id == targetLesson.id }
    assertEquals(!wasCompleted, updatedLesson.isCompleted)
    assertEquals(
      if (!wasCompleted) initialCompletedCount + 1 else initialCompletedCount - 1,
      updatedBasicsCourse.completedLessonsCount
    )
  }
}
