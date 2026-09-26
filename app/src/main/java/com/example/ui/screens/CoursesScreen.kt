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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
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
 * Features a dedicated search bar at the top for quickly finding courses by title or category,
 * quick category pills, level filters, and a responsive LazyVerticalGrid.
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

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("courses_screen")
  ) {
    // 1. PINNED TOP SEARCH & FILTER BAR
    CoursesTopSearchBar(
      searchQuery = searchQuery,
      onSearchQueryChange = { viewModel.setSearchQuery(it) },
      selectedLevel = selectedLevel,
      onLevelSelected = { viewModel.setSelectedLevel(it) },
      totalCoursesCount = courses.size,
      onClearFocus = { focusManager.clearFocus() },
      onResetFilters = {
        viewModel.setSearchQuery("")
        viewModel.setSelectedLevel(CourseLevel.ALL)
      }
    )

    // 2. COURSES CONTENT (Grid or Empty State)
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      val columnCount = when {
        maxWidth >= 900.dp -> 4
        maxWidth >= 600.dp -> 3
        else -> 2
      }

      if (courses.isEmpty()) {
        CoursesEmptyState(
          searchQuery = searchQuery,
          onResetFilters = {
            viewModel.setSearchQuery("")
            viewModel.setSelectedLevel(CourseLevel.ALL)
          }
        )
      } else {
        LazyVerticalGrid(
          columns = GridCells.Fixed(columnCount),
          modifier = Modifier
            .fillMaxSize()
            .testTag("courses_grid"),
          contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          if (searchQuery.isBlank() && selectedLevel == CourseLevel.ALL) {
            item(span = { GridItemSpan(maxLineSpan) }) {
              CourseProgressOverviewBanner(courses = courses)
            }
          }

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
}

/**
 * Prominent Search Bar and Filter System pinned at the top of CoursesScreen.
 * Supports instant search by Course Title or Category with real-time feedback.
 */
@Composable
private fun CoursesTopSearchBar(
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  selectedLevel: CourseLevel,
  onLevelSelected: (CourseLevel) -> Unit,
  totalCoursesCount: Int,
  onClearFocus: () -> Unit,
  onResetFilters: () -> Unit
) {
  val categories = remember {
    listOf(
      "All" to "",
      "Hardware" to "Hardware",
      "Operating Systems" to "Operating Systems",
      "Office" to "Office",
      "Programming" to "Programming",
      "Databases" to "Databases",
      "Networking" to "Networking",
      "Security" to "Security",
      "Cloud" to "Cloud",
      "AI" to "AI",
      "Troubleshooting" to "Troubleshooting"
    )
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(NavyDarkest)
      .padding(horizontal = 16.dp, vertical = 10.dp)
      .testTag("courses_search_bar")
  ) {
    // Top Bar: Screen Title & 18 Courses Badge
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
            fontSize = 22.sp
          ),
          color = TextPrimary,
          modifier = Modifier.testTag("courses_screen_title")
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "18 Courses • Beginner to Advanced Knowledge",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
          color = TextSecondary
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(TechBluePrimary.copy(alpha = 0.15f))
          .border(1.dp, TechBluePrimary.copy(alpha = 0.45f), RoundedCornerShape(12.dp))
          .padding(horizontal = 10.dp, vertical = 5.dp)
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

    Spacer(modifier = Modifier.height(10.dp))

    // Dedicated Search Input Field for Title or Category
    OutlinedTextField(
      value = searchQuery,
      onValueChange = onSearchQueryChange,
      placeholder = {
        Text(
          text = "Search by title or category (e.g. Python, Hardware, Office)...",
          style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
          color = TextTertiary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
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
            onClick = {
              onSearchQueryChange("")
              onClearFocus()
            },
            modifier = Modifier
              .size(28.dp)
              .testTag("courses_search_clear_button")
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
        focusedBorderColor = TechCyanAccent,
        unfocusedBorderColor = NavyCardBorder,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedPlaceholderColor = TextTertiary,
        unfocusedPlaceholderColor = TextTertiary
      )
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Quick Category Filter Pills Row
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("courses_category_chips")
    ) {
      items(categories) { (label, queryValue) ->
        val isSelected = if (queryValue.isEmpty()) {
          searchQuery.isBlank()
        } else {
          searchQuery.trim().equals(queryValue, ignoreCase = true)
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
              if (isSelected) {
                Brush.horizontalGradient(listOf(TechBluePrimary, Color(0xFF1D4ED8)))
              } else {
                Brush.horizontalGradient(listOf(NavyCard, NavyCardElevated))
              }
            )
            .border(
              width = 1.dp,
              color = if (isSelected) TechCyanAccent.copy(alpha = 0.85f) else NavyCardBorder,
              shape = RoundedCornerShape(14.dp)
            )
            .clickable {
              if (isSelected && queryValue.isNotEmpty()) {
                onSearchQueryChange("")
              } else {
                onSearchQueryChange(queryValue)
              }
            }
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .testTag("category_chip_${label.lowercase().replace(" ", "_")}")
        ) {
          Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.sp
            ),
            color = if (isSelected) Color.White else TextSecondary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Difficulty Level Filter Chips Row
    DifficultyFilterChips(
      selectedLevel = selectedLevel,
      onLevelSelected = onLevelSelected
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Results Count & Reset Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (searchQuery.isNotBlank()) {
          "Results for \"$searchQuery\" ($totalCoursesCount found)"
        } else if (selectedLevel != CourseLevel.ALL) {
          "${selectedLevel.label} Courses ($totalCoursesCount found)"
        } else {
          "All 18 Courses"
        },
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp
        ),
        color = TextPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(1f, fill = false)
      )

      if (searchQuery.isNotBlank() || selectedLevel != CourseLevel.ALL) {
        Text(
          text = "Clear All",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          ),
          color = TechCyanAccent,
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable { onResetFilters() }
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .testTag("courses_clear_all_filters")
        )
      } else {
        Text(
          text = "Showing $totalCoursesCount of 18",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp
          ),
          color = TechCyanAccent
        )
      }
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
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Text(
          text = level.label,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 11.5.sp
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
 * course category tag, lesson count, progress indicator, and smooth entrance animation.
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

      Spacer(modifier = Modifier.height(8.dp))

      // Category Pill / Tag
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(TechCyanAccent.copy(alpha = 0.12f))
          .border(0.5.dp, TechCyanAccent.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
          .padding(horizontal = 6.dp, vertical = 2.dp)
      ) {
        Text(
          text = course.courseCategory,
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Medium
          ),
          color = TechCyanAccent,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

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

      // Course Progress Tracker Bar (Always displayed for every course in the list)
      Spacer(modifier = Modifier.height(8.dp))
      val progressRatio = (course.progressPercent / 100f).coerceIn(0f, 1f)
      val animatedProgress by animateFloatAsState(
        targetValue = progressRatio,
        animationSpec = tween(durationMillis = 500),
        label = "course_progress_${course.id}"
      )
      val isFinished = course.progressPercent >= 100

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(NavyDark.copy(alpha = 0.5f))
          .border(
            width = 0.5.dp,
            color = when {
              isFinished -> TechGreen.copy(alpha = 0.45f)
              course.progressPercent > 0 -> TechCyanAccent.copy(alpha = 0.35f)
              else -> NavyCardBorder.copy(alpha = 0.5f)
            },
            shape = RoundedCornerShape(8.dp)
          )
          .padding(horizontal = 8.dp, vertical = 6.dp)
          .testTag("course_progress_tracker_${course.id}")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            if (isFinished) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Completed",
                tint = TechGreen,
                modifier = Modifier.size(11.dp)
              )
              Text(
                text = "Completed",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                ),
                color = TechGreen
              )
            } else if (course.progressPercent > 0) {
              Box(
                modifier = Modifier
                  .size(5.dp)
                  .clip(CircleShape)
                  .background(TechCyanAccent)
              )
              Text(
                text = "${course.completedLessonsCount}/${course.lessonCount} Done",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Medium
                ),
                color = TextSecondary
              )
            } else {
              Text(
                text = "0/${course.lessonCount} Lessons",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Normal
                ),
                color = TextTertiary
              )
            }
          }

          Text(
            text = "${course.progressPercent}%",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold
            ),
            color = when {
              isFinished -> TechGreen
              course.progressPercent > 0 -> TechCyanAccent
              else -> TextTertiary
            },
            modifier = Modifier.testTag("course_progress_percent_${course.id}")
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
          progress = { animatedProgress },
          modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp))
            .testTag("course_progress_bar_${course.id}"),
          color = when {
            isFinished -> TechGreen
            course.progressPercent > 0 -> TechBluePrimary
            else -> NavyCardBorder
          },
          trackColor = NavyDarkest
        )
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
  searchQuery: String = "",
  onResetFilters: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 32.dp, horizontal = 16.dp),
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
        tint = TechCyanAccent,
        modifier = Modifier.size(44.dp)
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = if (searchQuery.isNotBlank()) "No courses found for \"$searchQuery\"" else "No matching courses found",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        ),
        color = TextPrimary,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Try searching by another title (e.g. Word, Python, Hardware) or category (e.g. Office, Security, Operating Systems).",
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        color = TextSecondary,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )
      Spacer(modifier = Modifier.height(16.dp))
      Button(
        onClick = onResetFilters,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
        modifier = Modifier.testTag("courses_reset_filters_button")
      ) {
        Text("Clear Search & Filters")
      }
    }
  }
}

