package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PaymentTransaction
import com.example.data.repository.ListingRepository
import com.example.ui.components.NavItem
import com.example.ui.components.UzhavanBottomNav
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun PaymentHistoryScreen(
  payments: List<PaymentTransaction> = ListingRepository.payments,
  isTamil: Boolean,
  currentTab: NavItem = NavItem.EARNINGS,
  onTabSelected: (NavItem) -> Unit
) {
  val totalEarnings = payments.sumOf { it.amount }

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
        text = if (isTamil) "வருவாய் & பணப்பரிவர்த்தனைகள்" else "Payment History",
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary,
          fontSize = 24.sp
        )
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Total Earnings Banner Card
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = UzhavanDarkGreen),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Text(
            text = if (isTamil) "மொத்த வருவாய் (நேரடி விற்பனை)" else "Total Earnings (Direct Sales)",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = Color(0xFFD1FAE5),
              fontSize = 13.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "₹ ${totalEarnings.toInt()}",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = Color.White,
              fontSize = 28.sp
            )
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = if (isTamil) "4 ஆர்டர்கள் வெற்றிகரமாக பணம் விடுவிக்கப்பட்டது" else "4 orders settled directly to bank account",
            style = MaterialTheme.typography.bodySmall.copy(
              color = Color(0xFFA7F3D0),
              fontSize = 12.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = if (isTamil) "சமீபத்திய பரிவர்த்தனைகள்" else "Recent Transactions",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary,
          fontSize = 16.sp
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(payments) { item ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
            border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("payment_record_${item.id}")
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(UzhavanLightGreen),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Receipt,
                    contentDescription = null,
                    tint = UzhavanDarkGreen,
                    modifier = Modifier.size(22.dp)
                  )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                  Text(
                    text = item.date,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanTextPrimary,
                      fontSize = 15.sp
                    )
                  )
                  Text(
                    text = item.orderNumber,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary,
                      fontSize = 12.sp
                    )
                  )
                }
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "+ ₹ ${item.amount.toInt()}",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanDarkGreen,
                    fontSize = 16.sp
                  )
                )
                Text(
                  text = item.method,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = UzhavanTextSecondary,
                    fontSize = 11.sp
                  )
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
      isFarmerMode = true,
      isTamil = isTamil
    )
  }
}
