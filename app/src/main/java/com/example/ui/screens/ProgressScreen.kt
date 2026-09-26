package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Achievement
import com.example.data.model.DailyActivity
import com.example.ui.components.CourseIcon
import com.example.ui.components.StatCard
import com.example.ui.components.charts.DomainProgressDashboard
import com.example.ui.components.charts.MasteryCategoriesChart
import androidx.compose.material.icons.filled.DonutLarge
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel

@Composable
fun ProgressScreen(
  viewModel: ComputerMasterViewModel,
  onNavigateToCourse: (String) -> Unit,
  onNavigateToAchievements: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val allCourses by viewModel.allCourses.collectAsState()
  val achievements by viewModel.achievements.collectAsState()
  val dailyActivities by viewModel.dailyActivities.collectAsState()

  val activeCourses = allCourses.filter { it.progressPercent > 0 }
  val lessonsRemaining = userProfile.totalLessons - userProfile.lessonsCompleted
  val overallProgressPercent = if (userProfile.totalLessons > 0) {
    ((userProfile.lessonsCompleted.toFloat() / userProfile.totalLessons) * 100).toInt()
  } else 0

  var selectedTab by remember { mutableIntStateOf(0) } // 0 = Mastery Categories Chart, 1 = Domain Dashboard, 2 = Overview & Activity

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("progress_screen"),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // Header
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp)
      ) {
        Text(
          text = "Learning Analytics",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
          ),
          color = TextPrimary
        )
        Text(
          text = "Compose-powered mastery visualization & progress breakdown",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
          color = TextSecondary
        )
      }
    }

    // Top Segmented Tab Switcher (Mastery Charts vs Domain Dashboard vs Overview)
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
          .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        // Tab 0: Mastery Categories Chart
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (selectedTab == 0) TechBluePrimary else Color.Transparent)
            .clickable { selectedTab = 0 }
            .padding(vertical = 8.dp)
            .testTag("tab_mastery_charts"),
          contentAlignment = Alignment.Center
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
              imageVector = Icons.Default.DonutLarge,
              contentDescription = null,
              tint = if (selectedTab == 0) Color.White else TextTertiary,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "Mastery",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.5.sp
              ),
              color = if (selectedTab == 0) Color.White else TextSecondary
            )
          }
        }

        // Tab 1: Domain Dashboard
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (selectedTab == 1) TechBluePrimary else Color.Transparent)
            .clickable { selectedTab = 1 }
            .padding(vertical = 8.dp)
            .testTag("tab_domain_dashboard"),
          contentAlignment = Alignment.Center
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
              imageVector = Icons.Default.Timeline,
              contentDescription = null,
              tint = if (selectedTab == 1) Color.White else TextTertiary,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "Domains",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.5.sp
              ),
              color = if (selectedTab == 1) Color.White else TextSecondary
            )
          }
        }

        // Tab 2: Overview & Activity
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (selectedTab == 2) TechBluePrimary else Color.Transparent)
            .clickable { selectedTab = 2 }
            .padding(vertical = 8.dp)
            .testTag("tab_overview_activity"),
          contentAlignment = Alignment.Center
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = null,
              tint = if (selectedTab == 2) Color.White else TextTertiary,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "Overview",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.5.sp
              ),
              color = if (selectedTab == 2) Color.White else TextSecondary
            )
          }
        }
      }
    }

    if (selectedTab == 0) {
      // Compose-based Mastery Categories Chart
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
          MasteryCategoriesChart(
            courses = allCourses,
            onNavigateToCourse = onNavigateToCourse
          )
        }
      }
    } else if (selectedTab == 1) {
      // Recharts-inspired Multi-Domain Interactive Progress Dashboard
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
          DomainProgressDashboard(
            courses = allCourses,
            dailyActivities = dailyActivities,
            onNavigateToCourse = onNavigateToCourse
          )
        }
      }
    } else {
      // Overall Progress Hero Component
      item {
        OverallProgressHero(
          overallPercent = overallProgressPercent,
          completed = userProfile.lessonsCompleted,
          remaining = lessonsRemaining,
          total = userProfile.totalLessons
        )
      }

      // Statistics Metric Cards
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            StatCard(
              title = "Courses Started",
              value = "${userProfile.coursesStarted}",
              icon = Icons.Default.School,
              accentColor = TechCyanAccent,
              subtitle = "Active",
              modifier = Modifier.weight(1f)
            )
            StatCard(
              title = "Completed",
              value = "${userProfile.coursesCompleted}",
              icon = Icons.Default.CheckCircle,
              accentColor = TechGreen,
              subtitle = "Finished",
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            StatCard(
              title = "Average Quiz",
              value = "${userProfile.quizAverage}%",
              icon = Icons.Default.Quiz,
              accentColor = TechIndigo,
              subtitle = "Score",
              modifier = Modifier.weight(1f)
            )
            StatCard(
              title = "Learning Streak",
              value = "${userProfile.streakDays} Days",
              icon = Icons.Default.LocalFireDepartment,
              accentColor = TechAmber,
              subtitle = "Streak",
              modifier = Modifier.weight(1f)
            )
          }
        }
      }

      // 7-Day Learning Activity Bar Chart
      item {
        Spacer(modifier = Modifier.height(10.dp))
        SevenDayActivitySection(activities = dailyActivities)
      }

      // Active Course Progress Section
      item {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "Active Course Progress",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          ),
          color = TextPrimary,
          modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )
      }

      items(activeCourses, key = { "progress_${it.id}" }) { course ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                CourseIcon(iconName = course.iconName, size = 36.dp, iconSize = 20.dp)
                Column {
                  Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = TextPrimary
                  )
                  Text(
                    text = "${course.lessonCount} Lessons • ${course.estimatedHours} hrs",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = TextSecondary
                  )
                }
              }

              Text(
                text = "${course.progressPercent}%",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = TechCyanAccent
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
              progress = { course.progressPercent / 100f },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = TechCyanAccent,
              trackColor = NavyDark
            )
          }
        }
      }

      // Achievements Section
      item {
        Spacer(modifier = Modifier.height(14.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Earned Achievements",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp
            ),
            color = TextPrimary
          )

          Text(
            text = "View All Badges →",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = TechCyanAccent
            ),
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .clickable { onNavigateToAchievements() }
              .padding(horizontal = 6.dp, vertical = 4.dp)
              .testTag("view_all_achievements_header_button")
          )
        }
      }

      item {
        LazyRow(
          contentPadding = PaddingValues(horizontal = 20.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(achievements, key = { it.id }) { achievement ->
            AchievementItemCard(
              achievement = achievement,
              onClick = onNavigateToAchievements
            )
          }
        }
      }
    }
  }
}

