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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StudyNote
import com.example.data.update.UpdateUiState
import com.example.ui.components.CourseCard
import com.example.ui.components.LearningActivityChart
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
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel
import com.example.util.AppLanguage
import com.example.util.AppStrings
import com.example.util.LocalAppLanguage

@Composable
fun ProfileScreen(
  viewModel: ComputerMasterViewModel,
  onNavigateToCourse: (String) -> Unit,
  onNavigateToSettings: () -> Unit = {},
  onNavigateToAchievements: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  val userProfile by viewModel.userProfile.collectAsState()
  val accountState by viewModel.accountState.collectAsState()
  val allCourses by viewModel.allCourses.collectAsState()
  val notes by viewModel.notes.collectAsState()
  val dailyActivities by viewModel.dailyActivities.collectAsState()

  val bookmarkedCourses = allCourses.filter { it.isBookmarked }
  val completedCourses = allCourses.filter { it.progressPercent >= 100 }

  val updateState by viewModel.updateState.collectAsState()
  var showAddNoteDialog by remember { mutableStateOf(false) }
  var showResetConfirmDialog by remember { mutableStateOf(false) }
  var showConfigUrlDialog by remember { mutableStateOf(false) }

  // Settings mock toggles
  var hapticFeedbackEnabled by remember { mutableStateOf(true) }
  var dailyReminderEnabled by remember { mutableStateOf(true) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("profile_screen"),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // Top Hero Profile Info
    item {
      UserProfileHero(
        name = if (accountState.isLoggedIn && !accountState.userName.isNullOrBlank()) accountState.userName!! else userProfile.name,
        userEmail = if (accountState.isLoggedIn) accountState.userEmail else null,
        subtitle = "Level ${userProfile.levelNumber} • ${userProfile.title}",
        level = "Level ${userProfile.levelNumber}",
        xp = userProfile.currentXp,
        streak = userProfile.streakDays,
        isVerified = accountState.isLoggedIn
      )
    }

    // Quick Stats Bar
    item {
      ProfileStatsRow(
        lessonsCompleted = userProfile.lessonsCompleted,
        coursesCount = userProfile.coursesStarted,
        quizAverage = userProfile.quizAverage
      )
    }

    // Achievements & Badges Shortcut Banner
    item {
      val achievements by viewModel.achievements.collectAsState()
      val unlockedCount = remember(achievements) { achievements.count { it.isUnlocked } }
      val totalCount = remember(achievements) { achievements.size }

      Spacer(modifier = Modifier.height(10.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
          .clickable { onNavigateToAchievements() }
          .testTag("profile_achievements_banner")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.horizontalGradient(
                listOf(
                  TechAmber.copy(alpha = 0.15f),
                  NavyCardElevated.copy(alpha = 0.6f)
                )
              )
            )
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(TechAmber.copy(alpha = 0.2f))
                .border(1.dp, TechAmber, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = TechAmber, modifier = Modifier.size(20.dp))
            }
            Column {
              Text(
                text = "Achievements & Badges",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
              )
              Text(
                text = "$unlockedCount of $totalCount Badges Unlocked in Room",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = TextSecondary
              )
            }
          }

          Text(
            text = "View Badges →",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TechCyanAccent
            )
          )
        }
      }
    }

    // Learning Activity Chart (Visualizing lessons completed over the past week)
    item {
      Spacer(modifier = Modifier.height(10.dp))
      LearningActivityChart(activities = dailyActivities)
    }

    // Bookmarked Courses Section
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.Bookmark, contentDescription = null, tint = TechAmber, modifier = Modifier.size(20.dp))
          Text(
            text = "Bookmarked Courses",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 17.sp),
            color = TextPrimary
          )
        }

        Text(
          text = "${bookmarkedCourses.size} Saved",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
          color = TechCyanAccent
        )
      }
    }

    if (bookmarkedCourses.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(NavyCard)
            .padding(20.dp),
          contentAlignment = Alignment.Center
        ) {
          Text("No bookmarked courses yet", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
      }
    } else {
      items(bookmarkedCourses, key = { "bm_${it.id}" }) { course ->
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

    // Study Notes & Saved Items Section
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.NoteAdd, contentDescription = null, tint = TechCyanAccent, modifier = Modifier.size(20.dp))
          Text(
            text = "Study Notes & Key Takeaways",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 17.sp),
            color = TextPrimary
          )
        }

        IconButton(
          onClick = { showAddNoteDialog = true },
          modifier = Modifier
            .size(32.dp)
            .background(TechBluePrimary, CircleShape)
            .testTag("add_note_button")
        ) {
          Icon(Icons.Default.Add, contentDescription = "Add Note", tint = Color.White, modifier = Modifier.size(18.dp))
        }
      }
    }

    items(notes, key = { it.id }) { note ->
      StudyNoteCard(
        note = note,
        onDelete = { viewModel.deleteNote(note.id) }
      )
    }

    // App Preferences & Settings
    item {
      Spacer(modifier = Modifier.height(20.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = AppStrings.settings(currentLanguage),
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
          ),
          color = TextPrimary
        )
        Text(
          text = "Full Settings →",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          ),
          color = TechCyanAccent,
          modifier = Modifier
            .clickable { onNavigateToSettings() }
            .padding(4.dp)
        )
      }
    }

    // Prominent Full Settings Access Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(NavyCard)
          .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
          .clickable { onNavigateToSettings() }
          .padding(16.dp)
          .testTag("profile_open_settings_banner")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(TechBluePrimary),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = AppStrings.settingsTitle(currentLanguage),
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = TextPrimary
            )
            Text(
              text = "Account, Language, Appearance (Light/Dark), Updates, About & Privacy",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextSecondary
            )
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(18.dp)
          )
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    item {
      SettingsCard(
        currentLanguage = currentLanguage,
        onSelectLanguage = { viewModel.setLanguage(it) },
        dailyReminder = dailyReminderEnabled,
        onToggleDailyReminder = { dailyReminderEnabled = it },
        haptics = hapticFeedbackEnabled,
        onToggleHaptics = { hapticFeedbackEnabled = it },
        onResetProgress = { showResetConfirmDialog = true },
        updateState = updateState,
        currentVersionName = viewModel.updateManager.currentVersionName,
        onCheckForUpdates = { viewModel.checkForUpdates(isManual = true) },
        onOpenUpdateDialog = { viewModel.openUpdateDialog() },
        onOpenConfigUrl = { showConfigUrlDialog = true }
      )
    }
  }

  // Configure GitHub Update & APK URL Dialog
  if (showConfigUrlDialog) {
    var inputUrl by remember { mutableStateOf(viewModel.getUpdateMetadataUrl()) }
    var inputApkUrl by remember { mutableStateOf(viewModel.getCustomApkUrl() ?: "") }
    AlertDialog(
      onDismissRequest = { showConfigUrlDialog = false },
      title = {
        Text(
          text = AppStrings.updateSourceUrl(currentLanguage),
          color = TextPrimary,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = "1. GitHub Update Metadata JSON URL:",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = TechCyanAccent
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = inputUrl,
            onValueChange = { inputUrl = it },
            singleLine = false,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary,
              focusedBorderColor = TechCyanAccent
            )
          )
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "2. APK Direct Download URL (Custom Override):",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = TechCyanAccent
          )
          Text(
            text = "Leave empty to use the release APK defined in the metadata JSON.",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = inputApkUrl,
            onValueChange = { inputApkUrl = it },
            placeholder = { Text("https://github.com/.../app-debug.apk", color = TextTertiary, fontSize = 11.sp) },
            singleLine = false,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary,
              focusedBorderColor = TechCyanAccent
            )
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.setUpdateMetadataUrl(inputUrl)
            viewModel.setCustomApkUrl(inputApkUrl.ifBlank { null })
            showConfigUrlDialog = false
            viewModel.checkForUpdates(isManual = true)
          },
          colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
        ) {
          Text(AppStrings.save(currentLanguage))
        }
      },
      dismissButton = {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedButton(
            onClick = {
              viewModel.resetDefaultUpdateUrl()
              viewModel.resetCustomApkUrl()
              inputUrl = viewModel.getUpdateMetadataUrl()
              inputApkUrl = ""
              showConfigUrlDialog = false
              viewModel.checkForUpdates(isManual = true)
            }
          ) {
            Text(AppStrings.resetDefaultUrl(currentLanguage), color = TechAmber)
          }
          OutlinedButton(onClick = { showConfigUrlDialog = false }) {
            Text(AppStrings.cancel(currentLanguage), color = TextPrimary)
          }
        }
      },
      containerColor = NavyCardElevated,
      shape = RoundedCornerShape(18.dp)
    )
  }

  // Add Note Dialog
  if (showAddNoteDialog) {
    AddNoteDialog(
      onDismiss = { showAddNoteDialog = false },
      onSave = { title, content ->
        viewModel.addNote(
          courseId = "general",
          courseTitle = "General IT Knowledge",
          title = title,
          content = content
        )
        showAddNoteDialog = false
      }
    )
  }

  // Reset Confirmation Dialog
  if (showResetConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showResetConfirmDialog = false },
      title = { Text("Reset Progress?", color = TextPrimary) },
      text = { Text("This will reset your completed lessons and quiz scores to initial state. Are you sure?", color = TextSecondary) },
      confirmButton = {
        Button(
          onClick = {
            viewModel.resetAllProgress()
            showResetConfirmDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = TechRed)
        ) {
          Text("Reset")
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showResetConfirmDialog = false }) {
          Text("Cancel", color = TextPrimary)
        }
      },
      containerColor = NavyCardElevated,
      shape = RoundedCornerShape(18.dp)
    )
  }
}

