package com.example.ui.components.charts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.DailyActivity
import com.example.data.model.DomainProgressHelper
import com.example.data.model.DomainStats
import com.example.data.model.DomainTimelinePoint
import com.example.data.model.LearningDomain
import com.example.data.model.TimeRange
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

/**
 * Modern Recharts-inspired Interactive Domain Learning Progress Dashboard.
 * Visualizes user progress across different domains (Programming, Cybersecurity, Networking, etc.) over time.
 */
@Composable
fun DomainProgressDashboard(
  courses: List<Course>,
  dailyActivities: List<DailyActivity>,
  onNavigateToCourse: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTimeRange by remember { mutableStateOf(TimeRange.SEVEN_DAYS) }
  var selectedDomainFilter by remember { mutableStateOf<LearningDomain?>(null) } // null = All Domains

  val domainStats = remember(courses) {
    DomainProgressHelper.calculateDomainStats(courses)
  }

  val timelinePoints = remember(selectedTimeRange, domainStats, dailyActivities) {
    DomainProgressHelper.generateTimelinePoints(selectedTimeRange, domainStats, dailyActivities)
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("domain_progress_dashboard"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Dashboard Header & TimeRange Selector
    DashboardHeaderCard(
      selectedTimeRange = selectedTimeRange,
      onTimeRangeSelected = { selectedTimeRange = it },
      domainStats = domainStats
    )

    // 2. Domain Filter Chips (All, Programming, Cybersecurity, Networking, etc.)
    DomainFilterBar(
      selectedDomain = selectedDomainFilter,
      onDomainSelected = { selectedDomainFilter = it }
    )

    // 3. Interactive Recharts-Style Canvas Area & Line Chart
    RechartsTimelineChartCard(
      timelinePoints = timelinePoints,
      selectedDomain = selectedDomainFilter,
      onDomainSelected = { selectedDomainFilter = it }
    )

    // 4. Compose-based Mastery Categories Chart (Donut Rings, Benchmark Bars, Radar Web)
    MasteryCategoriesChart(
      courses = courses,
      onNavigateToCourse = onNavigateToCourse
    )

    // 5. Comparative Domain Mastery Bar Breakdown
    DomainComparativeBarCard(
      domainStats = domainStats,
      selectedDomain = selectedDomainFilter,
      onDomainClick = { selectedDomainFilter = if (selectedDomainFilter == it) null else it }
    )

    // 5. Domain Breakdown Grid Cards
    DomainCardsGrid(
      domainStats = domainStats,
      courses = courses,
      onNavigateToCourse = onNavigateToCourse
    )
  }
}

/**
 * Top dashboard header showing overall domain metrics and time-range segmented buttons.
 */
@Composable
private fun DashboardHeaderCard(
  selectedTimeRange: TimeRange,
  onTimeRangeSelected: (TimeRange) -> Unit,
  domainStats: List<DomainStats>,
  modifier: Modifier = Modifier
) {
  val totalCompleted = remember(domainStats) { domainStats.sumOf { it.completedLessons } }
  val totalHours = remember(domainStats) { domainStats.sumOf { it.estimatedHoursSpent } }
  val avgProgress = remember(domainStats) {
    if (domainStats.isNotEmpty()) domainStats.map { it.progressPercent }.average().toInt() else 0
  }

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(TechBluePrimary.copy(alpha = 0.4f), NavyCardBorder)))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            listOf(
              NavyCardElevated.copy(alpha = 0.8f),
              NavyCard
            )
          )
        )
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(TechCyanAccent)
            )
            Text(
              text = "Domain Progress Analytics",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
              ),
              color = TextPrimary
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Track progression across core computing fields",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
            color = TextSecondary
          )
        }

        // Time Range Segmented Control
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(NavyDarkest)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
            .padding(2.dp),
          horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
          TimeRange.entries.forEach { range ->
            val isSelected = range == selectedTimeRange
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) TechBluePrimary else Color.Transparent)
                .clickable { onTimeRangeSelected(range) }
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .testTag("time_range_${range.label.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = range.label,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  fontSize = 11.sp
                ),
                color = if (isSelected) Color.White else TextTertiary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3 Summary Metric Counters
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        SummaryMiniCard(
          title = "Avg Mastery",
          value = "$avgProgress%",
          subtitle = "Across 6 Domains",
          accentColor = TechCyanAccent,
          modifier = Modifier.weight(1f)
        )
        SummaryMiniCard(
          title = "Completed",
          value = "$totalCompleted",
          subtitle = "Lessons Tracked",
          accentColor = TechGreen,
          modifier = Modifier.weight(1f)
        )
        SummaryMiniCard(
          title = "Study Time",
          value = "${(totalHours * 10).toInt() / 10.0}h",
          subtitle = "Time Invested",
          accentColor = TechPurple,
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
private fun SummaryMiniCard(
  title: String,
  value: String,
  subtitle: String,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(NavyDark.copy(alpha = 0.7f))
      .border(0.5.dp, NavyCardBorder, RoundedCornerShape(12.dp))
      .padding(10.dp)
  ) {
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = TextSecondary
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        ),
        color = accentColor
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
        color = TextTertiary
      )
    }
  }
}

