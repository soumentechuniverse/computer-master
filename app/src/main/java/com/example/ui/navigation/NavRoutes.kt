package com.example.ui.navigation

object NavRoutes {
  const val SPLASH = "splash"
  const val WELCOME = "welcome"
  const val AUTH = "auth"
  const val INTERNET_REQUIRED = "internet_required"
  const val HOME = "home"
  const val COURSES = "courses"
  const val COURSE_DETAIL = "course_detail/{courseId}"
  const val LESSON_DETAIL = "lesson_detail/{courseId}/{lessonId}"
  const val QUIZ = "quiz"
  const val PROGRESS = "progress"
  const val PROFILE = "profile"
  const val SETTINGS = "settings"
  const val HARDWARE_VISUAL_LESSON = "hardware_visual_lesson"
  const val CPU_RAM_ROM_LESSON = "cpu_ram_rom_lesson"

  fun courseDetail(courseId: String): String = "course_detail/$courseId"
  fun lessonDetail(courseId: String, lessonId: String): String = "lesson_detail/$courseId/$lessonId"
}