@Composable
private fun UserProfileHero(
  name: String,
  userEmail: String? = null,
  subtitle: String,
  level: String,
  xp: Int,
  streak: Int,
  isVerified: Boolean = false,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 14.dp)
      .clip(RoundedCornerShape(24.dp))
      .background(
        brush = Brush.verticalGradient(
          listOf(NavyCardElevated, NavyCard)
        )
      )
      .border(1.dp, TechBluePrimary.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
      .padding(20.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Avatar with gradient border
      Box(
        modifier = Modifier
          .size(76.dp)
          .clip(CircleShape)
          .background(
            brush = Brush.linearGradient(
              if (isVerified) listOf(TechGreen, TechCyanAccent)
              else listOf(TechBluePrimary, TechCyanAccent)
            )
          )
          .padding(3.dp)
          .clip(CircleShape)
          .background(NavyDarkest),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = null,
          tint = if (isVerified) TechGreen else TechCyanAccent,
          modifier = Modifier.size(42.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Text(
          text = name,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
          ),
          color = TextPrimary
        )
        if (isVerified) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Verified Google User",
            tint = TechGreen,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      if (!userEmail.isNullOrBlank()) {
        Text(
          text = userEmail,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, color = TechCyanAccent)
        )
      }

      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Badge: Level & XP
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .background(TechPurple.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
            .border(1.dp, TechPurple.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = level,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              color = TechPurple
            )
          )
        }

        Box(
          modifier = Modifier
            .background(TechAmber.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
            .border(1.dp, TechAmber.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "$xp XP",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              color = TechAmber
            )
          )
        }
      }
    }
  }
}