@Composable
private fun OverallProgressHero(
  overallPercent: Int,
  completed: Int,
  remaining: Int,
  total: Int,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(24.dp))
      .background(
        brush = Brush.verticalGradient(
          listOf(NavyCardElevated, NavyCard)
        )
      )
      .border(1.dp, TechCyanAccent.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
      .padding(20.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "OVERALL CURRICULUM",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp,
            color = TechCyanAccent
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "$overallPercent% Mastered",
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
          ),
          color = TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
          Column {
            Text("Completed", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Text("$completed", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = TechGreen)
          }
          Column {
            Text("Remaining", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Text("$remaining", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = TechAmber)
          }
          Column {
            Text("Total", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Text("$total", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
          }
        }
      }

      // Circular Indicator
      Box(
        modifier = Modifier.size(76.dp),
        contentAlignment = Alignment.Center
      ) {
        CircularProgressIndicator(
          progress = { overallPercent / 100f },
          modifier = Modifier.fillMaxSize(),
          color = TechCyanAccent,
          trackColor = NavyDark,
          strokeWidth = 7.dp
        )
        Text(
          text = "$overallPercent%",
          style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp
          ),
          color = TextPrimary
        )
      }
    }
  }
}

@Composable
private fun SevenDayActivitySection(
  activities: List<DailyActivity>,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(NavyCard)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(18.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.Timeline, contentDescription = null, tint = TechCyanAccent, modifier = Modifier.size(18.dp))
          Text(
            text = "7-Day Learning Activity",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
            color = TextPrimary
          )
        }

        Text(
          text = "9.3 hrs total",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TechGreen)
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Custom Visual Bar Graph
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(90.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        activities.forEach { act ->
          val barHeightFraction = (act.hoursLearned / 3.0f).coerceIn(0.1f, 1.0f)
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.weight(1f)
          ) {
            Box(
              modifier = Modifier
                .width(18.dp)
                .fillMaxHeight(barHeightFraction)
                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .background(
                  brush = if (act.isCompleted) {
                    Brush.verticalGradient(listOf(TechCyanAccent, TechBluePrimary))
                  } else {
                    Brush.verticalGradient(listOf(Color(0xFF334155), Color(0xFF1E293B)))
                  }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = act.day,
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = if (act.isCompleted) FontWeight.Bold else FontWeight.Normal,
                color = if (act.isCompleted) TextPrimary else TextTertiary
              )
            )
          }
        }
      }
    }
  }
}

@Composable
private fun AchievementItemCard(
  achievement: Achievement,
  onClick: () -> Unit = {},
) {
  val iconVector = when (achievement.iconName) {
    "power" -> Icons.Default.PowerSettingsNew
    "keyboard" -> Icons.Default.Keyboard
    "code" -> Icons.Default.AutoAwesome
    "flame" -> Icons.Default.LocalFireDepartment
    "terminal" -> Icons.Default.Terminal
    "shield" -> Icons.Default.Security
    "brain" -> Icons.Default.Psychology
    else -> Icons.Default.EmojiEvents
  }

  Box(
    modifier = Modifier
      .width(160.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(NavyCard)
      .border(
        1.dp,
        if (achievement.isUnlocked) TechAmber.copy(alpha = 0.5f) else NavyCardBorder,
        RoundedCornerShape(16.dp)
      )
      .clickable { onClick() }
      .padding(14.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .background(
            if (achievement.isUnlocked) TechAmber.copy(alpha = 0.2f) else NavyDark,
            CircleShape
          )
          .border(
            1.dp,
            if (achievement.isUnlocked) TechAmber.copy(alpha = 0.5f) else Color.Transparent,
            CircleShape
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (achievement.isUnlocked) iconVector else Icons.Default.Lock,
          contentDescription = null,
          tint = if (achievement.isUnlocked) TechAmber else TextTertiary,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = achievement.title,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(3.dp))

      Text(
        text = achievement.description,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
        color = TextSecondary,
        maxLines = 2
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = if (achievement.isUnlocked) achievement.unlockedDate ?: "Unlocked" else "Locked",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 10.sp,
          color = if (achievement.isUnlocked) TechGreen else TextTertiary
        )
      )
    }
  }
}
