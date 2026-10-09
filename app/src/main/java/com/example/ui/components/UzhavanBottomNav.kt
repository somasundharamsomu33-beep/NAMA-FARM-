package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanTextSecondary

enum class NavItem(val title: String, val tamilTitle: String) {
  HOME("Home", "முகப்பு"),
  LISTINGS("Listings", "பட்டியல்கள்"),
  SEARCH("Search", "தேடல்"),
  ORDERS("Orders", "ஆர்டர்கள்"),
  EARNINGS("Earnings", "வருவாய்"),
  PROFILE("Profile", "சுயவிவரம்")
}

@Composable
fun UzhavanBottomNav(
  currentTab: NavItem,
  onTabSelected: (NavItem) -> Unit,
  isFarmerMode: Boolean,
  isTamil: Boolean,
  modifier: Modifier = Modifier
) {
  val tabs = if (isFarmerMode) {
    listOf(NavItem.HOME, NavItem.LISTINGS, NavItem.ORDERS, NavItem.EARNINGS, NavItem.PROFILE)
  } else {
    listOf(NavItem.HOME, NavItem.SEARCH, NavItem.ORDERS, NavItem.PROFILE)
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(Color.White)
      .navigationBarsPadding()
  ) {
    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      tabs.forEach { item ->
        val isSelected = currentTab == item
        val label = if (isTamil) item.tamilTitle else item.title

        val (selectedIcon, unselectedIcon) = when (item) {
          NavItem.HOME -> Icons.Filled.Home to Icons.Outlined.Home
          NavItem.LISTINGS -> Icons.Filled.Storefront to Icons.Outlined.Storefront
          NavItem.SEARCH -> Icons.Filled.Search to Icons.Outlined.Search
          NavItem.ORDERS -> Icons.Filled.ReceiptLong to Icons.Outlined.ReceiptLong
          NavItem.EARNINGS -> Icons.Filled.AccountBalanceWallet to Icons.Outlined.AccountBalanceWallet
          NavItem.PROFILE -> Icons.Filled.Person to Icons.Outlined.Person
        }

        val interactionSource = remember { MutableInteractionSource() }

        Column(
          modifier = Modifier
            .weight(1f)
            .clickable(
              interactionSource = interactionSource,
              indication = null,
              onClick = { onTabSelected(item) }
            )
            .testTag("nav_item_${item.name.lowercase()}"),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = label,
            tint = if (isSelected) UzhavanDarkGreen else UzhavanTextSecondary,
            modifier = Modifier.size(24.dp)
          )
          Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
              color = if (isSelected) UzhavanDarkGreen else UzhavanTextSecondary
            ),
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      }
    }
  }
}
