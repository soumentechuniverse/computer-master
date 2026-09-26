package com.example.ui.components.charts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.MasteryCategory
import com.example.data.model.MasteryCategoryHelper
import com.example.data.model.MasteryOverview
import com.example.data.model.MasteryTier
import com.example.ui.components.CourseIcon
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Chart display modes for visualizing user mastery progress.
 */
enum class MasteryChartMode(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
  RADIAL_DONUT("Donut Rings", Icons.Default.DonutLarge),
  BENCHMARK_BARS("Benchmark Bars", Icons.Default.BarChart),
  RADAR_WEB("Radar Web", Icons.Default.Hub)
}

/**
 * Modern Compose-based Data Visualization Chart showing user completion percentages
 * across different mastery categories (Programming, Cybersecurity, Networking, Hardware, etc.).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MasteryCategoriesChart(
  courses: List<Course>,
  onNavigateToCourse: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val categories = remember(courses) {
    MasteryCategoryHelper.calculateMasteryCategories(courses)
  }

  val overview = remember(categories) {
    MasteryCategoryHelper.calculateOverview(categories)
  }

  var selectedChartMode by remember { mutableStateOf(MasteryChartMode.RADIAL_DONUT) }
  var selectedCategory by remember { mutableStateOf<MasteryCategory?>(null) }
  var selectedTierFilter by remember { mutableStateOf<MasteryTier?>(null) }

  val filteredCategories = remember(categories, selectedTierFilter) {
    if (selectedTierFilter == null) categories
    else categories.filter { it.masteryTier == selectedTierFilter }
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("mastery_chart_container"),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(
        listOf(TechCyanAccent.copy(alpha = 0.35f), NavyCardBorder, TechBluePrimary.copy(alpha = 0.25f))
      )
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            listOf(NavyCardElevated.copy(alpha = 0.9f), NavyCard)
          )
        )
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // 1. Chart Header with Overall Completion Percentage
      MasteryChartHeader(
        overview = overview,
        selectedMode = selectedChartMode,
        onModeSelected = { selectedChartMode = it }
      )

      // 2. Mastery Tier Quick Filters (All, Mastered, Proficient, Intermediate, Novice)
      MasteryTierFilterChips(
        overview = overview,
        selectedTier = selectedTierFilter,
        onTierSelected = { selectedTierFilter = if (selectedTierFilter == it) null else it }
      )

      // 3. Compose-based Canvas Chart Display
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(NavyDarkest)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
          .padding(12.dp)
      ) {
        when (selectedChartMode) {
          MasteryChartMode.RADIAL_DONUT -> {
            MasteryDonutRadialChart(
              categories = filteredCategories,
              selectedCategory = selectedCategory,
              onSelectCategory = { selectedCategory = if (selectedCategory == it) null else it },
              overallPercent = overview.overallCompletionPercent
            )
          }
          MasteryChartMode.BENCHMARK_BARS -> {
            MasteryBenchmarkBarChart(
              categories = filteredCategories,
              selectedCategory = selectedCategory,
              onSelectCategory = { selectedCategory = if (selectedCategory == it) null else it }
            )
          }
          MasteryChartMode.RADAR_WEB -> {
            MasteryRadarPolygonChart(
              categories = categories, // radar needs all vertices
              selectedCategory = selectedCategory,
              onSelectCategory = { selectedCategory = if (selectedCategory == it) null else it }
            )
          }
        }
      }

      // 4. Interactive Category Detail or Category Quick Grid
      AnimatedVisibility(
        visible = selectedCategory != null,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        selectedCategory?.let { category ->
          SelectedCategoryDetailCard(
            category = category,
            onDismiss = { selectedCategory = null },
            onNavigateToCourse = onNavigateToCourse
          )
        }
      }

      // 5. Category Mastery Cards Grid (compact breakdown showing completion % and tier)
      MasteryCategoriesList(
        categories = filteredCategories,
        selectedCategory = selectedCategory,
        onCategoryClick = { selectedCategory = if (selectedCategory == it) null else it },
        onNavigateToCourse = onNavigateToCourse
      )
    }
  }
}

/**
 * Header showing overall percentage badge and chart mode selector buttons.
 */
