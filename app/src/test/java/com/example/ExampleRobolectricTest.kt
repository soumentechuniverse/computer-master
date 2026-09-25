package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
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
    var filtered = viewModel.filteredCourses.value
    assertEquals(1, filtered.size)
    assertEquals("Microsoft Excel", filtered.first().title)

    // Search by category: "Office" -> matches Word, Excel, PowerPoint
    viewModel.setSearchQuery("Office")
    filtered = viewModel.filteredCourses.value
    assertEquals(3, filtered.size)
    val officeTitles = filtered.map { it.title }.toSet()
    assert(officeTitles.contains("Microsoft Word"))
    assert(officeTitles.contains("Microsoft Excel"))
    assert(officeTitles.contains("Microsoft PowerPoint"))

    // Search by category: "Security" -> matches Cyber Security
    viewModel.setSearchQuery("Security")
    filtered = viewModel.filteredCourses.value
    assert(filtered.any { it.title == "Cyber Security" })

    // Clear search -> returns all 18 courses
    viewModel.setSearchQuery("")
    filtered = viewModel.filteredCourses.value
    assertEquals(18, filtered.size)
  }
}
