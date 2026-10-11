package com.example.data.repository

import com.example.data.model.CommonMistake
import com.example.data.model.Course
import com.example.data.model.HowItWorksStep
import com.example.data.model.Lesson
import com.example.data.model.LessonDetailData
import com.example.data.model.LessonQuizQuestion
import com.example.data.model.PracticalActivity
import com.example.data.model.RealWorldExample

/**
 * Builds a structured, readable lesson for every course-catalog entry.
 * Curated Computer Basics lessons continue to use their richer hand-written content.
 */
object CourseLessonDetailFactory {
  fun create(course: Course, lesson: Lesson): LessonDetailData {
    val orderedLessons = course.allLessons
    val index = orderedLessons.indexOfFirst { it.id == lesson.id }.coerceAtLeast(0)
    val previous = orderedLessons.getOrNull(index - 1)
    val next = orderedLessons.getOrNull(index + 1)
    val subject = lesson.title
    val explanation = lesson.description.ifBlank {
      "This lesson introduces $subject in the context of ${course.title}. Work through the key ideas, examples, and activity before moving on."
    }
    return LessonDetailData(
      lessonId = lesson.id,
      lessonNumber = index + 1,
      totalLessons = orderedLessons.size.coerceAtLeast(1),
      courseId = course.id,
      courseTitle = course.title,
      title = subject,
      readingTimeMinutes = lesson.durationMinutes.coerceAtLeast(5),
      level = course.level,
      objectives = listOf(
        "Explain the main purpose of $subject in your own words.",
        "Recognise the key terms and steps used in this topic.",
        "Connect the lesson to a practical computer task.",
        "Check your understanding before continuing to the next lesson."
      ),
      quickIntro = explanation,
      simpleExplanation = "Begin with the main idea: $explanation\n\nRead each section slowly, connect it to a computer or phone task you already know, and try the activity at the end. You do not need to memorise every term at once.",
      detailedSections = listOf(
        "1. The main idea" to "$subject is part of ${course.title}. Start by understanding what it is used for, what problem it solves, and where you may encounter it in everyday computing.\n\nKey description: $explanation",
        "2. How to approach it step by step" to "First identify the goal. Next, find the relevant tool, setting, or concept. Follow one step at a time, check the result after each action, and avoid changing several things at once. If the result is unexpected, return to the last step that worked.",
        "3. Practical use and good habits" to "Use a small example before trying a larger task. Keep important files backed up, prefer trusted software and sources, read messages before confirming actions, and record useful steps so you can repeat them confidently.",
        "4. Check your understanding" to "Explain the lesson in your own words. Try to name one real-world example, one common mistake to avoid, and one way to verify that the task worked. If any part is unclear, revisit the section before moving on."
      ),
      realWorldExamples = listOf(
        RealWorldExample(
          title = "Everyday computer use",
          description = "When you use a computer for school, work, or personal tasks, the ideas from $subject help you choose the right action and understand the result.",
          iconName = "computer"
        ),
        RealWorldExample(
          title = "A practical learning task",
          description = "Try the lesson in a safe practice environment, check what changed, and compare the result with your goal before using the same steps on important data.",
          iconName = "school"
        )
      ),
      howItWorksSteps = listOf(
        HowItWorksStep(1, "Identify the goal", "Decide what you want to learn or accomplish with $subject."),
        HowItWorksStep(2, "Choose the right tool or concept", "Locate the setting, feature, or idea described in this lesson."),
        HowItWorksStep(3, "Try one step at a time", "Follow the instructions carefully and observe the result after each action."),
        HowItWorksStep(4, "Verify and review", "Check whether the result matches your goal, then summarise what you learned.")
      ),
      visualDiagramType = "GENERIC_FLOW",
      importantPoints = listOf(
        "Understand the purpose of $subject before memorising terminology.",
        "Practise with a small, safe example and verify the result.",
        "Use trusted sources and protect important files and personal information.",
        "Review the key idea and complete the knowledge check before continuing."
      ),
      commonMistakes = listOf(
        CommonMistake(
          mistake = "Skipping instructions and changing many settings at once.",
          correction = "Follow one step at a time and check the result after each change."
        ),
        CommonMistake(
          mistake = "Assuming a task worked without checking the result.",
          correction = "Verify the outcome and keep a backup before making changes to important files or settings."
        )
      ),
      practicalActivity = PracticalActivity(
        title = "Practise: $subject",
        objective = "Apply one idea from this lesson and explain what you observed.",
        steps = listOf(
          "Write down the goal of the task in one sentence.",
          "Use a safe practice example related to $subject.",
          "Follow the steps from the lesson and observe the result.",
          "Explain what worked, what was confusing, and how you checked the result."
        )
      ),
      knowledgeCheck = LessonQuizQuestion(
        question = "What is the best way to learn and apply \"$subject\"?",
        options = listOf(
          "Skip the explanation and guess",
          "Practise step by step and verify the result",
          "Change many settings at the same time",
          "Ignore any error messages"
        ),
        correctOptionIndex = 1,
        explanation = "Step-by-step practice with a result check helps you understand what happened and makes mistakes easier to identify."
      ),
      prevLessonId = previous?.id,
      nextLessonId = next?.id
    )
  }
}
