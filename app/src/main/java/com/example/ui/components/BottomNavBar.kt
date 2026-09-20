package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.NavRoutes
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

sealed class BottomNavItem(
  val route: String,
  val label: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
) {
  data object Home : BottomNavItem(NavRoutes.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home)
  data object Courses : BottomNavItem(NavRoutes.COURSES, "Courses", Icons.Filled.MenuBook, Icons.Outlined.MenuBook)
  data object Quiz : BottomNavItem(NavRoutes.QUIZ, "Quiz", Icons.Filled.Quiz, Icons.Outlined.Quiz)
  data object Progress : BottomNavItem(NavRoutes.PROGRESS, "Progress", Icons.Filled.TrendingUp, Icons.Outlined.TrendingUp)
  data object Profile : BottomNavItem(NavRoutes.PROFILE, "Profile", Icons.Filled.Person, Icons.Outlined.Person)

  companion object {
    val items = listOf(Home, Courses, Quiz, Progress, Profile)
  }
}

@Composable
fun BottomNavBar(
  currentRoute: String,
  onNavigate: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(
            NavyDark.copy(alpha = 0.95f),
            NavyDarkest
          )
        )
      )
      .border(
        width = 1.dp,
        color = NavyCardBorder.copy(alpha = 0.6f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
      )
      .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
      .navigationBarsPadding()
      .padding(horizontal = 8.dp, vertical = 6.dp)
      .testTag("bottom_navigation_bar")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      BottomNavItem.items.forEach { item ->
        val isSelected = currentRoute == item.route

        val scale by animateFloatAsState(
          targetValue = if (isSelected) 1.06f else 1.0f,
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
          ),
          label = "nav_item_scale_${item.route}"
        )

        val iconTint by animateColorAsState(
          targetValue = if (isSelected) TechCyanAccent else TextSecondary,
          label = "nav_icon_color_${item.route}"
        )

        val textColor by animateColorAsState(
          targetValue = if (isSelected) TechCyanAccent else TextTertiary,
          label = "nav_text_color_${item.route}"
        )

        Column(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
              if (!isSelected) {
                onNavigate(item.route)
              }
            }
            .padding(vertical = 6.dp)
            .scale(scale)
            .testTag("nav_item_${item.route}"),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(width = 44.dp, height = 30.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(
                if (isSelected) TechBluePrimary.copy(alpha = 0.22f) else Color.Transparent
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
              contentDescription = item.label,
              tint = iconTint,
              modifier = Modifier.size(22.dp)
            )
          }

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.sp
            ),
            color = textColor,
            maxLines = 1
          )
        }
      }
    }
  }
}
