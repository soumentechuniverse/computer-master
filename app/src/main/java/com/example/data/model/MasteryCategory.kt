package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TechRed

/**
 * Mastery level tier classifications based on user completion percentage.
 */
enum class MasteryTier(
  val label: String,
  val minPercent: Int,
  val maxPercent: Int,
  val colorHex: Long,
  val badgeText: String,
) {
  MASTERED("Mastered", 100, 100, 0xFF10B981, "★ Mastered"),
  PROFICIENT("Proficient", 70, 99, 0xFF06B6D4, "◆ Proficient"),
  INTERMEDIATE("Intermediate", 40, 69, 0xFF3B82F6, "▲ Intermediate"),
  NOVICE("Novice", 1, 39, 0xFFF59E0B, "● Novice"),
  UNSTARTED("Not Started", 0, 0, 0xFF64748B, "○ Unstarted");

  val color: Color
    get() = Color(colorHex)

  companion object {
    fun fromPercent(percent: Int): MasteryTier = when {
      percent >= 100 -> MASTERED
      percent >= 70 -> PROFICIENT
      percent >= 40 -> INTERMEDIATE
      percent >= 1 -> NOVICE
      else -> UNSTARTED
    }
  }
}

/**
 * Mastery Category data model representing a core domain/category of computing knowledge
 * with calculated user completion percentage and lesson counts.
 */
data class MasteryCategory(
  val id: String,
  val name: String,
  val shortName: String,
  val description: String,
  val color: Color,
  val iconName: String,
  val targetCourseIds: List<String>,
  val completedLessons: Int,
  val totalLessons: Int,
  val completionPercent: Int, // 0..100
  val coursesCount: Int,
  val masteredCoursesCount: Int,
  val estimatedHours: Double,
  val masteryTier: MasteryTier,
  val relatedCourses: List<Course> = emptyList(),
) {
  val isMastered: Boolean
    get() = completionPercent >= 100

  val progressFraction: Float
    get() = (completionPercent / 100f).coerceIn(0f, 1f)

  val statusSummary: String
    get() = when (masteryTier) {
      MasteryTier.MASTERED -> "All lessons fully mastered"
      MasteryTier.PROFICIENT -> "Close to complete mastery"
      MasteryTier.INTERMEDIATE -> "Solid foundational skills gained"
      MasteryTier.NOVICE -> "In progress, keep practicing"
      MasteryTier.UNSTARTED -> "Ready to begin learning"
    }
}

/**
 * Summary metrics for mastery across all categories.
 */
data class MasteryOverview(
  val categories: List<MasteryCategory>,
  val overallCompletionPercent: Int,
  val totalCompletedLessons: Int,
  val totalLessons: Int,
  val masteredCategoriesCount: Int,
  val proficientCategoriesCount: Int,
  val intermediateCategoriesCount: Int,
  val noviceCategoriesCount: Int,
  val unstartedCategoriesCount: Int,
  val topCategory: MasteryCategory?,
  val lowestCategory: MasteryCategory?,
)

/**
 * Helper to compute mastery categories and completion percentages from live Course data.
 */
object MasteryCategoryHelper {

  private val CATEGORY_CONFIGS = listOf(
    CategoryConfig(
      id = "cat_programming",
      name = "Programming & Logic",
      shortName = "Programming",
      description = "Algorithms, control flow, functions, OOP, and Python coding fundamentals",
      color = Color(0xFF3B82F6), // Tech Blue
      iconName = "code",
      courseIds = listOf("course_programming")
    ),
    CategoryConfig(
      id = "cat_cyber",
      name = "Cybersecurity",
      shortName = "Security",
      description = "Threat vectors, authentication defenses, encryption, and secure computing",
      color = Color(0xFFEF4444), // Tech Red
      iconName = "shield",
      courseIds = listOf("course_cyber")
    ),
    CategoryConfig(
      id = "cat_networking",
      name = "Networking & Web",
      shortName = "Networking",
      description = "TCP/IP models, routing protocols, DNS, client-server, and the Internet",
      color = Color(0xFF06B6D4), // Tech Cyan
      iconName = "network",
      courseIds = listOf("course_networking", "course_internet")
    ),
    CategoryConfig(
      id = "cat_hardware",
      name = "Hardware & Architecture",
      shortName = "Hardware",
      description = "CPU pipelines, RAM, ROM, storage bus architecture, and component assembly",
      color = Color(0xFFF59E0B), // Tech Amber
      iconName = "cpu",
      courseIds = listOf("course_basics", "course_hardware", "course_troubleshooting")
    ),
    CategoryConfig(
      id = "cat_sql",
      name = "Databases & SQL",
      shortName = "Databases",
      description = "Relational tables, SQL queries, indexes, normalization, and ACID properties",
      color = Color(0xFF10B981), // Tech Green
      iconName = "database",
      courseIds = listOf("course_sql")
    ),
    CategoryConfig(
      id = "cat_cloud_ai",
      name = "Cloud & AI",
      shortName = "Cloud / AI",
      description = "Cloud virtualization, containers, serverless architectures, and AI networks",
      color = Color(0xFF8B5CF6), // Tech Purple
      iconName = "cloud",
      courseIds = listOf("course_cloud", "course_ai")
    ),
    CategoryConfig(
      id = "cat_os",
      name = "Operating Systems",
      shortName = "OS & Systems",
      description = "Process scheduling, memory management, file systems, and CLI commands",
      color = Color(0xFFEC4899), // Tech Pink
      iconName = "terminal",
      courseIds = listOf("course_os", "course_windows", "course_software", "course_files")
    ),
    CategoryConfig(
      id = "cat_office",
      name = "Office & Productivity",
      shortName = "Productivity",
      description = "Spreadsheet formulas, data visualization, document design, and slides",
      color = Color(0xFF14B8A6), // Tech Teal
      iconName = "library",
      courseIds = listOf("course_word", "course_excel", "course_powerpoint")
    )
  )

