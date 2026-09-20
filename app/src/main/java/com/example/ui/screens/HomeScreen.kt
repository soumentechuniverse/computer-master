package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.CourseLevel
import com.example.ui.components.CourseCard
import com.example.ui.components.CourseIcon
import com.example.ui.components.LevelBadge
import com.example.ui.components.StatCard
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
import com.example.util.AppLanguage
import com.example.util.AppStrings
import com.example.util.LocalAppLanguage

@Composable
fun HomeScreen(
  viewModel: ComputerMasterViewModel,
  onNavigateToCourse: (String) -> Unit,
  onNavigateToCoursesTab: (CourseLevel) -> Unit,
  onNavigateToQuiz: () -> Unit,
  onNavigateToHardwareVisualLesson: () -> Unit = {},
  onNavigateToCpuRamRomLesson: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val currentLanguage = LocalAppLanguage.current
  val userProfile by viewModel.userProfile.collectAsState()
  val allCourses by viewModel.allCourses.collectAsState()

  val continueCourse = allCourses.firstOrNull { it.progressPercent in 1..99 }
    ?: allCourses.firstOrNull()

  val recommendedCourses = remember(allCourses) {
    allCourses.filter { it.level == CourseLevel.BEGINNER || it.level == CourseLevel.INTERMEDIATE }.take(4)
  }

  val recentlyLearned = remember(allCourses) {
    allCourses.filter { it.progressPercent > 0 }.sortedByDescending { it.progressPercent }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("home_screen"),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // Top Section: Computer Master Branding & Welcome
    item {
      TopBrandingSection(
        userName = userProfile.name,
        streakDays = userProfile.streakDays
      )
    }

    // Main Card: "Continue Learning"
    item {
      if (continueCourse != null) {
        ContinueLearningCard(
          course = continueCourse,
          onContinueClick = { onNavigateToCourse(continueCourse.id) }
        )
      }
    }

    // Interactive 3D Visual Lesson Feature Banner
    item {
      FeaturedVisualHardwareCard(
        onClick = onNavigateToHardwareVisualLesson,
        currentLanguage = currentLanguage
      )
    }

    // Inside the Computer: CPU, RAM & ROM 3D Lesson Banner
    item {
      FeaturedCpuRamRomLessonCard(
        onClick = onNavigateToCpuRamRomLesson,
        currentLanguage = currentLanguage
      )
    }

    // Statistics 4-Grid
    item {
      StatisticsSection(
        lessonsCompleted = userProfile.lessonsCompleted,
        coursesStarted = userProfile.coursesStarted,
        quizAverage = userProfile.quizAverage,
        streakDays = userProfile.streakDays
      )
    }

    // Learning Levels Quick Selector
    item {
      LearningLevelsSection(
        onLevelSelected = { level ->
          viewModel.setSelectedLevel(level)
          onNavigateToCoursesTab(level)
        }
      )
    }

    // Daily Knowledge Check Card
    item {
      DailyKnowledgeCheckCard(
        onStartClick = onNavigateToQuiz
      )
    }

    // Recommended Courses
    item {
      SectionTitle(title = "Recommended Courses", subtitle = "Handpicked for your skill progression")
    }

    item {
      LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(recommendedCourses, key = { it.id }) { course ->
          Box(modifier = Modifier.width(300.dp)) {
            CourseCard(
              course = course,
              onClick = { onNavigateToCourse(course.id) },
              onBookmarkClick = { viewModel.toggleBookmark(course.id) }
            )
          }
        }
      }
    }

    // Recently Learned
    if (recentlyLearned.isNotEmpty()) {
      item {
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(title = "Recently Learned", subtitle = "Pick up where you left off")
      }

      items(recentlyLearned, key = { "recent_${it.id}" }) { course ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
          CourseCard(
            course = course,
            onClick = { onNavigateToCourse(course.id) },
            onBookmarkClick = { viewModel.toggleBookmark(course.id) }
          )
        }
      }
    }
  }
}

