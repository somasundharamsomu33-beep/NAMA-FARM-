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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun PaymentSuccessScreen(
  orderNumber: String = "UM12345",
  totalAmount: Double = 2040.0,
  isTamil: Boolean,
  onTrackOrder: () -> Unit,
  onBackToHome: () -> Unit
) {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 24.dp, vertical = 20.dp)
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Spacer(modifier = Modifier.height(28.dp))

        // Big Celebratory Green Checkmark (Screen 12)
        Box(
          modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(UzhavanDarkGreen),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Success",
            tint = Color.White,
            modifier = Modifier.size(54.dp)
          )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
          text = if (isTamil) StringsTa.paymentSuccessful else StringsEn.paymentSuccessful,
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 24.sp
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = if (isTamil) "உங்கள் ஆர்டருக்கு ₹ ${totalAmount.toInt()} செலுத்தப்பட்டுள்ளது." else "₹ ${totalAmount.toInt()} has been paid for your order.",
          style = MaterialTheme.typography.bodyMedium.copy(
            color = UzhavanTextSecondary,
            fontSize = 15.sp
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Order Summary Card (Screen 12)
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text(
              text = "Order #$orderNumber",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanDarkGreen
              )
            )

            HorizontalDivider(color = Color(0xFFE2E8E4), modifier = Modifier.padding(vertical = 4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Tomato 100 kg", color = UzhavanTextSecondary, fontSize = 14.sp)
              Text(text = "₹ 1,800", fontWeight = FontWeight.SemiBold, color = UzhavanTextPrimary, fontSize = 14.sp)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Delivery Charge", color = UzhavanTextSecondary, fontSize = 14.sp)
              Text(text = "₹ 150", fontWeight = FontWeight.SemiBold, color = UzhavanTextPrimary, fontSize = 14.sp)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Platform Commission", color = UzhavanTextSecondary, fontSize = 14.sp)
              Text(text = "₹ 90", fontWeight = FontWeight.SemiBold, color = UzhavanTextPrimary, fontSize = 14.sp)
            }

            HorizontalDivider(color = Color(0xFFE2E8E4), modifier = Modifier.padding(vertical = 4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Total",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary
                )
              )
              Text(
                text = "₹ ${totalAmount.toInt()}",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanDarkGreen
                )
              )
            }
          }
        }
      }

      // Bottom Buttons (Screen 12)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Button(
          onClick = onTrackOrder,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("track_order_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (isTamil) StringsTa.trackOrder else StringsEn.trackOrder,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = if (isTamil) "எஸ்எம்எஸ் மற்றும் வாட்ஸ்அப் மூலம் உறுதிப்படுத்தல் அனுப்பப்படும்." else "You will receive a confirmation via SMS and WhatsApp.",
          style = MaterialTheme.typography.bodySmall.copy(
            color = UzhavanTextSecondary,
            fontSize = 12.sp
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
          onClick = onBackToHome,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("back_to_home_button"),
          shape = RoundedCornerShape(16.dp),
          border = BorderStroke(1.dp, Color(0xFFD1DBD4))
        ) {
          Text(
            text = if (isTamil) StringsTa.backToHome else StringsEn.backToHome,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = UzhavanTextPrimary,
              fontSize = 15.sp
            )
          )
        }
      }
    }
  }
}
