package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ComputerMasterColorScheme =
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

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = ComputerMasterColorScheme,
    typography = Typography,
    content = content
  )
}

