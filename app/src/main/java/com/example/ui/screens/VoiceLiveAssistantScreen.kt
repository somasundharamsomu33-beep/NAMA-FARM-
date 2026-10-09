package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.service.GeminiMarketService
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChatMessage(
  val isUser: Boolean,
  val text: String,
  val timestamp: String = "Now"
)

@Composable
fun VoiceLiveAssistantScreen(
  isTamil: Boolean,
  onBack: () -> Unit
) {
  val messages = remember {
    mutableStateListOf(
      ChatMessage(
        isUser = false,
        text = if (isTamil) {
          "வணக்கம் உழவரே! நான் உங்கள் நேரடி குரல் உதவியாளர் (Gemini 3.8 Live API). இன்றைய தக்காளி, வெங்காயம் மற்றும் அனைத்து பயிர்களின் விலை நிலவரங்கள் குறித்து என்னிடம் நேரடியாகப் பேசலாம்."
        } else {
          "Hello farmer! I am your Uzhavan Live Voice Assistant powered by Gemini 3.8 Live API. You can talk to me directly in real-time about mandi rates, when to sell, and buyer demands."
        }
      )
    )
  }

  var inputText by remember { mutableStateOf("") }
  var isListening by remember { mutableStateOf(false) }
  var isThinking by remember { mutableStateOf(false) }

  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = if (isListening) 1.25f else 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_scale"
  )

  fun sendMessage(text: String) {
    if (text.isBlank()) return
    messages.add(ChatMessage(isUser = true, text = text))
    inputText = ""
    isThinking = true

    scope.launch {
      listState.animateScrollToItem(messages.lastIndex)
      val history = messages.filter { !it.isUser }.map { "farmer" to it.text }
      val result = GeminiMarketService.sendLiveVoiceMessage(text, history, isTamil)
      isThinking = false
      result.onSuccess { reply ->
        messages.add(ChatMessage(isUser = false, text = reply))
      }.onFailure { err ->
        messages.add(
          ChatMessage(
            isUser = false,
            text = if (isTamil) "மன்னிக்கவும், தகவலைப் பெற முடியவில்லை: ${err.message}" else "Sorry, could not process request: ${err.message}"
          )
        )
      }
      listState.animateScrollToItem(messages.lastIndex)
    }
  }

  val suggestedQueries = if (isTamil) {
    listOf(
      "தக்காளி இன்றைய விலை என்ன?",
      "எந்த சந்தை அதிக விலை தருகிறது?",
      "இப்போது விற்கலாமா அல்லது காத்திருக்கலாமா?",
      "வெங்காயம் விலை போக்கு எப்படி உள்ளது?"
    )
  } else {
    listOf(
      "What is today's tomato price?",
      "Which market gives the best price?",
      "Should I sell now or wait?",
      "How is the onion mandi trend?"
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
      // Header: Back button + Live API badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("voice_assistant_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Column {
            Text(
              text = if (isTamil) "நேரடி குரல் உதவியாளர்" else "Live Voice Assistant",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary,
                fontSize = 18.sp
              )
            )
            Text(
              text = "Uzhavan Market • Real-time AI",
              style = MaterialTheme.typography.bodySmall.copy(
                color = UzhavanTextSecondary,
                fontSize = 11.sp
              )
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFDCFCE7),
          border = BorderStroke(1.dp, Color(0xFF86EFAC))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = Color(0xFF15803D),
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "gemini-3.8-live",
              style = MaterialTheme.typography.labelSmall.copy(
                color = Color(0xFF15803D),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Chat Messages Stream
      LazyColumn(
        state = listState,
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(messages) { msg ->
          if (msg.isUser) {
            // User Message (Farmer)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp))
                  .background(UzhavanDarkGreen)
                  .padding(horizontal = 14.dp, vertical = 10.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = null,
                    tint = Color(0xFFD1FAE5),
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = msg.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                      color = Color.White,
                      fontWeight = FontWeight.Medium,
                      fontSize = 14.sp
                    )
                  )
                }
              }
            }
          } else {
            // AI Voice Assistant Reply
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Start
            ) {
              Card(
                shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
                border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
                modifier = Modifier.fillMaxWidth(0.92f)
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Box(
                        modifier = Modifier
                          .size(24.dp)
                          .clip(CircleShape)
                          .background(UzhavanLightGreen),
                        contentAlignment = Alignment.Center
                      ) {
                        Icon(
                          imageVector = Icons.Default.VolumeUp,
                          contentDescription = null,
                          tint = UzhavanDarkGreen,
                          modifier = Modifier.size(14.dp)
                        )
                      }
                      Spacer(modifier = Modifier.width(6.dp))
                      Text(
                        text = "Uzhavan Voice AI",
                        style = MaterialTheme.typography.labelMedium.copy(
                          fontWeight = FontWeight.Bold,
                          color = UzhavanDarkGreen
                        )
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(6.dp))

                  Text(
                    text = msg.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                      color = UzhavanTextPrimary,
                      lineHeight = 20.sp,
                      fontSize = 14.sp
                    )
                  )
                }
              }
            }
          }
        }

        if (isThinking) {
          item {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Start
            ) {
              Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFF1F8F4),
                border = BorderStroke(1.dp, Color(0xFFD4E8DC))
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = UzhavanDarkGreen,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = if (isTamil) "Gemini Live பதிலளிக்கிறது..." else "Listening & thinking with Gemini Live...",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary,
                      fontWeight = FontWeight.SemiBold
                    )
                  )
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Suggested Voice Prompts Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(suggestedQueries) { query ->
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF1F5F2),
            border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .clickable { sendMessage(query) }
          ) {
            Text(
              text = query,
              style = MaterialTheme.typography.bodySmall.copy(
                color = UzhavanDarkGreen,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
              ),
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Central Live Microphone Visualizer
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(76.dp)
            .scale(if (isListening) pulseScale else 1f)
            .clip(CircleShape)
            .background(if (isListening) Color(0xFFDC2626) else UzhavanDarkGreen)
            .clickable {
              if (!isListening) {
                isListening = true
                scope.launch {
                  delay(1600)
                  isListening = false
                  val sampleSpoken = if (isTamil) "இன்று திருவண்ணாமலையில் தக்காளி விலை என்ன?" else "What is today's tomato price in Tiruvannamalai?"
                  sendMessage(sampleSpoken)
                }
              }
            }
            .testTag("live_mic_action_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (isListening) Icons.Default.MicNone else Icons.Default.Mic,
            contentDescription = "Tap to speak in real-time",
            tint = Color.White,
            modifier = Modifier.size(38.dp)
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = if (isListening) {
            if (isTamil) "நேரலையில் கேட்கிறது..." else "Listening live in real-time..."
          } else {
            if (isTamil) "பேச தட்டவும் (Tap to Speak)" else "Tap to speak with Gemini Live"
          },
          style = MaterialTheme.typography.bodySmall.copy(
            color = UzhavanTextSecondary,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Bottom Text Input for Accessibility & Manual Typing
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = inputText,
          onValueChange = { inputText = it },
          placeholder = {
            Text(
              text = if (isTamil) "அல்லது கேள்வியை தட்டச்சு செய்யவும்..." else "Or type question here...",
              style = MaterialTheme.typography.bodyMedium.copy(color = UzhavanTextSecondary, fontSize = 13.sp)
            )
          },
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("voice_assistant_input"),
          shape = RoundedCornerShape(24.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4),
            focusedContainerColor = Color(0xFFF9FAF9),
            unfocusedContainerColor = Color(0xFFF9FAF9)
          ),
          singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = {
            if (inputText.isNotBlank()) {
              sendMessage(inputText)
            }
          },
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(UzhavanDarkGreen)
            .testTag("send_voice_message_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}
