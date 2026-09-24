package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.CourseLevel
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
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel

/**
 * Professional 18-Course Library Screen.
 * Displays a responsive LazyVerticalGrid (2 columns on phones, 3-4 on larger displays).
 */
@Composable
fun CoursesScreen(
  viewModel: ComputerMasterViewModel,
  onNavigateToCourseDetail: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val courses by viewModel.filteredCourses.collectAsState()
  val selectedLevel by viewModel.selectedLevel.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val focusManager = LocalFocusManager.current

  BoxWithConstraints(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("courses_screen")
  ) {
    val columnCount = when {
      maxWidth >= 900.dp -> 4
      maxWidth >= 600.dp -> 3
      else -> 2
    }

    LazyVerticalGrid(
      columns = GridCells.Fixed(columnCount),
      modifier = Modifier
        .fillMaxSize()
        .testTag("courses_grid"),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // 1. TOP HEADER: Title, Search Field, and Difficulty Filter Chips
      item(span = { GridItemSpan(maxLineSpan) }) {
        CoursesHeader(
          searchQuery = searchQuery,
          onSearchQueryChange = { viewModel.setSearchQuery(it) },
          selectedLevel = selectedLevel,
          onLevelSelected = { viewModel.setSelectedLevel(it) },
          totalCoursesCount = courses.size,
          onClearFocus = { focusManager.clearFocus() }
        )
      }

      // 2. EMPTY STATE
      if (courses.isEmpty()) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          CoursesEmptyState(
            onResetFilters = {
              viewModel.setSearchQuery("")
              viewModel.setSelectedLevel(CourseLevel.ALL)
            }
          )
        }
      } else {
        // 3. RESPONSIVE COURSE GRID ITEMS
        itemsIndexed(courses, key = { _, course -> course.id }) { index, course ->
          CourseGridCard(
            course = course,
            index = index,
            onClick = { onNavigateToCourseDetail(course.id) },
            onBookmarkClick = { viewModel.toggleBookmark(course.id) }
          )
        }
      }
    }
  }
}

/**
 * Top section containing "Computer Courses" title, instant search bar,
 * and difficulty filter chips.
 */
@Composable
private fun CoursesHeader(
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  selectedLevel: CourseLevel,
  onLevelSelected: (CourseLevel) -> Unit,
  totalCoursesCount: Int,
  onClearFocus: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 6.dp)
  ) {
    // Top Bar: Title & Total Courses Badge
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Computer Courses",
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
          ),
          color = TextPrimary,
          modifier = Modifier.testTag("courses_screen_title")
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "18 Courses • Beginner to Advanced Knowledge",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
          color = TextSecondary
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(TechBluePrimary.copy(alpha = 0.15f))
          .border(1.dp, TechBluePrimary.copy(alpha = 0.45f), RoundedCornerShape(12.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        Text(
          text = "18 Courses",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          ),
          color = TechCyanAccent
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Search Field
    OutlinedTextField(
      value = searchQuery,
      onValueChange = onSearchQueryChange,
      placeholder = {
        Text(
          text = "Search 18 courses, topics, keywords...",
          style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
          color = TextTertiary
        )
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = "Search",
          tint = TechCyanAccent,
          modifier = Modifier.size(20.dp)
        )
      },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(
            onClick = { onSearchQueryChange("") },
            modifier = Modifier.size(28.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Clear search",
              tint = TextSecondary,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
      keyboardActions = KeyboardActions(onSearch = { onClearFocus() }),
      singleLine = true,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("courses_search_field"),
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = NavyCard,
        unfocusedContainerColor = NavyCard,
        focusedBorderColor = TechBluePrimary,
        unfocusedBorderColor = NavyCardBorder,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedPlaceholderColor = TextTertiary,
        unfocusedPlaceholderColor = TextTertiary
      )
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Difficulty Filter Chips: All, Beginner, Intermediate, Advanced
    DifficultyFilterChips(
      selectedLevel = selectedLevel,
      onLevelSelected = onLevelSelected
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Results Count Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (selectedLevel == CourseLevel.ALL) "All Courses" else "${selectedLevel.label} Level",
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 14.sp
        ),
        color = TextPrimary
      )

      Text(
        text = "Showing $totalCoursesCount of 18 courses",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Medium,
          fontSize = 11.sp
        ),
        color = TechCyanAccent
      )
    }
  }
}

/**
 * Difficulty Filter Chips Row (All, Beginner, Intermediate, Advanced).
 */
@Composable
private fun DifficultyFilterChips(
  selectedLevel: CourseLevel,
  onLevelSelected: (CourseLevel) -> Unit
) {
  val levels = listOf(
    CourseLevel.ALL,
    CourseLevel.BEGINNER,
    CourseLevel.INTERMEDIATE,
    CourseLevel.ADVANCED
  )

  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(levels) { level ->
      val isSelected = selectedLevel == level
      val chipTag = "filter_chip_${level.name.lowercase()}"

      Box(
        modifier = Modifier
          .testTag(chipTag)
          .clip(RoundedCornerShape(20.dp))
          .background(
            if (isSelected) {
              Brush.horizontalGradient(
                listOf(TechBluePrimary, Color(0xFF1D4ED8))
              )
            } else {
              Brush.horizontalGradient(
                listOf(NavyCard, NavyCardElevated)
              )
            }
          )
          .border(
            width = 1.dp,
            color = if (isSelected) TechCyanAccent.copy(alpha = 0.8f) else NavyCardBorder,
            shape = RoundedCornerShape(20.dp)
          )
          .clickable { onLevelSelected(level) }
          .padding(horizontal = 14.dp, vertical = 7.dp)
      ) {
        Text(
          text = level.label,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp
          ),
          color = if (isSelected) Color.White else TextSecondary
        )
      }
    }
  }
}

