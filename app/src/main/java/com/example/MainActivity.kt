package com.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.update.InAppUpdateManager
import com.example.ui.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.ComputerMasterViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: ComputerMasterViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    handleUpdateIntent(intent)

    setContent {
      MyApplicationTheme {
        MainScreen(viewModel = viewModel)
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

