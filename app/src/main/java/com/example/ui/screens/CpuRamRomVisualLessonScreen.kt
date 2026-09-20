package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CpuInternalPart
import com.example.data.model.CpuRamRomLessonRepository
import com.example.data.model.RamPartType
import com.example.ui.components.CpuRamRomQuizSection
import com.example.ui.components.HardwareComparisonSection
import com.example.ui.components.InteractiveCpuIllustration
import com.example.ui.components.InteractiveRamIllustration
import com.example.ui.components.InteractiveRomIllustration
import com.example.ui.components.MemoryPipelineDiagram
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppLanguage

private enum class LessonTab {
  CPU,
  RAM,
  ROM,
  COMPARISON,
  DATA_FLOW,
  QUIZ
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CpuRamRomVisualLessonScreen(
  onNavigateBack: () -> Unit,
  onPreviousLesson: () -> Unit,
  onNextLesson: () -> Unit,
  initialLanguage: AppLanguage = AppLanguage.BENGALI,
  modifier: Modifier = Modifier
) {
  var currentLanguage by remember { mutableStateOf(initialLanguage) }
  var activeTab by remember { mutableStateOf(LessonTab.CPU) }
  var selectedCpuPart by remember { mutableStateOf(CpuInternalPart.CHIP_DIE) }
  var selectedRamPart by remember { mutableStateOf(RamPartType.RAM_STICK) }
  var isLessonCompleted by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier.testTag("cpu_ram_rom_lesson_screen"),
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = when (currentLanguage) {
                AppLanguage.BENGALI -> "সিপিইউ, র‍্যাম এবং রম"
                AppLanguage.HINDI -> "सीपीयू, रैम और रोम"
                AppLanguage.ENGLISH -> "Inside: CPU, RAM & ROM"
              },
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
              ),
              color = TextPrimary
            )
            Text(
              text = when (currentLanguage) {
                AppLanguage.BENGALI -> "কম্পিউটারের ভেতরে • ৩ডি পাঠ"
                AppLanguage.HINDI -> "कंप्यूटर के अंदर • 3D विज़ुअल पाठ"
                AppLanguage.ENGLISH -> "Inside the Computer • 3D Visual Lesson"
              },
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TechCyanAccent
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.testTag("back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = TextPrimary
            )
          }
        },
        actions = {
          LanguageSelectorRow(
            currentLanguage = currentLanguage,
            onLanguageSelected = { currentLanguage = it }
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = NavyDarkest,
          titleContentColor = TextPrimary
        )
      )
    },
    containerColor = NavyDarkest
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // 1. HERO BANNER
      item {
        LessonHeroBanner(currentLanguage = currentLanguage)
      }

      // 2. TAB SELECTOR BAR
      item {
        LessonTabBar(
          activeTab = activeTab,
          onSelectTab = { activeTab = it },
          currentLanguage = currentLanguage
        )
      }

      // 3. TAB CONTENT
      when (activeTab) {
        LessonTab.CPU -> {
          item {
            InteractiveCpuIllustration(
              selectedPart = selectedCpuPart,
              onSelectPart = { selectedCpuPart = it },
              currentLanguage = currentLanguage
            )
          }
          item {
            CpuDetailCard(
              partDetail = CpuRamRomLessonRepository.getCpuPart(selectedCpuPart),
              currentLanguage = currentLanguage
            )
          }
          item {
            CpuGhzExplanationCard(currentLanguage = currentLanguage)
          }
        }

        LessonTab.RAM -> {
          item {
            InteractiveRamIllustration(
              selectedPart = selectedRamPart,
              onSelectPart = { selectedRamPart = it },
              currentLanguage = currentLanguage
            )
          }
          item {
            RamDetailCard(
              ramPart = CpuRamRomLessonRepository.getRamPart(selectedRamPart),
              currentLanguage = currentLanguage
            )
          }
          item {
            RamCapacityGuideCard(currentLanguage = currentLanguage)
          }
        }

        LessonTab.ROM -> {
          item {
            InteractiveRomIllustration(currentLanguage = currentLanguage)
          }
          item {
            RomDetailExplanationCard(currentLanguage = currentLanguage)
          }
        }

        LessonTab.COMPARISON -> {
          item {
            HardwareComparisonSection(currentLanguage = currentLanguage)
          }
        }

        LessonTab.DATA_FLOW -> {
          item {
            MemoryPipelineDiagram(currentLanguage = currentLanguage)
          }
        }

        LessonTab.QUIZ -> {
          item {
            CpuRamRomQuizSection(currentLanguage = currentLanguage)
          }
        }
      }

      // 4. LESSON COMPLETION CHECKBOX / BUTTON
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isLessonCompleted) TechGreen.copy(alpha = 0.2f) else NavyDark)
            .border(
              1.dp,
              if (isLessonCompleted) TechGreen else NavyCardBorder,
              RoundedCornerShape(12.dp)
            )
            .clickable { isLessonCompleted = !isLessonCompleted }
            .padding(14.dp)
            .testTag("mark_lesson_completed_btn")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isLessonCompleted) TechGreen else NavyCard)
                .border(1.dp, if (isLessonCompleted) TechGreen else NavyCardBorder, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              if (isLessonCompleted) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = NavyDarkest,
                  modifier = Modifier.size(16.dp)
                )
              }
            }

            Column {
              Text(
                text = if (isLessonCompleted) {
                  when (currentLanguage) {
                    AppLanguage.BENGALI -> "পাঠটি সম্পন্ন হয়েছে! (Completed)"
                    AppLanguage.HINDI -> "पाठ पूरा हो गया! (Completed)"
                    AppLanguage.ENGLISH -> "Lesson Completed!"
                  }
                } else {
                  when (currentLanguage) {
                    AppLanguage.BENGALI -> "এই পাঠটি সম্পন্ন হিসেবে চিহ্নিত করুন"
                    AppLanguage.HINDI -> "इस पाठ को पूरा हुआ चिह्नित करें"
                    AppLanguage.ENGLISH -> "Mark this lesson as completed"
                  }
                },
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp
                ),
                color = if (isLessonCompleted) TechGreen else TextPrimary
              )
              Text(
                text = when (currentLanguage) {
                  AppLanguage.BENGALI -> "আপনার শেখার অগ্রগতি সংরক্ষিত হবে"
                  AppLanguage.HINDI -> "आपकी सीखने की प्रगति सुरक्षित होगी"
                  AppLanguage.ENGLISH -> "Saves your learning progress"
                },
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = TextSecondary
              )
            }
          }
        }
      }

      // 5. PREVIOUS & NEXT LESSON NAVIGATION
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onPreviousLesson,
            modifier = Modifier
              .weight(1f)
              .testTag("prev_lesson_btn"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
              contentColor = TechCyanAccent
            )
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
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
            )
          }

          Button(
            onClick = onNextLesson,
            modifier = Modifier
              .weight(1f)
              .testTag("next_lesson_btn"),
            shape = RoundedCornerShape(10.dp),
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
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
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
  }
}

