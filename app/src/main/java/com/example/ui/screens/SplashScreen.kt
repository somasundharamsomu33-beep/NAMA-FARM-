package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.LocaleManager
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun SplashScreen(
  isTamil: Boolean,
  onLanguageToggle: () -> Unit,
  onGetStarted: () -> Unit,
  onLoginClick: () -> Unit
) {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 24.dp, vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // Top Language Switcher Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = UzhavanLightGreen,
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onLanguageToggle() }
            .testTag("language_toggle_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Language,
              contentDescription = "Language",
              tint = UzhavanDarkGreen,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
              text = if (isTamil) "English" else "தமிழ்",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanDarkGreen
              )
            )
          }
        }
      }

      // Middle Brand & Hero Illustration
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Logo Sprout Symbol
        Box(
          modifier = Modifier
            .size(76.dp)
            .clip(CircleShape)
            .background(UzhavanLightGreen),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Eco,
            contentDescription = "Uzhavan Market Logo",
            tint = UzhavanDarkGreen,
            modifier = Modifier.size(46.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Brand Name
        Text(
          text = if (isTamil) StringsTa.appName else StringsEn.appName,
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanDarkGreen,
            fontSize = 32.sp
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
          text = if (isTamil) StringsTa.splashSub else StringsEn.splashSub,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = UzhavanTextSecondary,
            fontSize = 15.sp,
            lineHeight = 22.sp
          ),
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Agricultural Landscape Card
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(24.dp)),
          color = Color(0xFFE8F5E9)
        ) {
          Image(
            painter = painterResource(id = R.drawable.img_farmer_hero),
            contentDescription = "Farmer in lush farm fields",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )
        }
      }

      // Bottom Actions (Matching reference screen 1)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Button(
          onClick = onGetStarted,
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .testTag("get_started_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (isTamil) StringsTa.getStarted else StringsEn.getStarted,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = if (isTamil) StringsTa.alreadyAccountLogin else StringsEn.alreadyAccountLogin,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = UzhavanDarkGreen,
            fontWeight = FontWeight.SemiBold
          ),
          textAlign = TextAlign.Center,
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onLoginClick() }
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .testTag("login_link")
        )
      }
    }
  }
}