@Composable
private fun TopBrandingSection(
  userName: String,
  streakDays: Int,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp)
  ) {
    // Brand Top Bar
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(40.dp)
            .background(
              brush = Brush.linearGradient(listOf(TechBluePrimary, TechCyanAccent)),
              shape = RoundedCornerShape(12.dp)
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Computer,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
          )
        }

        Column {
          Text(
            text = "COMPUTER MASTER",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 1.2.sp,
              fontSize = 15.sp
            ),
            color = TechCyanAccent
          )
          Text(
            text = "ACADEMY OF COMPUTING",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold,
              letterSpacing = 1.sp,
              fontSize = 9.sp
            ),
            color = TextTertiary
          )
        }
      }

      // Streak Pill
      Row(
        modifier = Modifier
          .background(
            color = TechAmber.copy(alpha = 0.15f),
            shape = RoundedCornerShape(20.dp)
          )
          .border(
            width = 1.dp,
            color = TechAmber.copy(alpha = 0.3f),
            shape = RoundedCornerShape(20.dp)
          )
          .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.LocalFireDepartment,
          contentDescription = "Streak",
          tint = TechAmber,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "$streakDays Days",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          ),
          color = TechAmber
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Welcome Headline & Subtitle
    val currentLanguage = LocalAppLanguage.current
    val welcomeBackText = when (currentLanguage) {
      AppLanguage.BENGALI -> "স্বাগতম, $userName"
      AppLanguage.HINDI -> "वापसी पर स्वागत है, $userName"
      AppLanguage.ENGLISH -> "Welcome back, $userName"
    }
    val subtitleText = when (currentLanguage) {
      AppLanguage.BENGALI -> "ধাপে ধাপে প্রযুক্তি শিখুন শুরু থেকে পেশাদার পর্যায় পর্যন্ত।"
      AppLanguage.HINDI -> "शून्य से पेशेवर तक चरण-दर-चरण तकनीक में महारत हासिल करें।"
      AppLanguage.ENGLISH -> "Master technology step-by-step from zero to pro."
    }

    Text(
      text = welcomeBackText,
      style = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
      ),
      color = TextPrimary
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = subtitleText,
      style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
      color = TextSecondary
    )
  }
}

@Composable
private fun ContinueLearningCard(
  course: Course,
  onContinueClick: () -> Unit,
) {
  val currentLanguage = LocalAppLanguage.current
  val currentLessonTitle = course.modules.firstOrNull()?.lessons?.firstOrNull { !it.isCompleted }?.title
    ?: "Lesson 3: CPU, RAM & Storage Demystified"

  val continueBadge = when (currentLanguage) {
    AppLanguage.BENGALI -> "শেখা চালিয়ে যান"
    AppLanguage.HINDI -> "सीखना जारी रखें"
    AppLanguage.ENGLISH -> "CONTINUE LEARNING"
  }
  val resumeButtonText = when (currentLanguage) {
    AppLanguage.BENGALI -> "পাঠ পুনরায় শুরু করুন"
    AppLanguage.HINDI -> "पाठ फिर से शुरू करें"
    AppLanguage.ENGLISH -> "Resume Lesson"
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 10.dp)
      .clip(RoundedCornerShape(22.dp))
      .background(
        brush = Brush.linearGradient(
          colors = listOf(
            NavyCardElevated,
            NavyCard
          )
        )
      )
      .border(
        width = 1.5.dp,
        brush = Brush.linearGradient(
          colors = listOf(
            TechBluePrimary.copy(alpha = 0.8f),
            NavyCardBorder
          )
        ),
        shape = RoundedCornerShape(22.dp)
      )
      .padding(18.dp)
      .testTag("continue_learning_card")
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
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = continueBadge,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 1.sp,
              fontSize = 11.sp
            ),
            color = TechCyanAccent
          )
        }

        LevelBadge(level = course.level)
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = course.title,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 19.sp
        ),
        color = TextPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = currentLessonTitle,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
        color = TextSecondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Progress bar and percentage
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        LinearProgressIndicator(
          progress = { course.progressPercent / 100f },
          modifier = Modifier
            .weight(1f)
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
          color = TechCyanAccent,
          trackColor = NavyDark,
        )

        Text(
          text = "${course.progressPercent}%",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          ),
          color = TechCyanAccent
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Continue Button
      Button(
        onClick = onContinueClick,
        modifier = Modifier
          .fillMaxWidth()
          .height(46.dp)
          .testTag("continue_learning_button"),
        colors = ButtonDefaults.buttonColors(
          containerColor = TechBluePrimary,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = resumeButtonText,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
    }
  }
}

