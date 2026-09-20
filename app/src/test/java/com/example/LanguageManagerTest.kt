package com.example

import com.example.util.AppLanguage
import com.example.util.AppStrings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LanguageManagerTest {

  @Test
  fun testLanguageDefinitions() {
    val languages = AppLanguage.entries
    assertEquals(3, languages.size)

    // Bengali
    val bengali = AppLanguage.fromCode("bn")
    assertEquals(AppLanguage.BENGALI, bengali)
    assertEquals("বাংলা", bengali.nativeName)
    assertEquals("Bengali", bengali.displayName)

    // English
    val english = AppLanguage.fromCode("en")
    assertEquals(AppLanguage.ENGLISH, english)
    assertEquals("English", english.nativeName)
    assertEquals("English", english.displayName)

    // Hindi
    val hindi = AppLanguage.fromCode("hi")
    assertEquals(AppLanguage.HINDI, hindi)
    assertEquals("हिन्दी", hindi.nativeName)
    assertEquals("Hindi", hindi.displayName)

    // Default fallback
    assertEquals(AppLanguage.ENGLISH, AppLanguage.fromCode("unknown"))
  }

  @Test
  fun testCreatorNamePreservedInAllLanguages() {
    for (lang in AppLanguage.entries) {
      val createdBy = AppStrings.welcomeCreatedBy(lang)
      assertTrue(
        "Creator name Soumen Mondal must be preserved in ${lang.name}, got: $createdBy",
        createdBy.contains("Soumen Mondal")
      )
    }
  }

  @Test
  fun testAllNavigationLabelsPresent() {
    for (lang in AppLanguage.entries) {
      assertTrue(AppStrings.navHome(lang).isNotBlank())
      assertTrue(AppStrings.navCourses(lang).isNotBlank())
      assertTrue(AppStrings.navQuiz(lang).isNotBlank())
      assertTrue(AppStrings.navProfile(lang).isNotBlank())
      assertTrue(AppStrings.welcomeTitle(lang).isNotBlank())
      assertTrue(AppStrings.getStarted(lang).isNotBlank())
      assertTrue(AppStrings.appLanguage(lang).isNotBlank())
    }
  }
}
