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
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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

    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }
    var showConfigUrlDialog by remember { mutableStateOf(false) }

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
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
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

            // =========================================================
            // ACCOUNT
            // Login / Sign-in intentionally removed.
            // =========================================================

            SettingsSectionHeader(
                icon = Icons.Default.AccountCircle,
                title = AppStrings.settingsAccount(currentLanguage),
                subtitle = "Computer Master works without Login or Sign-in"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(NavyCard)
                    .border(
                        1.dp,
                        NavyCardBorder,
                        RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        TechBluePrimary,
                                        TechCyanAccent
                                    )
                                )
                            )
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(NavyDarkest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = TechCyanAccent,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Column {
                        Text(
                            text = AppStrings.guestModeTitle(currentLanguage),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = AppStrings.guestModeDesc(currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp
                            ),
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    TechCyanAccent.copy(alpha = 0.15f)
                                )
                                .padding(
                                    horizontal = 7.dp,
                                    vertical = 3.dp
                                )
                        ) {
                            Text(
                                text = "Guest Mode",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TechCyanAccent
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =========================================================
            // LANGUAGE
            // =========================================================

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
                            .background(
                                if (isSelected)
                                    TechBluePrimary
                                else
                                    NavyCard
                            )
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected)
                                    TechCyanAccent
                                else
                                    NavyCardBorder,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable {
                                viewModel.setLanguage(lang)
                            }
                            .padding(
                                vertical = 14.dp,
                                horizontal = 6.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = lang.nativeName,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight =
                                            if (isSelected)
                                                FontWeight.Bold
                                            else
                                                FontWeight.SemiBold,
                                        fontSize = 15.sp
                                    ),
                                    color =
                                        if (isSelected)
                                            Color.White
                                        else
                                            TextPrimary
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
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp
                                ),
                                color =
                                    if (isSelected)
                                        Color.White.copy(alpha = 0.85f)
                                    else
                                        TextTertiary
                            )

                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (isSelected)
                                            Color.White.copy(alpha = 0.2f)
                                        else
                                            NavyDark
                                    )
                                    .padding(
                                        horizontal = 6.dp,
                                        vertical = 1.dp
                                    )
                            ) {
                                Text(
                                    text = lang.symbol,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color =
                                        if (isSelected)
                                            Color.White
                                        else
                                            TechCyanAccent
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =========================================================
            // APPEARANCE
            // =========================================================

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
                    Triple(
                        AppThemeMode.SYSTEM,
                        AppStrings.themeSystem(currentLanguage),
                        Icons.Default.BrightnessAuto
                    ),
                    Triple(
                        AppThemeMode.LIGHT,
                        AppStrings.themeLight(currentLanguage),
                        Icons.Default.LightMode
                    ),
                    Triple(
                        AppThemeMode.DARK,
                        AppStrings.themeDark(currentLanguage),
                        Icons.Default.DarkMode
                    )
                ).forEach { (mode, label, icon) ->

                    val isSelected = themeMode == mode

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (isSelected)
                                    NavyCardElevated
                                else
                                    NavyCard
                            )
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected)
                                    TechCyanAccent
                                else
                                    NavyCardBorder,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable {
                                viewModel.setThemeMode(mode)
                            }
                            .padding(
                                vertical = 14.dp,
                                horizontal = 4.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected)
                                            TechBluePrimary
                                        else
                                            NavyDark
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint =
                                        if (isSelected)
                                            Color.White
                                        else
                                            TechCyanAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight =
                                        if (isSelected)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Medium,
                                    fontSize = 12.sp
                                ),
                                color =
                                    if (isSelected)
                                        Color.White
                                    else
                                        TextPrimary
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

            // =========================================================
            // UPDATES
            // =========================================================

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
                    .border(
                        1.dp,
                        NavyCardBorder,
                        RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

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

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Computer Master Update Engine",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = TextPrimary
                            )

                            Text(
                                text = AppStrings.checkForUpdatesDesc(currentLanguage),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp
                                ),
                                color = TextTertiary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyDark)
                            .border(
                                1.dp,
                                NavyCardBorder,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = "Installed: v${viewModel.updateManager.currentVersionName}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = TextSecondary
                                )

                                when (val state = updateState) {

                                    is UpdateUiState.Checking -> {
                                        Text(
                                            text = AppStrings.checkingUpdates(currentLanguage),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TechCyanAccent
                                        )
                                    }

                                    is UpdateUiState.UpdateAvailable -> {
                                        Text(
                                            text = "v${state.info.versionName} Available!",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = TechAmber
                                        )
                                    }

                                    is UpdateUiState.UpToDate -> {
                                        Text(
                                            text = AppStrings.appUpToDate(
                                                currentLanguage,
                                                state.versionName
                                            ),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TechGreen
                                        )
                                    }

                                    is UpdateUiState.Downloading -> {
                                        Text(
                                            text = "${AppStrings.updateDownloading(currentLanguage)} (${(state.progress * 100).toInt()}%)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TechCyanAccent
                                        )
                                    }

                                    is UpdateUiState.ReadyToInstall -> {
                                        Text(
                                            text = AppStrings.updateReadyToInstall(currentLanguage),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TechGreen
                                        )
                                    }

                                    is UpdateUiState.Error -> {
                                        Text(
                                            text = state.message,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TechRed
                                        )
                                    }

                                    else -> {
                                        Text(
                                            text = "GitHub Release Channel",
                                            style = MaterialTheme.typography.bodySmall,
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

                            } else if (
                                updateState is UpdateUiState.UpdateAvailable ||
                                updateState is UpdateUiState.Downloading ||
                                updateState is UpdateUiState.ReadyToInstall
                            ) {

                                Button(
                                    onClick = {
                                        viewModel.openUpdateDialog()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = TechAmber
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    modifier = Modifier.testTag(
                                        "settings_screen_view_update_btn"
                                    )
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
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = NavyDarkest
                                    )
                                }

                            } else {

                                Button(
                                    onClick = {
                                        viewModel.checkForUpdates(isManual = true)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = TechBluePrimary
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    modifier = Modifier.testTag(
                                        "settings_screen_check_update_btn"
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Sync,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = AppStrings.checkForUpdates(currentLanguage),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

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
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = TextPrimary
                                )

                                Text(
                                    text = AppStrings.updateNotificationsDesc(currentLanguage),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp
                                    ),
                                    color = TextSecondary
                                )
                            }
                        }

                        Switch(
                            checked = updateNotificationsEnabled,
                            onCheckedChange = {
                                viewModel.setUpdateNotificationsEnabled(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = TechBluePrimary
                            ),
                            modifier = Modifier.testTag(
                                "settings_update_notifications_switch"
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(color = NavyCardBorder)

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showConfigUrlDialog = true
                            }
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
                                style = MaterialTheme.typography.labelSmall,
                                color = TechCyanAccent
                            )
                        }

                        Text(
                            text = AppStrings.configureSource(currentLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TechCyanAccent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =========================================================
            // ABOUT
            // =========================================================

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
                    .border(
                        1.dp,
                        NavyCardBorder,
                        RoundedCornerShape(18.dp)
                    )
                    .padding(18.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            TechBluePrimary,
                                            TechCyanAccent
                                        )
                                    ),
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
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = TextPrimary
                            )

                            Text(
                                text = "Academy of Computing & Systems",
                                style = MaterialTheme.typography.bodySmall,
                                color = TechCyanAccent
                            )

                            Text(
                                text = "Version ${viewModel.updateManager.currentVersionName}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TechGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    HorizontalDivider(color = NavyCardBorder)

                    Spacer(modifier = Modifier.height(12.dp))

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
                                fontWeight = FontWeight.Bold
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
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =========================================================
            // PRIVACY
            // =========================================================

            SettingsSectionHeader(
                icon = Icons.Default.Security,
                title = AppStrings.settingsPrivacy(currentLanguage),
                subtitle = "Privacy and educational data settings"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(NavyCard)
                    .border(
                        1.dp,
                        NavyCardBorder,
                        RoundedCornerShape(18.dp)
                    )
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showPrivacyDialog = true
                            }
                            .padding(16.dp)
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

                            Text(
                                text = AppStrings.privacyPolicy(currentLanguage),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = TextPrimary
                            )
                        }

                        Text(
                            text = "View",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TechCyanAccent
                        )
                    }

                    HorizontalDivider(
                        color = NavyCardBorder,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showTermsDialog = true
                            }
                            .padding(16.dp)
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

                            Text(
                                text = AppStrings.termsAndConditions(currentLanguage),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = TextPrimary
                            )
                        }

                        Text(
                            text = "View",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TechCyanAccent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =========================================================
            // PREFERENCES
            // =========================================================

            SettingsSectionHeader(
                icon = Icons.Default.Vibration,
                title = "Preferences & Diagnostics",
                subtitle = "Feedback and device preferences"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(NavyCard)
                    .border(
                        1.dp,
                        NavyCardBorder,
                        RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

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
                                Icons.Default.Notifications,
                                contentDescription = null,
                                tint = TechCyanAccent,
                                modifier = Modifier.size(20.dp)
                            )

                            Column {
                                Text(
                                    text = AppStrings.dailyReminder(currentLanguage),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = TextPrimary
                                )

                                Text(
                                    text = AppStrings.dailyReminderDesc(currentLanguage),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }

                        Switch(
                            checked = dailyReminderEnabled,
                            onCheckedChange = {
                                dailyReminderEnabled = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = TechBluePrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

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
                                Icons.Default.Vibration,
                                contentDescription = null,
                                tint = TechCyanAccent,
                                modifier = Modifier.size(20.dp)
                            )

                            Column {
                                Text(
                                    text = AppStrings.hapticFeedback(currentLanguage),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = TextPrimary
                                )

                                Text(
                                    text = AppStrings.hapticFeedbackDesc(currentLanguage),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }

                        Switch(
                            checked = hapticFeedbackEnabled,
                            onCheckedChange = {
                                hapticFeedbackEnabled = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = TechBluePrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    HorizontalDivider(color = NavyCardBorder)

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedButton(
                        onClick = {
                            showResetConfirmDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("settings_reset_progress_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TechRed
                        )
                    ) {

                        Icon(
                            Icons.Default.RestartAlt,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = AppStrings.resetProgress(currentLanguage),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Computer Master • v${viewModel.updateManager.currentVersionName}",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp
                ),
                color = TextTertiary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    // =========================================================
    // UPDATE SOURCE DIALOG
    // =========================================================

    if (showConfigUrlDialog) {

        var inputUrl by remember {
            mutableStateOf(viewModel.getUpdateMetadataUrl())
        }

        var inputApkUrl by remember {
            mutableStateOf(viewModel.getCustomApkUrl() ?: "")
        }

        AlertDialog(
            onDismissRequest = {
                showConfigUrlDialog = false
            },

            title = {
                Text(
                    text = AppStrings.updateSourceUrl(currentLanguage),
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },

            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "GitHub Update Metadata JSON URL:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TechCyanAccent
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = inputUrl,
                        onValueChange = {
                            inputUrl = it
                        },
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
                        text = "APK Direct Download URL:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TechCyanAccent
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = inputApkUrl,
                        onValueChange = {
                            inputApkUrl = it
                        },
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

                        viewModel.setCustomApkUrl(
                            inputApkUrl.ifBlank {
                                null
                            }
                        )

                        showConfigUrlDialog = false

                        viewModel.checkForUpdates(
                            isManual = true
                        )
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = TechBluePrimary
                    )
                ) {
                    Text(
                        AppStrings.save(currentLanguage)
                    )
                }
            },

            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showConfigUrlDialog = false
                    }
                ) {
                    Text(
                        AppStrings.cancel(currentLanguage),
                        color = TextPrimary
                    )
                }
            },

            containerColor = NavyCardElevated,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // =========================================================
    // RESET PROGRESS
    // =========================================================

    if (showResetConfirmDialog) {

        AlertDialog(
            onDismissRequest = {
                showResetConfirmDialog = false
            },

            title = {
                Text(
                    text = "Reset Progress?",
                    color = TextPrimary
                )
            },

            text = {
                Text(
                    text = "This will reset your completed lessons and quiz scores to initial state. Are you sure?",
                    color = TextSecondary
                )
            },

            confirmButton = {
                Button(
                    onClick = {

                        viewModel.resetAllProgress()

                        showResetConfirmDialog = false
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = TechRed
                    )
                ) {
                    Text("Reset")
                }
            },

            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showResetConfirmDialog = false
                    }
                ) {
                    Text(
                        "Cancel",
                        color = TextPrimary
                    )
                }
            },

            containerColor = NavyCardElevated,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // =========================================================
    // PRIVACY
    // =========================================================

    if (showPrivacyDialog) {

        PrivacyPolicyDialog(
            onDismiss = {
                showPrivacyDialog = false
            },
            currentLanguage = currentLanguage
        )
    }

    // =========================================================
    // TERMS
    // =========================================================

    if (showTermsDialog) {

        TermsDialog(
            onDismiss = {
                showTermsDialog = false
            },
            currentLanguage = currentLanguage
        )
    }
}


// =============================================================
// SECTION HEADER
// =============================================================

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
                .border(
                    1.dp,
                    NavyCardBorder,
                    RoundedCornerShape(8.dp)
                ),
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
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp
                ),
                color = TextTertiary
            )
        }
    }
}