/**
 * Reusable Course Grid Card component.
 * Features rounded premium design, modern educational icon, level badge,
 * lesson count, progress indicator (where progress exists), and smooth entrance animation.
 */
@Composable
fun CourseGridCard(
  course: Course,
  index: Int,
  onClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isEntered by remember { mutableStateOf(false) }
  LaunchedEffect(Unit) {
    isEntered = true
  }

  val alphaAnim by animateFloatAsState(
    targetValue = if (isEntered) 1f else 0f,
    animationSpec = tween(durationMillis = 350, delayMillis = (index % 8) * 35),
    label = "cardAlpha_${course.id}"
  )
  val translationAnim by animateFloatAsState(
    targetValue = if (isEntered) 0f else 18f,
    animationSpec = tween(durationMillis = 350, delayMillis = (index % 8) * 35),
    label = "cardTranslation_${course.id}"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .graphicsLayer {
        alpha = alphaAnim
        translationY = translationAnim
      }
      .clip(RoundedCornerShape(18.dp))
      .clickable { onClick() }
      .testTag("course_card_${course.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = BorderStroke(
      1.dp,
      if (course.progressPercent > 0) TechCyanAccent.copy(alpha = 0.4f) else NavyCardBorder.copy(alpha = 0.65f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            listOf(
              NavyCardElevated.copy(alpha = 0.6f),
              NavyCard
            )
          )
        )
        .padding(12.dp)
    ) {
      // Top Row: Educational Icon + Difficulty Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        CourseIcon(
          iconName = course.iconName,
          size = 38.dp,
          iconSize = 22.dp
        )

        CourseLevelBadge(level = course.level)
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Course Title
      Text(
        text = course.title,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          lineHeight = 18.sp
        ),
        color = TextPrimary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(4.dp))

      // Short Beginner-friendly Description
      Text(
        text = course.description,
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 11.sp,
          lineHeight = 15.sp
        ),
        color = TextSecondary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Lesson Count & Bookmark Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.MenuBook,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (course.lessonCount > 0) "${course.lessonCount} Lessons" else "0 Lessons",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            ),
            color = TextTertiary
          )
        }

        IconButton(
          onClick = onBookmarkClick,
          modifier = Modifier.size(24.dp)
        ) {
          Icon(
            imageVector = if (course.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = if (course.isBookmarked) "Bookmarked" else "Bookmark",
            tint = if (course.isBookmarked) TechAmber else TextTertiary.copy(alpha = 0.5f),
            modifier = Modifier.size(16.dp)
          )
        }
      }

      // Progress Indicator (where progress exists!)
      if (course.progressPercent > 0) {
        Spacer(modifier = Modifier.height(6.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Progress",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TextTertiary
            )
            Text(
              text = "${course.progressPercent}%",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              ),
              color = TechCyanAccent
            )
          }
          Spacer(modifier = Modifier.height(3.dp))
          LinearProgressIndicator(
            progress = { course.progressPercent / 100f },
            modifier = Modifier
              .fillMaxWidth()
              .height(4.dp)
              .clip(RoundedCornerShape(2.dp)),
            color = TechBluePrimary,
            trackColor = NavyDark
          )
        }
      }
    }
  }
}

/**
 * Compact level badge for course cards.
 */
@Composable
private fun CourseLevelBadge(level: CourseLevel) {
  val (badgeBg, badgeBorder, badgeText) = when (level) {
    CourseLevel.BEGINNER -> Triple(
      TechGreen.copy(alpha = 0.15f),
      TechGreen.copy(alpha = 0.45f),
      TechGreen
    )
    CourseLevel.INTERMEDIATE -> Triple(
      TechAmber.copy(alpha = 0.15f),
      TechAmber.copy(alpha = 0.45f),
      TechAmber
    )
    CourseLevel.ADVANCED -> Triple(
      TechPurple.copy(alpha = 0.15f),
      TechPurple.copy(alpha = 0.45f),
      TechPurple
    )
    CourseLevel.ALL -> Triple(
      TechCyanAccent.copy(alpha = 0.15f),
      TechCyanAccent.copy(alpha = 0.45f),
      TechCyanAccent
    )
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(badgeBg)
      .border(1.dp, badgeBorder, RoundedCornerShape(8.dp))
      .padding(horizontal = 7.dp, vertical = 3.dp)
  ) {
    Text(
      text = level.label,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold
      ),
      color = badgeText
    )
  }
}

/**
 * Empty search or filter results container.
 */
@Composable
private fun CoursesEmptyState(
  onResetFilters: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 40.dp, horizontal = 16.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(NavyCard)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
        .padding(28.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = null,
        tint = TextTertiary,
        modifier = Modifier.size(44.dp)
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "No matching courses found",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        ),
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Try adjusting your search query or switching the difficulty filter.",
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        color = TextSecondary,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )
      Spacer(modifier = Modifier.height(16.dp))
      Button(
        onClick = onResetFilters,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
      ) {
        Text("Reset Filters")
      }
    }
  }
}
