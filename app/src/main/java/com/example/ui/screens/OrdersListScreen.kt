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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.ui.components.NavItem
import com.example.ui.components.UzhavanBottomNav
import com.example.ui.components.UzhavanProductImage
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun OrdersListScreen(
  orders: List<Order>,
  isFarmerMode: Boolean,
  isTamil: Boolean,
  onOrderClick: (Order) -> Unit,
  currentTab: NavItem = NavItem.ORDERS,
  onTabSelected: (NavItem) -> Unit
) {
  var selectedFilterTab by remember { mutableIntStateOf(0) } // 0: All, 1: Active, 2: Completed

  val filteredOrders = remember(selectedFilterTab, orders) {
    when (selectedFilterTab) {
      1 -> orders.filter { it.status != OrderStatus.DELIVERED }
      2 -> orders.filter { it.status == OrderStatus.DELIVERED }
      else -> orders
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    Column(
      modifier = Modifier
        .weight(1f)
        .statusBarsPadding()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      Text(
        text = if (isTamil) "என் ஆர்டர்கள்" else "My Orders",
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary,
          fontSize = 24.sp
        )
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Filter Tabs: [All] | [Active] | [Completed] (Screen 14)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(42.dp)
          .clip(RoundedCornerShape(20.dp))
          .background(Color(0xFFF1F5F2))
          .padding(3.dp)
      ) {
        listOf("All", "Active", "Completed").forEachIndexed { index, label ->
          val isSelected = selectedFilterTab == index
          val translatedLabel = if (isTamil) {
            when (index) {
              0 -> "அனைத்தும்"
              1 -> "செயலில்"
              else -> "முடிந்தவை"
            }
          } else label

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(16.dp))
              .background(if (isSelected) UzhavanDarkGreen else Color.Transparent)
              .clickable { selectedFilterTab = index }
              .testTag("orders_filter_$label"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = translatedLabel,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else UzhavanTextSecondary,
                fontSize = 13.sp
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(filteredOrders) { order ->
          val firstItem = order.items.firstOrNull()

          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
            border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOrderClick(order) }
              .testTag("order_item_${order.orderNumber}")
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Product image strictly verified or clean placeholder
              UzhavanProductImage(
                verifiedDrawableRes = firstItem?.verifiedDrawableRes,
                productName = firstItem?.commodityName ?: "Produce",
                altText = firstItem?.commodityName ?: "Produce",
                modifier = Modifier.size(64.dp)
              )

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = firstItem?.commodityName ?: "Produce Order",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanTextPrimary,
                    fontSize = 16.sp
                  )
                )
                Text(
                  text = "${firstItem?.quantityKg ?: 0} kg • ₹ ${order.grandTotal.toInt()}",
                  style = MaterialTheme.typography.bodyMedium.copy(
                    color = UzhavanTextSecondary,
                    fontSize = 13.sp
                  )
                )
                Text(
                  text = order.datePlaced,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = UzhavanTextSecondary,
                    fontSize = 11.sp
                  )
                )
              }

              Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = if (order.status == OrderStatus.DELIVERED) Color(0xFFE8F5E9) else Color(0xFFE0F2FE)
                ) {
                  Text(
                    text = order.status.label,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = if (order.status == OrderStatus.DELIVERED) UzhavanDarkGreen else Color(0xFF0369A1),
                      fontSize = 11.sp
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }

                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                  contentDescription = "Details",
                  tint = UzhavanTextSecondary,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          }
        }
      }
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