// -----------------------------------------------------------------------------
// LANGUAGE SELECTOR PILLS IN TOP BAR
// -----------------------------------------------------------------------------
@Composable
private fun LanguageSelectorRow(
  currentLanguage: AppLanguage,
  onLanguageSelected: (AppLanguage) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.padding(end = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    AppLanguage.entries.forEach { lang ->
      val isSelected = lang == currentLanguage
      val label = when (lang) {
        AppLanguage.BENGALI -> "বাংলা"
        AppLanguage.ENGLISH -> "ENG"
        AppLanguage.HINDI -> "हिन्दी"
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(if (isSelected) TechCyanAccent else NavyCard)
          .border(0.5.dp, if (isSelected) TechCyanAccent else NavyCardBorder, RoundedCornerShape(6.dp))
          .clickable { onLanguageSelected(lang) }
          .padding(horizontal = 6.dp, vertical = 4.dp)
          .testTag("lang_${lang.name.lowercase()}")
      ) {
        Text(
          text = label,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
            fontSize = 10.sp
          ),
          color = if (isSelected) NavyDarkest else TextSecondary
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// LESSON HERO BANNER
// -----------------------------------------------------------------------------
@Composable
private fun LessonHeroBanner(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(
        Brush.linearGradient(
          colors = listOf(Color(0xFF0D253B), Color(0xFF091422))
        )
      )
      .border(1.dp, TechCyanAccent.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(TechCyanAccent.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Memory,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(20.dp)
          )
        }

        Column {
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "কম্পিউটারের ভেতরে: সিপিইউ, র‍্যাম এবং রম"
              AppLanguage.HINDI -> "कंप्यूटर के अंदर: सीपीयू, रैम और रोम"
              AppLanguage.ENGLISH -> "Inside the Computer: CPU, RAM & ROM"
            },
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 15.sp
            ),
            color = TextPrimary
          )
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "গণনা ইঞ্জিন, দ্রুত কাজের মেমরি এবং স্থায়ী বুট ফার্মওয়্যারের বিশদ পাঠ"
              AppLanguage.HINDI -> "प्रोसेसिंग इंजन, कार्यशील मेमोरी और बूट फर्मवेयर का विस्तृत पाठ"
              AppLanguage.ENGLISH -> "Interactive guide to processing, memory & boot firmware"
            },
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = TextSecondary
          )
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// TAB BAR: CPU | RAM | ROM | Comparison | Data Flow | Quiz
// -----------------------------------------------------------------------------
@Composable
private fun LessonTabBar(
  activeTab: LessonTab,
  onSelectTab: (LessonTab) -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  LazyRow(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    items(LessonTab.entries.toList()) { tab ->
      val isSelected = tab == activeTab
      val label = when (tab) {
        LessonTab.CPU -> "CPU"
        LessonTab.RAM -> "RAM"
        LessonTab.ROM -> "ROM"
        LessonTab.COMPARISON -> when (currentLanguage) {
          AppLanguage.BENGALI -> "তুলনা"
          AppLanguage.HINDI -> "तुलना"
          AppLanguage.ENGLISH -> "Compare"
        }
        LessonTab.DATA_FLOW -> when (currentLanguage) {
          AppLanguage.BENGALI -> "ডেটা প্রবাহ"
          AppLanguage.HINDI -> "डेटा फ्लो"
          AppLanguage.ENGLISH -> "Data Flow"
        }
        LessonTab.QUIZ -> when (currentLanguage) {
          AppLanguage.BENGALI -> "কুইজ"
          AppLanguage.HINDI -> "प्रश्नोत्तरी"
          AppLanguage.ENGLISH -> "Quiz (8Q)"
        }
      }

      val accentColor = when (tab) {
        LessonTab.CPU -> TechCyanAccent
        LessonTab.RAM -> TechGreen
        LessonTab.ROM -> TechAmber
        LessonTab.COMPARISON -> Color(0xFF64B5F6)
        LessonTab.DATA_FLOW -> TechPurple
        LessonTab.QUIZ -> TechAmber
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(if (isSelected) accentColor else NavyCard)
          .border(1.dp, if (isSelected) accentColor else NavyCardBorder, RoundedCornerShape(10.dp))
          .clickable { onSelectTab(tab) }
          .padding(horizontal = 12.dp, vertical = 7.dp)
          .testTag("tab_${tab.name.lowercase()}")
      ) {
        Text(
          text = label,
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

// -----------------------------------------------------------------------------
// CPU DETAIL CARD
// -----------------------------------------------------------------------------
@Composable
private fun CpuDetailCard(
  partDetail: com.example.data.model.CpuPartDetail,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(
        text = partDetail.getName(currentLanguage),
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        ),
        color = TechCyanAccent
      )
      Text(
        text = partDetail.getSubtitle(currentLanguage),
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
        color = TechAmber
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "এটি কী (What it is):"
          AppLanguage.HINDI -> "यह क्या है (What it is):"
          AppLanguage.ENGLISH -> "What it is:"
        },
        body = partDetail.getWhatItIs(currentLanguage)
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "কী কাজ করে (What it does):"
          AppLanguage.HINDI -> "यह क्या करता है (What it does):"
          AppLanguage.ENGLISH -> "What it does:"
        },
        body = partDetail.getWhatItDoes(currentLanguage)
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "কীভাবে কাজ করে (How it works):"
          AppLanguage.HINDI -> "यह कैसे काम करता है (How it works):"
          AppLanguage.ENGLISH -> "How it works:"
        },
        body = partDetail.getHowItWorks(currentLanguage)
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "কেন জরুরি (Why important):"
          AppLanguage.HINDI -> "यह क्यों महत्वपूर्ण है (Why important):"
          AppLanguage.ENGLISH -> "Why it is important:"
        },
        body = partDetail.getWhyImportant(currentLanguage)
      )

      // Real Life Example
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(NavyDarkest)
          .border(0.5.dp, TechCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
          .padding(8.dp)
      ) {
        Column {
          Text(
            text = when (currentLanguage) {
              AppLanguage.BENGALI -> "বাস্তব জীবনের উদাহরণ:"
              AppLanguage.HINDI -> "वास्तविक जीवन का उदाहरण:"
              AppLanguage.ENGLISH -> "Real-Life Example:"
            },
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            ),
            color = TechCyanAccent
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = partDetail.getRealLifeExample(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = TextSecondary
          )
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// CPU GHZ EXPLANATION CARD (Educational breakdown)
// -----------------------------------------------------------------------------
@Composable
private fun CpuGhzExplanationCard(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFF0F2636))
      .border(1.dp, TechCyanAccent.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Speed,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(18.dp)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "সিপিইউ ক্লক স্পিড (GHz) এর গভীর রহস্য"
            AppLanguage.HINDI -> "सीपीयू क्लॉक स्पीड (GHz) का रहस्य"
            AppLanguage.ENGLISH -> "Understanding CPU Clock Speed & GHz"
          },
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          ),
          color = TechCyanAccent
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "• 1 GHz মানে হলো প্রতি সেকেন্ডে ১০০ কোটি ক্লক সাইকেল সম্পন্ন হওয়া।\n• সতর্কতা: বেশি GHz মানেই কম্পিউটার সবসময় দ্রুত নয়! একটি আধুনিক প্রসেসর প্রতি ক্লিকে ৩-৪টি নির্দেশ (IPC) সম্পাদন করে ৩.০ GHz-এও পুরনো ৪.০ GHz প্রসেসরকে সহজে পরাজিত করতে পারে।"
          AppLanguage.HINDI -> "• 1 GHz का अर्थ है प्रति सेकंड 1 अरब क्लॉक चक्र पूरा होना।\n• चेतावनी: अधिक GHz का मतलब हमेशा तेज होना नहीं है! नई पीढ़ी का 3.0 GHz चिप पुरानी पीढ़ी के 4.0 GHz चिप से कहीं अधिक तेज़ होता है क्योंकि यह प्रति चक्र ज्यादा निर्देश (IPC) निष्पादित करता है।"
          AppLanguage.ENGLISH -> "• 1 GHz equals 1,000,000,000 clock cycles per second.\n• Reality Check: Higher GHz does NOT automatically mean faster! A modern CPU executing 3-4 instructions per cycle (IPC) at 3.0 GHz easily destroys an older CPU running at 4.0 GHz with lower IPC."
        },
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 11.sp,
          lineHeight = 17.sp
        ),
        color = TextPrimary
      )
    }
  }
}

