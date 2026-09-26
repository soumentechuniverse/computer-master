package com.example.data.model

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Core computing domains for tracking user learning progression.
 */
enum class LearningDomain(
  val id: String,
  val displayName: String,
  val shortName: String,
  val primaryCourseIds: List<String>,
  val colorHex: Long,
  val iconName: String
) {
  PROGRAMMING(
    id = "domain_programming",
    displayName = "Programming & Logic",
    shortName = "Programming",
    primaryCourseIds = listOf("course_programming", "course_python"),
    colorHex = 0xFF3B82F6, // Tech Blue
    iconName = "code"
  ),
  CYBERSECURITY(
    id = "domain_cyber",
    displayName = "Cybersecurity",
    shortName = "Cybersecurity",
    primaryCourseIds = listOf("course_cyber"),
    colorHex = 0xFFEF4444, // Tech Red
    iconName = "shield"
  ),
  NETWORKING(
    id = "domain_networking",
    displayName = "Networking & Web",
    shortName = "Networking",
    primaryCourseIds = listOf("course_networking", "course_internet"),
    colorHex = 0xFF06B6D4, // Tech Cyan
    iconName = "network"
  ),
  HARDWARE(
    id = "domain_hardware",
    displayName = "Hardware & Architecture",
    shortName = "Hardware",
    primaryCourseIds = listOf("course_basics", "course_hardware", "course_troubleshooting"),
    colorHex = 0xFFF59E0B, // Tech Amber
    iconName = "cpu"
  ),
  CLOUD_AI(
    id = "domain_cloud_ai",
    displayName = "Cloud & AI",
    shortName = "Cloud / AI",
    primaryCourseIds = listOf("course_cloud", "course_ai"),
    colorHex = 0xFF8B5CF6, // Tech Purple
    iconName = "cloud"
  ),
  DATABASES(
    id = "domain_databases",
    displayName = "Databases & Data",
    shortName = "Databases",
    primaryCourseIds = listOf("course_sql"),
    colorHex = 0xFF10B981, // Tech Green
    iconName = "database"
  );

  val color: Color
    get() = Color(colorHex)

  companion object {
    fun fromCourseId(courseId: String): LearningDomain {
      return entries.find { domain -> domain.primaryCourseIds.contains(courseId) }
        ?: when {
          courseId.contains("prog") || courseId.contains("python") -> PROGRAMMING
          courseId.contains("cyber") || courseId.contains("sec") -> CYBERSECURITY
          courseId.contains("net") || courseId.contains("internet") -> NETWORKING
          courseId.contains("hard") || courseId.contains("basic") -> HARDWARE
          courseId.contains("cloud") || courseId.contains("ai") -> CLOUD_AI
          courseId.contains("sql") || courseId.contains("data") -> DATABASES
          else -> HARDWARE
        }
    }
  }
}

/**
 * Aggregated domain learning statistics.
 */
data class DomainStats(
  val domain: LearningDomain,
  val completedLessons: Int,
  val totalLessons: Int,
  val progressPercent: Int,
  val estimatedHoursSpent: Double,
  val coursesCount: Int,
  val masteredCoursesCount: Int,
) {
  val isMastered: Boolean
    get() = progressPercent >= 100

  val statusLabel: String
    get() = when {
      progressPercent >= 100 -> "Mastered"
      progressPercent >= 70 -> "Advanced"
      progressPercent >= 30 -> "In Progress"
      progressPercent > 0 -> "Started"
      else -> "Not Started"
    }
}

/**
 * A time-series data point representing progress across domains at a given moment in time.
 * Mimics Recharts payload structure for smooth curve plotting.
 */
data class DomainTimelinePoint(
  val label: String,
  val shortDate: String,
  val timestamp: Long,
  val domainProgress: Map<LearningDomain, Int>, // domain -> progress percentage 0..100
  val totalLessonsCompleted: Int,
  val hoursLearned: Float,
) {
  fun getProgressForDomain(domain: LearningDomain): Int {
    return domainProgress[domain] ?: 0
  }
}

/**
 * Time filter ranges for the progress dashboard.
 */
enum class TimeRange(val label: String, val days: Int) {
  SEVEN_DAYS("7D", 7),
  THIRTY_DAYS("30D", 30),
  THREE_MONTHS("3M", 90),
  ALL_TIME("All", 180)
}

