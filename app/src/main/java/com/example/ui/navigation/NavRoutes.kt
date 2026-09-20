package com.example.ui.navigation

object NavRoutes {
  const val HOME = "home"
  const val COURSES = "courses"
  const val COURSE_DETAIL = "course_detail/{courseId}"
  const val LESSON_DETAIL = "lesson_detail/{courseId}/{lessonId}"
  const val QUIZ = "quiz"
  const val PROGRESS = "progress"
  const val PROFILE = "profile"
  const val SETTINGS = "settings"

  fun courseDetail(courseId: String): String = "course_detail/$courseId"
  fun lessonDetail(courseId: String, lessonId: String): String = "lesson_detail/$courseId/$lessonId"
}
