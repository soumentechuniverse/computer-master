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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.update.UpdateUiState
import com.example.ui.theme.AppThemeMode
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  viewModel: ComputerMasterViewModel,
  onBackClick: () -> Unit,
  onLogout: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  val updateState by viewModel.updateState.collectAsState()
  val themeMode by viewModel.themeMode.collectAsState()
  val updateNotificationsEnabled by viewModel.updateNotificationsEnabled.collectAsState()
  val soundEffectsEnabled by viewModel.soundEffectsEnabled.collectAsState()
  val accountState by viewModel.accountState.collectAsState()

  // Dialog visibility states
  var showPrivacyDialog by remember { mutableStateOf(false) }
  var showTermsDialog by remember { mutableStateOf(false) }
  var showResetConfirmDialog by remember { mutableStateOf(false) }
  var showConfigUrlDialog by remember { mutableStateOf(false) }

  // App preferences
  var dailyReminderEnabled by remember { mutableStateOf(true) }
  var hapticFeedbackEnabled by remember { mutableStateOf(true) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("settings_screen"),
    containerColor = MaterialTheme.colorScheme.background,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = AppStrings.settingsTitle(currentLanguage),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
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
              tint = MaterialTheme.colorScheme.onBackground
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background
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

      // ==========================================
      // SECTION 1: STUDENT PROFILE
      // ==========================================
      SettingsSectionHeader(
        icon = Icons.Default.AccountCircle,
        title = AppStrings.settingsAccount(currentLanguage),
        subtitle = "Your active student learning profile & progress"
      )
      Spacer(modifier = Modifier.height(10.dp))

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
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            // Avatar
            Box(
              modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(
                  brush = Brush.linearGradient(
                    listOf(TechBluePrimary, TechCyanAccent)
                  )
                )
                .padding(2.dp)
                .clip(CircleShape)
                .background(NavyDarkest),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = TechCyanAccent,
                modifier = Modifier.size(30.dp)
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(
                  text = accountState.userName ?: "Computer Master Student",
                  style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                  color = TextPrimary
                )
                // Badge
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(TechGreen.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "Active",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold
                    ),
                    color = TechGreen
                  )
                }
              }

              Text(
                text = accountState.userEmail ?: "Offline & Cloud Sync Active",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TextSecondary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // ==========================================
      // SECTION 2: LANGUAGE (Bengali, English, Hindi)
      // ==========================================
      SettingsSectionHeader(
        icon = Icons.Default.Translate,
        title = AppStrings.settingsLanguage(currentLanguage),
        subtitle = AppStrings.settingsLanguageDesc(currentLanguage)
      )
      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("settings_language_options"),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        AppLanguage.entries.forEach { lang ->
          val isSelected = currentLanguage == lang
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(14.dp))
              .background(if (isSelected) TechBluePrimary else NavyCard)
              .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) TechCyanAccent else NavyCardBorder,
                shape = RoundedCornerShape(14.dp)
              )
              .clickable { viewModel.setLanguage(lang) }
              .padding(vertical = 14.dp, horizontal = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = lang.nativeName,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    fontSize = 15.sp
                  ),
                  color = if (isSelected) Color.White else TextPrimary
                )
                if (isSelected) {
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(2.dp))

              Text(
                text = lang.displayName,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = if (isSelected) Color.White.copy(alpha = 0.85f) else TextTertiary
              )

              Box(
                modifier = Modifier
                  .padding(top = 4.dp)
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (isSelected) Color.White.copy(alpha = 0.2f) else NavyDark)
                  .padding(horizontal = 6.dp, vertical = 1.dp)
              ) {
                Text(
                  text = lang.symbol,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                  ),
                  color = if (isSelected) Color.White else TechCyanAccent
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // ==========================================
      // SECTION 3: APPEARANCE (Light, Dark, System Default)
      // ==========================================
      SettingsSectionHeader(
        icon = Icons.Default.Palette,
        title = AppStrings.settingsAppearance(currentLanguage),
        subtitle = AppStrings.settingsAppearanceDesc(currentLanguage)
      )
      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("settings_theme_options"),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        listOf(
          Triple(AppThemeMode.SYSTEM, AppStrings.themeSystem(currentLanguage), Icons.Default.BrightnessAuto),
          Triple(AppThemeMode.LIGHT, AppStrings.themeLight(currentLanguage), Icons.Default.LightMode),
          Triple(AppThemeMode.DARK, AppStrings.themeDark(currentLanguage), Icons.Default.DarkMode)
        ).forEach { (mode, label, icon) ->
          val isSelected = themeMode == mode
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(14.dp))
              .background(if (isSelected) NavyCardElevated else NavyCard)
              .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) TechCyanAccent else NavyCardBorder,
                shape = RoundedCornerShape(14.dp)
              )
              .clickable { viewModel.setThemeMode(mode) }
              .padding(vertical = 14.dp, horizontal = 4.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(if (isSelected) TechBluePrimary else NavyDark),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = icon,
                  contentDescription = null,
                  tint = if (isSelected) Color.White else TechCyanAccent,
                  modifier = Modifier.size(18.dp)
                )
              }

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  fontSize = 12.sp
                ),
                color = if (isSelected) Color.White else TextPrimary
              )

              if (isSelected) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = TechCyanAccent,
                    modifier = Modifier.size(10.dp)
                  )
                  Text(
                    text = "Active",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Bold
                    ),
                    color = TechCyanAccent
                  )
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // ==========================================
      // SECTION 4: UPDATES (Version, Check, Notifications, GitHub)
      // ==========================================
      SettingsSectionHeader(
        icon = Icons.Default.SystemUpdate,
        title = AppStrings.settingsUpdates(currentLanguage),
        subtitle = "Continuous delivery & release verification system"
      )
      Spacer(modifier = Modifier.height(10.dp))

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

          // Status & Action Box
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .background(NavyDark)
              .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
              .padding(14.dp)
          ) {
            Column(modifier = Modifier.fillMaxWidth()) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Installed Version",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                  color = TextSecondary
                )
                Text(
                  text = "v${viewModel.updateManager.currentVersionName}",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                  ),
                  color = TechCyanAccent
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              // Current Status Display
              when (val state = updateState) {
                is UpdateUiState.Checking -> {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                  ) {
                    CircularProgressIndicator(
                      modifier = Modifier.size(18.dp),
                      color = TechCyanAccent,
                      strokeWidth = 2.dp
                    )
                    Text(
                      text = AppStrings.checkingUpdates(currentLanguage),
                      style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                      color = TechCyanAccent
                    )
                  }
                }
                is UpdateUiState.UpdateAvailable -> {
                  Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.CloudDownload,
                        contentDescription = null,
                        tint = TechAmber,
                        modifier = Modifier.size(18.dp)
                      )
                      Text(
                        text = "Update Available: v${state.info.versionName}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                          fontWeight = FontWeight.Bold,
                          fontSize = 13.sp
                        ),
                        color = TechAmber
                      )
                    }
                    if (!state.info.fileSize.isNullOrBlank()) {
                      Spacer(modifier = Modifier.height(2.dp))
                      Text(
                        text = "Download size: ${state.info.fileSize}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextTertiary
                      )
                    }
                    val msg = state.info.getLocalizedMessage(currentLanguage).lines().firstOrNull()
                    if (!msg.isNullOrBlank()) {
                      Spacer(modifier = Modifier.height(2.dp))
                      Text(
                        text = msg,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                        color = TextSecondary,
                        maxLines = 2
                      )
                    }
                  }
                }
                is UpdateUiState.UpToDate -> {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = null,
                      tint = TechGreen,
                      modifier = Modifier.size(18.dp)
                    )
                    Column {
                      Text(
                        text = AppStrings.latestVersionInstalled(currentLanguage),
                        style = MaterialTheme.typography.bodyMedium.copy(
                          fontWeight = FontWeight.Bold,
                          fontSize = 13.sp
                        ),
                        color = TechGreen
                      )
                      Text(
                        text = "Computer Master v${state.versionName} is up to date.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextSecondary
                      )
                    }
                  }
                }
                is UpdateUiState.Downloading -> {
                  Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Text(
                        text = "${AppStrings.updateDownloading(currentLanguage)} v${state.info.versionName}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                        color = TechCyanAccent
                      )
                      Text(
                        text = "${(state.progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = TechCyanAccent
                      )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                      progress = { state.progress },
                      modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                      color = TechCyanAccent,
                      trackColor = NavyCardBorder
                    )
                  }
                }
                is UpdateUiState.ReadyToInstall -> {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = null,
                      tint = TechGreen,
                      modifier = Modifier.size(18.dp)
                    )
                    Column {
                      Text(
                        text = "Update downloaded: v${state.info.versionName}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                          fontWeight = FontWeight.Bold,
                          fontSize = 13.sp
                        ),
                        color = TechGreen
                      )
                      Text(
                        text = AppStrings.updateReadyToInstall(currentLanguage),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextSecondary
                      )
                    }
                  }
                }
                is UpdateUiState.Error -> {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.Info,
                      contentDescription = null,
                      tint = TechRed,
                      modifier = Modifier.size(18.dp)
                    )
                    Text(
                      text = state.message,
                      style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                      color = TechRed
                    )
                  }
                }
                else -> {
                  Text(
                    text = "Tap 'Check for Updates' to verify whether a newer version is available.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                    color = TextSecondary
                  )
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Action Buttons Row: Permanent "Check for Updates" + Conditional "Update" button
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                // Permanent Check for Updates button - ALWAYS VISIBLE, NEVER HIDDEN
                Button(
                  onClick = { viewModel.checkForUpdates(isManual = true) },
                  enabled = updateState !is UpdateUiState.Checking,
                  colors = ButtonDefaults.buttonColors(
                    containerColor = TechBluePrimary,
                    disabledContainerColor = TechBluePrimary.copy(alpha = 0.5f)
                  ),
                  shape = RoundedCornerShape(10.dp),
                  contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                  modifier = Modifier
                    .weight(1f)
                    .testTag("settings_screen_check_update_btn")
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    if (updateState is UpdateUiState.Checking) {
                      CircularProgressIndicator(
                        modifier = Modifier.size(14.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                      )
                    } else {
                      Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                      )
                    }
                    Text(
                      text = AppStrings.checkForUpdates(currentLanguage),
                      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                  }
                }

                // If update is available or ready to install, provide dedicated Update button
                if (updateState is UpdateUiState.UpdateAvailable || updateState is UpdateUiState.Downloading || updateState is UpdateUiState.ReadyToInstall) {
                  Button(
                    onClick = {
                      if (updateState is UpdateUiState.ReadyToInstall) {
                        viewModel.launchPackageInstaller((updateState as UpdateUiState.ReadyToInstall).apkFile)
                      } else {
                        viewModel.openUpdateDialog()
                      }
                    },
                    colors = ButtonDefaults.buttonColors(
                      containerColor = if (updateState is UpdateUiState.ReadyToInstall) TechGreen else TechAmber
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    modifier = Modifier
                      .weight(1f)
                      .testTag("settings_screen_view_update_btn")
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                      Icon(
                        imageVector = if (updateState is UpdateUiState.ReadyToInstall) Icons.Default.SystemUpdate else Icons.Default.CloudDownload,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = NavyDarkest
                      )
                      Text(
                        text = when (updateState) {
                          is UpdateUiState.ReadyToInstall -> "Install Now"
                          is UpdateUiState.Downloading -> "Downloading..."
                          is UpdateUiState.UpdateAvailable -> "Update Now"
                          else -> AppStrings.updateNow(currentLanguage)
                        },
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = NavyDarkest
                      )
                    }
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Update Notifications Switch
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
              Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = TechCyanAccent,
                modifier = Modifier.size(20.dp)
              )
              Column {
                Text(
                  text = AppStrings.updateNotifications(currentLanguage),
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                  color = TextPrimary
                )
                Text(
                  text = AppStrings.updateNotificationsDesc(currentLanguage),
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = TextSecondary
                )
              }
            }
            Switch(
              checked = updateNotificationsEnabled,
              onCheckedChange = { viewModel.setUpdateNotificationsEnabled(it) },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = TechBluePrimary
              ),
              modifier = Modifier.testTag("settings_update_notifications_switch")
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          HorizontalDivider(color = NavyCardBorder)
          Spacer(modifier = Modifier.height(10.dp))

          // GitHub Source URL Link (Preserved)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { showConfigUrlDialog = true }
              .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null,
                tint = TechCyanAccent,
                modifier = Modifier.size(16.dp)
              )
              Text(
                text = AppStrings.updateSourceUrl(currentLanguage),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                color = TechCyanAccent
              )
            }
            Text(
              text = AppStrings.configureSource(currentLanguage),
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold),
              color = TechCyanAccent
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // ==========================================
      // SECTION 5: ABOUT (Computer Master, Soumen Mondal, Version)
      // ==========================================
      SettingsSectionHeader(
        icon = Icons.Default.Info,
        title = AppStrings.settingsAbout(currentLanguage),
        subtitle = "Developer credits & architecture overview"
      )
      Spacer(modifier = Modifier.height(10.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(18.dp))
          .padding(18.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Box(
              modifier = Modifier
                .size(54.dp)
                .background(
                  brush = Brush.linearGradient(listOf(TechBluePrimary, TechCyanAccent)),
                  shape = RoundedCornerShape(14.dp)
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Computer,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
              )
            }

            Column {
              Text(
                text = "Computer Master",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  letterSpacing = 0.5.sp
                ),
                color = TextPrimary
              )
              Text(
                text = "Academy of Computing & Systems",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TechCyanAccent
              )
              Text(
                text = "Version ${viewModel.updateManager.currentVersionName} (Build 2)",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                ),
                color = TechGreen
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))
          HorizontalDivider(color = NavyCardBorder)
          Spacer(modifier = Modifier.height(12.dp))

          // Author / Creator
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Engineering,
              contentDescription = null,
              tint = TechAmber,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = AppStrings.aboutCreator(currentLanguage),
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              ),
              color = TextPrimary
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = AppStrings.aboutDesc(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              lineHeight = 17.sp
            ),
            color = TextSecondary
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(NavyDark)
              .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Platform: Offline-First Android",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TextTertiary
            )
            Text(
              text = "Stack: Kotlin • Jetpack Compose",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TextTertiary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // ==========================================
      // SECTION 6: PRIVACY (Privacy Policy, Terms & Conditions)
      // ==========================================
      SettingsSectionHeader(
        icon = Icons.Default.Security,
        title = AppStrings.settingsPrivacy(currentLanguage),
        subtitle = "User trust, licensing & data safety commitments"
      )
      Spacer(modifier = Modifier.height(10.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(18.dp))
          .padding(vertical = 4.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          // Privacy Policy Row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { showPrivacyDialog = true }
              .padding(horizontal = 16.dp, vertical = 14.dp)
              .testTag("settings_privacy_policy_row"),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Policy,
                contentDescription = null,
                tint = TechCyanAccent,
                modifier = Modifier.size(20.dp)
              )
              Column {
                Text(
                  text = AppStrings.privacyPolicy(currentLanguage),
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                  color = TextPrimary
                )
                Text(
                  text = "Zero tracking, offline-first local data storage",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = TextTertiary
                )
              }
            }
            Text(
              text = "View",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = TechCyanAccent
            )
          }

          HorizontalDivider(
            color = NavyCardBorder,
            modifier = Modifier.padding(horizontal = 16.dp)
          )

          // Terms & Conditions Row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { showTermsDialog = true }
              .padding(horizontal = 16.dp, vertical = 14.dp)
              .testTag("settings_terms_row"),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                tint = TechIndigo,
                modifier = Modifier.size(20.dp)
              )
              Column {
                Text(
                  text = AppStrings.termsAndConditions(currentLanguage),
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                  color = TextPrimary
                )
                Text(
                  text = "Open educational platform licensing terms",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = TextTertiary
                )
              }
            }
            Text(
              text = "View",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = TechCyanAccent
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // ==========================================
      // SECTION 7: PREFERENCES & DATA (Reminders, Haptics, Reset)
      // ==========================================
      SettingsSectionHeader(
        icon = Icons.Default.Vibration,
        title = "Preferences & Diagnostics",
        subtitle = "Feedback toggles and device storage options"
      )
      Spacer(modifier = Modifier.height(10.dp))

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

          // Sound Effects
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
              Icon(Icons.Default.VolumeUp, contentDescription = null, tint = TechCyanAccent, modifier = Modifier.size(20.dp))
              Column {
                Text(
                  text = AppStrings.soundEffects(currentLanguage),
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                  color = TextPrimary
                )
                Text(
                  text = AppStrings.soundEffectsDesc(currentLanguage),
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = TextSecondary
                )
              }
            }
            Switch(
              checked = soundEffectsEnabled,
              onCheckedChange = { viewModel.setSoundEffectsEnabled(it) },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = TechBluePrimary
              ),
              modifier = Modifier.testTag("settings_sound_effects_switch")
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

          Spacer(modifier = Modifier.height(14.dp))
          HorizontalDivider(color = NavyCardBorder)
          Spacer(modifier = Modifier.height(14.dp))

          // Reset Progress Button
          OutlinedButton(
            onClick = { showResetConfirmDialog = true },
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
              .testTag("settings_reset_progress_btn"),
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
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Footer
      Text(
        text = "Computer Master • v${viewModel.updateManager.currentVersionName} Production Engine",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
        color = TextTertiary,
        modifier = Modifier.align(Alignment.CenterHorizontally)
      )
      Spacer(modifier = Modifier.height(40.dp))
    }
  }

  // ==========================================
  // DIALOGS & OVERLAYS
  // ==========================================

  // 1. Privacy Policy Dialog
  if (showPrivacyDialog) {
    PrivacyPolicyDialog(
      onDismiss = { showPrivacyDialog = false },
      currentLanguage = currentLanguage
    )
  }

  // 4. Terms & Conditions Dialog
  if (showTermsDialog) {
    TermsDialog(
      onDismiss = { showTermsDialog = false },
      currentLanguage = currentLanguage
    )
  }

  // 5. GitHub Update & APK URL configuration dialog (Preserved)
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

  // 6. Reset Progress Dialog (Preserved)
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
private fun SettingsSectionHeader(
  icon: ImageVector,
  title: String,
  subtitle: String
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Box(
      modifier = Modifier
        .size(32.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(NavyCard)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(8.dp)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = TechCyanAccent,
        modifier = Modifier.size(18.dp)
      )
    }

    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        ),
        color = TextPrimary
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = TextTertiary
      )
    }
  }
}

