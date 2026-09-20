package com.example.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ui.screens.CourseDetailScreen
import com.example.ui.screens.CoursesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.viewmodel.ComputerMasterViewModel

@Composable
fun AppNavigation(
  navController: NavHostController,
  viewModel: ComputerMasterViewModel,
  modifier: Modifier = Modifier,
) {
  NavHost(
    navController = navController,
    startDestination = NavRoutes.HOME,
    modifier = modifier,
    enterTransition = { fadeIn(animationSpec = tween(220)) },
    exitTransition = { fadeOut(animationSpec = tween(180)) },
    popEnterTransition = { fadeIn(animationSpec = tween(220)) },
    popExitTransition = { fadeOut(animationSpec = tween(180)) }
  ) {
    composable(NavRoutes.HOME) {
      HomeScreen(
        viewModel = viewModel,
        onNavigateToCourse = { courseId ->
          navController.navigate(NavRoutes.courseDetail(courseId))
        },
        onNavigateToCoursesTab = { level ->
          if (level != null) {
            viewModel.setSelectedLevel(level)
          }
          navController.navigate(NavRoutes.COURSES) {
            popUpTo(NavRoutes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
          }
        },
        onNavigateToQuiz = {
          navController.navigate(NavRoutes.QUIZ) {
            popUpTo(NavRoutes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
          }
        }
      )
    }

    composable(NavRoutes.COURSES) {
      CoursesScreen(
        viewModel = viewModel,
        onNavigateToCourseDetail = { courseId ->
          navController.navigate(NavRoutes.courseDetail(courseId))
        }
      )
    }

    composable(
      route = NavRoutes.COURSE_DETAIL,
      arguments = listOf(navArgument("courseId") { type = NavType.StringType }),
      enterTransition = {
        slideIntoContainer(
          AnimatedContentTransitionScope.SlideDirection.Left,
          animationSpec = tween(260)
        ) + fadeIn()
      },
      exitTransition = {
        slideOutOfContainer(
          AnimatedContentTransitionScope.SlideDirection.Right,
          animationSpec = tween(220)
        ) + fadeOut()
      }
    ) { backStackEntry ->
      val courseId = backStackEntry.arguments?.getString("courseId").orEmpty()
      CourseDetailScreen(
        courseId = courseId,
        viewModel = viewModel,
        onBackClick = { navController.popBackStack() },
        onNavigateToQuiz = {
          navController.navigate(NavRoutes.QUIZ)
        },
        onNavigateToLesson = { cId, lId ->
          navController.navigate(NavRoutes.lessonDetail(cId, lId))
        }
      )
    }

    composable(
      route = NavRoutes.LESSON_DETAIL,
      arguments = listOf(
        navArgument("courseId") { type = NavType.StringType },
        navArgument("lessonId") { type = NavType.StringType }
      ),
      enterTransition = {
        slideIntoContainer(
          AnimatedContentTransitionScope.SlideDirection.Left,
          animationSpec = tween(260)
        ) + fadeIn()
      },
      exitTransition = {
        slideOutOfContainer(
          AnimatedContentTransitionScope.SlideDirection.Right,
          animationSpec = tween(220)
        ) + fadeOut()
      }
    ) { backStackEntry ->
      val courseId = backStackEntry.arguments?.getString("courseId").orEmpty()
      val lessonId = backStackEntry.arguments?.getString("lessonId").orEmpty()
      LessonScreen(
        courseId = courseId,
        lessonId = lessonId,
        viewModel = viewModel,
        onBackClick = { navController.popBackStack() },
        onNavigateToLesson = { nextLessonId ->
          navController.navigate(NavRoutes.lessonDetail(courseId, nextLessonId)) {
            popUpTo(NavRoutes.LESSON_DETAIL) { inclusive = true }
          }
        },
        onNavigateToQuiz = {
          navController.navigate(NavRoutes.QUIZ)
        }
      )
    }

    composable(NavRoutes.QUIZ) {
      QuizScreen(viewModel = viewModel)
    }

    composable(NavRoutes.PROGRESS) {
      ProgressScreen(
        viewModel = viewModel,
        onNavigateToCourse = { courseId ->
          navController.navigate(NavRoutes.courseDetail(courseId))
        }
      )
    }

    composable(NavRoutes.PROFILE) {
      ProfileScreen(
        viewModel = viewModel,
        onNavigateToCourse = { courseId ->
          navController.navigate(NavRoutes.courseDetail(courseId))
        }
      )
    }
  }
}
