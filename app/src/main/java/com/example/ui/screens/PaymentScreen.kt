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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

enum class PaymentMethod(val label: String, val subtitle: String) {
  UPI("UPI", "PhonePe / GPay / Paytm / etc."),
  BANK_TRANSFER("Bank Transfer", "NEFT / RTGS / IMPS"),
  WALLET("Wallet", "Uzhavan Agri Wallet"),
  CARD("Card", "Debit / Credit Card")
}

@Composable
fun PaymentScreen(
  amountToPay: Double = 2040.0,
  isTamil: Boolean,
  onBack: () -> Unit,
  onPaymentSuccess: (method: String) -> Unit
) {
  var selectedMethod by remember { mutableStateOf(PaymentMethod.UPI) }

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
            modifier = Modifier.testTag("payment_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) "பணம் செலுத்துதல்" else "Payment",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
          text = if (isTamil) "பணம் செலுத்தும் முறையைத் தேர்ந்தெடுக்கவும்" else "Select Payment Method",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 17.sp
          )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Payment Options (Screen 11)
        PaymentMethodItem(
          method = PaymentMethod.UPI,
          icon = Icons.Default.QrCode,
          isSelected = selectedMethod == PaymentMethod.UPI,
          onClick = { selectedMethod = PaymentMethod.UPI }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PaymentMethodItem(
          method = PaymentMethod.BANK_TRANSFER,
          icon = Icons.Default.AccountBalance,
          isSelected = selectedMethod == PaymentMethod.BANK_TRANSFER,
          onClick = { selectedMethod = PaymentMethod.BANK_TRANSFER }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PaymentMethodItem(
          method = PaymentMethod.WALLET,
          icon = Icons.Default.AccountBalanceWallet,
          isSelected = selectedMethod == PaymentMethod.WALLET,
          onClick = { selectedMethod = PaymentMethod.WALLET }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PaymentMethodItem(
          method = PaymentMethod.CARD,
          icon = Icons.Default.CreditCard,
          isSelected = selectedMethod == PaymentMethod.CARD,
          onClick = { selectedMethod = PaymentMethod.CARD }
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Security Guarantee Card (Screen 11)
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F4)),
          border = BorderStroke(1.dp, Color(0xFFD4E8DC)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Security,
              contentDescription = null,
              tint = UzhavanDarkGreen,
              modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {
              Text(
                text = if (isTamil) "பாதுகாப்பான பணப்பரிவர்த்தனை" else "Secure & Safe Payments",
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanDarkGreen
                )
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = if (isTamil) "உங்கள் பணம் வங்கி நிலை பாதுகாப்புடன் பாதுகாக்கப்படுகிறது." else "Your payment is protected with bank-level security.",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 12.sp
                )
              )
            }
          }
        }
      }

      // Bottom Pay Button (Screen 11)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 16.dp)
      ) {
        Button(
          onClick = { onPaymentSuccess(selectedMethod.name) },
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("pay_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = "Pay ₹ ${amountToPay.toInt()}",
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
private fun PaymentMethodItem(
  method: PaymentMethod,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFF9FAF9) else Color.White),
    border = BorderStroke(
      width = if (isSelected) 1.5.dp else 1.dp,
      color = if (isSelected) UzhavanDarkGreen else Color(0xFFE2E8E4)
    ),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag("payment_method_${method.name.lowercase()}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) UzhavanDarkGreen else UzhavanTextSecondary,
        modifier = Modifier.size(24.dp)
      )

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = method.label,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 15.sp
          )
        )
        Text(
          text = method.subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            color = UzhavanTextSecondary,
            fontSize = 12.sp
          )
        )
      }

      RadioButton(
        selected = isSelected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(selectedColor = UzhavanDarkGreen)
      )
    }
  }
}
