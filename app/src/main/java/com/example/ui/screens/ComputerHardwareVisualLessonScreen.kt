package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HardwareComponentInfo
import com.example.data.model.HardwareComponentType
import com.example.data.model.HardwareLessonRepository
import com.example.data.model.HardwareQuizData
import com.example.data.model.HardwareQuizQuestion
import com.example.ui.components.HardwareDataFlowDiagram
import com.example.ui.components.HardwarePortsDiagram
import com.example.ui.components.Interactive3DComputerIllustration
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
import com.example.ui.viewmodel.ComputerMasterViewModel
import com.example.util.AppLanguage
import com.example.util.LocalAppLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComputerHardwareVisualLessonScreen(
  viewModel: ComputerMasterViewModel,
  onBackClick: () -> Unit,
  onNavigateToLesson: (courseId: String, lessonId: String) -> Unit = { _, _ -> },
  modifier: Modifier = Modifier
) {
  val currentLanguage = LocalAppLanguage.current
  val allCourses by viewModel.allCourses.collectAsState()
  val isCompleted = remember(allCourses) {
    allCourses.find { it.id == "course_basics" }?.allLessons?.find { it.id == "cb_lesson_3" }?.isCompleted ?: false
  }
  val bookmarkedLessons by viewModel.bookmarkedLessons.collectAsState()
  val isBookmarked = bookmarkedLessons.contains("cb_lesson_3")

  // Current selected hardware component
  var selectedComponentType by remember { mutableStateOf(HardwareComponentType.MONITOR) }
  val activeComponentInfo = remember(selectedComponentType) {
    HardwareLessonRepository.getByType(selectedComponentType)
  }

  // Step-by-Step progress (0 = Monitor, 1 = CPU, 2 = Keyboard, 3 = Mouse)
  val stepOrder = listOf(
    HardwareComponentType.MONITOR,
    HardwareComponentType.CPU,
    HardwareComponentType.KEYBOARD,
    HardwareComponentType.MOUSE
  )
  val currentStepIndex = stepOrder.indexOf(selectedComponentType).coerceAtLeast(0)

  // Quiz state
  val quizAnswers = remember { mutableStateMapOf<Int, Int>() }
  var showQuizResults by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = when (currentLanguage) {
                AppLanguage.BENGALI -> "কম্পিউটার হার্ডওয়্যার (৩ডি অভিজ্ঞতা)"
                AppLanguage.HINDI -> "कंप्यूटर हार्डवेयर (3D अनुभव)"
                AppLanguage.ENGLISH -> "Computer Hardware (3D Visual)"
              },
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
              ),
              color = TextPrimary
            )
            Text(
              text = when (currentLanguage) {
                AppLanguage.BENGALI -> "কম্পিউটার বেসিকস • পাঠ ৩ (ভিজ্যুয়াল লার্নিং)"
                AppLanguage.HINDI -> "कंप्यूटर बेसिक्स • पाठ 3 (विज़ुअल लर्निंग)"
                AppLanguage.ENGLISH -> "Computer Basics • Lesson 3 (Interactive)"
              },
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = TechCyanAccent
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier.testTag("hardware_lesson_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = TextPrimary
            )
          }
        },
        actions = {
          // Bookmark Action
          IconButton(
            onClick = { viewModel.toggleLessonBookmark("cb_lesson_3") },
            modifier = Modifier.testTag("hardware_lesson_bookmark_button")
          ) {
            Icon(
              imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Bookmark",
              tint = if (isBookmarked) TechAmber else TextSecondary,
              modifier = Modifier.size(24.dp)
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = NavyDarkest,
          titleContentColor = TextPrimary
        )
      )
    }
  ) { innerPadding ->
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .background(NavyDarkest)
        .padding(innerPadding)
        .testTag("hardware_visual_lesson_content"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      // 1. LANGUAGE SELECTOR QUICK CHIPS
      item {
        HardwareLanguageSelectorBar(
          currentLanguage = currentLanguage,
          onSelectLanguage = { newLang -> viewModel.setLanguage(newLang) }
        )
      }

      // 2. LESSON HERO INTRO CARD
      item {
        HardwareHeroIntroCard(currentLanguage = currentLanguage)
      }

      // 3. INTERACTIVE 3D-STYLE COMPUTER ILLUSTRATION
      item {
        Interactive3DComputerIllustration(
          selectedComponent = selectedComponentType,
          onSelectComponent = { comp -> selectedComponentType = comp },
          currentLanguage = currentLanguage,
          modifier = Modifier.testTag("interactive_3d_computer_illustration")
        )
      }

      // 4. SELECTED COMPONENT DETAIL CARD
      item {
        HardwareComponentDetailCard(
          component = activeComponentInfo,
          currentLanguage = currentLanguage,
          modifier = Modifier.testTag("component_detail_card_${selectedComponentType.name.lowercase()}")
        )
      }

      // 5. LEARN STEP BY STEP SECTION
      item {
        LearnStepByStepSection(
          currentStepIndex = currentStepIndex,
          stepOrder = stepOrder,
          onStepSelect = { comp -> selectedComponentType = comp },
          onPrevStep = {
            if (currentStepIndex > 0) {
              selectedComponentType = stepOrder[currentStepIndex - 1]
            }
          },
          onNextStep = {
            if (currentStepIndex < stepOrder.size - 1) {
              selectedComponentType = stepOrder[currentStepIndex + 1]
            }
          },
          currentLanguage = currentLanguage
        )
      }

      // 6. VISUAL EDUCATIONAL DIAGRAMS
      item {
        HardwareDataFlowDiagram(
          currentLanguage = currentLanguage,
          modifier = Modifier.testTag("hardware_data_flow_diagram")
        )
      }

      item {
        HardwarePortsDiagram(
          currentLanguage = currentLanguage,
          modifier = Modifier.testTag("hardware_ports_diagram")
        )
      }

      // 7. QUICK QUIZ SECTION
      item {
        HardwareQuickQuizSection(
          questions = HardwareQuizData.questions,
          userAnswers = quizAnswers,
          onSelectAnswer = { qId, optIndex ->
            quizAnswers[qId] = optIndex
          },
          showResults = showQuizResults,
          onToggleResults = { showQuizResults = !showQuizResults },
          onResetQuiz = {
            quizAnswers.clear()
            showQuizResults = false
          },
          currentLanguage = currentLanguage
        )
      }

      // Next Visual Lesson Teaser
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0F2636))
            .border(1.dp, TechAmber.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .clickable { onNavigateToLesson("course_basics", "cb_lesson_6") }
            .padding(14.dp)
            .testTag("bridge_to_cpu_ram_rom_lesson")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = when (currentLanguage) {
                  AppLanguage.BENGALI -> "পরবর্তী ৩ডি পাঠ: সিপিইউ, র‍্যাম এবং রম"
                  AppLanguage.HINDI -> "अगला 3D पाठ: सीपीयू, रैम और रोम"
                  AppLanguage.ENGLISH -> "Next 3D Lesson: CPU, RAM & ROM"
                },
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = TechAmber
              )
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                text = when (currentLanguage) {
                  AppLanguage.BENGALI -> "সিস্টেম ইউনিটের ভেতরে প্রবেশ করে প্রসেসর, র‍্যাম স্টিক ও রম বায়োস চিপ দেখুন।"
                  AppLanguage.HINDI -> "सिस्टम यूनिट के अंदर जाकर प्रोसेसर, रैम और रोम बायोस चिप देखें।"
                  AppLanguage.ENGLISH -> "Step inside the system unit to explore processor cores, RAM stick & ROM BIOS chip."
                },
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TextSecondary
              )
            }
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = TechAmber,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }

      // 8. LESSON COMPLETION & BOTTOM NAVIGATION
      item {
        HardwareLessonFooterNavigation(
          isCompleted = isCompleted,
          onToggleCompletion = {
            viewModel.toggleLessonCompletion("course_basics", "cb_lesson_3")
          },
          onPrevLesson = {
            onNavigateToLesson("course_basics", "cb_lesson_2")
          },
          onNextLesson = {
            onNavigateToLesson("course_basics", "cb_lesson_4")
          },
          currentLanguage = currentLanguage
        )
      }

      item {
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

// -----------------------------------------------------------------------------
// 1. LANGUAGE SELECTOR QUICK CHIPS
// -----------------------------------------------------------------------------
@Composable
private fun HardwareLanguageSelectorBar(
  currentLanguage: AppLanguage,
  onSelectLanguage: (AppLanguage) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
      .padding(horizontal = 12.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Translate,
        contentDescription = null,
        tint = TechCyanAccent,
        modifier = Modifier.size(16.dp)
      )
      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "ভাষা পরিবর্তন:"
          AppLanguage.HINDI -> "भाषा बदलें:"
          AppLanguage.ENGLISH -> "Language:"
        },
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp
        ),
        color = TextSecondary
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
      AppLanguage.entries.forEach { lang ->
        val isSelected = lang == currentLanguage
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) TechCyanAccent else NavyCard)
            .border(
              width = 1.dp,
              color = if (isSelected) TechCyanAccent else NavyCardBorder,
              shape = RoundedCornerShape(8.dp)
            )
            .clickable { onSelectLanguage(lang) }
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .testTag("lang_chip_${lang.code}")
        ) {
          Text(
            text = lang.nativeName,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
              fontSize = 11.sp
            ),
            color = if (isSelected) NavyDarkest else TextPrimary
          )
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// 2. HERO INTRO CARD
// -----------------------------------------------------------------------------
@Composable
private fun HardwareHeroIntroCard(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(
        brush = Brush.horizontalGradient(
          listOf(
            Color(0xFF0F2B48),
            Color(0xFF0A192F)
          )
        )
      )
      .border(
        width = 1.dp,
        brush = Brush.horizontalGradient(
          listOf(
            TechCyanAccent.copy(alpha = 0.5f),
            TechBluePrimary.copy(alpha = 0.3f)
          )
        ),
        shape = RoundedCornerShape(20.dp)
      )
      .padding(18.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(TechCyanAccent.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "ভিজ্যুয়াল শিক্ষণ অভিজ্ঞতা"
              AppLanguage.HINDI -> "विज़ुअल लर्निंग अनुभव"
              AppLanguage.ENGLISH -> "VISUAL INTERACTIVE LESSON"
            },
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp,
              letterSpacing = 0.8.sp
            ),
            color = TechCyanAccent
          )
        }

        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "৪টি মূল যন্ত্রাংশ"
            AppLanguage.HINDI -> "4 मुख्य घटक"
            AppLanguage.ENGLISH -> "4 Core Parts"
          },
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
          color = TextSecondary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "কম্পিউটার হার্ডওয়্যার পরিচিতি"
          AppLanguage.HINDI -> "कंप्यूटर हार्डवेयर का परिचय"
          AppLanguage.ENGLISH -> "Meet Your Computer Hardware"
        },
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.ExtraBold,
          fontSize = 20.sp
        ),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "কম্পিউটারের সমস্ত দৃশ্যমান ও স্পর্শযোগ্য যন্ত্রাংশকে হার্ডওয়্যার বলা হয়। নিচে ৩ডি ডেস্কটপ মডেল স্পর্শ করে প্রতিটি অংশের কাজ ও বাস্তব জীবনের উদাহরণ জানুন।"
          AppLanguage.HINDI -> "कंप्यूटर के सभी दृश्यमान और भौतिक घटकों को हार्डवेयर कहा जाता है। नीचे 3D मॉडल पर टैप करके प्रत्येक भाग के कार्य और वास्तविक उदाहरण समझें।"
          AppLanguage.ENGLISH -> "Hardware encompasses every tangible physical piece of a computer. Tap components on the 3D desktop model below to discover what each part does and how they work together."
        },
        style = MaterialTheme.typography.bodyMedium.copy(
          fontSize = 13.sp,
          lineHeight = 19.sp
        ),
        color = TextSecondary
      )
    }
  }
}

