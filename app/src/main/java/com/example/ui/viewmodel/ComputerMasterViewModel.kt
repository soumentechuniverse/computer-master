package com.example.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
  viewModel: ComputerMasterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
  modifier: Modifier = Modifier,
) {
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  val updateState by viewModel.updateState.collectAsState()
  val showUpdateDialog by viewModel.showUpdateDialog.collectAsState()
  val isOnline by viewModel.isOnline.collectAsState()

  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.SPLASH

  val topLevelRoutes = listOf(
    NavRoutes.HOME,
    NavRoutes.COURSES,
    NavRoutes.QUIZ,
    NavRoutes.PROGRESS,
    NavRoutes.PROFILE
  )

  // Internet required gate for authenticated app content
  val isAuthOrSplash =
    currentRoute == NavRoutes.SPLASH || currentRoute == NavRoutes.AUTH

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

        // Strict Internet Requirement Barrier
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