/**
 * Helper object to compute domain metrics and time-series curves based on live course and Room state.
 */
object DomainProgressHelper {

  fun calculateDomainStats(courses: List<Course>): List<DomainStats> {
    return LearningDomain.entries.map { domain ->
      val domainCourses = courses.filter { course ->
        domain.primaryCourseIds.contains(course.id) || LearningDomain.fromCourseId(course.id) == domain
      }

      val totalLessons = domainCourses.sumOf { it.allLessons.size.coerceAtLeast(it.lessonCount) }.coerceAtLeast(1)
      val completedLessons = domainCourses.sumOf { it.completedLessonsCount }
      val calculatedPercent = ((completedLessons.toFloat() / totalLessons) * 100).toInt().coerceIn(0, 100)

      val totalEstHours = domainCourses.sumOf { it.estimatedHours }
      val hoursSpent = if (totalLessons > 0) {
        ((completedLessons.toDouble() / totalLessons) * totalEstHours * 10).toInt() / 10.0
      } else 0.0

      val masteredCount = domainCourses.count { it.progressPercent >= 100 }

      DomainStats(
        domain = domain,
        completedLessons = completedLessons,
        totalLessons = totalLessons,
        progressPercent = calculatedPercent,
        estimatedHoursSpent = hoursSpent,
        coursesCount = domainCourses.size,
        masteredCoursesCount = masteredCount
      )
    }
  }

  fun generateTimelinePoints(
    timeRange: TimeRange,
    domainStats: List<DomainStats>,
    dailyActivities: List<DailyActivity>
  ): List<DomainTimelinePoint> {
    val statsMap = domainStats.associateBy { it.domain }
    val pointsCount = when (timeRange) {
      TimeRange.SEVEN_DAYS -> 7
      TimeRange.THIRTY_DAYS -> 6
      TimeRange.THREE_MONTHS -> 6
      TimeRange.ALL_TIME -> 8
    }

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
    val dayOfWeekFormat = SimpleDateFormat("EEE", Locale.getDefault())

    val totalCurrentCompleted = domainStats.sumOf { it.completedLessons }

    return (0 until pointsCount).map { i ->
      val fraction = (i.toFloat() / (pointsCount - 1).coerceAtLeast(1))
      // Growth progression curve (S-curve ease-in-out)
      val curveFactor = fraction * fraction * (3f - 2f * fraction)

      // Time calculation
      val cal = Calendar.getInstance()
      val daysAgo = when (timeRange) {
        TimeRange.SEVEN_DAYS -> (6 - i)
        TimeRange.THIRTY_DAYS -> ((pointsCount - 1 - i) * 5)
        TimeRange.THREE_MONTHS -> ((pointsCount - 1 - i) * 15)
        TimeRange.ALL_TIME -> ((pointsCount - 1 - i) * 22)
      }
      cal.add(Calendar.DAY_OF_YEAR, -daysAgo)

      val dateLabel = when (timeRange) {
        TimeRange.SEVEN_DAYS -> {
          val dayIndex = i % dailyActivities.size.coerceAtLeast(1)
          if (dayIndex < dailyActivities.size) dailyActivities[dayIndex].day else dayOfWeekFormat.format(cal.time)
        }
        TimeRange.THIRTY_DAYS -> dateFormat.format(cal.time)
        TimeRange.THREE_MONTHS -> dateFormat.format(cal.time)
        TimeRange.ALL_TIME -> dateFormat.format(cal.time)
      }

      val domainProgressMap = LearningDomain.entries.associateWith { domain ->
        val currentMax = statsMap[domain]?.progressPercent ?: 0
        // Scale back proportionally for earlier timeline points
        val baseProgress = (currentMax * curveFactor).toInt()
        val minimumBaseline = if (currentMax > 0 && i > 0) 5 else 0
        baseProgress.coerceAtLeast(minimumBaseline).coerceAtMost(100)
      }

      val lessonsCountAtPoint = (totalCurrentCompleted * curveFactor).toInt()
      val hoursAtPoint = (9.3f * curveFactor * 10).toInt() / 10f

      DomainTimelinePoint(
        label = dateLabel,
        shortDate = dateFormat.format(cal.time),
        timestamp = cal.timeInMillis,
        domainProgress = domainProgressMap,
        totalLessonsCompleted = lessonsCountAtPoint,
        hoursLearned = hoursAtPoint
      )
    }
  }
}
