package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.AppUpdateInfo
import com.example.data.update.UpdateUiState
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.util.AppLanguage
import com.example.util.AppStrings

@Composable
fun UpdateAvailableDialog(
  updateState: UpdateUiState,
  currentLanguage: AppLanguage,
  currentVersionName: String,
  onUpdateNow: (AppUpdateInfo) -> Unit,
  onLater: (Int?) -> Unit,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {
  val targetInfo = when (updateState) {
    is UpdateUiState.UpdateAvailable -> updateState.info
    is UpdateUiState.Downloading -> updateState.info
    is UpdateUiState.ReadyToInstall -> updateState.info
    else -> null
  } ?: return

  AlertDialog(
    onDismissRequest = { onLater(targetInfo.versionCode) },
    properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
    modifier = modifier.testTag("update_available_dialog"),
    containerColor = NavyCardElevated,
    shape = RoundedCornerShape(24.dp),
    tonalElevation = 6.dp,
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(
              Brush.linearGradient(listOf(TechBluePrimary, TechCyanAccent))
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.SystemUpdate,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
          )
        }

        Column {
          Text(
            text = AppStrings.updateAvailableDialogTitle(currentLanguage),
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp
            ),
            color = TextPrimary
          )
          Text(
            text = "Computer Master",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
            color = TechCyanAccent
          )
        }
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp)
      ) {
        // Version Comparison Badge Row
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NavyDark)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          // Current Version Box
          Column {
            Text(
              text = AppStrings.updateCurrentVersionBadge(currentLanguage),
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TextTertiary
            )
            Text(
              text = "v$currentVersionName",
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
              color = TextSecondary
            )
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TechCyanAccent,
            modifier = Modifier.size(16.dp)
          )

          // New Version Box
          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = AppStrings.updateNewVersionBadge(currentLanguage),
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TechAmber
            )
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = TechAmber,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = "v${targetInfo.versionName}",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = TechAmber
              )
            }
          }
        }

        // File size and release date badges
        if (targetInfo.fileSize != null || targetInfo.releaseDate != null) {
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            targetInfo.fileSize?.let { size ->
              Surface(
                color = NavyCard,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NavyCardBorder)
              ) {
                Text(
                  text = "Size: $size",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                  color = TextSecondary,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }

            targetInfo.releaseDate?.let { date ->
              Surface(
                color = NavyCard,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NavyCardBorder)
              ) {
                Text(
                  text = "Released: $date",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                  color = TextSecondary,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // What's New Title
        Text(
          text = AppStrings.updateWhatsNew(currentLanguage),
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = TextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Release Notes Content in Scrollable Card
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(NavyCard)
            .border(1.dp, NavyCardBorder, RoundedCornerShape(12.dp))
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
        ) {
          Text(
            text = targetInfo.getLocalizedMessage(currentLanguage),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 18.sp),
            color = TextSecondary
          )
        }

        // Live Downloading Progress Indicator
        if (updateState is UpdateUiState.Downloading) {
          Spacer(modifier = Modifier.height(16.dp))
          Column(modifier = Modifier.fillMaxWidth()) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = AppStrings.updateDownloading(currentLanguage),
                style = MaterialTheme.typography.labelSmall,
                color = TechCyanAccent
              )
              Text(
                text = "${(updateState.progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = TechCyanAccent
              )
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
              progress = { updateState.progress },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = TechCyanAccent,
              trackColor = NavyCard
            )
          }
        } else if (updateState is UpdateUiState.ReadyToInstall) {
          Spacer(modifier = Modifier.height(12.dp))
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TechGreen, modifier = Modifier.size(16.dp))
            Text(
              text = AppStrings.updateReadyToInstall(currentLanguage),
              style = MaterialTheme.typography.labelSmall,
              color = TechGreen
            )
          }
        } else if (updateState is UpdateUiState.Error) {
          Spacer(modifier = Modifier.height(12.dp))
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = TechRed, modifier = Modifier.size(16.dp))
            Text(
              text = updateState.message,
              style = MaterialTheme.typography.labelSmall,
              color = TechRed
            )
          }
        }
      }
    },
    confirmButton = {
      when (updateState) {
        is UpdateUiState.Downloading -> {
          Button(
            onClick = {},
            enabled = false,
            colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
            shape = RoundedCornerShape(12.dp)
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(16.dp),
              color = Color.White,
              strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = AppStrings.updateDownloading(currentLanguage),
              style = MaterialTheme.typography.labelMedium
            )
          }
        }
        is UpdateUiState.ReadyToInstall -> {
          Button(
            onClick = { onUpdateNow(targetInfo) },
            colors = ButtonDefaults.buttonColors(containerColor = TechGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.testTag("install_now_button")
          ) {
            Icon(Icons.Default.SystemUpdate, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Install APK",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
        is UpdateUiState.Error -> {
          Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = TechAmber),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.testTag("update_retry_button")
          ) {
            Text(
              text = AppStrings.updateRetry(currentLanguage),
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
        else -> {
          Button(
            onClick = { onUpdateNow(targetInfo) },
            colors = ButtonDefaults.buttonColors(
              containerColor = TechBluePrimary,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.testTag("update_now_button")
          ) {
            Icon(
              imageVector = Icons.Default.CloudDownload,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = AppStrings.updateNow(currentLanguage),
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    },
    dismissButton = {
      OutlinedButton(
        onClick = { onLater(targetInfo.versionCode) },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
        border = androidx.compose.foundation.BorderStroke(1.dp, NavyCardBorder),
        modifier = Modifier.testTag("update_later_button")
      ) {
        Text(
          text = AppStrings.updateLater(currentLanguage),
          style = MaterialTheme.typography.labelMedium
        )
      }
    }
  )
}
