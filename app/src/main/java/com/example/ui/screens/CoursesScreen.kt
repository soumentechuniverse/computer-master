package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CourseLevel
import com.example.ui.components.CourseCard
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel
import com.example.util.AppStrings
import com.example.util.LocalAppLanguage

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
  val currentLanguage = LocalAppLanguage.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("courses_screen"),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // 1. Header with Title & Stats
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = AppStrings.courseLibrary(currentLanguage),
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
              ),
              color = TextPrimary
            )
            Text(
              text = "20 Complete Courses • Beginner to AI Master",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
              color = TextSecondary
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(TechBluePrimary.copy(alpha = 0.15f))
              .border(1.dp, TechBluePrimary.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text(
              text = "20 Courses",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              ),
              color = TechCyanAccent
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Instant Search Bar (Search by course name, description, or level)
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { viewModel.setSearchQuery(it) },
          placeholder = {
            Text(
              text = AppStrings.searchPlaceholder(currentLanguage),
              style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp)
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
                onClick = { viewModel.setSearchQuery("") },
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
          keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
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

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Filter Tabs: All, Beginner, Intermediate, Advanced
        LevelFilterRow(
          selectedLevel = selectedLevel,
          onLevelSelected = { viewModel.setSelectedLevel(it) }
        )
      }
    }

    // 4. Results Count & Level Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (selectedLevel == CourseLevel.ALL) "All Courses" else "${selectedLevel.label} Level",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
          ),
          color = TextPrimary
        )

        Text(
          text = "${courses.size} of 20 courses",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
          ),
          color = TechCyanAccent
        )
      }
    }

    // 5. Courses List or Empty State
    if (courses.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp, horizontal = 30.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(20.dp))
              .background(NavyCard)
              .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
              .padding(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = null,
              tint = TextTertiary,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
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
              text = "Try adjusting your search query or switching the level filter tab.",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
              color = TextSecondary,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
              onClick = {
                viewModel.setSearchQuery("")
                viewModel.setSelectedLevel(CourseLevel.ALL)
              },
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
            ) {
              Text("Reset Filters")
            }
          }
        }
      }
    } else {
      items(courses, key = { it.id }) { course ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
          CourseCard(
            course = course,
            onClick = { onNavigateToCourseDetail(course.id) },
            onBookmarkClick = { viewModel.toggleBookmark(course.id) }
          )
        }
      }
    }
  }
}

@Composable
private fun LevelFilterRow(
  selectedLevel: CourseLevel,
  onLevelSelected: (CourseLevel) -> Unit,
) {
  val currentLanguage = LocalAppLanguage.current

  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    items(CourseLevel.entries.toTypedArray()) { level ->
      val isSelected = selectedLevel == level
      val localizedLabel = when (level) {
        CourseLevel.ALL -> AppStrings.filterAll(currentLanguage)
        CourseLevel.BEGINNER -> AppStrings.filterBeginner(currentLanguage)
        CourseLevel.INTERMEDIATE -> AppStrings.filterIntermediate(currentLanguage)
        CourseLevel.ADVANCED -> AppStrings.filterAdvanced(currentLanguage)
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(
            if (isSelected) TechBluePrimary else NavyCard
          )
          .border(
            width = 1.dp,
            color = if (isSelected) TechBluePrimary else NavyCardBorder,
            shape = RoundedCornerShape(12.dp)
          )
          .clickable { onLevelSelected(level) }
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .testTag("filter_chip_${level.name.lowercase()}"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = localizedLabel,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 13.sp
          ),
          color = if (isSelected) Color.White else TextSecondary
        )
      }
    }
  }
}
