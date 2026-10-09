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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Storefront
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
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun RoleSelectionScreen(
  isTamil: Boolean,
  onSelectFarmer: () -> Unit,
  onSelectBuyer: () -> Unit
) {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(24.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
          text = if (isTamil) StringsTa.welcomeHeader else StringsEn.welcomeHeader,
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 26.sp
          )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = if (isTamil) StringsTa.chooseRoleSubtitle else StringsEn.chooseRoleSubtitle,
          style = MaterialTheme.typography.bodyLarge.copy(
            color = UzhavanTextSecondary,
            fontSize = 16.sp
          )
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Role Card 1: Farmer
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.5.dp, Color(0xFFE2E8E4)),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onSelectFarmer() }
            .testTag("role_farmer_card")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(UzhavanLightGreen),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Agriculture,
                contentDescription = null,
                tint = UzhavanDarkGreen,
                modifier = Modifier.size(32.dp)
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (isTamil) StringsTa.roleFarmer else StringsEn.roleFarmer,
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 20.sp
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (isTamil) StringsTa.roleFarmerDesc else StringsEn.roleFarmerDesc,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 14.sp
                )
              )
            }

            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = "Select",
              tint = UzhavanDarkGreen,
              modifier = Modifier.size(24.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Role Card 2: Buyer
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.5.dp, Color(0xFFE2E8E4)),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onSelectBuyer() }
            .testTag("role_buyer_card")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFFEF3C7)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Storefront,
                contentDescription = null,
                tint = Color(0xFFD97706),
                modifier = Modifier.size(32.dp)
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (isTamil) StringsTa.roleBuyer else StringsEn.roleBuyer,
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 20.sp
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (isTamil) StringsTa.roleBuyerDesc else StringsEn.roleBuyerDesc,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 14.sp
                )
              )
            }

            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = "Select",
              tint = UzhavanDarkGreen,
              modifier = Modifier.size(24.dp)
            )
          }
        }
      }

      // Footer
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 20.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = if (isTamil) StringsTa.buildingAgriEcosystem else StringsEn.buildingAgriEcosystem,
          style = MaterialTheme.typography.bodySmall.copy(
            color = UzhavanTextSecondary,
            fontSize = 13.sp
          )
        )
      }
    }
  }
}