/**
 * Filter bar to switch between "All Domains" and single domain focus.
 */
@Composable
private fun DomainFilterBar(
  selectedDomain: LearningDomain?,
  onDomainSelected: (LearningDomain?) -> Unit
) {
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 2.dp)
  ) {
    item {
      val isAll = selectedDomain == null
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(if (isAll) TechCyanAccent.copy(alpha = 0.18f) else NavyCard)
          .border(
            1.dp,
            if (isAll) TechCyanAccent else NavyCardBorder,
            RoundedCornerShape(20.dp)
          )
          .clickable { onDomainSelected(null) }
          .padding(horizontal = 12.dp, vertical = 6.dp)
          .testTag("filter_all_domains")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          Box(
            modifier = Modifier
              .size(7.dp)
              .clip(CircleShape)
              .background(if (isAll) TechCyanAccent else TextTertiary)
          )
          Text(
            text = "All Domains",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isAll) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.5.sp
            ),
            color = if (isAll) TechCyanAccent else TextSecondary
          )
        }
      }
    }

    items(LearningDomain.entries) { domain ->
      val isSelected = selectedDomain == domain
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(if (isSelected) domain.color.copy(alpha = 0.2f) else NavyCard)
          .border(
            1.dp,
            if (isSelected) domain.color else NavyCardBorder,
            RoundedCornerShape(20.dp)
          )
          .clickable { onDomainSelected(if (isSelected) null else domain) }
          .padding(horizontal = 12.dp, vertical = 6.dp)
          .testTag("filter_${domain.shortName.lowercase()}")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          Box(
            modifier = Modifier
              .size(7.dp)
              .clip(CircleShape)
              .background(domain.color)
          )
          Text(
            text = domain.shortName,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.5.sp
            ),
            color = if (isSelected) domain.color else TextSecondary
          )
        }
      }
    }
  }
}

/**
 * Recharts-style Interactive Timeline Canvas Chart.
 * Features smooth bezier curves, gradient area fill, grid lines, touch scrubber, and interactive tooltip.
 */