// -----------------------------------------------------------------------------
// 4. COMPONENT DETAIL CARD
// -----------------------------------------------------------------------------
@Composable
private fun HardwareComponentDetailCard(
  component: HardwareComponentInfo,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  val (accentColor, icon) = when (component.type) {
    HardwareComponentType.MONITOR -> TechCyanAccent to Icons.Default.Tv
    HardwareComponentType.CPU -> TechBluePrimary to Icons.Default.DeveloperBoard
    HardwareComponentType.KEYBOARD -> TechGreen to Icons.Default.Keyboard
    HardwareComponentType.MOUSE -> TechPurple to Icons.Default.Mouse
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.5.dp, accentColor.copy(alpha = 0.7f), RoundedCornerShape(20.dp))
      .padding(18.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Top Row: Icon + Name + Category Badge
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(accentColor.copy(alpha = 0.2f))
          .border(1.dp, accentColor.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(28.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(accentColor.copy(alpha = 0.2f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = component.getCategory(currentLanguage),
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            ),
            color = accentColor
          )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = component.getName(currentLanguage),
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.sp
          ),
          color = TextPrimary
        )
      }
    }

    // 1. WHAT IT IS
    DetailBlock(
      icon = Icons.Default.Info,
      iconColor = accentColor,
      title = when (currentLanguage) {
        AppLanguage.BENGALI -> "এটি আসলে কী? (What it is)"
        AppLanguage.HINDI -> "यह वास्तव में क्या है? (What it is)"
        AppLanguage.ENGLISH -> "What it is"
      },
      content = component.getRole(currentLanguage)
    )

    // 2. WHAT IT DOES
    DetailBlock(
      icon = Icons.Default.Speed,
      iconColor = TechBluePrimary,
      title = when (currentLanguage) {
        AppLanguage.BENGALI -> "এটি কী কাজ করে? (What it does)"
        AppLanguage.HINDI -> "यह क्या काम करता है? (What it does)"
        AppLanguage.ENGLISH -> "What it does"
      },
      content = component.getWhatItDoes(currentLanguage)
    )

    // 3. REAL-LIFE EXAMPLE
    DetailBlock(
      icon = Icons.Default.Lightbulb,
      iconColor = TechAmber,
      title = when (currentLanguage) {
        AppLanguage.BENGALI -> "বাস্তব জীবনের সহজ উদাহরণ (Real-Life Example)"
        AppLanguage.HINDI -> "वास्तविक जीवन का सरल उदाहरण (Real-Life Example)"
        AppLanguage.ENGLISH -> "Simple Real-Life Analogy"
      },
      content = component.getRealWorldExample(currentLanguage)
    )

    // 4. FUN FACT & CONNECTION
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(NavyCard)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
        .padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.Top
    ) {
      Icon(
        imageVector = Icons.Default.Cable,
        contentDescription = null,
        tint = accentColor,
        modifier = Modifier.size(18.dp)
      )
      Column {
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "সংযোগ পোর্ট:"
            AppLanguage.HINDI -> "कनेक्शन पोर्ट:"
            AppLanguage.ENGLISH -> "Connection Type:"
          },
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = accentColor
        )
        Text(
          text = component.getConnectionPort(currentLanguage),
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = TextSecondary
        )
      }
    }

    // Fun fact pill
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(TechAmber.copy(alpha = 0.12f))
        .border(1.dp, TechAmber.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
        .padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.Top
    ) {
      Icon(
        imageVector = Icons.Default.EmojiObjects,
        contentDescription = null,
        tint = TechAmber,
        modifier = Modifier.size(18.dp)
      )
      Column {
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "মজার তথ্য (Fun Fact):"
            AppLanguage.HINDI -> "रोचक तथ्य (Fun Fact):"
            AppLanguage.ENGLISH -> "Did you know?"
          },
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = TechAmber
        )
        Text(
          text = component.getFunFact(currentLanguage),
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = TextPrimary
        )
      }
    }
  }
}