@Composable
private fun PrivacyPolicyDialog(
  onDismiss: () -> Unit,
  currentLanguage: AppLanguage
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(Icons.Default.Policy, contentDescription = null, tint = TechCyanAccent)
        Text(AppStrings.privacyPolicy(currentLanguage), color = TextPrimary, fontWeight = FontWeight.Bold)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(
          text = "Commitment to Student Privacy",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TechCyanAccent
        )
        Text(
          text = "Computer Master is designed with privacy and student autonomy at its foundation. Your educational journey, quizzes, and personal notes are your own.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
          color = TextSecondary
        )

        Text(
          text = "1. Local-First Data Storage",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TextPrimary
        )
        Text(
          text = "All course bookmarks, quiz attempts, study notes, and XP progress are stored exclusively on your device using encrypted Room SQLite databases and local preferences.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
          color = TextSecondary
        )

        Text(
          text = "2. Zero Tracking & Telemetry",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TextPrimary
        )
        Text(
          text = "The application does not embed any advertising trackers, user telemetry SDKs, or third-party behavioral profiling tools.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
          color = TextSecondary
        )

        Text(
          text = "3. GitHub Release Engine",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TextPrimary
        )
        Text(
          text = "When checking for updates, the app connects directly to official public GitHub repositories over HTTPS to compare version metadata. No identifying device tokens are transmitted.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
          color = TextSecondary
        )
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
        shape = RoundedCornerShape(10.dp)
      ) {
        Text(AppStrings.close(currentLanguage))
      }
    },
    containerColor = NavyCardElevated,
    shape = RoundedCornerShape(18.dp)
  )
}

