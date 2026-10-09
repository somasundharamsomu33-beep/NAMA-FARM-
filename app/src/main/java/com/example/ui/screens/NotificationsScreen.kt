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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.data.model.NotificationItem
import com.example.data.repository.ListingRepository
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun NotificationsScreen(
  notifications: List<NotificationItem> = ListingRepository.notifications,
  isTamil: Boolean,
  onBack: () -> Unit
) {
  var selectedCategory by remember { mutableStateOf("All") }
  val filterCategories = listOf("All", "Orders", "Payments", "Offers")

  val filteredNotifications = remember(selectedCategory, notifications) {
    if (selectedCategory == "All") notifications
    else notifications.filter { it.category.equals(selectedCategory, ignoreCase = true) }
  }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // Top Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBack,
          modifier = Modifier.testTag("notifications_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = UzhavanDarkGreen
          )
        }
        Text(
          text = if (isTamil) "அறிவிப்புகள்" else "Notifications",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 20.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Filter Chips: [All] [Orders] [Payments] [Offers] (Screen 17)
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(filterCategories) { cat ->
          val isSelected = selectedCategory == cat
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isSelected) UzhavanDarkGreen else Color(0xFFF1F5F2),
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .clickable { selectedCategory = cat }
              .testTag("filter_${cat.lowercase()}")
          ) {
            Text(
              text = cat,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else UzhavanTextSecondary,
                fontSize = 13.sp
              ),
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(filteredNotifications) { item ->
          val (icon, iconTint, iconBg) = when (item.category) {
            "Payments" -> Triple(Icons.Default.AccountBalanceWallet, Color(0xFF16A34A), Color(0xFFDCFCE7))
            "Orders" -> Triple(Icons.Default.LocalShipping, Color(0xFF0284C7), Color(0xFFE0F2FE))
            "Offers" -> Triple(Icons.Default.Message, Color(0xFF9333EA), Color(0xFFF3E8FF))
            else -> Triple(Icons.Default.TrendingUp, Color(0xFFD97706), Color(0xFFFEF3C7))
          }

          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
            border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .background(iconBg),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = icon,
                  contentDescription = null,
                  tint = iconTint,
                  modifier = Modifier.size(22.dp)
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanTextPrimary,
                      fontSize = 15.sp
                    )
                  )
                  Text(
                    text = item.timestamp,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary,
                      fontSize = 11.sp
                    )
                  )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = item.message,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    color = UzhavanTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                  )
                )
              }
            }
          }
        }
      }
    }
  }
}
