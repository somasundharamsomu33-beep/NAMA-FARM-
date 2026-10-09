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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.SupportAgent
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
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun OrderTrackingScreen(
  orderNumber: String = "UM12345",
  placedDate: String = "Placed on 12 Apr 2025, 10:15 AM",
  isTamil: Boolean,
  onBack: () -> Unit,
  onContactSupport: () -> Unit
) {
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
            modifier = Modifier.testTag("track_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) StringsTa.trackOrder else StringsEn.trackOrder,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Order Header Card
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
          ) {
            Text(
              text = "Order #$orderNumber",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanDarkGreen,
                fontSize = 18.sp
              )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = placedDate,
              style = MaterialTheme.typography.bodySmall.copy(
                color = UzhavanTextSecondary,
                fontSize = 13.sp
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tracking Stepper Timeline (Screen 13)
        TrackingTimelineItem(
          title = if (isTamil) "ஆர்டர் உறுதிப்படுத்தப்பட்டது" else "Order Confirmed",
          subtitle = "10:20 AM",
          isCompleted = true,
          isLast = false
        )

        TrackingTimelineItem(
          title = if (isTamil) "பிக்அப் திட்டமிடப்பட்டது" else "Pickup Scheduled",
          subtitle = "Today, 2:00 PM",
          isCompleted = true,
          isLast = false
        )

        TrackingTimelineItem(
          title = if (isTamil) "டெலிவரிக்கு அனுப்பப்பட்டது" else "Out for Delivery",
          subtitle = "Today, 4:00 PM",
          isCompleted = true,
          isCurrent = true,
          isLast = false
        )

        TrackingTimelineItem(
          title = if (isTamil) "டெலிவரி செய்யப்பட்டது" else "Delivered",
          subtitle = "Expected by 6:00 PM",
          isCompleted = false,
          isLast = true
        )
      }

      // Bottom Button (Screen 13)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 16.dp)
      ) {
        Button(
          onClick = onContactSupport,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("contact_support_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.SupportAgent,
              contentDescription = null,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (isTamil) "வாடிக்கையாளர் ஆதரவு" else "Contact Support",
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
}

@Composable
private fun TrackingTimelineItem(
  title: String,
  subtitle: String,
  isCompleted: Boolean,
  isCurrent: Boolean = false,
  isLast: Boolean = false
) {
  Row(modifier = Modifier.fillMaxWidth()) {
    // Stepper node + connecting vertical line
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(
            if (isCompleted) UzhavanDarkGreen else Color(0xFFE5E7EB)
          ),
        contentAlignment = Alignment.Center
      ) {
        if (isCompleted) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
          )
        } else {
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(Color(0xFF9CA3AF))
          )
        }
      }

      if (!isLast) {
        Box(
          modifier = Modifier
            .width(2.5.dp)
            .height(44.dp)
            .background(if (isCompleted) UzhavanDarkGreen else Color(0xFFE5E7EB))
        )
      }
    }

    Spacer(modifier = Modifier.width(16.dp))

    // Text descriptions
    Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 24.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = if (isCompleted || isCurrent) FontWeight.Bold else FontWeight.SemiBold,
          color = if (isCompleted || isCurrent) UzhavanTextPrimary else UzhavanTextSecondary,
          fontSize = 15.sp
        )
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(
          color = if (isCurrent) UzhavanDarkGreen else UzhavanTextSecondary,
          fontWeight = if (isCurrent) FontWeight.SemiBold else FontWeight.Normal,
          fontSize = 13.sp
        )
      )
    }
  }
}