@Composable
private fun StatisticsSection(
  lessonsCompleted: Int,
  coursesStarted: Int,
  quizAverage: Int,
  streakDays: Int,
) {
  val currentLanguage = LocalAppLanguage.current
  val completedTitle = when (currentLanguage) {
    AppLanguage.BENGALI -> "সম্পন্ন"
    AppLanguage.HINDI -> "पूर्ण"
    AppLanguage.ENGLISH -> "Completed"
  }
  val activeTitle = when (currentLanguage) {
    AppLanguage.BENGALI -> "সক্রিয়"
    AppLanguage.HINDI -> "सक्रिय"
    AppLanguage.ENGLISH -> "Active"
  }
  val quizAvgTitle = when (currentLanguage) {
    AppLanguage.BENGALI -> "কুইজ গড়"
    AppLanguage.HINDI -> "प्रश्नोत्तरी औसत"
    AppLanguage.ENGLISH -> "Quiz Average"
  }
  val streakTitle = when (currentLanguage) {
    AppLanguage.BENGALI -> "ধারাবাহিকতা"
    AppLanguage.HINDI -> "सिलसिला"
    AppLanguage.ENGLISH -> "Streak"
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
  ) {
    Text(
      text = when (currentLanguage) {
        AppLanguage.BENGALI -> "আপনার শিক্ষার পরিসংখ্যান"
        AppLanguage.HINDI -> "आपकी सीखने की सांख्यिकी"
        AppLanguage.ENGLISH -> "Your Learning Stats"
      },
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
      ),
      color = TextPrimary
    )

    Spacer(modifier = Modifier.height(12.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      StatCard(
        title = completedTitle,
        value = "$lessonsCompleted",
        icon = Icons.Default.CheckCircle,
        accentColor = TechGreen,
        subtitle = when (currentLanguage) { AppLanguage.BENGALI -> "পাঠ"; AppLanguage.HINDI -> "पाठ"; else -> "Lessons" },
        modifier = Modifier.weight(1f)
      )
      StatCard(
        title = activeTitle,
        value = "$coursesStarted",
        icon = Icons.Default.School,
        accentColor = TechCyanAccent,
        subtitle = when (currentLanguage) { AppLanguage.BENGALI -> "কোর্স"; AppLanguage.HINDI -> "पाठ्यक्रम"; else -> "Courses" },
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      StatCard(
        title = quizAvgTitle,
        value = "$quizAverage%",
        icon = Icons.Default.Quiz,
        accentColor = TechIndigo,
        subtitle = when (currentLanguage) { AppLanguage.BENGALI -> "সঠিকতা"; AppLanguage.HINDI -> "सटीकता"; else -> "Accuracy" },
        modifier = Modifier.weight(1f)
      )
      StatCard(
        title = streakTitle,
        value = "$streakDays Days",
        icon = Icons.Default.LocalFireDepartment,
        accentColor = TechAmber,
        subtitle = when (currentLanguage) { AppLanguage.BENGALI -> "অব্যাহত"; AppLanguage.HINDI -> "अटूट"; else -> "Unbroken" },
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun LearningLevelsSection(
  onLevelSelected: (CourseLevel) -> Unit,
) {
  val currentLanguage = LocalAppLanguage.current

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 10.dp)
  ) {
    Text(
      text = when (currentLanguage) {
        AppLanguage.BENGALI -> "লেভেল অনুযায়ী দেখুন"
        AppLanguage.HINDI -> "स्तर के अनुसार अन्वेषण करें"
        AppLanguage.ENGLISH -> "Explore by Level"
      },
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
      ),
      color = TextPrimary
    )

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      LevelQuickCard(
        level = CourseLevel.BEGINNER,
        title = AppStrings.filterBeginner(currentLanguage),
        count = "7 Courses",
        accentColor = TechGreen,
        onClick = { onLevelSelected(CourseLevel.BEGINNER) },
        modifier = Modifier.weight(1f)
      )
      LevelQuickCard(
        level = CourseLevel.INTERMEDIATE,
        title = AppStrings.filterIntermediate(currentLanguage),
        count = "7 Courses",
        accentColor = TechCyanAccent,
        onClick = { onLevelSelected(CourseLevel.INTERMEDIATE) },
        modifier = Modifier.weight(1f)
      )
      LevelQuickCard(
        level = CourseLevel.ADVANCED,
        title = AppStrings.filterAdvanced(currentLanguage),
        count = "6 Courses",
        accentColor = TechPurple,
        onClick = { onLevelSelected(CourseLevel.ADVANCED) },
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun LevelQuickCard(
  level: CourseLevel,
  title: String,
  count: String,
  accentColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(NavyCard)
      .border(
        width = 1.dp,
        color = accentColor.copy(alpha = 0.35f),
        shape = RoundedCornerShape(16.dp)
      )
      .clickable(onClick = onClick)
      .padding(vertical = 12.dp, horizontal = 10.dp)
      .testTag("level_quick_card_${level.name.lowercase()}"),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Box(
        modifier = Modifier
          .size(8.dp)
          .clip(CircleShape)
          .background(accentColor)
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp
        ),
        color = TextPrimary
      )

      Text(
        text = count,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = TextSecondary
      )
    }
  }
}

@Composable
private fun DailyKnowledgeCheckCard(
  onStartClick: () -> Unit,
) {
  val currentLanguage = LocalAppLanguage.current
  val checkTitle = when (currentLanguage) {
    AppLanguage.BENGALI -> "দৈনিক জ্ঞান যাচাই"
    AppLanguage.HINDI -> "दैनिक ज्ञान जांच"
    AppLanguage.ENGLISH -> "Daily Knowledge Check"
  }
  val checkSubtitle = when (currentLanguage) {
    AppLanguage.BENGALI -> "৩টি দ্রুত প্রশ্নে আপনার জ্ঞান পরীক্ষা করুন"
    AppLanguage.HINDI -> "3 त्वरित प्रश्नों में अपने ज्ञान का परीक्षण करें"
    AppLanguage.ENGLISH -> "Test your daily recall in 3 quick questions"
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 10.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(
        brush = Brush.horizontalGradient(
          listOf(
            Color(0xFF1E1B4B),
            NavyCard
          )
        )
      )
      .border(
        width = 1.dp,
        color = TechIndigo.copy(alpha = 0.4f),
        shape = RoundedCornerShape(20.dp)
      )
      .clickable(onClick = onStartClick)
      .padding(18.dp)
      .testTag("daily_knowledge_check_card")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .background(
              color = TechIndigo.copy(alpha = 0.2f),
              shape = RoundedCornerShape(14.dp)
            )
            .border(
              width = 1.dp,
              color = TechIndigo.copy(alpha = 0.5f),
              shape = RoundedCornerShape(14.dp)
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Quiz,
            contentDescription = null,
            tint = TechIndigo,
            modifier = Modifier.size(24.dp)
          )
        }

        Column {
          Text(
            text = checkTitle,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            ),
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = checkSubtitle,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = TextSecondary
          )
        }
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = "Start",
        tint = TechCyanAccent,
        modifier = Modifier.size(20.dp)
      )
    }
  }
}