@Composable
private fun ProfileStatsRow(
  lessonsCompleted: Int,
  coursesCount: Int,
  quizAverage: Int,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 4.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    StatPill(
      label = "Completed",
      value = "$lessonsCompleted",
      icon = Icons.Default.CheckCircle,
      accentColor = TechGreen,
      modifier = Modifier.weight(1f)
    )
    StatPill(
      label = "Courses",
      value = "$coursesCount",
      icon = Icons.Default.EmojiEvents,
      accentColor = TechCyanAccent,
      modifier = Modifier.weight(1f)
    )
    StatPill(
      label = "Accuracy",
      value = "$quizAverage%",
      icon = Icons.Default.Security,
      accentColor = TechIndigo,
      modifier = Modifier.weight(1f)
    )
  }
}

@Composable
private fun StatPill(
  label: String,
  value: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .background(NavyCard)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
      .padding(12.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = value,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = accentColor
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = TextSecondary
      )
    }
  }
}

@Composable
private fun StudyNoteCard(
  note: StudyNote,
  onDelete: () -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 5.dp)
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
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = note.title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = TextPrimary
          )
          Text(
            text = "${note.courseTitle} • Saved Note",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = TechCyanAccent
          )
        }

        IconButton(
          onClick = onDelete,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(Icons.Default.Delete, contentDescription = "Delete note", tint = TextTertiary, modifier = Modifier.size(18.dp))
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = note.content,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, lineHeight = 18.sp),
        color = TextSecondary
      )
    }
  }
}

