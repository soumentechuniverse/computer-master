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
}
