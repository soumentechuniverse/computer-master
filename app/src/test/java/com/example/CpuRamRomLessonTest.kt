package com.example

import com.example.data.model.CpuInternalPart
import com.example.data.model.CpuRamRomLessonRepository
import com.example.data.model.RamPartType
import com.example.util.AppLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CpuRamRomLessonTest {

  private val allLanguages = listOf(AppLanguage.BENGALI, AppLanguage.ENGLISH, AppLanguage.HINDI)

  @Test
  fun testAllCpuInternalPartsExist() {
    val cpuParts = CpuRamRomLessonRepository.cpuParts
    assertEquals(8, cpuParts.size)

    val types = cpuParts.map { it.part }.toSet()
    assertTrue(types.contains(CpuInternalPart.CHIP_DIE))
    assertTrue(types.contains(CpuInternalPart.CONTROL_UNIT))
    assertTrue(types.contains(CpuInternalPart.ALU))
    assertTrue(types.contains(CpuInternalPart.REGISTERS))
    assertTrue(types.contains(CpuInternalPart.CACHE))
    assertTrue(types.contains(CpuInternalPart.CORES))
    assertTrue(types.contains(CpuInternalPart.CLOCK))
    assertTrue(types.contains(CpuInternalPart.HEAT_COOLING))
  }

  @Test
  fun testCpuPartsLocalizationInAllThreeLanguages() {
    val cpuParts = CpuRamRomLessonRepository.cpuParts

    for (part in cpuParts) {
      for (lang in allLanguages) {
        val name = part.getName(lang)
        val subtitle = part.getSubtitle(lang)
        val whatItIs = part.getWhatItIs(lang)
        val whatItDoes = part.getWhatItDoes(lang)
        val howItWorks = part.getHowItWorks(lang)
        val whyImportant = part.getWhyImportant(lang)
        val realLife = part.getRealLifeExample(lang)

        assertTrue("Name should not be blank for ${part.part} in $lang", name.isNotBlank())
        assertTrue("Subtitle should not be blank for ${part.part} in $lang", subtitle.isNotBlank())
        assertTrue("WhatItIs should not be blank for ${part.part} in $lang", whatItIs.isNotBlank())
        assertTrue("WhatItDoes should not be blank for ${part.part} in $lang", whatItDoes.isNotBlank())
        assertTrue("HowItWorks should not be blank for ${part.part} in $lang", howItWorks.isNotBlank())
        assertTrue("WhyImportant should not be blank for ${part.part} in $lang", whyImportant.isNotBlank())
        assertTrue("RealLife should not be blank for ${part.part} in $lang", realLife.isNotBlank())
      }
    }
  }

  @Test
  fun testAllRamPartsExistAndAreLocalized() {
    val ramParts = CpuRamRomLessonRepository.ramParts
    assertEquals(4, ramParts.size)

    val types = ramParts.map { it.type }.toSet()
    assertTrue(types.contains(RamPartType.RAM_STICK))
    assertTrue(types.contains(RamPartType.MEMORY_CHIPS))
    assertTrue(types.contains(RamPartType.DIMM_SLOTS))
    assertTrue(types.contains(RamPartType.DATA_BUS))

    for (part in ramParts) {
      for (lang in allLanguages) {
        val name = part.getName(lang)
        val whatItIs = part.getWhatItIs(lang)
        val whatItDoes = part.getWhatItDoes(lang)
        val analogy = part.getAnalogy(lang)

        assertTrue("RAM part name should not be blank for ${part.type} in $lang", name.isNotBlank())
        assertTrue("RAM part whatItIs should not be blank for ${part.type} in $lang", whatItIs.isNotBlank())
        assertTrue("RAM part whatItDoes should not be blank for ${part.type} in $lang", whatItDoes.isNotBlank())
        assertTrue("RAM part analogy should not be blank for ${part.type} in $lang", analogy.isNotBlank())
      }
    }
  }

  @Test
  fun testRomDetailsAreLocalized() {
    val comparisons = CpuRamRomLessonRepository.comparisons
    assertTrue("Should have comparison rows", comparisons.isNotEmpty())

    for (row in comparisons) {
      for (lang in allLanguages) {
        assertTrue("Comparison feature non-blank in $lang", row.getFeature(lang).isNotBlank())
        assertTrue("Comparison cpu non-blank in $lang", row.getCpu(lang).isNotBlank())
        assertTrue("Comparison ram non-blank in $lang", row.getRam(lang).isNotBlank())
        assertTrue("Comparison rom non-blank in $lang", row.getRom(lang).isNotBlank())
      }
    }
  }

  @Test
  fun testQuizHasAtLeast8QuestionsWithFullLocalizationAndValidAnswers() {
    val quizQuestions = CpuRamRomLessonRepository.quizQuestions
    assertTrue("Quiz must have at least 8 questions", quizQuestions.size >= 8)

    for (q in quizQuestions) {
      assertTrue("Valid correctOptionIndex (0..3) for Q#${q.id}", q.correctOptionIndex in 0..3)

      for (lang in allLanguages) {
        val questionText = q.getQuestion(lang)
        val options = q.getOptions(lang)
        val explanation = q.getExplanation(lang)

        assertTrue("Question text should not be blank for Q#${q.id} in $lang", questionText.isNotBlank())
        assertEquals("Question Q#${q.id} must have 4 options in $lang", 4, options.size)
        options.forEachIndexed { idx, opt ->
          assertTrue("Option $idx for Q#${q.id} should not be blank in $lang", opt.isNotBlank())
        }
        assertTrue("Explanation should not be blank for Q#${q.id} in $lang", explanation.isNotBlank())
      }
    }
  }

  @Test
  fun testPipelineAndComparisonIntegrity() {
    val pipelineStages = CpuRamRomLessonRepository.pipelineStages
    assertEquals(5, pipelineStages.size)

    for (stage in pipelineStages) {
      for (lang in allLanguages) {
        assertTrue("Pipeline stage title non-blank in $lang", stage.getTitle(lang).isNotBlank())
        assertTrue("Pipeline stage subtitle non-blank in $lang", stage.getSubtitle(lang).isNotBlank())
        assertTrue("Pipeline stage desc non-blank in $lang", stage.getDesc(lang).isNotBlank())
      }
    }

    val comparisons = CpuRamRomLessonRepository.comparisons
    assertTrue("Should have multiple comparison rows", comparisons.size >= 6)

    for (row in comparisons) {
      for (lang in allLanguages) {
        assertTrue("Comparison feature non-blank in $lang", row.getFeature(lang).isNotBlank())
        assertTrue("Comparison cpu non-blank in $lang", row.getCpu(lang).isNotBlank())
        assertTrue("Comparison ram non-blank in $lang", row.getRam(lang).isNotBlank())
        assertTrue("Comparison rom non-blank in $lang", row.getRom(lang).isNotBlank())
      }
    }
  }
}
