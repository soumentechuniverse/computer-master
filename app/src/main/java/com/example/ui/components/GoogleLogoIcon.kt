package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

/**
 * Standard 4-color Google "G" logo rendered purely in Compose Canvas.
 * No external raster asset dependencies required.
 */
@Composable
fun GoogleLogoIcon(modifier: Modifier = Modifier.size(24.dp)) {
  Canvas(modifier = modifier) {
    val width = size.width
    val height = size.height
    val centerX = width / 2f
    val centerY = height / 2f
    val radius = width.coerceAtMost(height) / 2f

    // Google official brand colors
    val blue = Color(0xFF4285F4)
    val red = Color(0xFFEA4335)
    val yellow = Color(0xFFFBBC05)
    val green = Color(0xFF34A853)

    // Red arc (Top)
    val topRedPath = Path().apply {
      moveTo(centerX, centerY)
      lineTo(centerX + radius * 0.95f, centerY - radius * 0.35f)
      arcTo(
        rect = androidx.compose.ui.geometry.Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
        startAngleDegrees = -35f,
        sweepAngleDegrees = -110f,
        forceMoveTo = false
      )
      close()
    }
    drawPath(topRedPath, red, style = Fill)

    // Yellow arc (Left Top / Side)
    val yellowPath = Path().apply {
      moveTo(centerX, centerY)
      arcTo(
        rect = androidx.compose.ui.geometry.Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
        startAngleDegrees = -145f,
        sweepAngleDegrees = -90f,
        forceMoveTo = false
      )
      close()
    }
    drawPath(yellowPath, yellow, style = Fill)

    // Green arc (Bottom)
    val greenPath = Path().apply {
      moveTo(centerX, centerY)
      arcTo(
        rect = androidx.compose.ui.geometry.Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
        startAngleDegrees = -235f,
        sweepAngleDegrees = -90f,
        forceMoveTo = false
      )
      close()
    }
    drawPath(greenPath, green, style = Fill)

    // Blue section (Right bar & bottom sweep)
    val bluePath = Path().apply {
      moveTo(centerX, centerY)
      arcTo(
        rect = androidx.compose.ui.geometry.Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
        startAngleDegrees = -325f,
        sweepAngleDegrees = -35f,
        forceMoveTo = false
      )
      close()
    }
    drawPath(bluePath, blue, style = Fill)

    // Clear center cutout
    drawCircle(
      color = Color.White,
      radius = radius * 0.58f,
      center = Offset(centerX, centerY)
    )

    // Blue horizontal crossbar
    drawRect(
      color = blue,
      topLeft = Offset(centerX - radius * 0.05f, centerY - radius * 0.22f),
      size = androidx.compose.ui.geometry.Size(radius * 1.05f, radius * 0.44f)
    )
  }
}
