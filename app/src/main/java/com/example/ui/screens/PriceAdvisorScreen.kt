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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CatalogRepository
import com.example.ui.components.UzhavanProductImage
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun PriceAdvisorScreen(
  cropName: String,
  quantityKg: Int,
  expectedPrice: Double,
  isTamil: Boolean,
  onBack: () -> Unit,
  onPublishListing: () -> Unit
) {
  val commodity = CatalogRepository.commodities.find { it.name.equals(cropName, ignoreCase = true) }
    ?: CatalogRepository.commodities.first()

  val marketAvgPrice = commodity.defaultMandiPrice

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
        // Top Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("advisor_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) StringsTa.priceAdvisor else StringsEn.priceAdvisor,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Product Header Card (Matching Screen 6)
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
            UzhavanProductImage(
              verifiedDrawableRes = commodity.verifiedDrawableRes,
              productName = commodity.name,
              altText = commodity.altText,
              modifier = Modifier.size(76.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
              Text(
                text = if (isTamil) commodity.tamilName else commodity.name,
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 20.sp
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Tiruvannamalai",
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 14.sp
                )
              )
              Text(
                text = "$quantityKg kg listed",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = UzhavanDarkGreen,
                  fontWeight = FontWeight.SemiBold
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Market Price (Screen 6)
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (isTamil) StringsTa.marketPrice else StringsEn.marketPrice,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 14.sp
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "₹ ${marketAvgPrice.toInt()} /kg",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 22.sp
                )
              )
              Text(
                text = if (isTamil) StringsTa.todaysAverage else StringsEn.todaysAverage,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 12.sp
                )
              )
            }

            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(UzhavanLightGreen),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = null,
                tint = UzhavanDarkGreen,
                modifier = Modifier.size(24.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Your Expected Price (Screen 6)
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (isTamil) StringsTa.yourExpectedPrice else StringsEn.yourExpectedPrice,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 14.sp
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "₹ ${expectedPrice.toInt()} /kg",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanDarkGreen,
                  fontSize = 22.sp
                )
              )
            }

            Surface(
              shape = RoundedCornerShape(20.dp),
              color = Color(0xFFDCFCE7),
              border = BorderStroke(1.dp, Color(0xFF86EFAC))
            ) {
              Text(
                text = if (isTamil) StringsTa.goodPrice else StringsEn.goodPrice,
                style = MaterialTheme.typography.labelMedium.copy(
                  color = Color(0xFF15803D),
                  fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 7-Day Forecast Breakdown
        Text(
          text = if (isTamil) "விலை முன்னறிவிப்பு (அடுத்த 7 நாட்கள்)" else "Price Forecast (Next 7 Days)",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(8.dp))

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
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Tomorrow:", color = UzhavanTextSecondary, fontSize = 13.sp)
              Text(text = "₹ 21 – ₹ 23 /kg", fontWeight = FontWeight.Bold, color = UzhavanDarkGreen, fontSize = 13.sp)
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "In 3 Days:", color = UzhavanTextSecondary, fontSize = 13.sp)
              Text(text = "₹ 22 – ₹ 25 /kg", fontWeight = FontWeight.Bold, color = UzhavanDarkGreen, fontSize = 13.sp)
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "In 7 Days:", color = UzhavanTextSecondary, fontSize = 13.sp)
              Text(text = "₹ 23 – ₹ 27 /kg", fontWeight = FontWeight.Bold, color = UzhavanDarkGreen, fontSize = 13.sp)
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Confidence Level:", color = UzhavanTextSecondary, fontSize = 13.sp)
              Text(text = "78% (High Demand)", fontWeight = FontWeight.SemiBold, color = Color(0xFF0284C7), fontSize = 13.sp)
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Data source disclaimer (Screen 6)
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = UzhavanTextSecondary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isTamil) StringsTa.priceDataSourceNote else StringsEn.priceDataSourceNote,
            style = MaterialTheme.typography.bodySmall.copy(
              color = UzhavanTextSecondary,
              fontSize = 12.sp
            )
          )
        }

        // Demo data tag per rule #15 & #46
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(0xFFFEF3C7)
        ) {
          Text(
            text = "DEMO DATA (Phase 1)",
            style = MaterialTheme.typography.labelSmall.copy(
              color = Color(0xFF92400E),
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
          )
        }
      }

      // Bottom Button (Screen 6)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 16.dp)
      ) {
        Button(
          onClick = onPublishListing,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("publish_listing_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (isTamil) "பட்டியலை வெளியிடு" else "Publish Listing",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            )
          )
        }
      }
    }
  }
}