  private data class CategoryConfig(
    val id: String,
    val name: String,
    val shortName: String,
    val description: String,
    val color: Color,
    val iconName: String,
    val courseIds: List<String>,
  )

  /**
   * Calculates all mastery categories and their completion percentages from live course list.
   */
  fun calculateMasteryCategories(courses: List<Course>): List<MasteryCategory> {
    val courseMap = courses.associateBy { it.id }

    return CATEGORY_CONFIGS.map { config ->
      val matchedCourses = config.courseIds.mapNotNull { courseMap[it] }

      val totalLessons = matchedCourses.sumOf { course ->
        course.allLessons.size.coerceAtLeast(course.lessonCount)
      }.coerceAtLeast(1)

      val completedLessons = matchedCourses.sumOf { course ->
        course.completedLessonsCount
      }

      val percent = ((completedLessons.toFloat() / totalLessons) * 100).toInt().coerceIn(0, 100)
      val totalHours = matchedCourses.sumOf { it.estimatedHours }
      val masteredCount = matchedCourses.count { it.progressPercent >= 100 }
      val tier = MasteryTier.fromPercent(percent)

      MasteryCategory(
        id = config.id,
        name = config.name,
        shortName = config.shortName,
        description = config.description,
        color = config.color,
        iconName = config.iconName,
        targetCourseIds = config.courseIds,
        completedLessons = completedLessons,
        totalLessons = totalLessons,
        completionPercent = percent,
        coursesCount = matchedCourses.size,
        masteredCoursesCount = masteredCount,
        estimatedHours = totalHours,
        masteryTier = tier,
        relatedCourses = matchedCourses
      )
    }
  }

  /**
   * Calculates comprehensive overview metrics across all mastery categories.
   */
  fun calculateOverview(categories: List<MasteryCategory>): MasteryOverview {
    val totalCompleted = categories.sumOf { it.completedLessons }
    val totalLessons = categories.sumOf { it.totalLessons }.coerceAtLeast(1)
    val overallPercent = ((totalCompleted.toFloat() / totalLessons) * 100).toInt().coerceIn(0, 100)

    val masteredCount = categories.count { it.masteryTier == MasteryTier.MASTERED }
    val proficientCount = categories.count { it.masteryTier == MasteryTier.PROFICIENT }
    val intermediateCount = categories.count { it.masteryTier == MasteryTier.INTERMEDIATE }
    val noviceCount = categories.count { it.masteryTier == MasteryTier.NOVICE }
    val unstartedCount = categories.count { it.masteryTier == MasteryTier.UNSTARTED }

    val sortedByPercent = categories.sortedByDescending { it.completionPercent }
    val top = sortedByPercent.firstOrNull()
    val lowest = sortedByPercent.lastOrNull()

    return MasteryOverview(
      categories = categories,
      overallCompletionPercent = overallPercent,
      totalCompletedLessons = totalCompleted,
      totalLessons = totalLessons,
      masteredCategoriesCount = masteredCount,
      proficientCategoriesCount = proficientCount,
      intermediateCategoriesCount = intermediateCount,
      noviceCategoriesCount = noviceCount,
      unstartedCategoriesCount = unstartedCount,
      topCategory = top,
      lowestCategory = lowest
    )
  }
}
