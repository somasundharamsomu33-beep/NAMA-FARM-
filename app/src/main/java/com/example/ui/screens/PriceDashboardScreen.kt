package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CatalogRepository
import com.example.data.repository.MandiRepository
import com.example.data.service.GeminiMarketService
import com.example.ui.components.PriceTrendChart
import com.example.ui.components.UzhavanProductImage
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceDashboardScreen(
  isTamil: Boolean,
  onBack: () -> Unit,
  onListProduceWithPrice: (crop: String, price: Double) -> Unit,
  onOpenVoiceAssistant: () -> Unit = {}
) {
  val allCommodities = CatalogRepository.commodities
  var selectedCommodity by remember { mutableStateOf(allCommodities.first()) }
  var selectedDistrict by remember { mutableStateOf("Tiruvannamalai") }
  var isDistrictExpanded by remember { mutableStateOf(false) }

  val districts = listOf("Tiruvannamalai", "Vellore", "Chennai", "Bengaluru", "Dharmapuri", "Salem", "Madurai")

  var isLoadingAI by remember { mutableStateOf(false) }
  var aiMarketSummary by remember { mutableStateOf<String?>(null) }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  val scope = rememberCoroutineScope()

  // Function to fetch AI intelligence
  fun loadIntelligence() {
    isLoadingAI = true
    errorMessage = null
    scope.launch {
      val result = GeminiMarketService.fetchMarketIntelligence(
        commodity = selectedCommodity.name,
        marketDistrict = selectedDistrict,
        currentPricePerKg = selectedCommodity.defaultMandiPrice,
        isTamil = isTamil
      )
      isLoadingAI = false
      result.onSuccess { summary ->
        aiMarketSummary = summary
      }.onFailure { err ->
        errorMessage = err.message ?: "Failed to generate market summary"
      }
    }
  }

  // Load automatically when screen opens or commodity/language changes
  LaunchedEffect(selectedCommodity.id, selectedDistrict, isTamil) {
    loadIntelligence()
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
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        // Top Navigation Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = onBack,
              modifier = Modifier.testTag("price_dashboard_back_button")
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = UzhavanDarkGreen
              )
            }
            Text(
              text = if (isTamil) "சந்தை விலை நுண்ணறிவு" else "Price Dashboard",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary,
                fontSize = 20.sp
              )
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color(0xFFDCFCE7),
              border = BorderStroke(1.dp, Color(0xFF86EFAC)),
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { onOpenVoiceAssistant() }
                .testTag("voice_assistant_header_button")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.AutoAwesome,
                  contentDescription = null,
                  tint = Color(0xFF15803D),
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = if (isTamil) "குரல் AI" else "Voice AI",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF15803D),
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                  )
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Commodity Selector Chips
        Text(
          text = if (isTamil) "பயிர் தேர்ந்தெடுக்கவும்" else "Select Crop",
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextSecondary
          )
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(allCommodities.take(10)) { commodity ->
            val isSelected = selectedCommodity.id == commodity.id
            val label = if (isTamil) commodity.tamilName else commodity.name
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (isSelected) UzhavanDarkGreen else Color(0xFFF1F5F2),
              border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE2E8E4)),
              modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { selectedCommodity = commodity }
                .testTag("crop_chip_${commodity.name.lowercase()}")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = label,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.White else UzhavanTextPrimary,
                    fontSize = 13.sp
                  )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "₹${commodity.defaultMandiPrice.toInt()}",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFFA7F3D0) else UzhavanDarkGreen,
                    fontSize = 11.sp
                  )
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // District Selector
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isTamil) "சந்தை / மாவட்டம்:" else "Market Location:",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = UzhavanTextSecondary
            )
          )

          ExposedDropdownMenuBox(
            expanded = isDistrictExpanded,
            onExpandedChange = { isDistrictExpanded = !isDistrictExpanded }
          ) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color(0xFFF9FAF9),
              border = BorderStroke(1.dp, Color(0xFFD1DBD4)),
              modifier = Modifier
                .menuAnchor()
                .clip(RoundedCornerShape(12.dp))
                .clickable { isDistrictExpanded = true }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = selectedDistrict,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanDarkGreen,
                    fontSize = 13.sp
                  )
                )
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDistrictExpanded)
              }
            }

            ExposedDropdownMenu(
              expanded = isDistrictExpanded,
              onDismissRequest = { isDistrictExpanded = false }
            ) {
              districts.forEach { dist ->
                DropdownMenuItem(
                  text = { Text(dist) },
                  onClick = {
                    selectedDistrict = dist
                    isDistrictExpanded = false
                  }
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Price Board Card
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Product Image
              UzhavanProductImage(
                verifiedDrawableRes = selectedCommodity.verifiedDrawableRes,
                productName = selectedCommodity.name,
                altText = selectedCommodity.altText,
                modifier = Modifier.size(70.dp)
              )

              Spacer(modifier = Modifier.width(16.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = if (isTamil) selectedCommodity.tamilName else selectedCommodity.name,
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanTextPrimary,
                    fontSize = 20.sp
                  )
                )
                Text(
                  text = "$selectedDistrict APMC Mandi",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = UzhavanTextSecondary,
                    fontSize = 12.sp
                  )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "₹ ${selectedCommodity.defaultMandiPrice.toInt()} /${selectedCommodity.unit}",
                    style = MaterialTheme.typography.headlineSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanDarkGreen,
                      fontSize = 22.sp
                    )
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFDCFCE7)
                  ) {
                    Text(
                      text = "Modal Price",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF15803D),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                      ),
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFE2E8E4))
            Spacer(modifier = Modifier.height(10.dp))

            // Metrics Row
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(text = "Daily Range", color = UzhavanTextSecondary, fontSize = 11.sp)
                Text(
                  text = "₹${selectedCommodity.priceRangeMin.toInt()} – ₹${selectedCommodity.priceRangeMax.toInt()}",
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 14.sp
                )
              }
              Column {
                Text(text = "Arrival Volume", color = UzhavanTextSecondary, fontSize = 11.sp)
                Text(
                  text = "${selectedCommodity.arrivalTonnes} tonnes",
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 14.sp
                )
              }
              Column {
                Text(text = "Price Trend", color = UzhavanTextSecondary, fontSize = 11.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = UzhavanDarkGreen,
                    modifier = Modifier.size(14.dp)
                  )
                  Text(
                    text = selectedCommodity.trend,
                    fontWeight = FontWeight.Bold,
                    color = UzhavanDarkGreen,
                    fontSize = 14.sp
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Historical Price Trend Line Chart
        PriceTrendChart(
          commodityName = selectedCommodity.name,
          basePrice = selectedCommodity.defaultMandiPrice,
          isTamil = isTamil
        )

        Spacer(modifier = Modifier.height(20.dp))

        // AI Market Intelligence Summary Card (Powered by Gemini)
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F4)),
          border = BorderStroke(1.5.dp, Color(0xFFBBE5CB)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(UzhavanDarkGreen),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = if (isTamil) "AI சந்தை நுண்ணறிவு அறிக்கை" else "Gemini Market Intelligence",
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanDarkGreen,
                      fontSize = 16.sp
                    )
                  )
                  Text(
                    text = if (isTamil) "விவசாயிகளுக்கான வழிகாட்டல்" else "Tailored for Local Farmers",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary,
                      fontSize = 11.sp
                    )
                  )
                }
              }

              IconButton(
                onClick = { loadIntelligence() },
                enabled = !isLoadingAI,
                modifier = Modifier
                  .size(36.dp)
                  .testTag("refresh_ai_summary_button")
              ) {
                if (isLoadingAI) {
                  CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = UzhavanDarkGreen,
                    modifier = Modifier.size(18.dp)
                  )
                } else {
                  Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = UzhavanDarkGreen
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFD4E8DC))
            Spacer(modifier = Modifier.height(12.dp))

            if (isLoadingAI) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                CircularProgressIndicator(color = UzhavanDarkGreen)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                  text = if (isTamil) "Gemini API மூலம் சந்தை நிலவரம் ஆராயப்படுகிறது..." else "Analyzing market data with Gemini AI...",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = UzhavanTextSecondary,
                    fontWeight = FontWeight.SemiBold
                  )
                )
              }
            } else if (aiMarketSummary != null) {
              Text(
                text = aiMarketSummary ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = UzhavanTextPrimary,
                  fontSize = 14.sp,
                  lineHeight = 22.sp
                )
              )
            } else if (errorMessage != null) {
              Text(
                text = errorMessage ?: "Unable to fetch intelligence",
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFDC2626))
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Regional Mandi Arbitrage Table
        Text(
          text = if (isTamil) "அருகிலுள்ள மண்டிகள் ஒப்பீடு" else "Regional Mandi Arbitrage",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 16.sp
          )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            MandiRepository.demoMarketComparisons.take(3).forEach { market ->
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = market.marketName,
                    style = MaterialTheme.typography.titleSmall.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = UzhavanTextPrimary,
                      fontSize = 14.sp
                    )
                  )
                  Text(
                    text = "${market.distanceKm} km away • Freight: ₹${market.transportCostPerKg}/kg",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary,
                      fontSize = 11.sp
                    )
                  )
                }

                Column(horizontalAlignment = Alignment.End) {
                  Text(
                    text = "₹ ${market.modalPrice.toInt()} /kg",
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanDarkGreen,
                      fontSize = 15.sp
                    )
                  )
                  Text(
                    text = "Net: ₹${market.estimatedNetReturnPerKg}/kg",
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = Color(0xFF15803D),
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.sp
                    )
                  )
                }
              }
              HorizontalDivider(color = Color(0xFFECEFEA))
            }
          }
        }
      }

      // Bottom CTA Button: Pre-fill and list produce
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 24.dp, bottom = 12.dp)
      ) {
        Button(
          onClick = {
            onListProduceWithPrice(selectedCommodity.name, selectedCommodity.defaultMandiPrice)
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("list_with_market_price_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (isTamil) "இவ்விலையில் விளைபொருளை பட்டியலிடுக" else "List Produce at this Price (₹${selectedCommodity.defaultMandiPrice.toInt()}/kg)",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          )
        }
      }
    }
  }
}
