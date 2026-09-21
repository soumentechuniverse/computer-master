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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.update.UpdateUiState
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel
import com.example.util.AppLanguage
import com.example.util.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  viewModel: ComputerMasterViewModel,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  val updateState by viewModel.updateState.collectAsState()

  var showResetConfirmDialog by remember { mutableStateOf(false) }
  var showConfigUrlDialog by remember { mutableStateOf(false) }

  var dailyReminderEnabled by remember { mutableStateOf(true) }
  var hapticFeedbackEnabled by remember { mutableStateOf(true) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("settings_screen"),
    containerColor = NavyDarkest,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = AppStrings.settingsTitle(currentLanguage),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = TextPrimary
          )
        },
        navigationIcon = {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier.testTag("settings_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = TextPrimary
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = NavyDarkest
        )
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
      // 1. IN-APP UPDATE CENTER (Highlight Card)
      Text(
        text = AppStrings.checkForUpdates(currentLanguage),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(8.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(18.dp))
          .padding(16.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(TechBluePrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.SystemUpdate,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Computer Master Update Engine",
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

          Spacer(modifier = Modifier.height(14.dp))

          // Status & Action Row
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(NavyDark)
              .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
              .padding(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Installed: v${viewModel.updateManager.currentVersionName}",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                  color = TextSecondary
                )
                val state = updateState
                when (state) {
                  is UpdateUiState.Checking -> {
                    Text(
                      text = AppStrings.checkingUpdates(currentLanguage),
                      style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                      color = TechCyanAccent
                    )
                  }
                  is UpdateUiState.UpdateAvailable -> {
                    Text(
                      text = "v${state.info.versionName} Available!",
                      style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                      ),
                      color = TechAmber
                    )
                  }
                  is UpdateUiState.UpToDate -> {
                    Text(
                      text = AppStrings.appUpToDate(currentLanguage, state.versionName),
                      style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                      color = TechGreen
                    )
                  }
                  is UpdateUiState.Downloading -> {
                    Text(
                      text = "${AppStrings.updateDownloading(currentLanguage)} (${(state.progress * 100).toInt()}%)",
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
                      text = state.message,
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
                  onClick = { viewModel.openUpdateDialog() },
                  colors = ButtonDefaults.buttonColors(containerColor = TechAmber),
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                  modifier = Modifier.testTag("settings_screen_view_update_btn")
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
                  onClick = { viewModel.checkForUpdates(isManual = true) },
                  colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                  modifier = Modifier.testTag("settings_screen_check_update_btn")
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
          }

          Spacer(modifier = Modifier.height(10.dp))

          // GitHub Source URL Link
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { showConfigUrlDialog = true },
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
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = TechCyanAccent
              )
            }
            Text(
              text = AppStrings.configureSource(currentLanguage),
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
              color = TechCyanAccent
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 2. LANGUAGE SELECTOR
      Text(
        text = AppStrings.appLanguage(currentLanguage),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("settings_screen_language_options"),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        AppLanguage.entries.forEach { lang ->
          val isSelected = currentLanguage == lang
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) TechBluePrimary else NavyCard)
              .border(
                width = 1.dp,
                color = if (isSelected) TechCyanAccent else NavyCardBorder,
                shape = RoundedCornerShape(12.dp)
              )
              .clickable { viewModel.setLanguage(lang) }
              .padding(vertical = 12.dp, horizontal = 4.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = lang.nativeName,
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                  fontSize = 14.sp
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

      Spacer(modifier = Modifier.height(20.dp))

      // 3. PREFERENCES (Reminders, Haptics)
      Text(
        text = "Preferences",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(8.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(18.dp))
          .padding(16.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          // Daily Reminder
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Icon(Icons.Default.Notifications, contentDescription = null, tint = TechCyanAccent, modifier = Modifier.size(20.dp))
              Column {
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
            }
            Switch(
              checked = dailyReminderEnabled,
              onCheckedChange = { dailyReminderEnabled = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = TechBluePrimary
              )
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Haptics
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Icon(Icons.Default.Vibration, contentDescription = null, tint = TechCyanAccent, modifier = Modifier.size(20.dp))
              Column {
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
            }
            Switch(
              checked = hapticFeedbackEnabled,
              onCheckedChange = { hapticFeedbackEnabled = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = TechBluePrimary
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 4. RESET PROGRESS
      OutlinedButton(
        onClick = { showResetConfirmDialog = true },
        modifier = Modifier
          .fillMaxWidth()
          .height(46.dp),
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

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Computer Master • v${viewModel.updateManager.currentVersionName} Production Engine",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
        color = TextTertiary,
        modifier = Modifier.align(Alignment.CenterHorizontally)
      )
    }
  }

  // GitHub Update & APK URL configuration dialog
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

  // Reset Progress Dialog
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
