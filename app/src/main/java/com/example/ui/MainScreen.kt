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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.BottomNavBar
import com.example.ui.navigation.AppNavigation
import com.example.ui.navigation.NavRoutes
import com.example.ui.theme.NavyDarkest
import com.example.ui.viewmodel.ComputerMasterViewModel

@Composable
fun MainScreen(
  viewModel: ComputerMasterViewModel = viewModel(),
  modifier: Modifier = Modifier,
) {
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.HOME

  val topLevelRoutes = listOf(
    NavRoutes.HOME,
    NavRoutes.COURSES,
    NavRoutes.QUIZ,
    NavRoutes.PROGRESS,
    NavRoutes.PROFILE
  )

  val showBottomBar = currentRoute in topLevelRoutes

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
    }
  }
}