@Composable
private fun MasteryChartHeader(
  overview: MasteryOverview,
  selectedMode: MasteryChartMode,
  onModeSelected: (MasteryChartMode) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
            .size(38.dp)
            .clip(CircleShape)
            .background(TechCyanAccent.copy(alpha = 0.16f))
            .border(1.5.dp, TechCyanAccent.copy(alpha = 0.6f), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.TrendingUp,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(20.dp)
          )
        }

        Column {
          Text(
            text = "Mastery Categories Progress",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            ),
            color = TextPrimary
          )
          Text(
            text = "Completion percentage across key computing disciplines",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
            color = TextSecondary
          )
        }
      }

      // Overall percentage badge
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(TechBluePrimary.copy(alpha = 0.16f))
          .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        Text(
          text = "${overview.overallCompletionPercent}% Avg",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          ),
          color = TechCyanAccent
        )
      }
    }

    // Chart Mode Selector Tabs (Radial Donut, Benchmark Bars, Radar Web)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(NavyDarkest)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
        .padding(3.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      MasteryChartMode.entries.forEach { mode ->
        val isSelected = mode == selectedMode
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(9.dp))
            .background(if (isSelected) TechBluePrimary else Color.Transparent)
            .clickable { onModeSelected(mode) }
            .padding(vertical = 7.dp)
            .testTag(
              when (mode) {
                MasteryChartMode.RADIAL_DONUT -> "mastery_chart_mode_radial"
                MasteryChartMode.BENCHMARK_BARS -> "mastery_chart_mode_bar"
                MasteryChartMode.RADAR_WEB -> "mastery_chart_mode_radar"
              }
            ),
          contentAlignment = Alignment.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
          ) {
            Icon(
              imageVector = mode.icon,
              contentDescription = mode.label,
              tint = if (isSelected) Color.White else TextTertiary,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = mode.label,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp
              ),
              color = if (isSelected) Color.White else TextSecondary
            )
          }
        }
      }
    }
  }
}

/**
 * Filter chips for filtering categories by mastery tier (Mastered, Proficient, Intermediate, Novice).
 */
@Composable
private fun MasteryTierFilterChips(
  overview: MasteryOverview,
  selectedTier: MasteryTier?,
  onTierSelected: (MasteryTier) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyRow(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 2.dp)
  ) {
    items(MasteryTier.entries) { tier ->
      val isSelected = tier == selectedTier
      val count = when (tier) {
        MasteryTier.MASTERED -> overview.masteredCategoriesCount
        MasteryTier.PROFICIENT -> overview.proficientCategoriesCount
        MasteryTier.INTERMEDIATE -> overview.intermediateCategoriesCount
        MasteryTier.NOVICE -> overview.noviceCategoriesCount
        MasteryTier.UNSTARTED -> overview.unstartedCategoriesCount
      }

      val chipBg = if (isSelected) tier.color.copy(alpha = 0.25f) else NavyDark
      val chipBorder = if (isSelected) tier.color else NavyCardBorder

      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(chipBg)
          .border(1.dp, chipBorder, RoundedCornerShape(10.dp))
          .clickable { onTierSelected(tier) }
          .padding(horizontal = 10.dp, vertical = 5.dp)
          .testTag("mastery_tier_chip_${tier.name.lowercase()}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
      ) {
        Box(
          modifier = Modifier
            .size(7.dp)
            .clip(CircleShape)
            .background(tier.color)
        )
        Text(
          text = "${tier.label} ($count)",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 11.sp
          ),
          color = if (isSelected) tier.color else TextSecondary
        )
      }
    }
  }
}

/**
 * Compose Canvas Donut & Concentric Radial Chart.
 * Displays arcs for each category with completion percentages and interactive touch detection.
 */
