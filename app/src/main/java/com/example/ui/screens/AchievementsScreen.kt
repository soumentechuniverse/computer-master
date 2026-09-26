package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Achievement
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
import com.example.ui.viewmodel.ComputerMasterViewModel

enum class AchievementFilter(val label: String) {
  ALL("All"),
  UNLOCKED("Unlocked"),
  LOCKED("In Progress")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
  viewModel: ComputerMasterViewModel,
  onBackClick: () -> Unit,
  onNavigateToCourse: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler(onBack = onBackClick)

  val achievements by viewModel.achievements.collectAsState()
  val userProfile by viewModel.userProfile.collectAsState()

  var selectedCategory by remember { mutableStateOf("All") }
  var selectedStatusFilter by remember { mutableStateOf(AchievementFilter.ALL) }
  var searchQuery by remember { mutableStateOf("") }
  var selectedAchievementForDetail by remember { mutableStateOf<Achievement?>(null) }

  // Extract unique categories
  val categories = remember(achievements) {
    listOf("All") + achievements.map { it.category }.distinct().sorted()
  }

  // Filtered achievements
  val filteredAchievements = remember(achievements, selectedCategory, selectedStatusFilter, searchQuery) {
    achievements.filter { ach ->
      val matchesCategory = selectedCategory == "All" || ach.category.equals(selectedCategory, ignoreCase = true)
      val matchesStatus = when (selectedStatusFilter) {
        AchievementFilter.ALL -> true
        AchievementFilter.UNLOCKED -> ach.isUnlocked
        AchievementFilter.LOCKED -> !ach.isUnlocked
      }
      val matchesQuery = searchQuery.isBlank() ||
          ach.title.contains(searchQuery, ignoreCase = true) ||
          ach.description.contains(searchQuery, ignoreCase = true) ||
          ach.category.contains(searchQuery, ignoreCase = true)
      matchesCategory && matchesStatus && matchesQuery
    }
  }

  val totalUnlocked = achievements.count { it.isUnlocked }
  val totalBadges = achievements.size
  val totalXpEarned = achievements.filter { it.isUnlocked }.sumOf { it.xpReward }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(NavyDarkest)
      .testTag("achievements_screen")
  ) {
    // 1. Top App Bar
    AchievementsTopBar(
      totalUnlocked = totalUnlocked,
      totalBadges = totalBadges,
      onBackClick = onBackClick
    )

    // 2. Search & Category Filter Section
    AchievementsFilterSection(
      categories = categories,
      selectedCategory = selectedCategory,
      onCategorySelected = { selectedCategory = it },
      selectedStatus = selectedStatusFilter,
      onStatusSelected = { selectedStatusFilter = it },
      searchQuery = searchQuery,
      onSearchQueryChanged = { searchQuery = it }
    )

    // 3. Grid of Badges
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      val columns = when {
        maxWidth >= 900.dp -> 4
        maxWidth >= 600.dp -> 3
        else -> 2
      }

      LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
          .fillMaxSize()
          .testTag("achievements_grid"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Hero Progress Header
        item(span = { GridItemSpan(maxLineSpan) }) {
          AchievementsHeroCard(
            totalUnlocked = totalUnlocked,
            totalBadges = totalBadges,
            totalXp = totalXpEarned,
            userLevel = userProfile.levelNumber,
            userTitle = userProfile.title
          )
        }

        if (filteredAchievements.isEmpty()) {
          item(span = { GridItemSpan(maxLineSpan) }) {
            AchievementsEmptyState(
              searchQuery = searchQuery,
              onClearFilters = {
                searchQuery = ""
                selectedCategory = "All"
                selectedStatusFilter = AchievementFilter.ALL
              }
            )
          }
        } else {
          items(filteredAchievements, key = { it.id }) { achievement ->
            BadgeCard(
              achievement = achievement,
              onClick = {
                selectedAchievementForDetail = achievement
              }
            )
          }
        }
      }
    }
  }

  // Detail Bottom Sheet
  selectedAchievementForDetail?.let { achievement ->
    BadgeDetailSheet(
      achievement = achievement,
      onDismiss = { selectedAchievementForDetail = null },
      onNavigateToCourse = { courseId ->
        selectedAchievementForDetail = null
        onNavigateToCourse(courseId)
      }
    )
  }
}