// -----------------------------------------------------------------------------
// RAM DETAIL CARD
// -----------------------------------------------------------------------------
@Composable
private fun RamDetailCard(
  ramPart: com.example.data.model.RamPartDetail,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(
        text = ramPart.getName(currentLanguage),
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        ),
        color = TechGreen
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "এটি কী:"
          AppLanguage.HINDI -> "यह क्या है:"
          AppLanguage.ENGLISH -> "What it is:"
        },
        body = ramPart.getWhatItIs(currentLanguage)
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "এর ভূমিকা:"
          AppLanguage.HINDI -> "इसकी भूमिका:"
          AppLanguage.ENGLISH -> "What it does:"
        },
        body = ramPart.getWhatItDoes(currentLanguage)
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "বাস্তব তুলনা:"
          AppLanguage.HINDI -> "सरल तुलना:"
          AppLanguage.ENGLISH -> "Real-life Analogy:"
        },
        body = ramPart.getAnalogy(currentLanguage)
      )
    }
  }
}

// -----------------------------------------------------------------------------
// RAM CAPACITY GUIDE (4GB vs 8GB vs 16GB vs 32GB)
// -----------------------------------------------------------------------------
@Composable
private fun RamCapacityGuideCard(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFF0F2B20))
      .border(1.dp, TechGreen.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Column {
      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "র‍্যামের ধারণক্ষমতা নির্দেশিকা (Capacity Guide)"
          AppLanguage.HINDI -> "रैम क्षमता गाइड (4GB बनाम 8GB बनाम 16GB बनाम 32GB)"
          AppLanguage.ENGLISH -> "RAM Capacity Guide (4GB vs 8GB vs 16GB vs 32GB)"
        },
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp
        ),
        color = TechGreen
      )

      Spacer(modifier = Modifier.height(8.dp))

      val capacities = listOf(
        "4 GB" to when (currentLanguage) {
          AppLanguage.BENGALI -> "শুধুমাত্র সাধারণ ইন্টারনেট ব্রাউজিং ও সাধারণ কাজ।"
          AppLanguage.HINDI -> "केवल बुनियादी इंटरनेट ब्राउज़िंग और साधारण कार्य।"
          AppLanguage.ENGLISH -> "Basic web browsing, office docs; stutters with multi-tabs."
        },
        "8 GB" to when (currentLanguage) {
          AppLanguage.BENGALI -> "সাধারণ মাল্টিটাস্কিং, হালকা ফটো এডিটিং ও শিক্ষার্থীদের জন্য উপযোগী।"
          AppLanguage.HINDI -> "दैनिक मल्टीटास्किंग, छात्रों और सामान्य उपयोग के लिए पर्याप्त।"
          AppLanguage.ENGLISH -> "Everyday multitasking, students, 10-15 browser tabs."
        },
        "16 GB" to when (currentLanguage) {
          AppLanguage.BENGALI -> "আধুনিক গেম খেলা, মসৃণ মাল্টিটাস্কিং ও প্রোগ্রামিংয়ের জন্য সেরা ব্যালেন্স।"
          AppLanguage.HINDI -> "आधुनिक गेमिंग, सुचारू मल्टीटास्किंग और कोडिंग के लिए सर्वश्रेष्ठ।"
          AppLanguage.ENGLISH -> "Sweet spot for modern gaming, software development & multitasking."
        },
        "32 GB+" to when (currentLanguage) {
          AppLanguage.BENGALI -> "পেশাদার 4K ভিডিও এডিটিং, ৩ডি অ্যানিমেশন ও হেভি ভার্চুয়াল মেশিন।"
          AppLanguage.HINDI -> "प्रोफेशनल 4K वीडियो एडिटिंग, 3D डिजाइन और भारी सॉफ्टवेयर।"
          AppLanguage.ENGLISH -> "Heavy 4K video editing, 3D rendering & virtual machines."
        }
      )

      capacities.forEach { (size, desc) ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = size,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = TechCyanAccent,
            modifier = Modifier.width(52.dp)
          )
          Text(
            text = desc,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = TextPrimary
          )
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// ROM DETAILED EXPLANATION CARD
// -----------------------------------------------------------------------------
@Composable
private fun RomDetailExplanationCard(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "রম (Read-Only Memory) এবং ফার্মওয়্যার"
          AppLanguage.HINDI -> "रोम (Read-Only Memory) और फर्मवेयर"
          AppLanguage.ENGLISH -> "ROM (Read-Only Memory) & BIOS Firmware"
        },
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        ),
        color = TechAmber
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "মূল উদ্দেশ্য:"
          AppLanguage.HINDI -> "मुख्य उद्देश्य:"
          AppLanguage.ENGLISH -> "Core Purpose:"
        },
        body = when (currentLanguage) {
          AppLanguage.BENGALI -> "কম্পিউটার অফ থাকলে র‍্যাম পুরো খালি হয়ে যায়। তাই কম্পিউটারকে শূন্য থেকে জাগিয়ে তুলতে একটি স্থায়ী মেমরি দরকার যা বিদ্যুৎ ছাড়াও বেঁচে থাকে—এটাই রম।"
          AppLanguage.HINDI -> "कंप्यूटर बंद होने पर रैम खाली हो जाती है। इसलिए कंप्यूटर को शुरू करने के लिए ऐसी मेमोरी की जरूरत होती है जो बिना बिजली के भी जीवित रहे—यही रोम है।"
          AppLanguage.ENGLISH -> "Because RAM completely drains on shutdown, the PC needs a permanent, indestructible memory chip that survives with zero power to perform the initial startup."
        }
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "বায়োস ও ইউইএফআই (BIOS / UEFI):"
          AppLanguage.HINDI -> "बायोस और यूईएफआई (BIOS / UEFI):"
          AppLanguage.ENGLISH -> "BIOS & UEFI Boot Process:"
        },
        body = when (currentLanguage) {
          AppLanguage.BENGALI -> "রমে থাকা বিশেষ সফটওয়্যারকে ফার্মওয়্যার বলে। কম্পিউটার অন করার পর বায়োস প্রথমে কিবোর্ড, র‍্যাম, স্টোরেজ ঠিক আছে কিনা পরীক্ষা করে (POST - Power-On Self-Test)। সব ঠিক থাকলে এসএসডি থেকে উইন্ডোজকে র‍্যামে কপি করে দেয়।"
          AppLanguage.HINDI -> "रोम में स्थित सॉफ्टवेयर को फर्मवेयर कहा जाता है। कंप्यूटर चालू होने पर बायोस सभी पुर्जों की जांच करता है (POST)। सब ठीक होने पर यह विंडोज़ को एसएसडी से रैम में लोड कर देता है।"
          AppLanguage.ENGLISH -> "The startup software written inside ROM is called firmware. On power-on, the BIOS/UEFI runs the POST (Power-On Self-Test) routine to verify CPU, RAM and GPU health, then triggers the OS bootloader."
        }
      )

      SubSectionBlock(
        label = when (currentLanguage) {
          AppLanguage.BENGALI -> "সহজ বাস্তব তুলনা:"
          AppLanguage.HINDI -> "सरल दैनिक जीवन का उदाहरण:"
          AppLanguage.ENGLISH -> "Simple Analogy:"
        },
        body = when (currentLanguage) {
          AppLanguage.BENGALI -> "রম হলো কোনো বইয়ে প্রিন্ট করা রেসিপি অথবা আপনার মুদ্রিত জন্মসনদ—এটি সহজে মুছে নতুন করে লেখা যায় না, স্থায়ীভাবে সংরক্ষিত থাকে।"
          AppLanguage.HINDI -> "रोम किताब में छपी हुई रेसिपी या जन्म प्रमाण पत्र की तरह है—इसे आसानी से मिटाया या बदला नहीं जा सकता, यह हमेशा सुरक्षित रहता है।"
          AppLanguage.ENGLISH -> "ROM is like a printed recipe book or engraved birth certificate: permanently fixed and readable forever, unable to be accidentally wiped."
        }
      )
    }
  }
}

// -----------------------------------------------------------------------------
// REUSABLE SUB-SECTION BLOCK
// -----------------------------------------------------------------------------
@Composable
private fun SubSectionBlock(
  label: String,
  body: String,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp
      ),
      color = TechCyanAccent
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = body,
      style = MaterialTheme.typography.bodySmall.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp
      ),
      color = TextPrimary
    )
  }
}