@Composable
private fun MasteryDonutRadialChart(
  categories: List<MasteryCategory>,
  selectedCategory: MasteryCategory?,
  onSelectCategory: (MasteryCategory) -> Unit,
  overallPercent: Int,
  modifier: Modifier = Modifier
) {
  val animatedPercents = categories.map { cat ->
    animateFloatAsState(
      targetValue = cat.completionPercent.toFloat(),
      animationSpec = tween(durationMillis = 700),
      label = "arc_${cat.id}"
    )
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Box(
      modifier = Modifier
        .size(240.dp)
        .testTag("mastery_donut_canvas"),
      contentAlignment = Alignment.Center
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxSize()
          .pointerInput(categories) {
            detectTapGestures { tapOffset ->
              val center = Offset(size.width / 2f, size.height / 2f)
              val dx = tapOffset.x - center.x
              val dy = tapOffset.y - center.y
              val distance = kotlin.math.sqrt(dx * dx + dy * dy)
              val innerRadius = 50.dp.toPx()
              val outerRadius = 115.dp.toPx()

              if (distance in innerRadius..outerRadius && categories.isNotEmpty()) {
                // Calculate angle in degrees [0..360) starting from -90 (top)
                var angle = Math.toDegrees(kotlin.math.atan2(dy.toDouble(), dx.toDouble())).toFloat()
                angle = (angle + 90f + 360f) % 360f
                val sliceAngle = 360f / categories.size
                val index = (angle / sliceAngle).toInt().coerceIn(0, categories.size - 1)
                onSelectCategory(categories[index])
              }
            }
          }
      ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val outerRadius = size.minDimension / 2f - 12.dp.toPx()
        val strokeWidth = 20.dp.toPx()
        val arcRadius = outerRadius - strokeWidth / 2f

        if (categories.isEmpty()) return@Canvas

        val sliceAngle = 360f / categories.size
        val gapAngle = 4f

        categories.forEachIndexed { index, cat ->
          val isSelected = cat.id == selectedCategory?.id
          val startAngle = -90f + index * sliceAngle + gapAngle / 2f
          val availableAngle = sliceAngle - gapAngle

          val animatedPercent = animatedPercents[index].value
          val sweepAngle = (availableAngle * (animatedPercent / 100f)).coerceAtLeast(1.5f)

          // 1. Background Track Arc
          drawArc(
            color = cat.color.copy(alpha = 0.15f),
            startAngle = startAngle,
            sweepAngle = availableAngle,
            useCenter = false,
            topLeft = Offset(center.x - arcRadius, center.y - arcRadius),
            size = Size(arcRadius * 2f, arcRadius * 2f),
            style = Stroke(
              width = if (isSelected) strokeWidth + 4.dp.toPx() else strokeWidth,
              cap = StrokeCap.Round
            )
          )

          // 2. Active Progress Arc
          if (animatedPercent > 0) {
            drawArc(
              color = cat.color,
              startAngle = startAngle,
              sweepAngle = sweepAngle,
              useCenter = false,
              topLeft = Offset(center.x - arcRadius, center.y - arcRadius),
              size = Size(arcRadius * 2f, arcRadius * 2f),
              style = Stroke(
                width = if (isSelected) strokeWidth + 4.dp.toPx() else strokeWidth,
                cap = StrokeCap.Round
              )
            )
          }

          // 3. Highlight Glow indicator if selected
          if (isSelected) {
            drawArc(
              color = Color.White.copy(alpha = 0.6f),
              startAngle = startAngle,
              sweepAngle = sweepAngle,
              useCenter = false,
              topLeft = Offset(center.x - arcRadius, center.y - arcRadius),
              size = Size(arcRadius * 2f, arcRadius * 2f),
              style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
          }
        }
      }

      // Center Cutout Content
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
          .size(105.dp)
          .clip(CircleShape)
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, CircleShape)
          .padding(8.dp)
      ) {
        if (selectedCategory != null) {
          Text(
            text = selectedCategory.shortName,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
            color = selectedCategory.color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
          )
          Text(
            text = "${selectedCategory.completionPercent}%",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 22.sp
            ),
            color = TextPrimary
          )
          Text(
            text = selectedCategory.masteryTier.label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
            color = TextSecondary
          )
        } else {
          Text(
            text = "Overall",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = TextTertiary
          )
          Text(
            text = "$overallPercent%",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 24.sp
            ),
            color = TechCyanAccent
          )
          Text(
            text = "Mastery",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 10.sp
            ),
            color = TextSecondary
          )
        }
      }
    }

    Text(
      text = if (selectedCategory != null) "Tap segment again to clear selection" else "Tap any category ring segment to inspect",
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
      color = TextTertiary
    )
  }
}