@Composable
private fun RechartsTimelineChartCard(
  timelinePoints: List<DomainTimelinePoint>,
  selectedDomain: LearningDomain?,
  onDomainSelected: (LearningDomain?) -> Unit,
  modifier: Modifier = Modifier
) {
  var activeHoverIndex by remember { mutableIntStateOf(-1) }
  val textMeasurer = rememberTextMeasurer()

  val domainsToPlot = remember(selectedDomain) {
    if (selectedDomain != null) {
      listOf(selectedDomain)
    } else {
      // Plot top 3 key requested domains for multi-series clarity
      listOf(
        LearningDomain.PROGRAMMING,
        LearningDomain.CYBERSECURITY,
        LearningDomain.NETWORKING
      )
    }
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("recharts_timeline_chart"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(NavyCardBorder.copy(alpha = 0.8f), NavyDark)))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Chart Title & Interaction hint
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = if (selectedDomain != null) "${selectedDomain.displayName} Progress" else "Progress Over Time (Recharts View)",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 14.5.sp
            ),
            color = TextPrimary
          )
          Text(
            text = "Tap or drag along chart to scrub data points",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = TextTertiary
          )
        }

        // Legend Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(NavyDarkest)
            .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
          Text(
            text = if (selectedDomain != null) "Single Domain" else "3 Domains Compared",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
            color = TechCyanAccent
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Custom Interactive Canvas Chart
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Canvas(
          modifier = Modifier
            .fillMaxSize()
            .pointerInput(timelinePoints) {
              detectTapGestures { offset ->
                val chartWidth = size.width - 40.dp.toPx()
                val startX = 35.dp.toPx()
                val relativeX = offset.x - startX
                if (relativeX >= 0 && timelinePoints.isNotEmpty()) {
                  val stepX = chartWidth / (timelinePoints.size - 1).coerceAtLeast(1)
                  val index = (relativeX / stepX + 0.5f).toInt().coerceIn(0, timelinePoints.size - 1)
                  activeHoverIndex = index
                }
              }
            }
            .pointerInput(timelinePoints) {
              detectDragGestures(
                onDragStart = { offset ->
                  val chartWidth = size.width - 40.dp.toPx()
                  val startX = 35.dp.toPx()
                  val relativeX = offset.x - startX
                  if (relativeX >= 0 && timelinePoints.isNotEmpty()) {
                    val stepX = chartWidth / (timelinePoints.size - 1).coerceAtLeast(1)
                    val index = (relativeX / stepX + 0.5f).toInt().coerceIn(0, timelinePoints.size - 1)
                    activeHoverIndex = index
                  }
                },
                onDrag = { change, _ ->
                  val chartWidth = size.width - 40.dp.toPx()
                  val startX = 35.dp.toPx()
                  val relativeX = change.position.x - startX
                  if (relativeX >= 0 && timelinePoints.isNotEmpty()) {
                    val stepX = chartWidth / (timelinePoints.size - 1).coerceAtLeast(1)
                    val index = (relativeX / stepX + 0.5f).toInt().coerceIn(0, timelinePoints.size - 1)
                    activeHoverIndex = index
                  }
                }
              )
            }
            .testTag("domain_chart_canvas")
        ) {
          val leftMargin = 32.dp.toPx()
          val bottomMargin = 22.dp.toPx()
          val topMargin = 10.dp.toPx()
          val rightMargin = 12.dp.toPx()

          val chartWidth = size.width - leftMargin - rightMargin
          val chartHeight = size.height - bottomMargin - topMargin

          if (chartWidth <= 0 || chartHeight <= 0 || timelinePoints.isEmpty()) return@Canvas

          // 1. Draw Horizontal Grid Lines & Y-axis labels
          val yLabels = listOf(0, 25, 50, 75, 100)
          yLabels.forEach { percent ->
            val yPos = topMargin + chartHeight * (1f - (percent / 100f))

            // Dotted grid line
            drawLine(
              color = Color(0xFF1E293B),
              start = Offset(leftMargin, yPos),
              end = Offset(size.width - rightMargin, yPos),
              strokeWidth = 1.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
            )

            // Y label text
            val textLayoutResult = textMeasurer.measure(
              text = "$percent%",
              style = TextStyle(fontSize = 9.sp, color = TextTertiary, fontWeight = FontWeight.Normal)
            )
            drawText(
              textLayoutResult = textLayoutResult,
              topLeft = Offset(0f, yPos - textLayoutResult.size.height / 2f)
            )
          }

          // 2. Draw X-axis date labels
          val stepX = chartWidth / (timelinePoints.size - 1).coerceAtLeast(1)
          timelinePoints.forEachIndexed { index, point ->
            val xPos = leftMargin + index * stepX
            val textLayoutResult = textMeasurer.measure(
              text = point.label,
              style = TextStyle(
                fontSize = 9.5.sp,
                color = if (index == activeHoverIndex) TechCyanAccent else TextTertiary,
                fontWeight = if (index == activeHoverIndex) FontWeight.Bold else FontWeight.Normal
              )
            )
            drawText(
              textLayoutResult = textLayoutResult,
              topLeft = Offset(xPos - textLayoutResult.size.width / 2f, size.height - bottomMargin + 4.dp.toPx())
            )
          }

          // 3. Draw Curves & Area Fill for each Domain
          domainsToPlot.forEach { domain ->
            val path = Path()
            val fillPath = Path()

            val points = timelinePoints.mapIndexed { index, point ->
              val progress = point.getProgressForDomain(domain).coerceIn(0, 100)
              val x = leftMargin + index * stepX
              val y = topMargin + chartHeight * (1f - (progress / 100f))
              Offset(x, y)
            }

            if (points.isNotEmpty()) {
              path.moveTo(points.first().x, points.first().y)
              fillPath.moveTo(points.first().x, topMargin + chartHeight)
              fillPath.lineTo(points.first().x, points.first().y)

              for (i in 0 until points.size - 1) {
                val current = points[i]
                val next = points[i + 1]
                val controlPoint1 = Offset((current.x + next.x) / 2f, current.y)
                val controlPoint2 = Offset((current.x + next.x) / 2f, next.y)

                path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, next.x, next.y)
                fillPath.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, next.x, next.y)
              }

              fillPath.lineTo(points.last().x, topMargin + chartHeight)
              fillPath.close()

              // Area gradient fill
              drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                  colors = listOf(
                    domain.color.copy(alpha = if (selectedDomain != null) 0.35f else 0.15f),
                    domain.color.copy(alpha = 0.01f)
                  ),
                  startY = topMargin,
                  endY = topMargin + chartHeight
                )
              )

              // Line stroke
              drawPath(
                path = path,
                color = domain.color,
                style = Stroke(
                  width = if (selectedDomain != null) 3.5.dp.toPx() else 2.5.dp.toPx(),
                  cap = StrokeCap.Round
                )
              )

              // Data points dots
              points.forEachIndexed { idx, pt ->
                val isHovered = idx == activeHoverIndex
                drawCircle(
                  color = domain.color,
                  radius = if (isHovered) 5.dp.toPx() else 3.dp.toPx(),
                  center = pt
                )
                drawCircle(
                  color = NavyDarkest,
                  radius = if (isHovered) 2.5.dp.toPx() else 1.5.dp.toPx(),
                  center = pt
                )
              }
            }
          }

          // 4. Draw Scrubber Reference Line & Tooltip Anchor if active
          if (activeHoverIndex in timelinePoints.indices) {
            val scrubberX = leftMargin + activeHoverIndex * stepX
            drawLine(
              color = TechCyanAccent.copy(alpha = 0.7f),
              start = Offset(scrubberX, topMargin),
              end = Offset(scrubberX, topMargin + chartHeight),
              strokeWidth = 1.5.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
            )
          }
        }

        // Floating Recharts-Style Interactive Tooltip
        if (activeHoverIndex in timelinePoints.indices) {
          val point = timelinePoints[activeHoverIndex]
          RechartsInteractiveTooltip(
            point = point,
            selectedDomain = selectedDomain,
            domainsToPlot = domainsToPlot,
            index = activeHoverIndex,
            totalPoints = timelinePoints.size,
            modifier = Modifier.align(Alignment.TopCenter)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Recharts-Style Interactive Legend
      RechartsChartLegend(
        domainsToPlot = domainsToPlot,
        selectedDomain = selectedDomain,
        onDomainSelected = onDomainSelected
      )
    }
  }
}