// =============================================================
// PRIVACY DIALOG
// =============================================================

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

                Icon(
                    Icons.Default.Policy,
                    contentDescription = null,
                    tint = TechCyanAccent
                )

                Text(
                    AppStrings.privacyPolicy(currentLanguage),
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
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
                    text = "Computer Master Privacy",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TechCyanAccent
                )

                Text(
                    text = "Computer Master is designed as an educational application. Course progress, notes and quiz information are designed to remain available locally on the device.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    ),
                    color = TextSecondary
                )

                Text(
                    text = "No Login Required",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )

                Text(
                    text = "The current version does not require a Google account, email login or password to use the application.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    ),
                    color = TextSecondary
                )

                Text(
                    text = "Update checking may connect to the configured update source when you manually check for an application update.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    ),
                    color = TextSecondary
                )
            }
        },

        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TechBluePrimary
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    AppStrings.close(currentLanguage)
                )
            }
        },

        containerColor = NavyCardElevated,
        shape = RoundedCornerShape(18.dp)
    )
}


// =============================================================
// TERMS DIALOG
// =============================================================

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

                Icon(
                    Icons.Default.Description,
                    contentDescription = null,
                    tint = TechIndigo
                )

                Text(
                    AppStrings.termsAndConditions(currentLanguage),
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
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
                    text = "Computer Master Educational Terms",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TechCyanAccent
                )

                Text(
                    text = "Computer Master is an educational application designed to help learners study computer fundamentals, hardware, software, networking, programming and advanced computing topics.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    ),
                    color = TextSecondary
                )

                Text(
                    text = "The application currently operates without requiring an account or subscription.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    ),
                    color = TextSecondary
                )
            }
        },

        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TechBluePrimary
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    AppStrings.close(currentLanguage)
                )
            }
        },

        containerColor = NavyCardElevated,
        shape = RoundedCornerShape(18.dp)
    )
}