/**
 * Compose Canvas Benchmark Bar Chart.
 * Compares completion percentages against Novice (25%), Intermediate (50%), Proficient (75%), and Mastered (100%) thresholds.
 */
@Composable
private fun MasteryBenchmarkBarChart(
  categories: List<MasteryCategory>,
  selectedCategory: MasteryCategory?,
  onSelectCategory: (MasteryCategory) -> Unit,
  modifier: Modifier = Modifier
) {
  val textMeasurer = rememberTextMeasurer()

  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("mastery_bar_canvas"),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    // Benchmark Threshold Legend Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Category Benchmarks",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
        color = TextSecondary
      )
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        BenchmarkIndicator(label = "25% Novice", color = TechAmber)
        BenchmarkIndicator(label = "50% Inter.", color = TechBluePrimary)
        BenchmarkIndicator(label = "75% Prof.", color = TechCyanAccent)
        BenchmarkIndicator(label = "100% Master", color = TechGreen)
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // Interactive Bars for each category
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      categories.forEach { cat ->
        val isSelected = cat.id == selectedCategory?.id
        val animatedProgress by animateFloatAsState(
          targetValue = cat.progressFraction,
          animationSpec = tween(durationMillis = 650),
          label = "bar_${cat.id}"
        )

        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) cat.color.copy(alpha = 0.12f) else NavyCard)
            .border(
              1.dp,
              if (isSelected) cat.color else NavyCardBorder.copy(alpha = 0.6f),
              RoundedCornerShape(10.dp)
            )
            .clickable { onSelectCategory(cat) }
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .testTag("mastery_category_item_${cat.shortName.lowercase()}")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(cat.color)
              )
              Text(
                text = cat.name,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 12.sp
                ),
                color = if (isSelected) cat.color else TextPrimary
              )
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = "${cat.completedLessons}/${cat.totalLessons} lessons",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                color = TextTertiary
              )

              // Percentage Badge with Tier Color
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(cat.color.copy(alpha = 0.18f))
                  .border(0.8.dp, cat.color.copy(alpha = 0.45f), RoundedCornerShape(6.dp))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "${cat.completionPercent}%",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                  ),
                  color = cat.color
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          // Canvas Bar with Benchmark Grid Lines
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(10.dp)
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height
              val cornerRadius = h / 2f

              // Background track
              drawRoundRect(
                color = NavyDarkest,
                size = Size(w, h),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius, cornerRadius)
              )

              // Active Filled Bar
              val filledW = (w * animatedProgress).coerceAtLeast(0f)
              if (filledW > 0) {
                drawRoundRect(
                  brush = Brush.horizontalGradient(listOf(cat.color.copy(alpha = 0.7f), cat.color)),
                  size = Size(filledW, h),
                  cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius, cornerRadius)
                )
              }

              // Benchmark Dotted Markers (25%, 50%, 75%)
              val benchmarkPoints = listOf(0.25f, 0.50f, 0.75f)
              benchmarkPoints.forEach { fraction ->
                val xPos = w * fraction
                drawLine(
                  color = Color.White.copy(alpha = 0.25f),
                  start = Offset(xPos, 0f),
                  end = Offset(xPos, h),
                  strokeWidth = 1.dp.toPx(),
                  pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f)
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun BenchmarkIndicator(label: String, color: Color) {
  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
    Box(
      modifier = Modifier
        .size(5.dp)
        .clip(CircleShape)
        .background(color)
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
      color = TextTertiary
    )
  }
}

/**
 * Compose Canvas Spider/Radar Web Chart.
 * Plots a multi-axis polygon showcasing mastery completion across all categories.
 */