@Composable
private fun SectionTitle(
  title: String,
  subtitle: String,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
      ),
      color = TextPrimary
    )
    Text(
      text = subtitle,
      style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
      color = TextSecondary
    )
  }
}

@Composable
private fun FeaturedVisualHardwareCard(
  onClick: () -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 6.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(
        brush = Brush.horizontalGradient(
          listOf(
            Color(0xFF0F2B48),
            Color(0xFF1B1842)
          )
        )
      )
      .border(
        width = 1.5.dp,
        brush = Brush.horizontalGradient(
          listOf(
            TechCyanAccent.copy(alpha = 0.8f),
            TechPurple.copy(alpha = 0.8f)
          )
        ),
        shape = RoundedCornerShape(20.dp)
      )
      .clickable(onClick = onClick)
      .padding(18.dp)
      .testTag("featured_hardware_card")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(TechCyanAccent.copy(alpha = 0.2f))
            .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(TechCyanAccent)
            )
            Text(
              text = when (currentLanguage) {
                AppLanguage.BENGALI -> "৩ডি ভিজ্যুয়াল অভিজ্ঞতা"
                AppLanguage.HINDI -> "3D विज़ुअल अनुभव"
                AppLanguage.ENGLISH -> "3D INTERACTIVE LESSON"
              },
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp
              ),
              color = TechCyanAccent
            )
          }
        }

        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = null,
          tint = TechAmber,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "কম্পিউটার হার্ডওয়্যার এক্সপ্লোর করুন"
          AppLanguage.HINDI -> "कंप्यूटर हार्डवेयर एक्सप्लोर करें"
          AppLanguage.ENGLISH -> "Explore Computer Hardware in 3D"
        },
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          fontSize = 17.sp
        ),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "মনিটর, সিপিইউ, কীবোর্ড এবং মাউস স্পর্শ করে ৩ডি ভিজ্যুয়াল উপায়ে শিখুন। সাথে রয়েছে কুইক কুইজ!"
          AppLanguage.HINDI -> "मॉनिटर, सीपीयू, कीबोर्ड और माउस को 3D विज़ुअल तरीके से स्पर्श करके जानें। साथ ही क्विक क्विज़ उपलब्ध है!"
          AppLanguage.ENGLISH -> "Tap & interact with a 3D desktop setup (Monitor, CPU, Keyboard, Mouse) with guided walkthrough & quick quiz."
        },
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 12.sp,
          lineHeight = 17.sp
        ),
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(NavyDark)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "4 Parts",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TechCyanAccent
            )
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(NavyDark)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "Interactive",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TechGreen
            )
          }
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "শুরু করুন"
              AppLanguage.HINDI -> "शुरू करें"
              AppLanguage.ENGLISH -> "Start 3D Lesson"
            },
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            ),
            color = TechCyanAccent
          )
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}