/**
 * Top Overview Banner displayed on the Course List screen.
 * Summarizes the learner's overall course progression, completed lessons,
 * and completion ratio stored and synchronized via Room.
 */
@Composable
private fun CourseProgressOverviewBanner(
  courses: List<Course>,
  modifier: Modifier = Modifier
) {
  val totalLessons = remember(courses) { courses.sumOf { it.allLessons.size.coerceAtLeast(it.lessonCount) } }
  val completedLessons = remember(courses) { courses.sumOf { it.completedLessonsCount } }
  val overallPercent = if (totalLessons > 0) ((completedLessons.toFloat() / totalLessons) * 100).toInt() else 0
  val startedCoursesCount = remember(courses) { courses.count { it.progressPercent > 0 } }
  val completedCoursesCount = remember(courses) { courses.count { it.progressPercent >= 100 } }

  val animatedOverallProgress by animateFloatAsState(
    targetValue = (overallPercent / 100f).coerceIn(0f, 1f),
    animationSpec = tween(durationMillis = 600),
    label = "overall_library_progress"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 6.dp)
      .testTag("course_library_progress_banner"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = BorderStroke(1.dp, NavyCardBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.horizontalGradient(
            listOf(
              TechBluePrimary.copy(alpha = 0.14f),
              NavyCardElevated.copy(alpha = 0.7f)
            )
          )
        )
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(30.dp)
              .clip(CircleShape)
              .background(TechCyanAccent.copy(alpha = 0.15f))
              .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.TrendingUp,
              contentDescription = null,
              tint = TechCyanAccent,
              modifier = Modifier.size(16.dp)
            )
          }

          Column {
            Text(
              text = "Overall Learning Progress",
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
              ),
              color = TextPrimary
            )
            Text(
              text = "$startedCoursesCount of ${courses.size} Courses Started • $completedCoursesCount Completed",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = TextSecondary
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(TechCyanAccent.copy(alpha = 0.15f))
            .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag("overall_library_progress_badge")
        ) {
          Text(
            text = "$overallPercent%",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            ),
            color = TechCyanAccent
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      LinearProgressIndicator(
        progress = { animatedOverallProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .testTag("overall_library_progress_bar"),
        color = TechCyanAccent,
        trackColor = NavyDarkest
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "$completedLessons of $totalLessons Lessons Completed (Room Persisted)",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
          color = TextTertiary
        )
        Text(
          text = if (overallPercent >= 100) "All Mastered!" else "${100 - overallPercent}% Remaining",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp, fontWeight = FontWeight.SemiBold),
          color = if (overallPercent >= 100) TechGreen else TechAmber
        )
      }
    }
  }
}
