package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyActivity
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

enum class ActivityChartMode {
  BARS,
  TREND_AREA
}

/**
 * Modern, interactive Learning Activity data visualization chart inspired by D3 & Recharts.
 * Visualizes lessons completed over the past week with animated rendering,
 * interactive day selection tooltips, grid guide lines, and Bar / Trend-Area mode switching.
 */
@Composable
fun LearningActivityChart(
  activities: List<DailyActivity>,
  modifier: Modifier = Modifier
) {
  var chartMode by remember { mutableStateOf(ActivityChartMode.BARS) }
  var selectedIndex by remember { mutableIntStateOf(3) } // Default to Thursday (peak day)
  var isAnimated by remember { mutableStateOf(false) }

  LaunchedEffect(Unit) {
    isAnimated = true
  }

  val animProgress by animateFloatAsState(
    targetValue = if (isAnimated) 1f else 0f,
    animationSpec = tween(durationMillis = 800),
    label = "chartAnimProgress"
  )

  // Fallback data if empty
  val data = if (activities.isNotEmpty()) {
    activities
  } else {
    listOf(
      DailyActivity("Mon", 1.5f, true, lessonsCount = 4),
      DailyActivity("Tue", 2.0f, true, lessonsCount = 6),
      DailyActivity("Wed", 1.0f, true, lessonsCount = 3),
      DailyActivity("Thu", 2.5f, true, lessonsCount = 7),
      DailyActivity("Fri", 1.8f, true, lessonsCount = 5),
      DailyActivity("Sat", 0.5f, false, lessonsCount = 2),
      DailyActivity("Sun", 0.0f, false, lessonsCount = 1)
    )
  }

  val totalLessons = data.sumOf { it.lessonsCount }
  val maxLessons = (data.maxOfOrNull { it.lessonsCount } ?: 8).coerceAtLeast(8)
  val averageLessons = if (data.isNotEmpty()) totalLessons.toFloat() / data.size else 0f
  val peakActivity = data.maxByOrNull { it.lessonsCount } ?: data.first()
  val selectedActivity = data.getOrElse(selectedIndex) { data.first() }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 10.dp)
      .clip(RoundedCornerShape(22.dp))
      .testTag("learning_activity_chart"),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = BorderStroke(1.dp, NavyCardBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      // Header: Title, Subtitle, and Chart Mode Selector (Recharts style)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Timeline,
              contentDescription = null,
              tint = TechCyanAccent,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "Learning Activity",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
              ),
              color = TextPrimary
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Lessons completed over the past week",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = TextSecondary
          )
        }

        // Toggle buttons: Bar vs Area
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(NavyDark)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
            .padding(2.dp),
          horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(if (chartMode == ActivityChartMode.BARS) TechBluePrimary else Color.Transparent)
              .clickable { chartMode = ActivityChartMode.BARS }
              .padding(horizontal = 8.dp, vertical = 5.dp)
              .testTag("chart_mode_bars"),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.BarChart,
              contentDescription = "Bar View",
              tint = if (chartMode == ActivityChartMode.BARS) Color.White else TextTertiary,
              modifier = Modifier.size(16.dp)
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(if (chartMode == ActivityChartMode.TREND_AREA) TechBluePrimary else Color.Transparent)
              .clickable { chartMode = ActivityChartMode.TREND_AREA }
              .padding(horizontal = 8.dp, vertical = 5.dp)
              .testTag("chart_mode_area"),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.ShowChart,
              contentDescription = "Trend Area View",
              tint = if (chartMode == ActivityChartMode.TREND_AREA) Color.White else TextTertiary,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Quick metric pill indicators (Total, Daily Avg, Peak Day)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        MetricTag(
          label = "Total Completed",
          value = "$totalLessons lessons",
          color = TechCyanAccent,
          modifier = Modifier.weight(1f)
        )
        MetricTag(
          label = "Daily Average",
          value = String.format("%.1f/day", averageLessons),
          color = TechGreen,
          modifier = Modifier.weight(1f)
        )
        MetricTag(
          label = "Peak Output",
          value = "${peakActivity.day} (${peakActivity.lessonsCount})",
          color = TechAmber,
          modifier = Modifier.weight(1.1f)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Interactive Selected Day Tooltip Box (Recharts tooltip style)
      AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NavyCardElevated)
            .border(1.dp, TechCyanAccent.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(TechCyanAccent)
              )
              Text(
                text = "${selectedActivity.day} Activity:",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = TextSecondary
              )
              Text(
                text = "${selectedActivity.lessonsCount} lessons completed",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp
                ),
                color = TextPrimary
              )
            }

            if (selectedActivity.day == peakActivity.day) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.LocalFireDepartment,
                  contentDescription = null,
                  tint = TechAmber,
                  modifier = Modifier.size(14.dp)
                )
                Text(
                  text = "Peak Day",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.5.sp
                  ),
                  color = TechAmber
                )
              }
            } else if (selectedActivity.lessonsCount >= 3) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = TechGreen,
                  modifier = Modifier.size(13.dp)
                )
                Text(
                  text = "Target Met",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.5.sp
                  ),
                  color = TechGreen
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Main Chart Canvas with Axes, Grid Lines, and Touch Interaction
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(170.dp)
      ) {
        val yGridLabels = listOf(maxLessons, (maxLessons * 0.75f).toInt(), (maxLessons * 0.5f).toInt(), (maxLessons * 0.25f).toInt(), 0)

        Canvas(
          modifier = Modifier
            .fillMaxSize()
            .pointerInput(data) {
              detectTapGestures { offset ->
                val chartWidth = size.width - 45f // account for y-axis offset
                val slotWidth = chartWidth / data.size
                val clickedIndex = ((offset.x - 35f) / slotWidth).toInt().coerceIn(0, data.lastIndex)
                selectedIndex = clickedIndex
              }
            }
        ) {
          val leftPadding = 35f
          val bottomPadding = 40f
          val topPadding = 15f
          val chartWidth = size.width - leftPadding - 10f
          val chartHeight = size.height - bottomPadding - topPadding

          // 1. Draw subtle horizontal grid lines (Y-axis grid)
          val gridLineCount = 4
          val dashEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
          for (i in 0..gridLineCount) {
            val yPos = topPadding + (chartHeight * i / gridLineCount)
            drawLine(
              color = NavyCardBorder.copy(alpha = 0.5f),
              start = Offset(leftPadding, yPos),
              end = Offset(size.width - 10f, yPos),
              strokeWidth = 1f,
              pathEffect = dashEffect
            )
          }

          val slotWidth = chartWidth / data.size
          val barWidth = (slotWidth * 0.46f).coerceIn(16f, 32f)

          if (chartMode == ActivityChartMode.BARS) {
            // ==========================================
            // BARS MODE (Recharts-style Rounded Bar Chart)
            // ==========================================
            data.forEachIndexed { index, item ->
              val count = item.lessonsCount.toFloat() * animProgress
              val barHeight = (count / maxLessons.toFloat()) * chartHeight
              val xCenter = leftPadding + (slotWidth * index) + (slotWidth / 2f)
              val isSelected = index == selectedIndex

              val barLeft = xCenter - (barWidth / 2f)
              val barTop = topPadding + (chartHeight - barHeight)

              // Background track bar (ghost bar)
              drawRoundRect(
                color = NavyDark.copy(alpha = 0.6f),
                topLeft = Offset(barLeft, topPadding),
                size = Size(barWidth, chartHeight),
                cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f)
              )

              // Active bar gradient
              val gradientBrush = Brush.verticalGradient(
                colors = if (isSelected) {
                  listOf(TechCyanAccent, TechBluePrimary)
                } else if (item.day == peakActivity.day) {
                  listOf(TechAmber, Color(0xFFD97706))
                } else {
                  listOf(TechBluePrimary.copy(alpha = 0.9f), Color(0xFF1E3A8A))
                },
                startY = barTop,
                endY = topPadding + chartHeight
              )

              if (barHeight > 4f) {
                drawRoundRect(
                  brush = gradientBrush,
                  topLeft = Offset(barLeft, barTop),
                  size = Size(barWidth, barHeight),
                  cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f)
                )

                // Highlight cap for selected bar
                if (isSelected) {
                  drawCircle(
                    color = Color.White,
                    radius = 3.5f,
                    center = Offset(xCenter, barTop + 4f)
                  )
                }
              }
            }
          } else {
            // ==========================================
            // TREND AREA MODE (Recharts/D3 Spline Area Chart)
            // ==========================================
            val points = data.mapIndexed { index, item ->
              val count = item.lessonsCount.toFloat() * animProgress
              val x = leftPadding + (slotWidth * index) + (slotWidth / 2f)
              val y = topPadding + chartHeight - ((count / maxLessons.toFloat()) * chartHeight)
              Offset(x, y)
            }

            if (points.size >= 2) {
              val linePath = Path()
              val fillPath = Path()

              linePath.moveTo(points.first().x, points.first().y)
              fillPath.moveTo(points.first().x, topPadding + chartHeight)
              fillPath.lineTo(points.first().x, points.first().y)

              // Smooth cubic bezier curve (spline interpolation)
              for (i in 0 until points.size - 1) {
                val p0 = points[i]
                val p1 = points[i + 1]
                val midX = (p0.x + p1.x) / 2f
                linePath.cubicTo(midX, p0.y, midX, p1.y, p1.x, p1.y)
                fillPath.cubicTo(midX, p0.y, midX, p1.y, p1.x, p1.y)
              }

              fillPath.lineTo(points.last().x, topPadding + chartHeight)
              fillPath.close()

              // Draw soft gradient area fill
              drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                  colors = listOf(
                    TechCyanAccent.copy(alpha = 0.35f),
                    TechBluePrimary.copy(alpha = 0.08f),
                    Color.Transparent
                  ),
                  startY = topPadding,
                  endY = topPadding + chartHeight
                )
              )

              // Draw smooth glowing trend line
              drawPath(
                path = linePath,
                brush = Brush.horizontalGradient(listOf(TechCyanAccent, TechBluePrimary)),
                style = Stroke(width = 3.5f, cap = StrokeCap.Round)
              )

              // Draw data node circles
              points.forEachIndexed { idx, pt ->
                val isSelected = idx == selectedIndex
                if (isSelected) {
                  drawCircle(
                    color = TechCyanAccent.copy(alpha = 0.3f),
                    radius = 9f,
                    center = pt
                  )
                  drawCircle(
                    color = Color.White,
                    radius = 5f,
                    center = pt
                  )
                  drawCircle(
                    color = TechBluePrimary,
                    radius = 3f,
                    center = pt
                  )
                } else {
                  drawCircle(
                    color = NavyCard,
                    radius = 4f,
                    center = pt
                  )
                  drawCircle(
                    color = TechCyanAccent,
                    radius = 2.5f,
                    center = pt
                  )
                }
              }
            }
          }
        }

        // X-Axis Day Labels Row below the canvas
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 28.dp, end = 4.dp)
            .align(Alignment.BottomCenter),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          data.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier
                .clickable { selectedIndex = index }
                .padding(vertical = 4.dp, horizontal = 4.dp)
            ) {
              Text(
                text = item.day,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  fontSize = 11.sp
                ),
                color = if (isSelected) TechCyanAccent else TextSecondary
              )
              Text(
                text = "${item.lessonsCount}",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                ),
                color = if (isSelected) Color.White else TextTertiary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Footer: Educational tip & streak continuity
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Tap any day to inspect details",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = TextTertiary
        )

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Box(
            modifier = Modifier
              .size(6.dp)
              .clip(CircleShape)
              .background(TechGreen)
          )
          Text(
            text = "Active Week",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 11.sp
            ),
            color = TechGreen
          )
        }
      }
    }
  }
}

@Composable
private fun MetricTag(
  label: String,
  value: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(color.copy(alpha = 0.12f))
      .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
      .padding(horizontal = 8.dp, vertical = 7.dp)
  ) {
    Column {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = TextSecondary,
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 12.5.sp
        ),
        color = color,
        maxLines = 1
      )
    }
  }
}
