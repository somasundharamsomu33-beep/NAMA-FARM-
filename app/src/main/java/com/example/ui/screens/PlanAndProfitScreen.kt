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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CropPlanResult
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanAndProfitScreen(
  isTamil: Boolean,
  onBack: () -> Unit
) {
  var selectedCrop by remember { mutableStateOf("Tomato") }
  var isCropExpanded by remember { mutableStateOf(false) }
  var landAreaAcresText by remember { mutableStateOf("1.0") }
  var soilType by remember { mutableStateOf("Red Loam") }

  val crops = listOf("Tomato", "Onion", "Brinjal", "Potato", "Carrot", "Paddy")

  val acres = landAreaAcresText.toDoubleOrNull() ?: 1.0

  val planResult = remember(selectedCrop, acres) {
    when (selectedCrop) {
      "Tomato" -> CropPlanResult(
        cropName = "Tomato",
        landAreaAcres = acres,
        seedCost = 4500.0 * acres,
        fertilizerCost = 9000.0 * acres,
        labourCost = 14000.0 * acres,
        irrigationCost = 3500.0 * acres,
        pestControlCost = 5000.0 * acres,
        equipmentCost = 4000.0 * acres,
        transportCost = 3000.0 * acres,
        otherCost = 2000.0 * acres,
        expectedYieldKg = 12000.0 * acres,
        expectedPricePerKg = 20.0
      )
      "Onion" -> CropPlanResult(
        cropName = "Onion",
        landAreaAcres = acres,
        seedCost = 6000.0 * acres,
        fertilizerCost = 8000.0 * acres,
        labourCost = 12000.0 * acres,
        irrigationCost = 3000.0 * acres,
        pestControlCost = 4000.0 * acres,
        equipmentCost = 3500.0 * acres,
        transportCost = 2500.0 * acres,
        otherCost = 1500.0 * acres,
        expectedYieldKg = 8000.0 * acres,
        expectedPricePerKg = 18.0
      )
      else -> CropPlanResult(
        cropName = selectedCrop,
        landAreaAcres = acres,
        seedCost = 4000.0 * acres,
        fertilizerCost = 7500.0 * acres,
        labourCost = 11000.0 * acres,
        irrigationCost = 3000.0 * acres,
        pestControlCost = 4000.0 * acres,
        equipmentCost = 3000.0 * acres,
        transportCost = 2000.0 * acres,
        otherCost = 1500.0 * acres,
        expectedYieldKg = 9000.0 * acres,
        expectedPricePerKg = 22.0
      )
    }
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
        .verticalScroll(rememberScrollState())
    ) {
      // Top Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBack,
          modifier = Modifier.testTag("plan_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = UzhavanDarkGreen
          )
        }
        Text(
          text = if (isTamil) "பயிர் திட்டம் & லாபம்" else "Plan & Profit",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 20.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Input Controls
      Text(
        text = if (isTamil) "பயிர் தேர்ந்தெடுக்கவும்" else "Select Crop",
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.SemiBold,
          color = UzhavanTextPrimary
        )
      )
      Spacer(modifier = Modifier.height(6.dp))
      ExposedDropdownMenuBox(
        expanded = isCropExpanded,
        onExpandedChange = { isCropExpanded = !isCropExpanded }
      ) {
        OutlinedTextField(
          value = selectedCrop,
          onValueChange = {},
          readOnly = true,
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCropExpanded) },
          modifier = Modifier
            .menuAnchor()
            .fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          )
        )
        ExposedDropdownMenu(
          expanded = isCropExpanded,
          onDismissRequest = { isCropExpanded = false }
        ) {
          crops.forEach { c ->
            DropdownMenuItem(
              text = { Text(c) },
              onClick = {
                selectedCrop = c
                isCropExpanded = false
              }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = if (isTamil) "நிலப்பரப்பு (ஏக்கர்)" else "Land Area (Acres)",
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.SemiBold,
          color = UzhavanTextPrimary
        )
      )
      Spacer(modifier = Modifier.height(6.dp))
      OutlinedTextField(
        value = landAreaAcresText,
        onValueChange = { landAreaAcresText = it },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = UzhavanDarkGreen,
          unfocusedBorderColor = Color(0xFFD1DBD4)
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Investment vs Returns Result Card
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F4)),
        border = BorderStroke(1.dp, Color(0xFFD4E8DC)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Text(
            text = "Estimated Profit Analysis",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanDarkGreen
            )
          )
          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(text = "Total Investment", color = UzhavanTextSecondary, fontSize = 12.sp)
              Text(
                text = "₹ ${planResult.totalInvestment.toInt()}",
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary,
                fontSize = 18.sp
              )
            }
            Column {
              Text(text = "Expected Yield", color = UzhavanTextSecondary, fontSize = 12.sp)
              Text(
                text = "${planResult.expectedYieldKg.toInt()} kg",
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary,
                fontSize = 18.sp
              )
            }
            Column {
              Text(text = "Est. Profit", color = UzhavanTextSecondary, fontSize = 12.sp)
              Text(
                text = "₹ ${planResult.expectedProfit.toInt()}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF15803D),
                fontSize = 18.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))
          HorizontalDivider(color = Color(0xFFD4E8DC))
          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Est. Revenue: ₹ ${planResult.expectedRevenue.toInt()}", color = UzhavanTextSecondary, fontSize = 13.sp)
            Text(
              text = "Profit Margin: ~${planResult.profitMarginPercent.toInt()}%",
              fontWeight = FontWeight.Bold,
              color = Color(0xFF15803D),
              fontSize = 13.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Cost Breakdown Items (Section 14 of prompt)
      Text(
        text = if (isTamil) "செலவு மதிப்பீடு விவரங்கள்" else "Cost Estimation Breakdown",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = UzhavanTextPrimary
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
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          CostRow(label = "Seed Cost", amount = planResult.seedCost)
          CostRow(label = "Fertilizer & Nutrition", amount = planResult.fertilizerCost)
          CostRow(label = "Labour (Planting, Weeding, Harvest)", amount = planResult.labourCost)
          CostRow(label = "Irrigation & Electricity", amount = planResult.irrigationCost)
          CostRow(label = "Pest & Disease Control", amount = planResult.pestControlCost)
          CostRow(label = "Tractor / Equipment Rental", amount = planResult.equipmentCost)
          CostRow(label = "Transport to Mandi", amount = planResult.transportCost)
          CostRow(label = "Miscellaneous Costs", amount = planResult.otherCost)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Crucial Disclaimer per prompt rule #14:
      // "IMPORTANT: Never guarantee profit. Always use: Estimated, Expected, Approximate, Forecast"
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
          text = if (isTamil) "அனைத்து கணக்கீடுகளும் தோராயமான மதிப்பீடுகள் மட்டுமே. உண்மையான லாபம் தட்பவெப்பநிலை மற்றும் சந்தை விலையைப் பொறுத்தது." else "All figures are estimated approximations based on regional farm averages. Never guaranteed.",
          style = MaterialTheme.typography.bodySmall.copy(
            color = UzhavanTextSecondary,
            fontSize = 11.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun CostRow(label: String, amount: Double) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = label, color = UzhavanTextSecondary, fontSize = 13.sp)
    Text(text = "₹ ${amount.toInt()}", fontWeight = FontWeight.SemiBold, color = UzhavanTextPrimary, fontSize = 13.sp)
  }
}