@Composable
private fun AchievementsTopBar(
  totalUnlocked: Int,
  totalBadges: Int,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(NavyDarkest)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      IconButton(
        onClick = onBackClick,
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, CircleShape)
          .testTag("achievements_back_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = TextPrimary,
          modifier = Modifier.size(20.dp)
        )
      }

      Column {
        Text(
          text = "Achievements & Badges",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          ),
          color = TextPrimary
        )
        Text(
          text = "Tracked from Room Database Progress",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
          color = TextSecondary
        )
      }
    }

    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(TechCyanAccent.copy(alpha = 0.15f))
        .border(1.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
        .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
      Text(
        text = "$totalUnlocked / $totalBadges",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        ),
        color = TechCyanAccent
      )
    }
  }
}

@Composable
private fun AchievementsHeroCard(
  totalUnlocked: Int,
  totalBadges: Int,
  totalXp: Int,
  userLevel: Int,
  userTitle: String,
  modifier: Modifier = Modifier
) {
  val progressPercent = if (totalBadges > 0) ((totalUnlocked.toFloat() / totalBadges) * 100).toInt() else 0
  val animatedProgress by animateFloatAsState(
    targetValue = (progressPercent / 100f).coerceIn(0f, 1f),
    animationSpec = tween(600),
    label = "hero_badge_progress"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 6.dp)
      .testTag("achievements_hero_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard),
    border = BorderStroke(1.dp, NavyCardBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.horizontalGradient(
            listOf(
              TechBluePrimary.copy(alpha = 0.18f),
              NavyCardElevated.copy(alpha = 0.85f),
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
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(TechAmber.copy(alpha = 0.18f))
              .border(1.5.dp, TechAmber, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.EmojiEvents,
              contentDescription = null,
              tint = TechAmber,
              modifier = Modifier.size(26.dp)
            )
          }

          Column {
            Text(
              text = "Badge Collection",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
              ),
              color = TextPrimary
            )
            Text(
              text = "Rank: Level $userLevel • $userTitle",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
              color = TextSecondary
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(TechGreen.copy(alpha = 0.15f))
            .border(1.dp, TechGreen.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(
            text = "+$totalXp XP",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            ),
            color = TechGreen
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Progress bar
      LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(7.dp)
          .clip(RoundedCornerShape(4.dp))
          .testTag("achievements_master_progress_bar"),
        color = TechCyanAccent,
        trackColor = NavyDarkest
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "$totalUnlocked of $totalBadges Badges Unlocked",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.5.sp),
          color = TextSecondary
        )
        Text(
          text = "$progressPercent% Completed",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 11.5.sp
          ),
          color = TechCyanAccent
        )
      }
    }
  }
}

@Composable
private fun AchievementsFilterSection(
  categories: List<String>,
  selectedCategory: String,
  onCategorySelected: (String) -> Unit,
  selectedStatus: AchievementFilter,
  onStatusSelected: (AchievementFilter) -> Unit,
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    // Search Field
    OutlinedTextField(
      value = searchQuery,
      onValueChange = onSearchQueryChanged,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("achievements_search_field"),
      placeholder = {
        Text(
          text = "Search badges (e.g. Programming, Lesson, Quiz)...",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp),
          color = TextTertiary
        )
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = "Search",
          tint = TechCyanAccent,
          modifier = Modifier.size(18.dp)
        )
      },
      trailingIcon = {
        if (searchQuery.isNotBlank()) {
          IconButton(onClick = { onSearchQueryChanged("") }) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Clear",
              tint = TextTertiary,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      },
      singleLine = true,
      shape = RoundedCornerShape(14.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = NavyCard,
        unfocusedContainerColor = NavyCard,
        focusedBorderColor = TechBluePrimary,
        unfocusedBorderColor = NavyCardBorder,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary
      )
    )

    // Status Filter Tabs: All, Unlocked, In Progress
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(NavyDark)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
        .padding(3.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      AchievementFilter.entries.forEach { status ->
        val isSelected = status == selectedStatus
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) TechBluePrimary else Color.Transparent)
            .clickable { onStatusSelected(status) }
            .padding(vertical = 6.dp)
            .testTag("filter_${status.name.lowercase()}"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = status.label,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.5.sp
            ),
            color = if (isSelected) Color.White else TextSecondary
          )
        }
      }
    }

    // Category Filter Chips
    LazyRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(vertical = 4.dp)
    ) {
      items(categories) { category ->
        val isSelected = category.equals(selectedCategory, ignoreCase = true)
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(if (isSelected) TechCyanAccent.copy(alpha = 0.2f) else NavyCard)
            .border(
              1.dp,
              if (isSelected) TechCyanAccent else NavyCardBorder,
              RoundedCornerShape(18.dp)
            )
            .clickable { onCategorySelected(category) }
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("category_chip_${category.lowercase()}"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = category,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.5.sp
            ),
            color = if (isSelected) TechCyanAccent else TextSecondary
          )
        }
      }
    }
  }
}

