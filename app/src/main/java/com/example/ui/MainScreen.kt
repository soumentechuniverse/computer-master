package com.example.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.update.UpdateUiState
import com.example.ui.components.BottomNavBar
import com.example.ui.components.UpdateAvailableDialog
import com.example.ui.navigation.AppNavigation
import com.example.ui.navigation.NavRoutes
import com.example.ui.screens.InternetRequiredScreen
import com.example.ui.theme.NavyDarkest
import com.example.ui.viewmodel.ComputerMasterViewModel
import com.example.util.ProvideAppLanguage

@Composable
fun MainScreen(
  viewModel: ComputerMasterViewModel = viewModel(),
  modifier: Modifier = Modifier,
) {
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  val updateState by viewModel.updateState.collectAsState()
  val showUpdateDialog by viewModel.showUpdateDialog.collectAsState()
  val isOnline by viewModel.isOnline.collectAsState()

  val context = LocalContext.current
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.SPLASH

  // Safe runtime notification permission flow for Android 13+ (API 33)
  val notificationPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { /* Handled gracefully by Android notification system */ }
  )

  LaunchedEffect(Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (
        ContextCompat.checkSelfPermission(
          context,
          Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
      ) {
        notificationPermissionLauncher.launch(
          Manifest.permission.POST_NOTIFICATIONS
        )
      }
    }
  }

  val topLevelRoutes = listOf(
    NavRoutes.HOME,
    NavRoutes.COURSES,
    NavRoutes.QUIZ,
    NavRoutes.PROGRESS,
    NavRoutes.PROFILE
  )

  // Splash screen is the only pre-app screen.
  // Login/Auth is not used.
  val isAuthOrSplash = currentRoute == NavRoutes.SPLASH

  val showBottomBar = currentRoute in topLevelRoutes && isOnline

  ProvideAppLanguage(language = currentLanguage) {
    Scaffold(
      modifier = modifier.fillMaxSize(),
      containerColor = NavyDarkest,
      contentWindowInsets = WindowInsets.statusBars,
      bottomBar = {
        AnimatedVisibility(
          visible = showBottomBar,
          enter = slideInVertically(initialOffsetY = { it }),
          exit = slideOutVertically(targetOffsetY = { it })
        ) {
          BottomNavBar(
            currentRoute = currentRoute,
            onNavigate = { targetRoute ->
              navController.navigate(targetRoute) {
                popUpTo(NavRoutes.HOME) {
                  saveState = true
                }
                launchSingleTop = true
                restoreState = true
              }
            }
          )
        }
      }
    ) { innerPadding ->

      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {

        AppNavigation(
          navController = navController,
          viewModel = viewModel
        )

        // Strict Internet Requirement Barrier:
        // Do not allow the user to view or interact with main app content without active internet.
        if (!isOnline && !isAuthOrSplash) {
          InternetRequiredScreen(
            currentLanguage = currentLanguage,
            onRetry = {
              viewModel.checkNetworkConnection()
            }
          )
        }

        // Global New Update Available Dialog
        if (showUpdateDialog) {
          UpdateAvailableDialog(
            updateState = updateState,
            currentLanguage = currentLanguage,
            currentVersionName = viewModel.updateManager.currentVersionName,

            onUpdateNow = { info ->
              if (updateState is UpdateUiState.ReadyToInstall) {
                viewModel.launchPackageInstaller(
                  (updateState as UpdateUiState.ReadyToInstall).apkFile
                )
              } else {
                viewModel.downloadUpdateApk(info)
              }
            },

            onLater = { dismissedVersionCode ->
              viewModel.dismissUpdateDialog(dismissedVersionCode)
            },

            onRetry = {
              if (updateState is UpdateUiState.UpdateAvailable) {
                viewModel.downloadUpdateApk(
                  (updateState as UpdateUiState.UpdateAvailable).info
                )
              } else {
                viewModel.checkForUpdates(isManual = true)
              }
            }
          )
        }
      }
    }
  }
}