@Composable
private fun FeaturedCpuRamRomLessonCard(
  onClick: () -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 6.dp)
      .clip(RoundedCornerShape(22.dp))
      .background(
        brush = Brush.linearGradient(
          colors = listOf(
            Color(0xFF0C243B),
            Color(0xFF091422)
          )
        )
      )
      .border(
        width = 1.5.dp,
        brush = Brush.linearGradient(
          colors = listOf(
            TechAmber.copy(alpha = 0.8f),
            TechCyanAccent.copy(alpha = 0.4f)
          )
        ),
        shape = RoundedCornerShape(22.dp)
      )
      .clickable { onClick() }
      .padding(18.dp)
      .testTag("featured_cpu_ram_rom_card")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(TechAmber.copy(alpha = 0.2f))
            .border(1.dp, TechAmber.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(TechAmber)
            )
            Text(
              text = when (currentLanguage) {
                AppLanguage.BENGALI -> "নতুন ৩ডি পাঠ • আর্কিটেকচার"
                AppLanguage.HINDI -> "नया 3D पाठ • आर्किटेक्चर"
                AppLanguage.ENGLISH -> "NEW 3D LESSON • ARCHITECTURE"
              },
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp
              ),
              color = TechAmber
            )
          }
        }

        Icon(
          imageVector = Icons.Default.Computer,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "কম্পিউটারের ভেতরে: CPU, RAM ও ROM"
          AppLanguage.HINDI -> "कंप्यूटर के अंदर: CPU, RAM और ROM"
          AppLanguage.ENGLISH -> "Inside the Computer: CPU, RAM & ROM"
        },
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          fontSize = 17.sp
        ),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "সিপিইউ সিলিকন চিপ, ক্লক স্পিড (GHz), র‍্যামের কাজের মেমরি এবং রমের বায়োস ফার্মওয়্যার ৩ডি অ্যানিমেশনে এক্সপ্লোর করুন। সাথে ৮টি কুইজ প্রশ্ন!"
          AppLanguage.HINDI -> "सीपीयू सिलिकॉन चिप, क्लॉक स्पीड (GHz), रैम की कार्यशील मेमोरी और रोम के बायोस फर्मवेयर को 3D एनिमेशन में समझें। साथ में 8 प्रश्नोत्तरी!"
          AppLanguage.ENGLISH -> "Interactive 3D CPU die, GHz speed, RAM workspace vs volatile DRAM, and permanent ROM BIOS firmware with animated data-flow & 8-question quiz."
        },
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 12.sp,
          lineHeight = 17.sp
        ),
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(NavyDark)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "CPU • RAM • ROM",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TechAmber
            )
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(NavyDark)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "8 Quizzes",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TechGreen
            )
          }
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "পাঠ দেখুন"
              AppLanguage.HINDI -> "पाठ देखें"
              AppLanguage.ENGLISH -> "Open 3D Lesson"
            },
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            ),
            color = TechAmber
          )
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TechAmber,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}


