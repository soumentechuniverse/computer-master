package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * App theme mode preference options.
 */
enum class AppThemeMode(val key: String, val title: String) {
  SYSTEM("system", "System Default"),
  LIGHT("light", "Light"),
  DARK("dark", "Dark");

  companion object {
    fun fromKey(key: String): AppThemeMode =
      entries.find { it.key.equals(key, ignoreCase = true) } ?: SYSTEM
  }
}

private val ComputerMasterDarkColorScheme =
  darkColorScheme(
    primary = TechBluePrimary,
    onPrimary = Color.White,
    primaryContainer = NavyCardElevated,
    onPrimaryContainer = TechCyanAccent,
    secondary = TechCyanAccent,
    onSecondary = NavyDarkest,
    secondaryContainer = NavyCard,
    onSecondaryContainer = TextPrimary,
    tertiary = TechIndigo,
    onTertiary = Color.White,
    background = NavyDarkest,
    onBackground = TextPrimary,
    surface = NavyDark,
    onSurface = TextPrimary,
    surfaceVariant = NavySurface,
    onSurfaceVariant = TextSecondary,
    outline = NavyCardBorder,
    outlineVariant = Color(0xFF1B2845),
    error = TechRed,
    onError = Color.White,
  )

private val ComputerMasterLightColorScheme =
  lightColorScheme(
    primary = TechBluePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E40AF),
    secondary = Color(0xFF0284C7),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF0369A1),
    tertiary = TechIndigo,
    onTertiary = Color.White,
    background = Color(0xFFF1F5F9),
    onBackground = Color(0xFF0F172A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF334155),
    outline = Color(0xFFCBD5E1),
    outlineVariant = Color(0xFFE2E8F0),
    error = TechRed,
    onError = Color.White,
  )

@Composable
fun MyApplicationTheme(
  themeMode: AppThemeMode = AppThemeMode.DARK,
  darkTheme: Boolean = when (themeMode) {
    AppThemeMode.LIGHT -> false
    AppThemeMode.DARK -> true
    AppThemeMode.SYSTEM -> isSystemInDarkTheme()
  },
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) ComputerMasterDarkColorScheme else ComputerMasterLightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}


