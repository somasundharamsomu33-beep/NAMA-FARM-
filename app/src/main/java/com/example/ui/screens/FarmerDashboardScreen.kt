package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.NavItem
import com.example.ui.components.UzhavanBottomNav
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun FarmerDashboardScreen(
  isTamil: Boolean,
  farmerName: String = "Ramasamy",
  onNavigateToListProduce: () -> Unit,
  onNavigateToListings: () -> Unit,
  onNavigateToOrders: () -> Unit,
  onNavigateToPayments: () -> Unit,
  onNavigateToPriceAdvisor: () -> Unit,
  onNavigateToPriceDashboard: () -> Unit,
  onNavigateToVoiceAssistant: () -> Unit = {},
  onNavigateToPlanProfit: () -> Unit,
  onNavigateToLiveMandi: () -> Unit,
  onNavigateToFarmingGuide: () -> Unit,
  onNavigateToNotifications: () -> Unit,
  onNavigateToProfile: () -> Unit,
  currentTab: NavItem = NavItem.HOME,
  onTabSelected: (NavItem) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    // Scrollable Content
    Column(
      modifier = Modifier
        .weight(1f)
        .statusBarsPadding()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // Top Header (Matching Screen 4)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "${if (isTamil) StringsTa.goodMorning else StringsEn.goodMorning},",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = UzhavanTextSecondary,
              fontSize = 14.sp
            )
          )
          Text(
            text = "$farmerName 🌾",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 22.sp
            )
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onNavigateToNotifications,
            modifier = Modifier.testTag("notifications_icon_button")
          ) {
            BadgedBox(
              badge = {
                Badge(
                  containerColor = Color(0xFFEF4444),
                  modifier = Modifier.size(8.dp)
                )
              }
            ) {
              Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = UzhavanDarkGreen,
                modifier = Modifier.size(26.dp)
              )
            }
          }

          Spacer(modifier = Modifier.width(4.dp))

          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(UzhavanLightGreen)
              .clickable { onNavigateToProfile() }
              .testTag("profile_avatar_button"),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = "Profile",
              tint = UzhavanDarkGreen,
              modifier = Modifier.size(24.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Hero Banner Card: "Better Price for Your Produce" (Matching Screen 4)
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1.2f)) {
            Text(
              text = if (isTamil) StringsTa.heroBannerTitle else StringsEn.heroBannerTitle,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanDarkGreen,
                fontSize = 18.sp,
                lineHeight = 24.sp
              )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
              onClick = onNavigateToListProduce,
              shape = RoundedCornerShape(20.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = UzhavanDarkGreen,
                contentColor = Color.White
              ),
              contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
              modifier = Modifier.testTag("hero_list_now_button")
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = if (isTamil) StringsTa.listNow else StringsEn.listNow,
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                  )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                  contentDescription = null,
                  modifier = Modifier.size(14.dp)
                )
              }
            }
          }

          Box(
            modifier = Modifier
              .weight(0.9f)
              .height(100.dp)
              .clip(RoundedCornerShape(14.dp))
          ) {
            Image(
              painter = painterResource(id = R.drawable.img_farmer_hero),
              contentDescription = "Farm landscape",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 4 Main Feature Cards (2x2 Grid matching Screen 4)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        FarmerActionCard(
          title = if (isTamil) StringsTa.listProduce else StringsEn.listProduce,
          icon = Icons.Default.Eco,
          iconBgColor = Color(0xFFE8F5E9),
          iconTint = UzhavanDarkGreen,
          onClick = onNavigateToListProduce,
          modifier = Modifier.weight(1f),
          testTag = "action_list_produce"
        )
        FarmerActionCard(
          title = if (isTamil) StringsTa.myListings else StringsEn.myListings,
          icon = Icons.Default.Storefront,
          iconBgColor = Color(0xFFFEF3C7),
          iconTint = Color(0xFFD97706),
          onClick = onNavigateToListings,
          modifier = Modifier.weight(1f),
          testTag = "action_my_listings"
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        FarmerActionCard(
          title = if (isTamil) StringsTa.orders else StringsEn.orders,
          icon = Icons.Default.ReceiptLong,
          iconBgColor = Color(0xFFE0F2FE),
          iconTint = Color(0xFF0284C7),
          onClick = onNavigateToOrders,
          modifier = Modifier.weight(1f),
          testTag = "action_orders"
        )
        FarmerActionCard(
          title = if (isTamil) StringsTa.payments else StringsEn.payments,
          icon = Icons.Default.AccountBalanceWallet,
          iconBgColor = Color(0xFFF3E8FF),
          iconTint = Color(0xFF9333EA),
          onClick = onNavigateToPayments,
          modifier = Modifier.weight(1f),
          testTag = "action_payments"
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Farmer Intelligence Shortcuts Row
      Text(
        text = if (isTamil) "விவசாய நுண்ணறிவு கருவிகள்" else "Farmer Intelligence Tools",
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary,
          fontSize = 15.sp
        )
      )
      Spacer(modifier = Modifier.height(10.dp))
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        item {
          IntelligenceChip(
            title = if (isTamil) "🎙️ நேரடி குரல் AI" else "🎙️ Live Voice AI",
            onClick = onNavigateToVoiceAssistant,
            testTag = "chip_voice_assistant"
          )
        }
        item {
          IntelligenceChip(
            title = if (isTamil) "✨ AI விலை நுண்ணறிவு" else "✨ AI Price Dashboard",
            onClick = onNavigateToPriceDashboard,
            testTag = "chip_price_dashboard"
          )
        }
        item {
          IntelligenceChip(
            title = if (isTamil) "🌱 திட்டமிடு & லாபம்" else "🌱 Plan & Profit",
            onClick = onNavigateToPlanProfit,
            testTag = "chip_plan_profit"
          )
        }
        item {
          IntelligenceChip(
            title = if (isTamil) "📊 நேரடி மண்டி விலை" else "📊 Live Mandi",
            onClick = onNavigateToLiveMandi,
            testTag = "chip_live_mandi"
          )
        }
        item {
          IntelligenceChip(
            title = if (isTamil) "📈 விலை ஆலோசகர்" else "📈 Price Advisor",
            onClick = onNavigateToPriceAdvisor,
            testTag = "chip_price_advisor"
          )
        }
        item {
          IntelligenceChip(
            title = if (isTamil) "🌾 சாகுபடி வழிகாட்டி" else "🌾 Farming Guide",
            onClick = onNavigateToFarmingGuide,
            testTag = "chip_farming_guide"
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Recent Activity (Matching Screen 4)
      Text(
        text = if (isTamil) StringsTa.recentActivity else StringsEn.recentActivity,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary,
          fontSize = 17.sp
        )
      )
      Spacer(modifier = Modifier.height(10.dp))

      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onNavigateToListings() }
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFFFFEBEE)),
            contentAlignment = Alignment.Center
          ) {
            Image(
              painter = painterResource(id = R.drawable.ic_tomato_verified),
              contentDescription = "Tomato listing",
              modifier = Modifier.size(32.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (isTamil) "உங்கள் தக்காளி பட்டியல் நேரலையில் உள்ளது" else "Your tomato listing is live",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = UzhavanTextPrimary
              )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = if (isTamil) "2 மணி நேரம் முன்பு • 500 கிலோ" else "2 hours ago • 500 kg available",
              style = MaterialTheme.typography.bodySmall.copy(
                color = UzhavanTextSecondary,
                fontSize = 12.sp
              )
            )
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFE8F5E9)
          ) {
            Text(
              text = "Live",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanDarkGreen
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
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

@Composable
private fun FarmerActionCard(
  title: String,
  icon: ImageVector,
  iconBgColor: Color,
  iconTint: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  testTag: String
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.dp, Color(0xFFE5EBE6)),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .clip(RoundedCornerShape(18.dp))
      .clickable { onClick() }
      .testTag(testTag)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(iconBgColor),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = iconTint,
          modifier = Modifier.size(26.dp)
        )
      }
      Spacer(modifier = Modifier.height(14.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary,
          fontSize = 15.sp
        )
      )
    }
  }
}

@Composable
private fun IntelligenceChip(
  title: String,
  onClick: () -> Unit,
  testTag: String
) {
  Surface(
    shape = RoundedCornerShape(20.dp),
    color = Color(0xFFF1F6F2),
    border = BorderStroke(1.dp, Color(0xFFD6E4D9)),
    modifier = Modifier
      .clip(RoundedCornerShape(20.dp))
      .clickable { onClick() }
      .testTag(testTag)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.SemiBold,
        color = UzhavanDarkGreen,
        fontSize = 13.sp
      ),
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
    )
  }
}