@Composable
private fun MasteryRadarPolygonChart(
  categories: List<MasteryCategory>,
  selectedCategory: MasteryCategory?,
  onSelectCategory: (MasteryCategory) -> Unit,
  modifier: Modifier = Modifier
) {
  val textMeasurer = rememberTextMeasurer()
  val count = categories.size.coerceAtLeast(3)

  val animatedRatios = categories.map { cat ->
    animateFloatAsState(
      targetValue = cat.progressFraction,
      animationSpec = tween(durationMillis = 700),
      label = "radar_${cat.id}"
    )
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Box(
      modifier = Modifier
        .size(260.dp)
        .testTag("mastery_radar_canvas"),
      contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = size.minDimension / 2f - 24.dp.toPx()

        if (categories.isEmpty() || maxRadius <= 0) return@Canvas

        val angleStep = (2 * PI / count).toFloat()

        // 1. Concentric Web Rings (20%, 40%, 60%, 80%, 100%)
        val levels = listOf(0.2f, 0.4f, 0.6f, 0.8f, 1.0f)
        levels.forEach { level ->
          val levelPath = Path()
          for (i in 0 until count) {
            val angle = -PI.toFloat() / 2f + i * angleStep
            val r = maxRadius * level
            val x = center.x + r * cos(angle)
            val y = center.y + r * sin(angle)
            if (i == 0) levelPath.moveTo(x, y) else levelPath.lineTo(x, y)
          }
          levelPath.close()

          drawPath(
            path = levelPath,
            color = NavyCardBorder.copy(alpha = if (level == 1f) 0.8f else 0.4f),
            style = Stroke(
              width = if (level == 1f) 1.5.dp.toPx() else 1.dp.toPx(),
              pathEffect = if (level < 1f) PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f) else null
            )
          )
        }

        // 2. Radial Axis Lines and Category Labels
        for (i in 0 until count) {
          val angle = -PI.toFloat() / 2f + i * angleStep
          val endX = center.x + maxRadius * cos(angle)
          val endY = center.y + maxRadius * sin(angle)

          drawLine(
            color = NavyCardBorder.copy(alpha = 0.5f),
            start = center,
            end = Offset(endX, endY),
            strokeWidth = 1.dp.toPx()
          )

          // Draw Category Name & Percentage
          val cat = categories[i]
          val labelRadius = maxRadius + 14.dp.toPx()
          val labelX = center.x + labelRadius * cos(angle)
          val labelY = center.y + labelRadius * sin(angle)

          val textLayoutResult = textMeasurer.measure(
            text = "${cat.shortName}\n${cat.completionPercent}%",
            style = TextStyle(
              fontSize = 9.sp,
              color = if (cat.id == selectedCategory?.id) cat.color else TextSecondary,
              fontWeight = if (cat.id == selectedCategory?.id) FontWeight.Bold else FontWeight.Normal,
              textAlign = TextAlign.Center
            )
          )
          drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(labelX - textLayoutResult.size.width / 2f, labelY - textLayoutResult.size.height / 2f)
          )
        }

        // 3. User Progress Polygon Shape
        val userPath = Path()
        val points = mutableListOf<Offset>()

        for (i in 0 until count) {
          val angle = -PI.toFloat() / 2f + i * angleStep
          val ratio = animatedRatios[i].value.coerceIn(0.04f, 1f) // baseline visual dot
          val r = maxRadius * ratio
          val x = center.x + r * cos(angle)
          val y = center.y + r * sin(angle)
          val pt = Offset(x, y)
          points.add(pt)

          if (i == 0) userPath.moveTo(x, y) else userPath.lineTo(x, y)
        }
        userPath.close()

        // Fill Progress Polygon with Gradient
        drawPath(
          path = userPath,
          brush = Brush.radialGradient(
            colors = listOf(TechCyanAccent.copy(alpha = 0.45f), TechBluePrimary.copy(alpha = 0.2f)),
            center = center,
            radius = maxRadius
          ),
          style = Fill
        )

        // Draw Polygon Glowing Outline
        drawPath(
          path = userPath,
          color = TechCyanAccent,
          style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        // Draw Vertex Points
        points.forEachIndexed { i, pt ->
          val cat = categories[i]
          drawCircle(color = cat.color, radius = 4.dp.toPx(), center = pt)
          drawCircle(color = Color.White, radius = 1.5.dp.toPx(), center = pt)
        }
      }
    }

    Text(
      text = "Polygon expands outward as category completion percentage increases",
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
      color = TextTertiary
    )
  }
}