/**
 * Recharts-style hovering tooltip displaying values at the current scrubbed date.
 */
@Composable
private fun RechartsInteractiveTooltip(
  point: DomainTimelinePoint,
  selectedDomain: LearningDomain?,
  domainsToPlot: List<LearningDomain>,
  index: Int,
  totalPoints: Int,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .padding(top = 4.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(NavyDarkest.copy(alpha = 0.95f))
      .border(1.dp, TechCyanAccent.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
      .padding(horizontal = 10.dp, vertical = 6.dp)
      .testTag("recharts_tooltip")
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = "${point.label} (${point.shortDate})",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.5.sp),
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(2.dp))

      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        domainsToPlot.forEach { domain ->
          val progress = point.getProgressForDomain(domain)
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(domain.color)
            )
            Text(
              text = "${domain.shortName}: $progress%",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
              ),
              color = domain.color
            )
          }
        }
      }
    }
  }
}

/**
 * Interactive Legend mimicking Recharts `<Legend />`.
 */
@Composable
private fun RechartsChartLegend(
  domainsToPlot: List<LearningDomain>,
  selectedDomain: LearningDomain?,
  onDomainSelected: (LearningDomain?) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    domainsToPlot.forEach { domain ->
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .clickable {
            onDomainSelected(if (selectedDomain == domain) null else domain)
          }
          .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(domain.color)
        )
        Text(
          text = domain.shortName,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp
          ),
          color = TextSecondary
        )
      }
    }
  }
}

/**
 * Comparative multi-row Bar Chart comparing progress percentages across all domains.
 */
