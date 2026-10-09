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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.MandiRepository
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun LiveMandiScreen(
  isTamil: Boolean,
  onBack: () -> Unit,
  onOpenPriceDashboard: () -> Unit = {}
) {
  val comparisons = MandiRepository.demoMarketComparisons

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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("mandi_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) "நேரடி மண்டி விலை நிலவரம்" else "Live Mandi Intelligence",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        // Demo Data badge per requirement #15 & #46
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(0xFFFEF3C7)
        ) {
          Text(
            text = "DEMO DATA",
            style = MaterialTheme.typography.labelSmall.copy(
              color = Color(0xFF92400E),
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        // AI Price Intelligence Dashboard Banner
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = UzhavanDarkGreen),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenPriceDashboard() }
              .testTag("open_ai_dashboard_banner")
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = if (isTamil) "✨ AI சந்தை விலை நுண்ணறிவு" else "✨ Gemini AI Price Intelligence",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 15.sp
                  )
                )
                Text(
                  text = if (isTamil) "விலை முன்னறிவிப்பு மற்றும் சந்தை ஆலோசனைகளைப் பெறுங்கள்" else "Get commodity forecasts, sell/hold advice & arbitrage",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFFD1FAE5),
                    fontSize = 12.sp
                  )
                )
              }
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White
              ) {
                Text(
                  text = if (isTamil) "திறக்க" else "Open",
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanDarkGreen
                  ),
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
              }
            }
          }
        }

        // Market Flow Overview Card (Section 17 of prompt)
        item {
          Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F4)),
            border = BorderStroke(1.dp, Color(0xFFD4E8DC)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Tomato • Market Flow",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanDarkGreen
                  )
                )
                Text(
                  text = "Updated: Today 8:00 AM",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = UzhavanTextSecondary,
                    fontSize = 11.sp
                  )
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column {
                  Text(text = "Today's Arrival", color = UzhavanTextSecondary, fontSize = 12.sp)
                  Text(text = "180 tonnes", fontWeight = FontWeight.Bold, color = UzhavanTextPrimary, fontSize = 16.sp)
                }
                Column {
                  Text(text = "Yesterday", color = UzhavanTextSecondary, fontSize = 12.sp)
                  Text(text = "245 tonnes", fontWeight = FontWeight.Bold, color = UzhavanTextPrimary, fontSize = 16.sp)
                }
                Column {
                  Text(text = "Supply Change", color = UzhavanTextSecondary, fontSize = 12.sp)
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.TrendingDown,
                      contentDescription = null,
                      tint = Color(0xFFDC2626),
                      modifier = Modifier.size(16.dp)
                    )
                    Text(text = "-26%", fontWeight = FontWeight.Bold, color = Color(0xFFDC2626), fontSize = 16.sp)
                  }
                }
              }

              Spacer(modifier = Modifier.height(10.dp))
              HorizontalDivider(color = Color(0xFFD4E8DC))
              Spacer(modifier = Modifier.height(8.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "Demand: High", fontWeight = FontWeight.SemiBold, color = Color(0xFF15803D), fontSize = 13.sp)
                Text(text = "Supply: Low", fontWeight = FontWeight.SemiBold, color = Color(0xFFD97706), fontSize = 13.sp)
                Text(text = "Trend: ↗ Increasing", fontWeight = FontWeight.Bold, color = UzhavanDarkGreen, fontSize = 13.sp)
              }
            }
          }
        }

        // Section Title: Regional Market Comparisons
        item {
          Text(
            text = if (isTamil) "சந்தை ஒப்பீடு & நிகர வருவாய்" else "Regional Mandi Comparison",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 17.sp
            )
          )
        }

        // Mandi Market Comparison Items (Section 16 of prompt)
        items(comparisons) { info ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
            border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = info.marketName,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanTextPrimary,
                      fontSize = 15.sp
                    )
                  )
                  Text(
                    text = "${info.district} • ${info.distanceKm} km away",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary,
                      fontSize = 12.sp
                    )
                  )
                }

                Column(horizontalAlignment = Alignment.End) {
                  Text(
                    text = "₹ ${info.modalPrice.toInt()} /kg",
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanDarkGreen,
                      fontSize = 18.sp
                    )
                  )
                  Text(
                    text = "Modal Price",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary,
                      fontSize = 11.sp
                    )
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))
              HorizontalDivider(color = Color(0xFFE2E8E4))
              Spacer(modifier = Modifier.height(8.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Transport: ₹ ${info.transportCostPerKg}/kg",
                  color = UzhavanTextSecondary,
                  fontSize = 12.sp
                )
                Text(
                  text = "Est. Net: ₹ ${info.estimatedNetReturnPerKg}/kg",
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF15803D),
                  fontSize = 13.sp
                )
              }
            }
          }
        }
      }
    }
  }
}
