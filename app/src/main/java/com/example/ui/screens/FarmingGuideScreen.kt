package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

data class GuideStage(val stageName: String, val description: String, val tips: String)

@Composable
fun FarmingGuideScreen(
  isTamil: Boolean,
  onBack: () -> Unit
) {
  var selectedCrop by remember { mutableStateOf("Tomato") }
  val crops = listOf("Tomato", "Onion", "Brinjal", "Paddy")

  val stages = remember(selectedCrop) {
    listOf(
      GuideStage(
        stageName = "1. Before Planting (மண் தயாரிப்பு)",
        description = "Deep ploughing 2-3 times. Apply 25 tonnes of well-rotted Farm Yard Manure (FYM) per hectare. Prepare raised beds for good drainage.",
        tips = "Maintain soil pH around 6.0 to 7.0 for optimal root nutrient uptake."
      ),
      GuideStage(
        stageName = "2. Planting & Nursery (நடவு)",
        description = "Transplant 25-30 day old healthy seedlings during late afternoon to prevent wilting. Maintain 60 x 45 cm spacing.",
        tips = "Dip roots in Pseudomonas fluorescens bio-fungicide prior to transplanting."
      ),
      GuideStage(
        stageName = "3. Irrigation & Nutrition (நீர்ப்பாசனம்)",
        description = "Drip irrigation is recommended. Irrigate immediately after planting, then at 3-4 days intervals depending on soil moisture.",
        tips = "Apply 19:19:19 water soluble fertilizer during vegetative stage; switch to 13:0:45 during fruiting."
      ),
      GuideStage(
        stageName = "4. Pest & Disease Management (பூச்சி கட்டுப்பாடு)",
        description = "Monitor regularly for fruit borer, whitefly, and early blight. Install yellow sticky traps (15 per acre).",
        tips = "Spray Neem oil (3 ml/litre) at first sign of sucking pests as organic preventive measure."
      ),
      GuideStage(
        stageName = "5. Harvest & Grading (அறுவடை & தரம்)",
        description = "Harvest when fruits reach breaker or pink stage for distant markets. Sort by size and firmness into Grade A and Grade B crates.",
        tips = "Do not wash produce before transport to prevent moisture fungal decay."
      )
    )
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
    ) {
      // Top Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBack,
          modifier = Modifier.testTag("guide_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = UzhavanDarkGreen
          )
        }
        Text(
          text = if (isTamil) "சாகுபடி வழிகாட்டி" else "Farming Guide",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 20.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Crop selector chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(crops) { crop ->
          val isSelected = selectedCrop == crop
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isSelected) UzhavanDarkGreen else Color(0xFFF1F5F2),
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .clickable { selectedCrop = crop }
          ) {
            Text(
              text = crop,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else UzhavanTextSecondary,
                fontSize = 13.sp
              ),
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(stages) { stage ->
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
                text = stage.stageName,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanDarkGreen,
                  fontSize = 16.sp
                )
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = stage.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = UzhavanTextPrimary,
                  fontSize = 13.sp,
                  lineHeight = 19.sp
                )
              )
              Spacer(modifier = Modifier.height(8.dp))
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFE8F5E9)
              ) {
                Text(
                  text = "💡 Expert Tip: ${stage.tips}",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                  ),
                  modifier = Modifier.padding(8.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}