@Composable
private fun BadgeCard(
  achievement: Achievement,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isUnlocked = achievement.isUnlocked
  val rarityColor = when (achievement.rarity.lowercase()) {
    "legendary" -> TechAmber
    "epic" -> TechPurple
    "rare" -> TechCyanAccent
    else -> TechGreen
  }

  val iconVector = getBadgeIcon(achievement.iconName)

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable { onClick() }
      .testTag("badge_card_${achievement.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = if (isUnlocked) NavyCard else NavyCard.copy(alpha = 0.6f)),
    border = BorderStroke(
      width = if (isUnlocked) 1.5.dp else 1.dp,
      color = if (isUnlocked) rarityColor.copy(alpha = 0.6f) else NavyCardBorder.copy(alpha = 0.5f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          if (isUnlocked) {
            Brush.verticalGradient(
              listOf(
                rarityColor.copy(alpha = 0.12f),
                NavyCardElevated.copy(alpha = 0.5f),
                NavyCard
              )
            )
          } else {
            Brush.verticalGradient(
              listOf(
                NavyDark.copy(alpha = 0.6f),
                NavyCard
              )
            )
          }
        )
        .padding(12.dp)
    ) {
      // Top Row: Badge Icon + Rarity Tag / Status
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(if (isUnlocked) rarityColor.copy(alpha = 0.18f) else NavyDark)
            .border(
              width = if (isUnlocked) 1.5.dp else 1.dp,
              color = if (isUnlocked) rarityColor else NavyCardBorder,
              shape = CircleShape
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (isUnlocked) iconVector else Icons.Default.Lock,
            contentDescription = achievement.title,
            tint = if (isUnlocked) rarityColor else TextTertiary,
            modifier = Modifier.size(22.dp)
          )
        }

        Column(horizontalAlignment = Alignment.End) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(rarityColor.copy(alpha = 0.15f))
              .padding(horizontal = 5.dp, vertical = 2.dp)
          ) {
            Text(
              text = achievement.rarity,
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              ),
              color = rarityColor
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "+${achievement.xpReward} XP",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.5.sp,
              fontWeight = FontWeight.SemiBold
            ),
            color = TechGreen
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Title
      Text(
        text = achievement.title,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 13.5.sp
        ),
        color = if (isUnlocked) TextPrimary else TextSecondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(4.dp))

      // Description
      Text(
        text = achievement.description,
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 10.5.sp,
          lineHeight = 14.sp
        ),
        color = TextTertiary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Progress Tracker Bar or Unlocked Pill
      if (isUnlocked) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Unlocked",
            tint = TechGreen,
            modifier = Modifier.size(12.dp)
          )
          Text(
            text = achievement.unlockedDate?.let { "Unlocked $it" } ?: "Unlocked",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            ),
            color = TechGreen
          )
        }
      } else {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Room Progress",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
              color = TextTertiary
            )
            Text(
              text = "${achievement.currentProgress} / ${achievement.maxProgress}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold
              ),
              color = TechCyanAccent
            )
          }

          Spacer(modifier = Modifier.height(3.dp))

          LinearProgressIndicator(
            progress = { achievement.progressPercent / 100f },
            modifier = Modifier
              .fillMaxWidth()
              .height(4.dp)
              .clip(RoundedCornerShape(2.dp)),
            color = TechCyanAccent,
            trackColor = NavyDarkest
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BadgeDetailSheet(
  achievement: Achievement,
  onDismiss: () -> Unit,
  onNavigateToCourse: (String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val isUnlocked = achievement.isUnlocked
  val rarityColor = when (achievement.rarity.lowercase()) {
    "legendary" -> TechAmber
    "epic" -> TechPurple
    "rare" -> TechCyanAccent
    else -> TechGreen
  }
  val iconVector = getBadgeIcon(achievement.iconName)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = NavyDark,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 16.dp)
        .testTag("badge_detail_sheet"),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Large Badge Icon
      Box(
        modifier = Modifier
          .size(76.dp)
          .clip(CircleShape)
          .background(rarityColor.copy(alpha = 0.18f))
          .border(2.dp, rarityColor, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (isUnlocked) iconVector else Icons.Default.Lock,
          contentDescription = null,
          tint = if (isUnlocked) rarityColor else TextTertiary,
          modifier = Modifier.size(40.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = achievement.title,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        ),
        color = TextPrimary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(4.dp))

      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(rarityColor.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = achievement.rarity,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = rarityColor
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(TechGreen.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "+${achievement.xpReward} XP Reward",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = TechGreen
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(NavyCard)
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = achievement.category,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
            color = TextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = achievement.description,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
        color = TextSecondary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(18.dp))

      // Room Database Tracking Box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
          .padding(14.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = if (isUnlocked) "Status: Unlocked" else "Database Requirement",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = if (isUnlocked) TechGreen else TextPrimary
            )
            Text(
              text = if (isUnlocked) "✓ Complete" else "${achievement.currentProgress} / ${achievement.maxProgress}",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = if (isUnlocked) TechGreen else TechCyanAccent
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          LinearProgressIndicator(
            progress = { if (isUnlocked) 1f else (achievement.progressPercent / 100f) },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = if (isUnlocked) TechGreen else TechCyanAccent,
            trackColor = NavyDarkest
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = if (isUnlocked) {
              "You have unlocked this badge based on your verified progress stored in Room database."
            } else {
              "Complete the required lessons or quizzes to record completions in Room and unlock this badge."
            },
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
            color = TextTertiary
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Action Button
      if (achievement.relatedCourseId != null) {
        Button(
          onClick = { onNavigateToCourse(achievement.relatedCourseId) },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("badge_action_start_course"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
        ) {
          Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("Go to Related Course", fontWeight = FontWeight.Bold)
        }
      } else {
        Button(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
        ) {
          Text("Keep Learning", fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
private fun AchievementsEmptyState(
  searchQuery: String,
  onClearFilters: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(32.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(
        imageVector = Icons.Default.FilterList,
        contentDescription = null,
        tint = TextTertiary,
        modifier = Modifier.size(48.dp)
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = if (searchQuery.isNotBlank()) "No badges matching \"$searchQuery\"" else "No badges in this filter",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Try adjusting your filters or category selection.",
        style = MaterialTheme.typography.bodySmall,
        color = TextSecondary
      )
      Spacer(modifier = Modifier.height(14.dp))
      Button(
        onClick = onClearFilters,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary)
      ) {
        Text("Reset Filters")
      }
    }
  }
}

private fun getBadgeIcon(iconName: String): ImageVector {
  return when (iconName.lowercase()) {
    "code" -> Icons.Default.Code
    "cpu" -> Icons.Default.Memory
    "network" -> Icons.Default.Public
    "shield" -> Icons.Default.Security
    "database" -> Icons.Default.Storage
    "cloud" -> Icons.Default.Cloud
    "check_circle" -> Icons.Default.CheckCircle
    "graduation" -> Icons.Default.School
    "speed" -> Icons.Default.Speed
    "star" -> Icons.Default.Star
    "trophy" -> Icons.Default.EmojiEvents
    "quiz" -> Icons.Default.Quiz
    "flame" -> Icons.Default.LocalFireDepartment
    else -> Icons.Default.AutoAwesome
  }
}
