package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.NavItem
import com.example.ui.components.UzhavanBottomNav
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun ProfileScreen(
  farmerName: String = "Ramasamy",
  district: String = "Tiruvannamalai",
  isFarmerMode: Boolean,
  isTamil: Boolean,
  onLanguageToggle: () -> Unit,
  onSwitchMode: () -> Unit,
  onLogout: () -> Unit,
  currentTab: NavItem = NavItem.PROFILE,
  onTabSelected: (NavItem) -> Unit
) {
  var notificationsEnabled by remember { mutableStateOf(true) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    Column(
      modifier = Modifier
        .weight(1f)
        .statusBarsPadding()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      Text(
        text = if (isTamil) "சுயவிவரம் & அமைப்புகள்" else "Profile / Settings",
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary,
          fontSize = 24.sp
        )
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Header Profile Card (Matching Screen 16)
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
        border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(60.dp)
              .clip(CircleShape)
              .background(UzhavanLightGreen),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = UzhavanDarkGreen,
              modifier = Modifier.size(34.dp)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column {
            Text(
              text = if (isFarmerMode) farmerName else "Hotel Sunrise Buyer",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary,
                fontSize = 19.sp
              )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = if (isFarmerMode) "Farmer • $district" else "Institutional Buyer • $district",
              style = MaterialTheme.typography.bodyMedium.copy(
                color = UzhavanTextSecondary,
                fontSize = 14.sp
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Profile Options List (Matching Screen 16)
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          ProfileOptionRow(
            icon = Icons.Default.Person,
            title = if (isTamil) "என் விவரம்" else "My Profile",
            onClick = {},
            testTag = "opt_my_profile"
          )
          HorizontalDivider(color = Color(0xFFF3F4F6))

          ProfileOptionRow(
            icon = Icons.Default.AccountBalance,
            title = if (isTamil) "வங்கி விவரங்கள்" else "Bank Details",
            trailingText = "SBI •••• 4589",
            onClick = {},
            testTag = "opt_bank_details"
          )
          HorizontalDivider(color = Color(0xFFF3F4F6))

          // Notifications Switch
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = UzhavanTextSecondary,
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.width(14.dp))
              Text(
                text = if (isTamil) "அறிவிப்புகள்" else "Notifications",
                style = MaterialTheme.typography.bodyLarge.copy(
                  fontWeight = FontWeight.Medium,
                  color = UzhavanTextPrimary
                )
              )
            }
            Switch(
              checked = notificationsEnabled,
              onCheckedChange = { notificationsEnabled = it },
              colors = SwitchDefaults.colors(checkedThumbColor = UzhavanDarkGreen, checkedTrackColor = UzhavanLightGreen)
            )
          }
          HorizontalDivider(color = Color(0xFFF3F4F6))

          // Language Option (Screen 16)
          ProfileOptionRow(
            icon = Icons.Default.Language,
            title = if (isTamil) "மொழி / Language" else "Language",
            trailingText = if (isTamil) "தமிழ்" else "English",
            onClick = onLanguageToggle,
            testTag = "opt_language"
          )
          HorizontalDivider(color = Color(0xFFF3F4F6))

          // Switch Role Mode
          ProfileOptionRow(
            icon = Icons.Default.SwapHoriz,
            title = if (isFarmerMode) "Switch to Buyer Mode" else "Switch to Farmer Mode",
            trailingText = if (isFarmerMode) "Farmer" else "Buyer",
            onClick = onSwitchMode,
            testTag = "opt_switch_mode"
          )
          HorizontalDivider(color = Color(0xFFF3F4F6))

          ProfileOptionRow(
            icon = Icons.Default.HelpOutline,
            title = if (isTamil) "உதவி & ஆதரவு" else "Help & Support",
            onClick = {},
            testTag = "opt_help"
          )
          HorizontalDivider(color = Color(0xFFF3F4F6))

          ProfileOptionRow(
            icon = Icons.Default.Logout,
            title = if (isTamil) "வெளியேறு" else "Logout",
            tint = Color(0xFFDC2626),
            onClick = onLogout,
            testTag = "opt_logout"
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))
    }

    // Bottom Navigation Bar
    UzhavanBottomNav(
      currentTab = currentTab,
      onTabSelected = onTabSelected,
      isFarmerMode = isFarmerMode,
      isTamil = isTamil
    )
  }
}

@Composable
private fun ProfileOptionRow(
  icon: ImageVector,
  title: String,
  trailingText: String? = null,
  tint: Color = UzhavanTextSecondary,
  onClick: () -> Unit,
  testTag: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(horizontal = 16.dp, vertical = 15.dp)
      .testTag(testTag),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = tint,
        modifier = Modifier.size(22.dp)
      )
      Spacer(modifier = Modifier.width(14.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.Medium,
          color = if (tint == Color(0xFFDC2626)) tint else UzhavanTextPrimary
        )
      )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
      if (trailingText != null) {
        Text(
          text = trailingText,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = UzhavanDarkGreen,
            fontWeight = FontWeight.Bold
          )
        )
        Spacer(modifier = Modifier.width(6.dp))
      }
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = UzhavanTextSecondary,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}
