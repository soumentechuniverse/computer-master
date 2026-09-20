package com.example.data.repository

import com.example.data.model.LessonDetailData

object ComputerBasicsLessonRepository {

  private val allBasicsLessons: List<LessonDetailData> by lazy {
    ComputerBasicsLessonsPart1.getLessons1To5() + ComputerBasicsLessonsPart2.getLessons6To10()
  }

  fun getAllLessons(): List<LessonDetailData> = allBasicsLessons

  fun getLessonById(lessonId: String): LessonDetailData? {
    return allBasicsLessons.find { it.lessonId == lessonId }
  }

  fun getLessonByIndex(index: Int): LessonDetailData? {
    return allBasicsLessons.getOrNull(index)
  }
}