@Composable
private fun DetailBlock(
  icon: ImageVector,
  iconColor: Color,
  title: String,
  content: String
) {
  Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = iconColor,
        modifier = Modifier.size(16.dp)
      )
      Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        ),
        color = iconColor
      )
    }
    Text(
      text = content,
      style = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 13.sp,
        lineHeight = 19.sp
      ),
      color = TextPrimary,
      modifier = Modifier.padding(start = 22.dp)
    )
  }
}

// -----------------------------------------------------------------------------
// 5. LEARN STEP BY STEP SECTION
// -----------------------------------------------------------------------------
@Composable
private fun LearnStepByStepSection(
  currentStepIndex: Int,
  stepOrder: List<HardwareComponentType>,
  onStepSelect: (HardwareComponentType) -> Unit,
  onPrevStep: () -> Unit,
  onNextStep: () -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.MenuBook,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "ধাপে ধাপে শিখুন"
            AppLanguage.HINDI -> "चरणबद्ध तरीके से सीखें"
            AppLanguage.ENGLISH -> "LEARN STEP BY STEP"
          },
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          color = TechCyanAccent
        )
      }

      Text(
        text = "${currentStepIndex + 1} / ${stepOrder.size}",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        ),
        color = TechCyanAccent
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Step Progress Bar
    LinearProgressIndicator(
      progress = { (currentStepIndex + 1) / stepOrder.size.toFloat() },
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(RoundedCornerShape(3.dp)),
      color = TechCyanAccent,
      trackColor = NavyCard
    )

    Spacer(modifier = Modifier.height(14.dp))

    // 4 Steps Interactive Chips Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      stepOrder.forEachIndexed { index, compType ->
        val isCurrent = index == currentStepIndex
        val isPassed = index < currentStepIndex
        val title = when (compType) {
          HardwareComponentType.MONITOR -> when (currentLanguage) { AppLanguage.BENGALI -> "১. মনিটর"; AppLanguage.HINDI -> "1. मॉनिटर"; else -> "1. Monitor" }
          HardwareComponentType.CPU -> when (currentLanguage) { AppLanguage.BENGALI -> "২. সিপিইউ"; AppLanguage.HINDI -> "2. सीपीयू"; else -> "2. CPU" }
          HardwareComponentType.KEYBOARD -> when (currentLanguage) { AppLanguage.BENGALI -> "৩. কীবোর্ড"; AppLanguage.HINDI -> "3. कीबोर्ड"; else -> "3. Keyboard" }
          HardwareComponentType.MOUSE -> when (currentLanguage) { AppLanguage.BENGALI -> "৪. মাউস"; AppLanguage.HINDI -> "4. माउस"; else -> "4. Mouse" }
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(
              if (isCurrent) TechCyanAccent.copy(alpha = 0.25f)
              else if (isPassed) TechGreen.copy(alpha = 0.15f)
              else NavyCard
            )
            .border(
              width = if (isCurrent) 1.5.dp else 1.dp,
              color = if (isCurrent) TechCyanAccent
              else if (isPassed) TechGreen.copy(alpha = 0.5f)
              else NavyCardBorder,
              shape = RoundedCornerShape(10.dp)
            )
            .clickable { onStepSelect(compType) }
            .padding(vertical = 8.dp, horizontal = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
              fontSize = 10.sp
            ),
            color = if (isCurrent) TechCyanAccent else if (isPassed) TechGreen else TextSecondary,
            maxLines = 1
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Step Previous / Next navigation buttons
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedButton(
        onClick = onPrevStep,
        enabled = currentStepIndex > 0,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = TextPrimary,
          disabledContentColor = TextTertiary
        )
      ) {
        Icon(
          imageVector = Icons.Default.NavigateBefore,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "পূর্ববর্তী ধাপ"
            AppLanguage.HINDI -> "पिछला चरण"
            AppLanguage.ENGLISH -> "Previous Step"
          },
          style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp)
        )
      }

      Button(
        onClick = onNextStep,
        enabled = currentStepIndex < stepOrder.size - 1,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = TechCyanAccent,
          contentColor = NavyDarkest,
          disabledContainerColor = NavyCard,
          disabledContentColor = TextTertiary
        )
      ) {
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "পরবর্তী ধাপ"
            AppLanguage.HINDI -> "अगला चरण"
            AppLanguage.ENGLISH -> "Next Step"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
          imageVector = Icons.Default.NavigateNext,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// 7. QUICK QUIZ SECTION
// -----------------------------------------------------------------------------
@Composable
private fun HardwareQuickQuizSection(
  questions: List<HardwareQuizQuestion>,
  userAnswers: Map<Int, Int>,
  onSelectAnswer: (questionId: Int, optionIndex: Int) -> Unit,
  showResults: Boolean,
  onToggleResults: () -> Unit,
  onResetQuiz: () -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  val correctCount = questions.count { q -> userAnswers[q.id] == q.correctOptionIndex }
  val allAnswered = userAnswers.size == questions.size

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Quiz,
          contentDescription = null,
          tint = TechAmber,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "কুইক কুইজ (Quick Quiz)"
            AppLanguage.HINDI -> "त्वरित प्रश्नोत्तरी (Quick Quiz)"
            AppLanguage.ENGLISH -> "QUICK HARDWARE QUIZ"
          },
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          color = TechAmber
        )
      }

      Text(
        text = "${userAnswers.size}/${questions.size} answered",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
        color = TextSecondary
      )
    }

    Text(
      text = when (currentLanguage) {
        AppLanguage.BENGALI -> "পাঠটি কতটা বুঝতে পেরেছেন তা যাচাই করতে নিচের প্রশ্নগুলোর উত্তর দিন:"
        AppLanguage.HINDI -> "पाठ की अपनी समझ की जांच करने के लिए नीचे दिए गए प्रश्नों के उत्तर दें:"
        AppLanguage.ENGLISH -> "Test your hardware knowledge with these 4 quick questions:"
      },
      style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
      color = TextSecondary
    )

    // Questions list
    questions.forEachIndexed { qIndex, question ->
      QuizQuestionCard(
        questionNumber = qIndex + 1,
        question = question,
        selectedOption = userAnswers[question.id],
        onSelectOption = { optIndex -> onSelectAnswer(question.id, optIndex) },
        showResult = showResults,
        currentLanguage = currentLanguage
      )
    }

    // Results celebration banner when showResults is active
    if (showResults) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(if (correctCount >= 3) TechGreen.copy(alpha = 0.2f) else TechAmber.copy(alpha = 0.2f))
          .border(
            1.dp,
            if (correctCount >= 3) TechGreen else TechAmber,
            RoundedCornerShape(14.dp)
          )
          .padding(14.dp)
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "কুইজ ফলাফল: $correctCount / ${questions.size} সঠিক হয়েছে!"
              AppLanguage.HINDI -> "प्रश्नोत्तरी परिणाम: $correctCount / ${questions.size} सही हुए!"
              AppLanguage.ENGLISH -> "Quiz Score: $correctCount out of ${questions.size} Correct!"
            },
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            ),
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = if (correctCount == questions.size) {
              when (currentLanguage) {
                AppLanguage.BENGALI -> "চমৎকার! আপনি কম্পিউটার হার্ডওয়্যার সম্পূর্ণ আয়ত্ত করেছেন।"
                AppLanguage.HINDI -> "उत्कृष्ट! आपने कंप्यूटर हार्डवेयर में पूरी महारत हासिल कर ली है।"
                AppLanguage.ENGLISH -> "Perfect score! You've mastered computer hardware components."
              }
            } else {
              when (currentLanguage) {
                AppLanguage.BENGALI -> "ভালো চেষ্টা! উপরের ব্যাখ্যাগুলো আবার পড়ে সঠিক উত্তর দেখে নিন।"
                AppLanguage.HINDI -> "अच्छा प्रयास! स्पष्टीकरण देखें और अपनी समझ को मजबूत करें।"
                AppLanguage.ENGLISH -> "Good effort! Review the explanations above to solidify your learning."
              }
            },
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = TextSecondary,
            textAlign = TextAlign.Center
          )
        }
      }
    }

    // Action buttons: Check Answers / Retake
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      if (!showResults) {
        Button(
          onClick = onToggleResults,
          enabled = userAnswers.isNotEmpty(),
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = TechAmber,
            contentColor = NavyDarkest,
            disabledContainerColor = NavyCard,
            disabledContentColor = TextTertiary
          )
        ) {
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "উত্তর যাচাই করুন"
              AppLanguage.HINDI -> "उत्तर जांचें"
              AppLanguage.ENGLISH -> "Check My Answers"
            },
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
        }
      } else {
        OutlinedButton(
          onClick = onResetQuiz,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TechAmber)
        ) {
          Icon(
            imageVector = Icons.Default.RestartAlt,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "পুনরায় কুইজ দিন"
              AppLanguage.HINDI -> "फिर से प्रयास करें"
              AppLanguage.ENGLISH -> "Retake Quiz"
            },
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
    }
  }
}