/**
 * Detailed inspector card shown when a category is tapped.
 */
@Composable
private fun SelectedCategoryDetailCard(
  category: MasteryCategory,
  onDismiss: () -> Unit,
  onNavigateToCourse: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = NavyDarkest),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(category.color, NavyCardBorder)))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
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
              .size(34.dp)
              .clip(CircleShape)
              .background(category.color.copy(alpha = 0.18f))
              .border(1.dp, category.color, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = null,
              tint = category.color,
              modifier = Modifier.size(18.dp)
            )
          }

          Column {
            Text(
              text = category.name,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
              color = TextPrimary
            )
            Text(
              text = category.masteryTier.badgeText,
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
              color = category.color
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(NavyCard)
            .clickable { onDismiss() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(text = "Close", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        }
      }

      Text(
        text = category.description,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
        color = TextSecondary
      )

      // Progress bar & metrics
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "${category.completedLessons} of ${category.totalLessons} Lessons Finished",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = TextSecondary
          )
          Text(
            text = "${category.completionPercent}%",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
            color = category.color
          )
        }

        LinearProgressIndicator(
          progress = { category.progressFraction },
          modifier = Modifier
            .fillMaxWidth()
            .height(7.dp)
            .clip(RoundedCornerShape(4.dp)),
          color = category.color,
          trackColor = NavyCard
        )
      }

      // Related Courses Chips
      if (category.relatedCourses.isNotEmpty()) {
        Text(
          text = "Included Courses (${category.relatedCourses.size}):",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.sp),
          color = TextTertiary
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          category.relatedCourses.take(2).forEach { course ->
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(NavyCard)
                .border(1.dp, NavyCardBorder, RoundedCornerShape(10.dp))
                .clickable { onNavigateToCourse(course.id) }
                .padding(8.dp)
            ) {
              Column {
                Text(
                  text = course.title,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                  color = TextPrimary,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
                Text(
                  text = "${course.progressPercent}% Done",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                  color = TechCyanAccent
                )
              }
            }
          }
        }
      }
    }
  }
}

/**
 * Compact list/grid of all mastery categories showing completion percentages and quick status.
 */
@Composable
private fun MasteryCategoriesList(
  categories: List<MasteryCategory>,
  selectedCategory: MasteryCategory?,
  onCategoryClick: (MasteryCategory) -> Unit,
  onNavigateToCourse: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = "All Mastery Categories (${categories.size})",
      style = MaterialTheme.typography.titleSmall.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 13.5.sp
      ),
      color = TextPrimary
    )

    categories.chunked(2).forEach { rowCategories ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        rowCategories.forEach { category ->
          val isSelected = category.id == selectedCategory?.id
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(14.dp))
              .background(if (isSelected) category.color.copy(alpha = 0.15f) else NavyDarkest)
              .border(
                1.dp,
                if (isSelected) category.color else NavyCardBorder,
                RoundedCornerShape(14.dp)
              )
              .clickable { onCategoryClick(category) }
              .padding(10.dp)
              .testTag("mastery_category_card_${category.shortName.lowercase()}")
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(5.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Box(
                    modifier = Modifier
                      .size(7.dp)
                      .clip(CircleShape)
                      .background(category.color)
                  )
                  Text(
                    text = category.shortName,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.5.sp
                    ),
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }

                Text(
                  text = "${category.completionPercent}%",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                  ),
                  color = category.color
                )
              }

              // Mini Progress Bar
              LinearProgressIndicator(
                progress = { category.progressFraction },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(4.dp)
                  .clip(RoundedCornerShape(2.dp)),
                color = category.color,
                trackColor = NavyCard
              )

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "${category.completedLessons}/${category.totalLessons} done",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                  color = TextTertiary
                )
                Text(
                  text = category.masteryTier.label,
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                  color = category.color
                )
              }
            }
          }
        }

        // Fill empty column if odd
        if (rowCategories.size == 1) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}
