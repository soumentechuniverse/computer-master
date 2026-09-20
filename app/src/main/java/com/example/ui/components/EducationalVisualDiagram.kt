package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun EducationalVisualDiagram(
  diagramType: String,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .background(NavyDark)
      .border(1.dp, NavyCardBorder, RoundedCornerShape(18.dp))
      .padding(16.dp)
  ) {
    when (diagramType) {
      "IPO_CYCLE" -> IpoCycleDiagram()
      "COMPUTER_WORKFLOW" -> ComputerWorkflowDiagram()
      "HARDWARE_SOFTWARE" -> HardwareSoftwareDiagram()
      "INPUT_DEVICES" -> InputDevicesDiagram()
      "OUTPUT_DEVICES" -> OutputDevicesDiagram()
      "CPU_ARCHITECTURE" -> CpuArchitectureDiagram()
      "RAM_HIERARCHY" -> RamHierarchyDiagram()
      "ROM_FIRMWARE" -> RomFirmwareDiagram()
      "STORAGE_HDD_SSD" -> StorageHddSsdDiagram()
      "PORTS_CONNECTORS" -> PortsConnectorsDiagram()
      else -> IpoCycleDiagram()
    }
  }
}

// 1. IPO Cycle Diagram
@Composable
private fun IpoCycleDiagram() {
  var selectedStep by remember { mutableIntStateOf(0) }
  val steps = listOf(
    Triple("1. INPUT", "Capturing raw data into binary signals", TechCyanAccent),
    Triple("2. PROCESSING", "CPU performs math and logical calculations", TechBluePrimary),
    Triple("3. OUTPUT", "Delivering visible pixels or audible sounds", TechGreen),
    Triple("4. STORAGE", "Saving files permanently onto disk or flash", TechAmber)
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "THE UNIVERSAL IPOS COMPUTING CYCLE",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechCyanAccent
    )
    Text(
      text = "Tap any phase to inspect the data transformation",
      style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
      color = TextTertiary
    )
    Spacer(modifier = Modifier.height(14.dp))

    // Interactive 4-Box Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      steps.forEachIndexed { index, (label, _, color) ->
        val isSelected = selectedStep == index
        val bg by animateColorAsState(
          if (isSelected) color.copy(alpha = 0.25f) else NavyCard,
          label = "ipo_bg"
        )
        val border by animateColorAsState(
          if (isSelected) color else NavyCardBorder,
          label = "ipo_border"
        )

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(10.dp))
            .clickable { selectedStep = index }
            .padding(vertical = 10.dp, horizontal = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = label.substringAfter(". "),
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.sp
            ),
            color = if (isSelected) color else TextSecondary,
            textAlign = TextAlign.Center
          )
        }

        if (index < steps.size - 1) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TextTertiary,
            modifier = Modifier.size(14.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Detail Callout
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(NavyCardElevated)
        .padding(12.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(steps[selectedStep].third)
        )
        Column {
          Text(
            text = steps[selectedStep].first,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = steps[selectedStep].third
          )
          Text(
            text = steps[selectedStep].second,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = TextPrimary
          )
        }
      }
    }
  }
}

// 2. Computer Workflow Diagram
@Composable
private fun ComputerWorkflowDiagram() {
  val workflow = listOf(
    "User Keypress" to "Keyboard generates scancode interrupt signal",
    "Staged in RAM" to "OS stages active buffer data into memory addresses",
    "CPU Calculation" to "ALU computes binary logic & updates registers",
    "Screen Pixel Draw" to "GPU renders updated text glyphs onto display",
    "Saved to SSD" to "Non-volatile storage traps electrons permanently"
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "DATA JOURNEY: FROM KEYPRESS TO STORAGE",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechBluePrimary
    )
    Spacer(modifier = Modifier.height(12.dp))

    workflow.forEachIndexed { index, (stage, desc) ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(TechBluePrimary.copy(alpha = 0.2f))
            .border(1.dp, TechBluePrimary, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "${index + 1}",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = TechCyanAccent
          )
        }

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = stage,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = TextPrimary
          )
          Text(
            text = desc,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = TextSecondary
          )
        }
      }

      if (index < workflow.size - 1) {
        Box(
          modifier = Modifier
            .padding(start = 11.dp, top = 2.dp, bottom = 2.dp)
            .width(2.dp)
            .height(10.dp)
            .background(NavyCardBorder)
        )
      }
    }
  }
}

