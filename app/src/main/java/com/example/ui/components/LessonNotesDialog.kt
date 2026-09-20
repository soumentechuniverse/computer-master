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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyCardBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ComputerMasterViewModel

@Composable
fun LessonNotesDialog(
  courseId: String,
  courseTitle: String,
  lessonTitle: String,
  viewModel: ComputerMasterViewModel,
  onDismiss: () -> Unit
) {
  val allNotes by viewModel.notes.collectAsState()
  val courseNotes = allNotes.filter { it.courseId == courseId }

  var noteTitle by remember { mutableStateOf("") }
  var noteContent by remember { mutableStateOf("") }
  var isAddingNote by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = NavyDark,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp)
        .border(1.dp, NavyCardBorder, RoundedCornerShape(20.dp))
        .testTag("lesson_notes_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        // Header Row
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
              imageVector = Icons.Default.NoteAdd,
              contentDescription = null,
              tint = TechCyanAccent,
              modifier = Modifier.size(22.dp)
            )
            Column {
              Text(
                text = "Study Notes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
              )
              Text(
                text = lessonTitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TextSecondary,
                maxLines = 1
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextSecondary,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isAddingNote) {
          // Add Note Form
          Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
              value = noteTitle,
              onValueChange = { noteTitle = it },
              label = { Text("Note Title") },
              placeholder = { Text("e.g. CPU Machine Cycle") },
              singleLine = true,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("note_title_input"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TechCyanAccent,
                unfocusedBorderColor = NavyCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = noteContent,
              onValueChange = { noteContent = it },
              label = { Text("Key Takeaways / Questions") },
              placeholder = { Text("Write your thoughts, formulas, or reminders...") },
              minLines = 3,
              maxLines = 5,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("note_content_input"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TechCyanAccent,
                unfocusedBorderColor = NavyCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Button(
                onClick = { isAddingNote = false },
                colors = ButtonDefaults.buttonColors(containerColor = NavyCard),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("Cancel", color = TextSecondary)
              }

              Button(
                onClick = {
                  if (noteTitle.isNotBlank() && noteContent.isNotBlank()) {
                    viewModel.addNote(
                      courseId = courseId,
                      courseTitle = courseTitle,
                      title = noteTitle.trim(),
                      content = noteContent.trim()
                    )
                    noteTitle = ""
                    noteContent = ""
                    isAddingNote = false
                  }
                },
                enabled = noteTitle.isNotBlank() && noteContent.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("save_note_button")
              ) {
                Text("Save Note", fontWeight = FontWeight.Bold)
              }
            }
          }
        } else {
          // List of Notes
          if (courseNotes.isEmpty()) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(NavyCard)
                .padding(20.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "No notes yet for this course",
                  style = MaterialTheme.typography.bodyMedium,
                  color = TextSecondary
                )
                Text(
                  text = "Capture insights and key points as you learn",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = TextTertiary
                )
              }
            }
          } else {
            LazyColumn(
              modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              items(courseNotes, key = { it.id }) { note ->
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(NavyCard)
                    .border(1.dp, NavyCardBorder, RoundedCornerShape(10.dp))
                    .padding(10.dp)
                ) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Column(modifier = Modifier.weight(1f)) {
                      Text(
                        text = note.title,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = TechCyanAccent
                      )
                      Spacer(modifier = Modifier.height(2.dp))
                      Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextPrimary
                      )
                      Spacer(modifier = Modifier.height(4.dp))
                      val formattedDate = remember(note.dateAdded) {
                        java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
                          .format(java.util.Date(note.dateAdded))
                      }
                      Text(
                        text = "Saved on $formattedDate",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = TextTertiary
                      )
                    }

                    IconButton(
                      onClick = { viewModel.deleteNote(note.id) },
                      modifier = Modifier.size(28.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Note",
                        tint = TextTertiary,
                        modifier = Modifier.size(16.dp)
                      )
                    }
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { isAddingNote = true },
            modifier = Modifier
              .fillMaxWidth()
              .height(44.dp)
              .testTag("create_note_button"),
            colors = ButtonDefaults.buttonColors(containerColor = TechBluePrimary),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Add New Note",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }
  }
}