@Composable
private fun QuizQuestionCard(
  questionNumber: Int,
  question: HardwareQuizQuestion,
  selectedOption: Int?,
  onSelectOption: (Int) -> Unit,
  showResult: Boolean,
  currentLanguage: AppLanguage
) {
  val options = question.getOptions(currentLanguage)

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(NavyCard)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(14.dp))
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Text(
      text = "$questionNumber. ${question.getQuestion(currentLanguage)}",
      style = MaterialTheme.typography.titleSmall.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 18.sp
      ),
      color = TextPrimary
    )

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
      options.forEachIndexed { optIndex, optionText ->
        val isSelected = selectedOption == optIndex
        val isCorrect = optIndex == question.correctOptionIndex

        val (optBg, optBorder, optColor) = when {
          showResult && isCorrect -> Triple(
            TechGreen.copy(alpha = 0.25f),
            TechGreen,
            TechGreen
          )
          showResult && isSelected && !isCorrect -> Triple(
            TechRed.copy(alpha = 0.25f),
            TechRed,
            TechRed
          )
          isSelected -> Triple(
            TechCyanAccent.copy(alpha = 0.2f),
            TechCyanAccent,
            TechCyanAccent
          )
          else -> Triple(
            NavyDark,
            NavyCardBorder,
            TextSecondary
          )
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(optBg)
            .border(1.dp, optBorder, RoundedCornerShape(10.dp))
            .clickable { onSelectOption(optIndex) }
            .padding(horizontal = 12.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(optBorder.copy(alpha = 0.3f)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = ('A' + optIndex).toString(),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                ),
                color = optColor
              )
            }

            Text(
              text = optionText,
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 12.sp
              ),
              color = if (isSelected) TextPrimary else TextSecondary
            )
          }

          if (showResult) {
            if (isCorrect) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Correct",
                tint = TechGreen,
                modifier = Modifier.size(16.dp)
              )
            } else if (isSelected) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Incorrect",
                tint = TechRed,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }

    // Explanation when results revealed
    if (showResult) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(TechBluePrimary.copy(alpha = 0.15f))
          .border(0.5.dp, TechBluePrimary.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
          .padding(8.dp)
      ) {
        Text(
          text = "💡 ${question.getExplanation(currentLanguage)}",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 16.sp),
          color = TextPrimary
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// 8. LESSON FOOTER NAVIGATION
// -----------------------------------------------------------------------------
@Composable
private fun HardwareLessonFooterNavigation(
  isCompleted: Boolean,
  onToggleCompletion: () -> Unit,
  onPrevLesson: () -> Unit,
  onNextLesson: () -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Mark Complete CTA
    Button(
      onClick = onToggleCompletion,
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("hardware_lesson_complete_button"),
      shape = RoundedCornerShape(14.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = if (isCompleted) TechGreen else TechBluePrimary
      )
    ) {
      Icon(
        imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.Check,
        contentDescription = null,
        modifier = Modifier.size(18.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = if (isCompleted) {
          when (currentLanguage) {
            AppLanguage.BENGALI -> "পাঠটি সম্পূর্ণ হয়েছে ✓"
            AppLanguage.HINDI -> "पाठ पूरा हुआ ✓"
            AppLanguage.ENGLISH -> "Lesson Completed ✓"
          }
        } else {
          when (currentLanguage) {
            AppLanguage.BENGALI -> "সম্পূর্ণ হিসেবে চিহ্নিত করুন"
            AppLanguage.HINDI -> "पूर्ण के रूप में चिह्नित करें"
            AppLanguage.ENGLISH -> "Mark Lesson as Completed"
          }
        },
        style = MaterialTheme.typography.labelLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp
        )
      )
    }

    // Previous / Next Lesson Navigation
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      OutlinedButton(
        onClick = onPrevLesson,
        modifier = Modifier
          .weight(1f)
          .height(44.dp)
          .testTag("hardware_prev_lesson_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "পূর্ববর্তী পাঠ"
            AppLanguage.HINDI -> "पिछला पाठ"
            AppLanguage.ENGLISH -> "Previous"
          },
          style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp)
        )
      }

      Button(
        onClick = onNextLesson,
        modifier = Modifier
          .weight(1f)
          .height(44.dp)
          .testTag("hardware_next_lesson_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = TechCyanAccent,
          contentColor = NavyDarkest
        )
      ) {
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "পরবর্তী পাঠ"
            AppLanguage.HINDI -> "अगला पाठ"
            AppLanguage.ENGLISH -> "Next Lesson"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}