// 3. Hardware vs Software Diagram
@Composable
private fun HardwareSoftwareDiagram() {
  val layers = listOf(
    Triple("USER LAYER", "Human user initiating goals & commands", TechPurple),
    Triple("APPLICATION SOFTWARE", "Chrome, Word, Spotify, Games, Calculator", TechCyanAccent),
    Triple("OPERATING SYSTEM", "Windows, macOS, Android, Linux & Drivers", TechBluePrimary),
    Triple("HARDWARE SILICON", "CPU, RAM, Motherboard, GPU, SSD/HDD, Display", TechGreen)
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "THE COMPUTING STACK ARCHITECTURE",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechCyanAccent
    )
    Text(
      text = "How software layers translate human intent to silicon",
      style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
      color = TextTertiary
    )
    Spacer(modifier = Modifier.height(12.dp))

    layers.forEachIndexed { index, (layerTitle, layerDesc, layerColor) ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(layerColor.copy(alpha = 0.12f))
          .border(1.dp, layerColor.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
          .padding(horizontal = 14.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = layerTitle,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              ),
              color = layerColor
            )
            Text(
              text = layerDesc,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextPrimary
            )
          }
        }
      }

      if (index < layers.size - 1) {
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = null,
            tint = TextTertiary,
            modifier = Modifier
              .size(16.dp)
              .padding(vertical = 1.dp)
          )
        }
      }
    }
  }
}

// 4. Input Devices Diagram
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InputDevicesDiagram() {
  val inputs = listOf(
    Triple("Keyboard", Icons.Default.Keyboard, "Text & Shortcuts"),
    Triple("Mouse", Icons.Default.Mouse, "2D Coordinates"),
    Triple("Touchscreen", Icons.Default.TouchApp, "Multi-touch gestures"),
    Triple("Microphone", Icons.Default.Mic, "Acoustic audio waves"),
    Triple("Webcam", Icons.Default.CameraAlt, "Optical light pixels"),
    Triple("Fingerprint", Icons.Default.Fingerprint, "Biometric security")
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "DIGITAL INPUT SENSORS & CONTROLLERS",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechCyanAccent
    )
    Spacer(modifier = Modifier.height(10.dp))

    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      inputs.forEach { (name, icon, desc) ->
        Box(
          modifier = Modifier
            .weight(1f, fill = false)
            .clip(RoundedCornerShape(10.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(10.dp))
            .padding(10.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = icon,
              contentDescription = null,
              tint = TechCyanAccent,
              modifier = Modifier.size(20.dp)
            )
            Column {
              Text(
                text = name,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = TextPrimary
              )
              Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = TextSecondary
              )
            }
          }
        }
      }
    }
  }
}

// 5. Output Devices Diagram
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OutputDevicesDiagram() {
  val outputs = listOf(
    Triple("Monitor / OLED", Icons.Default.Tv, "Visual pixels & refresh rate"),
    Triple("Speakers / DAC", Icons.Default.Headphones, "Sound waves & music"),
    Triple("Laser / Ink Printer", Icons.Default.Print, "Permanent physical paper"),
    Triple("Haptic Motor", Icons.Default.Vibration, "Tactile physical rumble")
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "HUMAN PERCEPTION OUTPUT PIPELINES",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechGreen
    )
    Spacer(modifier = Modifier.height(10.dp))

    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      outputs.forEach { (name, icon, desc) ->
        Box(
          modifier = Modifier
            .weight(1f, fill = false)
            .clip(RoundedCornerShape(10.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(10.dp))
            .padding(10.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = icon,
              contentDescription = null,
              tint = TechGreen,
              modifier = Modifier.size(20.dp)
            )
            Column {
              Text(
                text = name,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = TextPrimary
              )
              Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = TextSecondary
              )
            }
          }
        }
      }
    }
  }
}

// 6. CPU Architecture Diagram
@Composable
private fun CpuArchitectureDiagram() {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "CENTRAL PROCESSING UNIT INTERNAL ARCHITECTURE",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechCyanAccent
    )
    Spacer(modifier = Modifier.height(10.dp))

    // CPU Die Box
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(NavyDarkest)
        .border(1.5.dp, TechBluePrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
        .padding(12.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Control Unit
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(TechIndigo.copy(alpha = 0.2f))
              .border(1.dp, TechIndigo.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
              .padding(10.dp)
          ) {
            Column {
              Text("Control Unit (CU)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = TechCyanAccent)
              Text("Fetches & decodes machine instructions", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = TextSecondary)
            }
          }

          // ALU
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(TechGreen.copy(alpha = 0.15f))
              .border(1.dp, TechGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
              .padding(10.dp)
          ) {
            Column {
              Text("ALU (Math & Logic)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = TechGreen)
              Text("Executes +, -, *, /, AND, OR, >", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = TextSecondary)
            }
          }
        }

        // Registers & Cache
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(TechAmber.copy(alpha = 0.15f))
            .border(1.dp, TechAmber.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Internal Registers & L1/L2/L3 High-Speed Cache",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.sp),
              color = TechAmber
            )
            Text(
              text = "< 1 Nanosecond",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TextPrimary
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))
    Text(
      text = "Machine Cycle: FETCH → DECODE → EXECUTE → STORE",
      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
      color = TechCyanAccent,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

// 7. RAM Hierarchy Diagram
@Composable
private fun RamHierarchyDiagram() {
  val hierarchy = listOf(
    Triple("CPU Registers", "< 0.5 nanoseconds", "Bytes of immediate active storage"),
    Triple("L1 / L2 / L3 Cache", "1 – 5 nanoseconds", "Megabytes of ultra-fast on-die memory"),
    Triple("RAM (System Memory)", "50 – 100 nanoseconds", "8GB – 64GB temporary multitasking space"),
    Triple("SSD Storage", "50,000+ nanoseconds", "512GB – 4TB permanent persistent storage")
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "MEMORY & SPEED HIERARCHY",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechAmber
    )
    Spacer(modifier = Modifier.height(10.dp))

    hierarchy.forEachIndexed { index, (tier, speed, cap) ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 3.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(if (index == 2) TechAmber.copy(alpha = 0.2f) else NavyCard)
          .border(
            1.dp,
            if (index == 2) TechAmber else NavyCardBorder,
            RoundedCornerShape(8.dp)
          )
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = tier,
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = if (index == 2) TechAmber else TextPrimary
            )
            Text(
              text = cap,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
              color = TextSecondary
            )
          }
          Text(
            text = speed,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium, fontSize = 11.sp),
            color = TechCyanAccent
          )
        }
      }
    }
  }
}

