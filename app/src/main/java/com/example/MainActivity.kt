package com.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import com.example.data.update.InAppUpdateManager
import com.example.ui.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.ComputerMasterViewModel
import java.io.PrintWriter
import java.io.StringWriter

class MainActivity : ComponentActivity() {
  private val viewModel: ComputerMasterViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    if (savedInstanceState == null) {
      viewModel.soundManager.playStartup()
    }

    // Install a crash catcher so a silent crash writes its stack trace
    // to SharedPreferences instead of just killing the app with no trace.
    val prefs = getSharedPreferences("crash_log", MODE_PRIVATE)
    val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
      try {
        val sw = StringWriter()
        throwable.printStackTrace(PrintWriter(sw))
        prefs.edit().putString("last_crash", sw.toString()).commit()
      } catch (_: Throwable) {
        // ignore
      }
      defaultHandler?.uncaughtException(thread, throwable)
    }

    handleUpdateIntent(intent)

    setContent {
      val themeMode by viewModel.themeMode.collectAsState()

      var crashLog by remember {
        mutableStateOf(prefs.getString("last_crash", null))
      }

      MyApplicationTheme(themeMode = themeMode) {
        MainScreen(viewModel = viewModel)

        if (crashLog != null) {
          AlertDialog(
            onDismissRequest = {
              prefs.edit().remove("last_crash").apply()
              crashLog = null
            },
            confirmButton = {
              TextButton(onClick = {
                prefs.edit().remove("last_crash").apply()
                crashLog = null
              }) {
                Text("OK")
              }
            },
            title = { Text("Last crash details") },
            text = {
              Text(
                text = crashLog ?: "",
                modifier = Modifier.verticalScroll(rememberScrollState())
              )
            }
          )
        }
      }
    }
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    handleUpdateIntent(intent)
  }

  private fun handleUpdateIntent(intent: Intent?) {
    if (intent?.getBooleanExtra(InAppUpdateManager.EXTRA_LAUNCH_UPDATE, false) == true) {
      viewModel.openUpdateDialog()
    }
  }
}