@Composable
private fun DomainComparativeBarCard(
  domainStats: List<DomainStats>,
  selectedDomain: LearningDomain?,
  onDomainClick: (LearningDomain) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyCardBorder, NavyDark)))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          Icon(Icons.Default.TrendingUp, contentDescription = null, tint = TechCyanAccent, modifier = Modifier.size(16.dp))
          Text(
            text = "Domain Mastery Distribution",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.5.sp),
            color = TextPrimary
          )
        }
        Text(
          text = "6 Core Domains",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
          color = TextTertiary
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Bars for each domain
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        domainStats.forEach { stat ->
          val isSelected = stat.domain == selectedDomain
          val animatedProgress by animateFloatAsState(
            targetValue = (stat.progressPercent / 100f).coerceIn(0f, 1f),
            animationSpec = tween(durationMillis = 600),
            label = "bar_progress_${stat.domain.id}"
          )

          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) stat.domain.color.copy(alpha = 0.08f) else Color.Transparent)
              .clickable { onDomainClick(stat.domain) }
              .padding(horizontal = 4.dp, vertical = 3.dp)
              .testTag("domain_bar_${stat.domain.shortName.lowercase()}")
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(stat.domain.color)
                )
                Text(
                  text = stat.domain.displayName,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                  ),
                  color = if (isSelected) stat.domain.color else TextPrimary
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                  text = "${stat.completedLessons}/${stat.totalLessons} Lessons",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                  color = TextTertiary
                )
                Text(
                  text = "${stat.progressPercent}%",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                  ),
                  color = stat.domain.color
                )
              }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Progress Bar
            LinearProgressIndicator(
              progress = { animatedProgress },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = stat.domain.color,
              trackColor = NavyDarkest
            )
          }
        }
      }
    }
  }
}

/**
 * Grid of deep-dive domain breakdown cards with circular progress indicators.
 */
@Composable
private fun DomainCardsGrid(
  domainStats: List<DomainStats>,
  courses: List<Course>,
  onNavigateToCourse: (String) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Text(
      text = "Domain Deep Dive",
      style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
      color = TextPrimary
    )

    domainStats.chunked(2).forEach { rowStats ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        rowStats.forEach { stat ->
          DomainSummaryCard(
            stat = stat,
            courses = courses,
            onNavigateToCourse = onNavigateToCourse,
            modifier = Modifier.weight(1f)
          )
        }
        if (rowStats.size == 1) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}

@Composable
private fun DomainSummaryCard(
  stat: DomainStats,
  courses: List<Course>,
  onNavigateToCourse: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val iconVector = when (stat.domain) {
    LearningDomain.PROGRAMMING -> Icons.Default.Code
    LearningDomain.CYBERSECURITY -> Icons.Default.Security
    LearningDomain.NETWORKING -> Icons.Default.Public
    LearningDomain.HARDWARE -> Icons.Default.Memory
    LearningDomain.CLOUD_AI -> Icons.Default.Cloud
    LearningDomain.DATABASES -> Icons.Default.Storage
  }

  val targetCourse = remember(courses, stat.domain) {
    courses.firstOrNull { stat.domain.primaryCourseIds.contains(it.id) }
  }

  Card(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .clickable {
        if (targetCourse != null) onNavigateToCourse(targetCourse.id)
      }
      .testTag("domain_card_${stat.domain.shortName.lowercase()}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(stat.domain.color.copy(alpha = 0.35f), NavyCardBorder)))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(stat.domain.color.copy(alpha = 0.15f))
            .border(1.dp, stat.domain.color.copy(alpha = 0.4f), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = iconVector,
            contentDescription = stat.domain.displayName,
            tint = stat.domain.color,
            modifier = Modifier.size(18.dp)
          )
        }

        Box(
          modifier = Modifier.size(36.dp),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator(
            progress = { stat.progressPercent / 100f },
            modifier = Modifier.fillMaxSize(),
            color = stat.domain.color,
            trackColor = NavyDarkest,
            strokeWidth = 3.5.dp
          )
          Text(
            text = "${stat.progressPercent}%",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.5.sp,
              fontWeight = FontWeight.Bold
            ),
            color = TextPrimary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = stat.domain.shortName,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
        color = TextPrimary,
        maxLines = 1
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = "${stat.completedLessons} of ${stat.totalLessons} Lessons",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${stat.estimatedHoursSpent} hrs",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
          color = TextTertiary
        )

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(stat.domain.color.copy(alpha = 0.12f))
            .padding(horizontal = 5.dp, vertical = 2.dp)
        ) {
          Text(
            text = stat.statusLabel,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            ),
            color = stat.domain.color
          )
        }
      }
    }
  }
}
