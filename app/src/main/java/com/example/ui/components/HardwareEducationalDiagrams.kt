package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.util.AppLanguage

@Composable
fun HardwareDataFlowDiagram(
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  var selectedStage by remember { mutableIntStateOf(1) }

  // Animated data pulse dot moving from Input -> CPU -> Monitor
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_trans")
  val pulseProgress by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(2400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "data_flow_pulse"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
      .padding(16.dp)
  ) {
    // Title
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
          imageVector = Icons.Default.Sync,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "হার্ডওয়্যার ডেটা প্রবাহ ডায়াগ্রাম"
            AppLanguage.HINDI -> "हार्डवेयर डेटा प्रवाह आरेख"
            AppLanguage.ENGLISH -> "HARDWARE DATA FLOW DIAGRAM"
          },
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          color = TechCyanAccent
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(TechGreen.copy(alpha = 0.2f))
          .padding(horizontal = 6.dp, vertical = 3.dp)
      ) {
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "লাইভ ডেটা সাইকেল"
            AppLanguage.HINDI -> "लाइव डेटा चक्र"
            AppLanguage.ENGLISH -> "LIVE CYCLE"
          },
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          ),
          color = TechGreen
        )
      }
    }

    Text(
      text = when (currentLanguage) {
        AppLanguage.BENGALI -> "দেখুন কীভাবে হাত থেকে ডেটা প্রসেসর হয়ে চোখের সামনে পৌঁছায়"
        AppLanguage.HINDI -> "देखें कि कैसे इनपुट से डेटा प्रोसेसर तक और फिर स्क्रीन पर पहुंचता है"
        AppLanguage.ENGLISH -> "Tap each stage to see how information travels between components"
      },
      style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
      color = TextSecondary,
      modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
    )

    // Visual Flow: [INPUT] ---> [PROCESSING] ---> [OUTPUT]
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // 1. INPUT STAGE
      FlowStageCard(
        stageNumber = 1,
        title = when (currentLanguage) {
          AppLanguage.BENGALI -> "ইনপুট"
          AppLanguage.HINDI -> "इनपुट"
          AppLanguage.ENGLISH -> "INPUT"
        },
        subtitle = when (currentLanguage) {
          AppLanguage.BENGALI -> "কীবোর্ড ও মাউস"
          AppLanguage.HINDI -> "कीबोर्ड व माउस"
          AppLanguage.ENGLISH -> "Keyboard & Mouse"
        },
        icon = Icons.Default.Keyboard,
        accentColor = TechGreen,
        isSelected = selectedStage == 1,
        onClick = { selectedStage = 1 },
        modifier = Modifier.weight(1f)
      )

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = TechCyanAccent.copy(alpha = 0.7f),
        modifier = Modifier.padding(horizontal = 4.dp).size(18.dp)
      )

      // 2. PROCESSING STAGE
      FlowStageCard(
        stageNumber = 2,
        title = when (currentLanguage) {
          AppLanguage.BENGALI -> "প্রসেসিং"
          AppLanguage.HINDI -> "प्रोसेसिंग"
          AppLanguage.ENGLISH -> "PROCESS"
        },
        subtitle = when (currentLanguage) {
          AppLanguage.BENGALI -> "সিপিইউ টাওয়ার"
          AppLanguage.HINDI -> "सीपीयू टॉवर"
          AppLanguage.ENGLISH -> "CPU System Unit"
        },
        icon = Icons.Default.DeveloperBoard,
        accentColor = TechBluePrimary,
        isSelected = selectedStage == 2,
        onClick = { selectedStage = 2 },
        modifier = Modifier.weight(1f)
      )

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = TechCyanAccent.copy(alpha = 0.7f),
        modifier = Modifier.padding(horizontal = 4.dp).size(18.dp)
      )

      // 3. OUTPUT STAGE
      FlowStageCard(
        stageNumber = 3,
        title = when (currentLanguage) {
          AppLanguage.BENGALI -> "আউটপুট"
          AppLanguage.HINDI -> "आउटपुट"
          AppLanguage.ENGLISH -> "OUTPUT"
        },
        subtitle = when (currentLanguage) {
          AppLanguage.BENGALI -> "মনিটর স্ক্রিন"
          AppLanguage.HINDI -> "मॉनिटर स्क्रीन"
          AppLanguage.ENGLISH -> "Monitor Display"
        },
        icon = Icons.Default.Tv,
        accentColor = TechCyanAccent,
        isSelected = selectedStage == 3,
        onClick = { selectedStage = 3 },
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Interactive Stage Detail Box
    val (stageExplanation, stageDetail) = when (selectedStage) {
      1 -> when (currentLanguage) {
        AppLanguage.BENGALI -> "১. ইনপুট সংকেত তৈরি:" to "কীবোর্ডে কী চাপলে বা মাউস নড়ালে বাইনারি বৈদ্যুতিক সিগন্যাল (০ এবং ১) USB ক্যাবলের মাধ্যমে সরাসরি সিপিইউতে প্রেরিত হয়।"
        AppLanguage.HINDI -> "1. इनपुट सिग्नल निर्माण:" to "कुंजी दबाने या माउस हिलाने पर बाइनरी सिग्नल (0 और 1) यूएसबी केबल के माध्यम से सीधे सीपीयू तक पहुंचते हैं।"
        AppLanguage.ENGLISH -> "1. Input Signal Generation:" to "Pressing keys or sliding the mouse generates binary electrical pulses (0s & 1s) transmitted instantly via USB cable into the CPU."
      }
      2 -> when (currentLanguage) {
        AppLanguage.BENGALI -> "২. গাণিতিক প্রসেসিং ও সমন্বয়:" to "সিপিইউ চিপ ইনপুট কোড গ্রহণ করে, র‍্যাম মেমোরিতে রেখে হিসাব করে এবং ফলাফল গ্রাফিক্স কার্ডে পাঠায়।"
        AppLanguage.HINDI -> "2. गणना और समन्वय:" to "सीपीयू इनपुट कोड स्वीकार करता है, इसे रैम मेमोरी में रखता है, गणना करता है और परिणाम ग्राफिक्स प्रोसेसर को भेजता है।"
        AppLanguage.ENGLISH -> "2. Arithmetic & Logic Processing:" to "The CPU receives raw input scancodes, computes instructions through ALU registers, and directs framebuffers to the GPU."
      }
      else -> when (currentLanguage) {
        AppLanguage.BENGALI -> "৩. ভিজ্যুয়াল আউটপুট প্রদর্শন:" to "মনিটর HDMI/DisplayPort ক্যাবল দিয়ে কোটি কোটি রঙিন পিক্সেল জ্বালিয়ে তাৎক্ষণিকভাবে পরিষ্কার ছবি ও লেখা প্রদর্শন করে।"
        AppLanguage.HINDI -> "3. विज़ुअल आउटपुट डिस्प्ले:" to "मॉनिटर एचडीएमआई केबल के माध्यम से लाखों रंगीन पिक्सल को रोशन करता है और तुरंत स्पष्ट चित्र और टेक्स्ट प्रदर्शित करता है।"
        AppLanguage.ENGLISH -> "3. Visual Display Output:" to "The monitor receives high-speed video frames through HDMI/DisplayPort and activates 2+ million pixels to render crisp graphics."
      }
    }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(NavyCard)
        .border(1.dp, TechCyanAccent.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column {
        Text(
          text = stageExplanation,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          ),
          color = TechCyanAccent
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = stageDetail,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 18.sp),
          color = TextPrimary
        )
      }
    }
  }
}