@Composable
private fun SettingsCard(
  currentLanguage: AppLanguage,
  onSelectLanguage: (AppLanguage) -> Unit,
  dailyReminder: Boolean,
  onToggleDailyReminder: (Boolean) -> Unit,
  haptics: Boolean,
  onToggleHaptics: (Boolean) -> Unit,
  onResetProgress: () -> Unit,
  updateState: UpdateUiState,
  currentVersionName: String,
  onCheckForUpdates: () -> Unit,
  onOpenUpdateDialog: () -> Unit,
  onOpenConfigUrl: () -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 6.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(NavyCard)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(18.dp))
      .padding(16.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // 1. LANGUAGE SELECTOR OPTION
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Language,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(20.dp)
          )
          Column {
            Text(
              text = AppStrings.appLanguage(currentLanguage),
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
              color = TextPrimary
            )
            Text(
              text = "${currentLanguage.nativeName} (${currentLanguage.displayName})",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TechCyanAccent
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 3 CLEAR LANGUAGE CHOICES (বাংলা, English, हिन्दी)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("settings_language_options"),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        AppLanguage.entries.forEach { lang ->
          val isSelected = currentLanguage == lang
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) TechBluePrimary else NavyCardElevated)
              .border(
                width = 1.dp,
                color = if (isSelected) TechCyanAccent else NavyCardBorder,
                shape = RoundedCornerShape(12.dp)
              )
              .clickable { onSelectLanguage(lang) }
              .padding(vertical = 10.dp, horizontal = 4.dp)
              .testTag("settings_lang_${lang.code}"),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = lang.nativeName, // বাংলা / English / हिन्दी
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                  fontSize = 13.sp
                ),
                color = if (isSelected) Color.White else TextPrimary
              )
              if (lang != AppLanguage.ENGLISH) {
                Text(
                  text = lang.displayName,
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                  color = if (isSelected) Color.White.copy(alpha = 0.85f) else TextTertiary
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Daily Reminder Switch
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = AppStrings.dailyReminder(currentLanguage),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = TextPrimary
          )
          Text(
            text = AppStrings.dailyReminderDesc(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = TextSecondary
          )
        }
        Switch(
          checked = dailyReminder,
          onCheckedChange = onToggleDailyReminder,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = TechBluePrimary
          )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Haptics Switch
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = AppStrings.hapticFeedback(currentLanguage),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = TextPrimary
          )
          Text(
            text = AppStrings.hapticFeedbackDesc(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = TextSecondary
          )
        }
        Switch(
          checked = haptics,
          onCheckedChange = onToggleHaptics,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = TechBluePrimary
          )
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 4. IN-APP UPDATE SYSTEM
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.SystemUpdate,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(20.dp)
          )
          Column {
            Text(
              text = AppStrings.checkForUpdates(currentLanguage),
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
              color = TextPrimary
            )
            Text(
              text = AppStrings.checkForUpdatesDesc(currentLanguage),
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextTertiary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Update Action / Status Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(NavyDark)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
          .padding(12.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Installed: v$currentVersionName",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = TextSecondary
              )
              when (updateState) {
                is UpdateUiState.Checking -> {
                  Text(
                    text = AppStrings.checkingUpdates(currentLanguage),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TechCyanAccent
                  )
                }
                is UpdateUiState.UpdateAvailable -> {
                  Text(
                    text = "v${updateState.info.versionName} Available!",
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold
                    ),
                    color = TechAmber
                  )
                }
                is UpdateUiState.UpToDate -> {
                  Text(
                    text = AppStrings.appUpToDate(currentLanguage, updateState.versionName),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TechGreen
                  )
                }
                is UpdateUiState.Downloading -> {
                  Text(
                    text = "${AppStrings.updateDownloading(currentLanguage)} (${(updateState.progress * 100).toInt()}%)",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TechCyanAccent
                  )
                }
                is UpdateUiState.ReadyToInstall -> {
                  Text(
                    text = AppStrings.updateReadyToInstall(currentLanguage),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TechGreen
                  )
                }
                is UpdateUiState.Error -> {
                  Text(
                    text = updateState.message,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TechRed
                  )
                }
                else -> {
                  Text(
                    text = "GitHub Release Channel",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TextTertiary
                  )
                }
              }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (updateState is UpdateUiState.Checking) {
              CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = TechCyanAccent,
                strokeWidth = 2.dp
              )
            } else if (updateState is UpdateUiState.UpdateAvailable || updateState is UpdateUiState.Downloading || updateState is UpdateUiState.ReadyToInstall) {
              Button(
                onClick = onOpenUpdateDialog,
                colors = ButtonDefaults.buttonColors(containerColor = TechAmber),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.testTag("settings_view_update_btn")
              ) {
                Icon(
                  imageVector = Icons.Default.CloudDownload,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp),
                  tint = NavyDarkest
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = AppStrings.updateNow(currentLanguage),
                  style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                  color = NavyDarkest
                )
              }
            } else {
              Button(
                onClick = onCheckForUpdates,
                colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.testTag("settings_check_update_btn")
              ) {
                Icon(
                  imageVector = Icons.Default.Sync,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = AppStrings.checkForUpdates(currentLanguage),
                  style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Config source URL row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenConfigUrl() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null,
                tint = TechCyanAccent,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = AppStrings.updateSourceUrl(currentLanguage),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = TechCyanAccent
              )
            }
            Text(
              text = AppStrings.configureSource(currentLanguage),
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
              color = TechCyanAccent
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Reset Button
      OutlinedButton(
        onClick = onResetProgress,
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TechRed)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
          Text(
            text = AppStrings.resetProgress(currentLanguage),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // App Version info
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Computer Master • v1.0.0 Native Android Foundation",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = TextTertiary
        )
      }
    }
  }
}

@Composable
private fun AddNoteDialog(
  onDismiss: () -> Unit,
  onSave: (String, String) -> Unit,
) {
  var title by remember { mutableStateOf("") }
  var content by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("New Study Note", color = TextPrimary) },
    text = {
      Column {
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          placeholder = { Text("Note Title, e.g. Port 443 HTTPS") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = TechBluePrimary
          )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = content,
          onValueChange = { content = it },
          placeholder = { Text("Key takeaway, shortcut, or definition...") },
          modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = TechBluePrimary
          )
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (title.isNotBlank() && content.isNotBlank()) {
            onSave(title, content)
          }
        },
        enabled = title.isNotBlank() && content.isNotBlank(),
        colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
      ) {
        Text("Save Note")
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) {
        Text("Cancel", color = TextPrimary)
      }
    },
    containerColor = NavyCardElevated,
    shape = RoundedCornerShape(18.dp)
  )
}
