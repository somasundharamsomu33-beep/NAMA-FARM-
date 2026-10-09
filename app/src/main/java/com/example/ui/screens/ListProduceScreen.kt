package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CatalogRepository
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProduceScreen(
  isTamil: Boolean,
  onBack: () -> Unit,
  onProceedToAdvisor: (crop: String, quantity: Int, expectedPrice: Double) -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Voice, 1: Manual
  var selectedCrop by remember { mutableStateOf("Tomato") }
  var isCropDropdownExpanded by remember { mutableStateOf(false) }
  var quantityText by remember { mutableStateOf("500") }
  var expectedPriceText by remember { mutableStateOf("22") }
  var availableFromText by remember { mutableStateOf("Tomorrow") }

  var isListening by remember { mutableStateOf(false) }
  var speechRecognizedText by remember { mutableStateOf<String?>(null) }
  val scope = rememberCoroutineScope()

  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = if (isListening) 1.25f else 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseScale"
  )

  val cropsList = CatalogRepository.commodities.take(12).map { it.name }

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
        // Top Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("list_produce_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) "விளைபொருளை பட்டியலிடுக" else "List Your Produce",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Segmented Tabs: [Voice] | [Manual] (Matching Screen 5)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF1F5F2))
            .padding(4.dp)
        ) {
          val voiceSelected = selectedTab == 0
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(20.dp))
              .background(if (voiceSelected) UzhavanDarkGreen else Color.Transparent)
              .clickable { selectedTab = 0 }
              .testTag("tab_voice"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (isTamil) StringsTa.voiceTab else StringsEn.voiceTab,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (voiceSelected) Color.White else UzhavanTextSecondary
              )
            )
          }

          val manualSelected = selectedTab == 1
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(20.dp))
              .background(if (manualSelected) UzhavanDarkGreen else Color.Transparent)
              .clickable { selectedTab = 1 }
              .testTag("tab_manual"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (isTamil) StringsTa.manualTab else StringsEn.manualTab,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (manualSelected) Color.White else UzhavanTextSecondary
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // If Voice tab is active: Microphone section
        if (selectedTab == 0) {
          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
            border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp, horizontal = 16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              // Large Circular Mic Button
              Box(
                modifier = Modifier
                  .size(92.dp)
                  .scale(if (isListening) pulseScale else 1f)
                  .clip(CircleShape)
                  .background(if (isListening) Color(0xFFDC2626) else UzhavanDarkGreen)
                  .clickable {
                    if (!isListening) {
                      isListening = true
                      speechRecognizedText = null
                      scope.launch {
                        delay(1800)
                        isListening = false
                        speechRecognizedText = "\"500 kg tomato, ready tomorrow\""
                        selectedCrop = "Tomato"
                        quantityText = "500"
                        expectedPriceText = "22"
                        availableFromText = "Tomorrow"
                      }
                    }
                  }
                  .testTag("voice_mic_button"),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (isListening) Icons.Default.MicNone else Icons.Default.Mic,
                  contentDescription = "Tap to speak",
                  tint = Color.White,
                  modifier = Modifier.size(46.dp)
                )
              }

              Spacer(modifier = Modifier.height(14.dp))

              Text(
                text = if (isListening) {
                  if (isTamil) "கேட்கிறது..." else "Listening..."
                } else {
                  if (isTamil) StringsTa.tapToSpeak else StringsEn.tapToSpeak
                },
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 17.sp
                )
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = speechRecognizedText ?: if (isTamil) "\"500 கிலோ தக்காளி, நாளை தயார்\"" else "\"500 kg tomato, ready tomorrow\"",
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = if (speechRecognizedText != null) UzhavanDarkGreen else UzhavanTextSecondary,
                  fontWeight = if (speechRecognizedText != null) FontWeight.SemiBold else FontWeight.Normal,
                  fontSize = 14.sp
                ),
                textAlign = TextAlign.Center
              )

              if (speechRecognizedText != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = Color(0xFFE8F5E9)
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(
                      imageVector = Icons.Default.Check,
                      contentDescription = null,
                      tint = UzhavanDarkGreen,
                      modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = if (isTamil) "தானாக நிரப்பப்பட்டது" else "Parsed & Applied",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = UzhavanDarkGreen,
                        fontWeight = FontWeight.Bold
                      )
                    )
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))
          Text(
            text = if (isTamil) "அல்லது கைமுறையாக உள்ளிடவும்" else "Or enter details manually",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = UzhavanTextSecondary
            )
          )
          Spacer(modifier = Modifier.height(12.dp))
        }

        // Form Fields (Matching Screen 5)
        // Crop
        Text(
          text = if (isTamil) StringsTa.cropLabel else StringsEn.cropLabel,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        ExposedDropdownMenuBox(
          expanded = isCropDropdownExpanded,
          onExpandedChange = { isCropDropdownExpanded = !isCropDropdownExpanded }
        ) {
          OutlinedTextField(
            value = selectedCrop,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCropDropdownExpanded) },
            modifier = Modifier
              .menuAnchor()
              .fillMaxWidth()
              .testTag("crop_selector"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = UzhavanDarkGreen,
              unfocusedBorderColor = Color(0xFFD1DBD4)
            )
          )
          ExposedDropdownMenu(
            expanded = isCropDropdownExpanded,
            onDismissRequest = { isCropDropdownExpanded = false }
          ) {
            cropsList.forEach { crop ->
              DropdownMenuItem(
                text = { Text(crop) },
                onClick = {
                  selectedCrop = crop
                  isCropDropdownExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Quantity (kg)
        Text(
          text = if (isTamil) StringsTa.quantityLabel else StringsEn.quantityLabel,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = quantityText,
          onValueChange = { quantityText = it },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("quantity_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          ),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Expected Price (per kg)
        Text(
          text = if (isTamil) StringsTa.expectedPriceLabel else StringsEn.expectedPriceLabel,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = expectedPriceText,
          onValueChange = { expectedPriceText = it },
          prefix = { Text("₹ ", fontWeight = FontWeight.Bold, color = UzhavanDarkGreen) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("expected_price_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          ),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Available From
        Text(
          text = if (isTamil) StringsTa.availableFromLabel else StringsEn.availableFromLabel,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = availableFromText,
          onValueChange = { availableFromText = it },
          trailingIcon = {
            Icon(
              imageVector = Icons.Default.CalendarToday,
              contentDescription = null,
              tint = UzhavanTextSecondary,
              modifier = Modifier.size(20.dp)
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("available_from_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          ),
          singleLine = true
        )
      }

      // Bottom Button: "Next" (Screen 5)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 16.dp)
      ) {
        Button(
          onClick = {
            val qty = quantityText.toIntOrNull() ?: 500
            val price = expectedPriceText.toDoubleOrNull() ?: 22.0
            onProceedToAdvisor(selectedCrop, qty, price)
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("list_produce_next_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (isTamil) StringsTa.next else StringsEn.next,
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