@Composable
private fun FlowStageCard(
  stageNumber: Int,
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val bg by animateColorAsState(
    if (isSelected) accentColor.copy(alpha = 0.2f) else NavyCard,
    label = "stage_bg"
  )
  val border by animateColorAsState(
    if (isSelected) accentColor else NavyCardBorder,
    label = "stage_border"
  )

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .background(bg)
      .border(1.5.dp, border, RoundedCornerShape(14.dp))
      .clickable(onClick = onClick)
      .padding(10.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.25f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(18.dp)
        )
      }

      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Black,
          fontSize = 11.sp
        ),
        color = if (isSelected) accentColor else TextPrimary
      )

      Text(
        text = subtitle,
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 9.sp,
          lineHeight = 12.sp
        ),
        color = TextSecondary,
        maxLines = 1
      )
    }
  }
}

@Composable
fun HardwarePortsDiagram(
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
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Cable,
        contentDescription = null,
        tint = TechAmber,
        modifier = Modifier.size(20.dp)
      )
      Text(
        text = when (currentLanguage) {
          AppLanguage.BENGALI -> "ক্যাবল ও সংযোগ পোর্ট গাইড"
          AppLanguage.HINDI -> "केबल और पोर्ट कनेक्शन गाइड"
          AppLanguage.ENGLISH -> "HOW COMPONENTS CONNECT (PORTS & CABLES)"
        },
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        ),
        color = TechAmber
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Port Rows
    val portList = listOf(
      Triple(
        when (currentLanguage) {
          AppLanguage.BENGALI -> "HDMI / DisplayPort"
          AppLanguage.HINDI -> "HDMI / DisplayPort"
          AppLanguage.ENGLISH -> "HDMI / DisplayPort"
        },
        when (currentLanguage) {
          AppLanguage.BENGALI -> "মনিটরে উচ্চমানের ভিডিও ও অডিও প্রেরণ করে"
          AppLanguage.HINDI -> "मॉनिटर को हाई-डेफिनिशन वीडियो और ऑडियो भेजता है"
          AppLanguage.ENGLISH -> "Connects Monitor to GPU for crystal-clear visuals"
        },
        TechCyanAccent
      ),
      Triple(
        when (currentLanguage) {
          AppLanguage.BENGALI -> "USB (Universal Serial Bus)"
          AppLanguage.HINDI -> "USB (यूनिवर्सल सीरियल बस)"
          AppLanguage.ENGLISH -> "USB (Type-A & Type-C)"
        },
        when (currentLanguage) {
          AppLanguage.BENGALI -> "কীবোর্ড, মাউস, পেনড্রাইভ ও প্রিন্টার যুক্ত করে"
          AppLanguage.HINDI -> "कीबोर्ड, माउस, पेन ड्राइव और प्रिंटर को जोड़ता है"
          AppLanguage.ENGLISH -> "Plugs Keyboard, Mouse, and external devices into CPU"
        },
        TechGreen
      ),
      Triple(
        when (currentLanguage) {
          AppLanguage.BENGALI -> "AC Power Cable (বিদ্যুৎ সংযোগ)"
          AppLanguage.HINDI -> "AC Power Cable (पावर केबल)"
          AppLanguage.ENGLISH -> "AC Power Cable"
        },
        when (currentLanguage) {
          AppLanguage.BENGALI -> "দেওয়ালের সকেট থেকে সিপিইউ ও মনিটরে বিদ্যুৎ সরবরাহ করে"
          AppLanguage.HINDI -> "दीवार के सॉकेट से सीपीयू और मॉनिटर को बिजली देता है"
          AppLanguage.ENGLISH -> "Supplies electrical voltage from wall outlet to SMPS"
        },
        TechAmber
      )
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      portList.forEach { (title, desc, color) ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NavyCard)
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Usb,
              contentDescription = null,
              tint = color,
              modifier = Modifier.size(18.dp)
            )
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = title,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              ),
              color = color
            )
            Text(
              text = desc,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}
