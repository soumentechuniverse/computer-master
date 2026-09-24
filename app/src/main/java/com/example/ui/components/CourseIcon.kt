package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Javascript
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechBluePrimary
import com.example.ui.theme.TechCyanAccent
import com.example.ui.theme.TechGreen
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechPurple

@Composable
fun CourseIcon(
  iconName: String,
  modifier: Modifier = Modifier,
  size: Dp = 48.dp,
  iconSize: Dp = 26.dp,
) {
  val (icon, gradient) = getIconAndGradient(iconName)

  Box(
    modifier = modifier
      .size(size)
      .background(
        brush = gradient,
        shape = RoundedCornerShape(14.dp)
      ),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = Color.White,
      modifier = Modifier.size(iconSize)
    )
  }
}

private fun getIconAndGradient(iconName: String): Pair<ImageVector, Brush> {
  return when (iconName.lowercase()) {
    // 1. Computer Basics → Desktop computer
    "computer", "desktop", "fundamentals" -> Pair(
      Icons.Default.Computer,
      Brush.linearGradient(listOf(Color(0xFF1E40AF), TechCyanAccent))
    )
    // 2. Computer Hardware → Hardware / Chip
    "hardware" -> Pair(
      Icons.Default.Memory,
      Brush.linearGradient(listOf(Color(0xFF2563EB), Color(0xFF60A5FA)))
    )
    // 3. Software
    "software" -> Pair(
      Icons.Default.Code,
      Brush.linearGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7)))
    )
    // 4. Operating Systems
    "os", "operatingsystem", "operating_systems" -> Pair(
      Icons.Default.Terminal,
      Brush.linearGradient(listOf(Color(0xFF0F766E), Color(0xFF14B8A6)))
    )
    // 5. Troubleshooting
    "troubleshooting", "repair", "tools" -> Pair(
      Icons.Default.Build,
      Brush.linearGradient(listOf(Color(0xFFE11D48), Color(0xFFFB7185)))
    )
    // 6. Windows & File Management → Folder/files
    "folder", "files", "window", "windows" -> Pair(
      Icons.Default.Folder,
      Brush.linearGradient(listOf(Color(0xFF0284C7), Color(0xFF38BDF8)))
    )
    // 3. Keyboard & Typing → Keyboard
    "keyboard", "typing" -> Pair(
      Icons.Default.Keyboard,
      Brush.linearGradient(listOf(Color(0xFF0D9488), Color(0xFF2DD4BF)))
    )
    // 4. Microsoft Word → Document
    "document", "word", "doc" -> Pair(
      Icons.Default.Description,
      Brush.linearGradient(listOf(Color(0xFF2563EB), Color(0xFF60A5FA)))
    )
    // 5. Microsoft Excel → Spreadsheet/chart
    "spreadsheet", "excel", "chart" -> Pair(
      Icons.Default.TableChart,
      Brush.linearGradient(listOf(Color(0xFF059669), TechGreen))
    )
    // 6. Microsoft PowerPoint → Presentation
    "presentation", "powerpoint", "slides" -> Pair(
      Icons.Default.Slideshow,
      Brush.linearGradient(listOf(Color(0xFFD97706), Color(0xFFF59E0B)))
    )
    // 7. Internet & Email → Globe/mail
    "globe", "internet", "email", "mail" -> Pair(
      Icons.Default.Public,
      Brush.linearGradient(listOf(Color(0xFF0284C7), TechCyanAccent))
    )
    // 8. Computer Networking → Network nodes
    "network", "networking", "nodes" -> Pair(
      Icons.Default.Hub,
      Brush.linearGradient(listOf(TechIndigo, Color(0xFF818CF8)))
    )
    // 9. Programming Fundamentals → Code
    "code", "programming", "logic" -> Pair(
      Icons.Default.Code,
      Brush.linearGradient(listOf(Color(0xFF2563EB), TechCyanAccent))
    )
    // 10. Python → Python/programming
    "python", "dataobject" -> Pair(
      Icons.Default.DataObject,
      Brush.linearGradient(listOf(Color(0xFF0284C7), Color(0xFFFBBF24)))
    )
    // 11. C/C++ → Code/compiler
    "compiler", "cpp", "c", "c_cpp" -> Pair(
      Icons.Default.Memory,
      Brush.linearGradient(listOf(Color(0xFF4F46E5), Color(0xFF9333EA)))
    )
    // 12. JavaScript → Web/code
    "javascript", "js" -> Pair(
      Icons.Default.Javascript,
      Brush.linearGradient(listOf(Color(0xFFD97706), Color(0xFFFDE047)))
    )
    // 13. Web Development → Browser
    "browser", "web", "html", "css" -> Pair(
      Icons.Default.Web,
      Brush.linearGradient(listOf(Color(0xFFEA580C), Color(0xFFF97316)))
    )
    // 14. Database & SQL → Database
    "database", "sql", "storage" -> Pair(
      Icons.Default.Storage,
      Brush.linearGradient(listOf(Color(0xFF0369A1), TechIndigo))
    )
    // 15. App Development → Smartphone
    "smartphone", "mobile", "android", "appdev" -> Pair(
      Icons.Default.PhoneAndroid,
      Brush.linearGradient(listOf(Color(0xFF059669), TechGreen))
    )
    // 16. Linux → Terminal
    "terminal", "linux", "cli", "bash" -> Pair(
      Icons.Default.Terminal,
      Brush.linearGradient(listOf(Color(0xFF334155), Color(0xFF64748B)))
    )
    // 17. Cybersecurity Fundamentals → Shield/lock
    "shield", "lock", "cybersecurity", "security" -> Pair(
      Icons.Default.Security,
      Brush.linearGradient(listOf(Color(0xFFDC2626), Color(0xFFF87171)))
    )
    // 18. Cloud Computing → Cloud
    "cloud", "cloudcomputing" -> Pair(
      Icons.Default.Cloud,
      Brush.linearGradient(listOf(Color(0xFF0284C7), Color(0xFF60A5FA)))
    )
    // 19. Advanced IT → Server
    "server", "it", "advanced_it", "dns" -> Pair(
      Icons.Default.Dns,
      Brush.linearGradient(listOf(TechIndigo, Color(0xFF818CF8)))
    )
    // 20. Artificial Intelligence → AI/neural network
    "ai", "brain", "artificial_intelligence", "neural" -> Pair(
      Icons.Default.Psychology,
      Brush.linearGradient(listOf(TechPurple, Color(0xFFC084FC)))
    )
    else -> Pair(
      Icons.Default.Computer,
      Brush.linearGradient(listOf(TechBluePrimary, TechCyanAccent))
    )
  }
}

