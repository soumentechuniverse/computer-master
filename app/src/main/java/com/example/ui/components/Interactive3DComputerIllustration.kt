package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HardwareComponentType
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
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
import com.example.util.AppLanguage

@Composable
fun Interactive3DComputerIllustration(
  selectedComponent: HardwareComponentType,
  onSelectComponent: (HardwareComponentType) -> Unit,
  currentLanguage: AppLanguage,
  modifier: Modifier = Modifier
) {
  // Infinite transition for gentle ambient breathing glow
  val infiniteTransition = rememberInfiniteTransition(label = "ambient_glow")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.45f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(1800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_alpha"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(24.dp))
      .background(
        brush = Brush.verticalGradient(
          listOf(
            Color(0xFF0F172A), // Slate deep
            NavyDarkest
          )
        )
      )
      .border(
        width = 1.5.dp,
        brush = Brush.horizontalGradient(
          listOf(
            TechCyanAccent.copy(alpha = 0.6f),
            TechBluePrimary.copy(alpha = 0.3f),
            TechPurple.copy(alpha = 0.6f)
          )
        ),
        shape = RoundedCornerShape(24.dp)
      )
      .padding(16.dp)
  ) {
    // Header Tip
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.TouchApp,
          contentDescription = null,
          tint = TechCyanAccent,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = when (currentLanguage) {
            AppLanguage.BENGALI -> "অংশ স্পর্শ করে জানুন"
            AppLanguage.HINDI -> "घटक पर टैप करके जानें"
            AppLanguage.ENGLISH -> "Tap any hardware part to explore"
          },
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 0.5.sp
          ),
          color = TechCyanAccent
        )
      }

      // Active component badge
      val activeName = when (selectedComponent) {
        HardwareComponentType.MONITOR -> when (currentLanguage) {
          AppLanguage.BENGALI -> "১. মনিটর"
          AppLanguage.HINDI -> "1. मॉनिटर"
          AppLanguage.ENGLISH -> "1. Monitor"
        }
        HardwareComponentType.CPU -> when (currentLanguage) {
          AppLanguage.BENGALI -> "২. সিপিইউ টাওয়ার"
          AppLanguage.HINDI -> "2. सीपीयू टॉवर"
          AppLanguage.ENGLISH -> "2. CPU Tower"
        }
        HardwareComponentType.KEYBOARD -> when (currentLanguage) {
          AppLanguage.BENGALI -> "৩. কীবোর্ড"
          AppLanguage.HINDI -> "3. कीबोर्ड"
          AppLanguage.ENGLISH -> "3. Keyboard"
        }
        HardwareComponentType.MOUSE -> when (currentLanguage) {
          AppLanguage.BENGALI -> "৪. মাউস"
          AppLanguage.HINDI -> "4. माउस"
          AppLanguage.ENGLISH -> "4. Mouse"
        }
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(TechBluePrimary.copy(alpha = 0.2f))
          .border(1.dp, TechCyanAccent.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = activeName,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp
          ),
          color = TextPrimary
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 3D Visual Stage
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(260.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(
          brush = Brush.radialGradient(
            colors = listOf(
              Color(0xFF1E293B), // Slate 800
              Color(0xFF090D16)  // Almost black
            ),
            radius = 600f
          )
        )
        .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp)),
      contentAlignment = Alignment.Center
    ) {
      // Background Perspective Grid Desk Lines
      Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val deskY = h * 0.65f

        // Draw Desk Surface Plane
        val deskPath = Path().apply {
          moveTo(0f, deskY)
          lineTo(w, deskY)
          lineTo(w, h)
          lineTo(0f, h)
          close()
        }
        drawPath(
          path = deskPath,
          brush = Brush.verticalGradient(
            listOf(
              Color(0xFF131C2E),
              Color(0xFF0A0F1A)
            )
          )
        )

        // Desk front rim edge
        drawLine(
          color = Color(0xFF334155),
          start = Offset(0f, deskY),
          end = Offset(w, deskY),
          strokeWidth = 2.5f
        )

        // Perspective grid lines on desk
        val perspectiveVanishX = w * 0.5f
        val perspectiveVanishY = deskY - 40f
        for (i in -4..4) {
          val bottomX = w * 0.5f + (i * w * 0.16f)
          drawLine(
            color = TechBluePrimary.copy(alpha = 0.12f),
            start = Offset(perspectiveVanishX, perspectiveVanishY),
            end = Offset(bottomX, h),
            strokeWidth = 1f
          )
        }
      }

      // Upper Stage: Monitor (Center-Left) and CPU Tower (Right)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 14.dp)
          .offset(y = (-20).dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
      ) {
        // 1. MONITOR COMPONENT
        val isMonitorSelected = selectedComponent == HardwareComponentType.MONITOR
        val monitorScale by animateFloatAsState(
          if (isMonitorSelected) 1.06f else 0.98f,
          animationSpec = tween(220),
          label = "monitor_scale"
        )
        val monitorAlpha by animateFloatAsState(
          if (selectedComponent == HardwareComponentType.MONITOR) 1.0f else 0.85f,
          label = "monitor_alpha"
        )

        Box(
          modifier = Modifier
            .scale(monitorScale)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onSelectComponent(HardwareComponentType.MONITOR) }
            .padding(4.dp)
            .testTag("interactive_monitor_target"),
          contentAlignment = Alignment.BottomCenter
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Monitor Screen Frame
            Box(
              modifier = Modifier
                .size(width = 158.dp, height = 104.dp)
                .shadow(
                  elevation = if (isMonitorSelected) 14.dp else 4.dp,
                  shape = RoundedCornerShape(10.dp),
                  spotColor = if (isMonitorSelected) TechCyanAccent else Color.Black
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF0F172A))
                .border(
                  width = if (isMonitorSelected) 2.dp else 1.dp,
                  color = if (isMonitorSelected) TechCyanAccent.copy(alpha = pulseAlpha) else Color(0xFF334155),
                  shape = RoundedCornerShape(10.dp)
                )
                .padding(6.dp)
            ) {
              // Inner Display Screen Glass
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .clip(RoundedCornerShape(6.dp))
                  .background(
                    brush = Brush.verticalGradient(
                      listOf(
                        Color(0xFF0284C7).copy(alpha = 0.35f), // Cyan glow
                        Color(0xFF031525)
                      )
                    )
                  )
                  .border(0.5.dp, TechCyanAccent.copy(alpha = 0.3f), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
              ) {
                // Screen contents (Simulated high-tech UI)
                Column(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                  verticalArrangement = Arrangement.SpaceBetween
                ) {
                  // Top bar of OS window
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                      Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color(0xFFEF4444)))
                      Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color(0xFFF59E0B)))
                      Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color(0xFF10B981)))
                    }
                    Text(
                      text = "COMPUTER MASTER",
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                      ),
                      color = TechCyanAccent
                    )
                  }

                  // Simulated content graphs
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Box(
                      modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(TechBluePrimary.copy(alpha = 0.3f)),
                      contentAlignment = Alignment.Center
                    ) {
                      Icon(
                        imageVector = Icons.Default.Computer,
                        contentDescription = null,
                        tint = TechCyanAccent,
                        modifier = Modifier.size(16.dp)
                      )
                    }
                    Column {
                      Box(
                        modifier = Modifier
                          .width(60.dp)
                          .height(3.dp)
                          .clip(RoundedCornerShape(2.dp))
                          .background(Color.White.copy(alpha = 0.8f))
                      )
                      Spacer(modifier = Modifier.height(3.dp))
                      Box(
                        modifier = Modifier
                          .width(40.dp)
                          .height(2.5.dp)
                          .clip(RoundedCornerShape(2.dp))
                          .background(TechCyanAccent.copy(alpha = 0.7f))
                      )
                    }
                  }

                  // Bottom taskbar
                  Row(
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(6.dp)
                      .clip(RoundedCornerShape(2.dp))
                      .background(Color(0xFF0B1120).copy(alpha = 0.8f)),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(TechCyanAccent))
                  }
                }
              }

              // Power LED dot on bezel
              Box(
                modifier = Modifier
                  .align(Alignment.BottomCenter)
                  .offset(y = 3.dp)
                  .size(3.dp)
                  .clip(CircleShape)
                  .background(TechCyanAccent)
              )
            }

            // Monitor Stand Neck
            Box(
              modifier = Modifier
                .width(14.dp)
                .height(18.dp)
                .background(
                  brush = Brush.verticalGradient(
                    listOf(
                      Color(0xFF475569),
                      Color(0xFF1E293B)
                    )
                  )
                )
            )

            // Monitor Stand Base Plate
            Box(
              modifier = Modifier
                .width(54.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(
                  brush = Brush.horizontalGradient(
                    listOf(
                      Color(0xFF334155),
                      Color(0xFF64748B),
                      Color(0xFF334155)
                    )
                  )
                )
                .border(0.5.dp, Color(0xFF64748B), RoundedCornerShape(3.dp))
            )

            // Selection Label Pill
            if (isMonitorSelected) {
              Box(
                modifier = Modifier
                  .offset(y = 4.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(TechCyanAccent)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "MONITOR",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black
                  ),
                  color = NavyDarkest
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // 2. CPU / SYSTEM UNIT (Tower Chassis)
        val isCpuSelected = selectedComponent == HardwareComponentType.CPU
        val cpuScale by animateFloatAsState(
          if (isCpuSelected) 1.06f else 0.98f,
          animationSpec = tween(220),
          label = "cpu_scale"
        )

        Box(
          modifier = Modifier
            .scale(cpuScale)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onSelectComponent(HardwareComponentType.CPU) }
            .padding(4.dp)
            .testTag("interactive_cpu_target"),
          contentAlignment = Alignment.BottomCenter
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Tower Case
            Box(
              modifier = Modifier
                .size(width = 72.dp, height = 138.dp)
                .shadow(
                  elevation = if (isCpuSelected) 14.dp else 4.dp,
                  shape = RoundedCornerShape(10.dp),
                  spotColor = if (isCpuSelected) TechBluePrimary else Color.Black
                )
                .clip(RoundedCornerShape(10.dp))
                .background(
                  brush = Brush.verticalGradient(
                    listOf(
                      Color(0xFF1E293B),
                      Color(0xFF0F172A)
                    )
                  )
                )
                .border(
                  width = if (isCpuSelected) 2.dp else 1.dp,
                  color = if (isCpuSelected) TechBluePrimary.copy(alpha = pulseAlpha) else Color(0xFF334155),
                  shape = RoundedCornerShape(10.dp)
                )
                .padding(6.dp)
            ) {
              Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
              ) {
                // Front Panel Ports & Power Button
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // Glowing Power Button
                  Box(
                    modifier = Modifier
                      .size(10.dp)
                      .clip(CircleShape)
                      .background(TechBluePrimary)
                      .border(1.dp, Color.White, CircleShape)
                  )

                  // Dual USB 3.0 Ports
                  Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    Box(
                      modifier = Modifier
                        .size(width = 5.dp, height = 3.dp)
                        .background(TechCyanAccent)
                    )
                    Box(
                      modifier = Modifier
                        .size(width = 5.dp, height = 3.dp)
                        .background(TechCyanAccent)
                    )
                  }
                }

                // Internal Glass View with RGB Cooler & Fan
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF0A0E1A))
                    .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(6.dp)),
                  contentAlignment = Alignment.Center
                ) {
                  // Animated glowing cooler ring
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .background(
                        brush = Brush.sweepGradient(
                          listOf(
                            TechBluePrimary,
                            TechCyanAccent,
                            TechPurple,
                            TechBluePrimary
                          )
                        )
                      )
                      .padding(3.dp)
                  ) {
                    Box(
                      modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color(0xFF0B1120)),
                      contentAlignment = Alignment.Center
                    ) {
                      Icon(
                        imageVector = Icons.Default.DeveloperBoard,
                        contentDescription = null,
                        tint = TechCyanAccent,
                        modifier = Modifier.size(16.dp)
                      )
                    }
                  }
                }

                // Lower Honeycomb Intake Ventilation Grill
                Column(
                  modifier = Modifier.fillMaxWidth(),
                  verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                  for (r in 0..2) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                      for (c in 0..4) {
                        Box(
                          modifier = Modifier
                            .weight(1f)
                            .height(2.5.dp)
                            .clip(RoundedCornerShape(1.dp))
                            .background(Color(0xFF334155))
                        )
                      }
                    }
                  }
                }
              }
            }

            // Selection Label Pill
            if (isCpuSelected) {
              Box(
                modifier = Modifier
                  .offset(y = 4.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(TechBluePrimary)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "CPU TOWER",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black
                  ),
                  color = Color.White
                )
              }
            }
          }
        }
      }

      // Lower Stage: Keyboard & Mouse on Desk
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp)
          .align(Alignment.BottomCenter)
          .offset(y = (-14).dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // 3. KEYBOARD COMPONENT
        val isKeyboardSelected = selectedComponent == HardwareComponentType.KEYBOARD
        val keyboardScale by animateFloatAsState(
          if (isKeyboardSelected) 1.06f else 0.98f,
          animationSpec = tween(220),
          label = "keyboard_scale"
        )

        Box(
          modifier = Modifier
            .scale(keyboardScale)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onSelectComponent(HardwareComponentType.KEYBOARD) }
            .padding(4.dp)
            .testTag("interactive_keyboard_target"),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Keyboard Base Chassis
            Box(
              modifier = Modifier
                .size(width = 175.dp, height = 52.dp)
                .shadow(
                  elevation = if (isKeyboardSelected) 12.dp else 3.dp,
                  shape = RoundedCornerShape(8.dp),
                  spotColor = if (isKeyboardSelected) TechGreen else Color.Black
                )
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E293B))
                .border(
                  width = if (isKeyboardSelected) 2.dp else 1.dp,
                  color = if (isKeyboardSelected) TechGreen.copy(alpha = pulseAlpha) else Color(0xFF334155),
                  shape = RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
            ) {
              Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
              ) {
                // Row 1: Function Keys
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                  for (i in 0..11) {
                    Box(
                      modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(Color(0xFF334155))
                    )
                  }
                }

                // Row 2: Alphanumeric Keys
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                  for (i in 0..10) {
                    Box(
                      modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(Color(0xFF475569))
                    )
                  }
                }

                // Row 3: Bottom Spacebar Row
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(3.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Box(
                    modifier = Modifier
                      .size(width = 16.dp, height = 8.dp)
                      .clip(RoundedCornerShape(1.5.dp))
                      .background(Color(0xFF334155))
                  )
                  // Spacebar
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .height(8.dp)
                      .clip(RoundedCornerShape(2.dp))
                      .background(TechGreen.copy(alpha = 0.5f))
                  )
                  // Arrow Keys
                  Box(
                    modifier = Modifier
                      .size(width = 16.dp, height = 8.dp)
                      .clip(RoundedCornerShape(1.5.dp))
                      .background(Color(0xFF334155))
                  )
                }
              }
            }

            // Selection Label Pill
            if (isKeyboardSelected) {
              Box(
                modifier = Modifier
                  .offset(y = 3.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(TechGreen)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "KEYBOARD",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Black
                  ),
                  color = NavyDarkest
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // 4. MOUSE COMPONENT
        val isMouseSelected = selectedComponent == HardwareComponentType.MOUSE
        val mouseScale by animateFloatAsState(
          if (isMouseSelected) 1.08f else 0.98f,
          animationSpec = tween(220),
          label = "mouse_scale"
        )

        Box(
          modifier = Modifier
            .scale(mouseScale)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onSelectComponent(HardwareComponentType.MOUSE) }
            .padding(4.dp)
            .testTag("interactive_mouse_target"),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Ergonomic Mouse Body
            Box(
              modifier = Modifier
                .size(width = 34.dp, height = 52.dp)
                .shadow(
                  elevation = if (isMouseSelected) 12.dp else 3.dp,
                  shape = RoundedCornerShape(14.dp),
                  spotColor = if (isMouseSelected) TechPurple else Color.Black
                )
                .clip(RoundedCornerShape(14.dp))
                .background(
                  brush = Brush.verticalGradient(
                    listOf(
                      Color(0xFF334155),
                      Color(0xFF1E293B)
                    )
                  )
                )
                .border(
                  width = if (isMouseSelected) 2.dp else 1.dp,
                  color = if (isMouseSelected) TechPurple.copy(alpha = pulseAlpha) else Color(0xFF475569),
                  shape = RoundedCornerShape(14.dp)
                )
                .padding(3.dp)
            ) {
              Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                // Top: Left & Right Click Split with Scroll Wheel
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // Left button
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .height(16.dp)
                      .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 2.dp))
                      .background(Color(0xFF475569))
                  )

                  // Scroll wheel
                  Box(
                    modifier = Modifier
                      .width(6.dp)
                      .height(12.dp)
                      .clip(RoundedCornerShape(3.dp))
                      .background(TechCyanAccent)
                  )

                  // Right button
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .height(16.dp)
                      .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 2.dp))
                      .background(Color(0xFF475569))
                  )
                }

                // Palm Rest Contour Line
                Box(
                  modifier = Modifier
                    .width(18.dp)
                    .height(2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(TechPurple.copy(alpha = 0.6f))
                )

                // Optical Sensor Glow dot
                Box(
                  modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEF4444))
                )
              }
            }

            // Selection Label Pill
            if (isMouseSelected) {
              Box(
                modifier = Modifier
                  .offset(y = 3.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(TechPurple)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "MOUSE",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Black
                  ),
                  color = Color.White
                )
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Quick Component Switcher Pills
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      HardwareComponentType.entries.forEach { compType ->
        val isSelected = selectedComponent == compType
        val (label, icon, activeColor) = when (compType) {
          HardwareComponentType.MONITOR -> Triple(
            when (currentLanguage) { AppLanguage.BENGALI -> "মনিটর"; AppLanguage.HINDI -> "मॉनिटर"; else -> "Monitor" },
            Icons.Default.Computer,
            TechCyanAccent
          )
          HardwareComponentType.CPU -> Triple(
            when (currentLanguage) { AppLanguage.BENGALI -> "সিপিইউ"; AppLanguage.HINDI -> "सीपीयू"; else -> "CPU Tower" },
            Icons.Default.DeveloperBoard,
            TechBluePrimary
          )
          HardwareComponentType.KEYBOARD -> Triple(
            when (currentLanguage) { AppLanguage.BENGALI -> "কীবোর্ড"; AppLanguage.HINDI -> "कीबोर्ड"; else -> "Keyboard" },
            Icons.Default.Keyboard,
            TechGreen
          )
          HardwareComponentType.MOUSE -> Triple(
            when (currentLanguage) { AppLanguage.BENGALI -> "মাউস"; AppLanguage.HINDI -> "माउस"; else -> "Mouse" },
            Icons.Default.Mouse,
            TechPurple
          )
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) activeColor.copy(alpha = 0.2f) else NavyCard)
            .border(
              width = if (isSelected) 1.5.dp else 1.dp,
              color = if (isSelected) activeColor else NavyCardBorder,
              shape = RoundedCornerShape(12.dp)
            )
            .clickable { onSelectComponent(compType) }
            .padding(vertical = 10.dp, horizontal = 6.dp)
            .testTag("component_selector_${compType.name.lowercase()}"),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = icon,
              contentDescription = null,
              tint = if (isSelected) activeColor else TextSecondary,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = label,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp
              ),
              color = if (isSelected) TextPrimary else TextSecondary,
              maxLines = 1
            )
          }
        }
      }
    }
  }
}