@Composable
private fun TermsDialog(
  onDismiss: () -> Unit,
  currentLanguage: AppLanguage
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(Icons.Default.Description, contentDescription = null, tint = TechIndigo)
        Text(AppStrings.termsAndConditions(currentLanguage), color = TextPrimary, fontWeight = FontWeight.Bold)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(
          text = "Terms of Educational Use",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TechCyanAccent
        )
        Text(
          text = "Computer Master is developed by Soumen Mondal to provide universally accessible, high-caliber computer science, system engineering, and IT education.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
          color = TextSecondary
        )

        Text(
          text = "1. Free Access for Students",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TextPrimary
        )
        Text(
          text = "All syllabus lessons, 3D interactive hardware simulations, and certification quizzes are offered 100% free of charge for learners worldwide.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
          color = TextSecondary
        )

        Text(
          text = "2. Open Innovation & Integrity",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TextPrimary
        )
        Text(
          text = "The content is curated for academic instruction and self-study. Redistribution of official release APKs must maintain author attribution to Soumen Mondal.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
          color = TextSecondary
        )
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
        shape = RoundedCornerShape(10.dp)
      ) {
        Text(AppStrings.close(currentLanguage))
      }
    },
    containerColor = NavyCardElevated,
    shape = RoundedCornerShape(18.dp)
  )
}
