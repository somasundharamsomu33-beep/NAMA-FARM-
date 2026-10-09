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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
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
import com.example.data.model.ProductListing
import com.example.ui.components.UzhavanProductImage
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun ProductDetailScreen(
  listing: ProductListing,
  isTamil: Boolean,
  onBack: () -> Unit,
  onAddToCart: (quantityKg: Int) -> Unit
) {
  var selectedQuantityKg by remember { mutableIntStateOf(100) }

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
        // Top Back Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("product_detail_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) "விளைபொருள் விவரங்கள்" else "Product Details",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Big verified photo container (Screen 8)
        UzhavanProductImage(
          verifiedDrawableRes = listing.verifiedDrawableRes,
          productName = listing.commodityName,
          altText = "Fresh ${listing.commodityName}",
          modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Title + Price (Screen 8)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = listing.commodityName,
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 24.sp
            )
          )
          Text(
            text = "₹ ${listing.pricePerKg.toInt()} /kg",
            style = MaterialTheme.typography.headlineSmall.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanDarkGreen,
              fontSize = 22.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Badges: "Fresh", "Grade A", "500 kg available" (Screen 8)
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          DetailBadge(text = if (isTamil) "புதியது" else "Fresh", color = Color(0xFFDCFCE7), textColor = UzhavanDarkGreen)
          DetailBadge(text = listing.qualityGrade, color = Color(0xFFE0F2FE), textColor = Color(0xFF0369A1))
          DetailBadge(text = "${listing.quantityKg} kg available", color = Color(0xFFF3F4F6), textColor = UzhavanTextSecondary)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Farmer Details Card (Screen 8)
        Text(
          text = if (isTamil) StringsTa.farmerDetails else StringsEn.farmerDetails,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 16.sp
          )
        )
        Spacer(modifier = Modifier.height(8.dp))

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
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(UzhavanLightGreen),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = UzhavanDarkGreen,
                modifier = Modifier.size(26.dp)
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = listing.farmerName,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 15.sp
                )
              )
              Text(
                text = "${listing.farmerVillage}, ${listing.farmerDistrict}",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 12.sp
                )
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFF59E0B),
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "${listing.farmerRating} (${listing.reviewCount})",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 12.sp
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Description (Screen 8)
        Text(
          text = if (isTamil) StringsTa.description else StringsEn.description,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 16.sp
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = listing.description,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = UzhavanTextSecondary,
            lineHeight = 22.sp
          )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Quantity Stepper (Screen 8)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isTamil) "தேவையான அளவு" else "Select Quantity",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary
            )
          )

          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFFF3F4F6))
              .padding(4.dp)
          ) {
            IconButton(
              onClick = { if (selectedQuantityKg > 20) selectedQuantityKg -= 20 },
              modifier = Modifier
                .size(36.dp)
                .testTag("qty_decrease_button")
            ) {
              Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease",
                tint = UzhavanDarkGreen
              )
            }

            Text(
              text = "$selectedQuantityKg kg",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary
              ),
              modifier = Modifier.padding(horizontal = 12.dp)
            )

            IconButton(
              onClick = { if (selectedQuantityKg < listing.quantityKg) selectedQuantityKg += 20 },
              modifier = Modifier
                .size(36.dp)
                .testTag("qty_increase_button")
            ) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                tint = UzhavanDarkGreen
              )
            }
          }
        }
      }

      // Add to Cart Button (Screen 8)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 16.dp)
      ) {
        Button(
          onClick = { onAddToCart(selectedQuantityKg) },
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("add_to_cart_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = "${if (isTamil) StringsTa.addToCart else StringsEn.addToCart} (₹ ${(selectedQuantityKg * listing.pricePerKg).toInt()})",
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

@Composable
private fun DetailBadge(
  text: String,
  color: Color,
  textColor: Color
) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = color
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        color = textColor,
        fontSize = 12.sp
      ),
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
    )
  }
}