// 8. ROM Firmware Boot Sequence Diagram
@Composable
private fun RomFirmwareDiagram() {
  val bootSteps = listOf(
    "1. Power On" to "Electricity energizes motherboard circuitry",
    "2. ROM Startup" to "CPU automatically executes permanent UEFI/BIOS firmware",
    "3. POST Check" to "Verifies RAM, CPU, GPU, and keyboard are responding",
    "4. Boot OS into RAM" to "Locates Windows/Linux boot partition on SSD and starts OS"
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "SYSTEM BOOT SEQUENCE POWERED BY ROM",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechIndigo
    )
    Spacer(modifier = Modifier.height(10.dp))

    bootSteps.forEach { (step, detail) ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 3.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(8.dp))
          .padding(10.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(16.dp)
          )
          Column {
            Text(
              text = step,
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = TextPrimary
            )
            Text(
              text = detail,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}

// 9. Storage: HDD vs SSD Diagram
@Composable
private fun StorageHddSsdDiagram() {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "HDD VS SSD: MECHANICAL VS FLASH COMPARISON",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechCyanAccent
    )
    Spacer(modifier = Modifier.height(12.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // HDD Card
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(12.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
          .padding(12.dp)
      ) {
        Column {
          Text("Mechanical HDD", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = TechAmber)
          Spacer(modifier = Modifier.height(6.dp))
          Text("• Spinning magnetic platters", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = TextSecondary)
          Text("• Mechanical actuator arm", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = TextSecondary)
          Text("• Speed: ~120 MB/s", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold), color = TechAmber)
          Text("• Vulnerable to physical drop", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = TextTertiary)
        }
      }

      // SSD Card
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(12.dp))
          .background(NavyCardElevated)
          .border(1.dp, TechGreen.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
          .padding(12.dp)
      ) {
        Column {
          Text("Solid-State SSD", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = TechGreen)
          Spacer(modifier = Modifier.height(6.dp))
          Text("• Silicon NAND Flash chips", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = TextSecondary)
          Text("• Zero mechanical moving parts", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = TextSecondary)
          Text("• Speed: up to 7,000 MB/s", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold), color = TechGreen)
          Text("• Drop and shock resistant", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = TextPrimary)
        }
      }
    }
  }
}

// 10. Ports and Connectors Diagram
@Composable
private fun PortsConnectorsDiagram() {
  val ports = listOf(
    Triple("USB-C", "Reversible, up to 40Gbps, 240W charging & 8K video", TechCyanAccent),
    Triple("USB-A", "Classic rectangular peripheral connector", TechBluePrimary),
    Triple("HDMI", "High-Definition video and audio to TVs & monitors", TechPurple),
    Triple("DisplayPort", "High-bandwidth monitor port for fast refresh gaming", TechIndigo),
    Triple("Ethernet RJ-45", "Wired low-latency gigabit networking jack", TechGreen),
    Triple("3.5mm Audio", "Analog stereo sound for headphones and mic", TechAmber)
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "ESSENTIAL COMPUTER PORTS & CONNECTORS",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      ),
      color = TechCyanAccent
    )
    Spacer(modifier = Modifier.height(10.dp))

    ports.forEach { (portName, description, portColor) ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 3.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(NavyCard)
          .border(1.dp, NavyCardBorder, RoundedCornerShape(8.dp))
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(portColor)
          )
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = portName,
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = portColor
            )
            Text(
              text = description,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}
