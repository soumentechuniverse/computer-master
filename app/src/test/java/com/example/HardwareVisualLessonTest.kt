package com.example

import com.example.data.model.HardwareComponentType
import com.example.data.model.HardwareLessonRepository
import com.example.data.model.HardwareQuizData
import com.example.util.AppLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class HardwareVisualLessonTest {

  @Test
  fun testAllFourHardwareComponentsExist() {
    val components = HardwareLessonRepository.components
    assertEquals(4, components.size)

    val types = components.map { it.type }.toSet()
    assertTrue(types.contains(HardwareComponentType.MONITOR))
    assertTrue(types.contains(HardwareComponentType.CPU))
    assertTrue(types.contains(HardwareComponentType.KEYBOARD))
    assertTrue(types.contains(HardwareComponentType.MOUSE))
  }

  @Test
  fun testHardwareComponentsHaveLocalizationInAllThreeLanguages() {
    val components = HardwareLessonRepository.components
    val languages = listOf(AppLanguage.BENGALI, AppLanguage.ENGLISH, AppLanguage.HINDI)

    for (component in components) {
      for (lang in languages) {
        val name = component.getName(lang)
        val role = component.getRole(lang)
        val whatItDoes = component.getWhatItDoes(lang)
        val example = component.getRealWorldExample(lang)
        val funFact = component.getFunFact(lang)
        val category = component.getCategory(lang)
        val port = component.getConnectionPort(lang)

        assertTrue("Name must not be blank for ${component.type} in $lang", name.isNotBlank())
        assertTrue("Role must not be blank for ${component.type} in $lang", role.isNotBlank())
        assertTrue("WhatItDoes must not be blank for ${component.type} in $lang", whatItDoes.isNotBlank())
        assertTrue("Example must not be blank for ${component.type} in $lang", example.isNotBlank())
        assertTrue("FunFact must not be blank for ${component.type} in $lang", funFact.isNotBlank())
        assertTrue("Category must not be blank for ${component.type} in $lang", category.isNotBlank())
        assertTrue("Port must not be blank for ${component.type} in $lang", port.isNotBlank())
      }
    }
  }

  @Test
  fun testHardwareQuizQuestionsIntegrity() {
    val questions = HardwareQuizData.questions
    assertEquals(4, questions.size)

    val languages = listOf(AppLanguage.BENGALI, AppLanguage.ENGLISH, AppLanguage.HINDI)

    for (question in questions) {
      for (lang in languages) {
        val qText = question.getQuestion(lang)
        val options = question.getOptions(lang)
        val explanation = question.getExplanation(lang)

        assertTrue("Question text must not be blank", qText.isNotBlank())
        assertEquals("Must have 4 options", 4, options.size)
        options.forEach { opt ->
          assertTrue("Option must not be blank", opt.isNotBlank())
        }
        assertTrue("Correct option index must be in 0..3", question.correctOptionIndex in 0..3)
        assertTrue("Explanation must not be blank", explanation.isNotBlank())
      }
    }
  }
}
