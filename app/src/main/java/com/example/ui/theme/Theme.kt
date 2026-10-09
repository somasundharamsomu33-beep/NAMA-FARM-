package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val UzhavanColorScheme = lightColorScheme(
  primary = UzhavanDarkGreen,
  onPrimary = Color.White,
  primaryContainer = UzhavanLightGreen,
  onPrimaryContainer = UzhavanDarkGreen,
  secondary = UzhavanAccentGreen,
  onSecondary = Color.White,
  secondaryContainer = UzhavanContainerGreen,
  onSecondaryContainer = UzhavanDarkGreen,
  tertiary = UzhavanVibrantGreen,
  onTertiary = Color.White,
  background = UzhavanBackground,
  onBackground = UzhavanTextPrimary,
  surface = UzhavanSurface,
  onSurface = UzhavanTextPrimary,
  surfaceVariant = UzhavanSurfaceVariant,
  onSurfaceVariant = UzhavanTextSecondary,
  outline = UzhavanBorder,
  outlineVariant = Color(0xFFD1DBD4),
  error = UzhavanRed,
  onError = Color.White
)

@Composable
fun UzhavanMarketTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = UzhavanColorScheme,
    typography = Typography,
    content = content
  )
}
