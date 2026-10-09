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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.example.data.model.OrderItem
import com.example.ui.components.UzhavanProductImage
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun OrderSummaryScreen(
  orderItems: List<OrderItem>,
  deliveryAddress: String = "Hotel Sunrise, 123, Main Road, Tiruvannamalai - 606601",
  deliveryFee: Double = 150.0,
  platformCommissionPercent: Double = 5.0,
  isTamil: Boolean,
  onBack: () -> Unit,
  onProceedToPayment: (totalAmount: Double) -> Unit
) {
  val itemsTotal = orderItems.sumOf { it.subtotal }
  val commissionAmount = (itemsTotal * (platformCommissionPercent / 100.0)).toInt().toDouble()
  val grandTotal = itemsTotal + deliveryFee + commissionAmount

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
            modifier = Modifier.testTag("summary_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) StringsTa.orderSummary else StringsEn.orderSummary,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Step Indicator: Address (checked) -> Payment -> Confirm (Screen 10)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          StepCircle(step = "1", label = "Address", isDone = true)
          StepLine(isDone = false)
          StepCircle(step = "2", label = "Payment", isDone = false, isCurrent = true)
          StepLine(isDone = false)
          StepCircle(step = "3", label = "Confirm", isDone = false)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Delivery Address Card (Screen 10)
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
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.LocationOn,
                  contentDescription = null,
                  tint = UzhavanDarkGreen,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (isTamil) StringsTa.deliveryAddress else StringsEn.deliveryAddress,
                  style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanTextPrimary
                  )
                )
              }

              Text(
                text = "Change",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = UzhavanDarkGreen,
                  fontWeight = FontWeight.Bold
                )
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = deliveryAddress,
              style = MaterialTheme.typography.bodyMedium.copy(
                color = UzhavanTextSecondary,
                lineHeight = 20.sp
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Order Items Card (Screen 10)
        Text(
          text = if (isTamil) "ஆர்டர் செய்யப்பட்ட பொருட்கள்" else "Order Items",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 16.sp
          )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            orderItems.forEach { item ->
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                UzhavanProductImage(
                  verifiedDrawableRes = item.verifiedDrawableRes,
                  productName = item.commodityName,
                  altText = item.commodityName,
                  modifier = Modifier.size(54.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = item.commodityName,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = UzhavanTextPrimary
                    )
                  )
                  Text(
                    text = "${item.quantityKg} kg × ₹ ${item.pricePerKg.toInt()}",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = UzhavanTextSecondary
                    )
                  )
                }

                Text(
                  text = "₹ ${item.subtotal.toInt()}",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = UzhavanTextPrimary
                  )
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Price Breakdown (Screen 10)
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
          border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = if (isTamil) StringsTa.subtotal else StringsEn.subtotal,
                style = MaterialTheme.typography.bodyMedium.copy(color = UzhavanTextSecondary)
              )
              Text(
                text = "₹ ${itemsTotal.toInt()}",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = UzhavanTextPrimary
                )
              )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = if (isTamil) StringsTa.delivery else StringsEn.delivery,
                style = MaterialTheme.typography.bodyMedium.copy(color = UzhavanTextSecondary)
              )
              Text(
                text = "₹ ${deliveryFee.toInt()}",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = UzhavanTextPrimary
                )
              )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = if (isTamil) StringsTa.platformCommission else StringsEn.platformCommission,
                style = MaterialTheme.typography.bodyMedium.copy(color = UzhavanTextSecondary)
              )
              Text(
                text = "₹ ${commissionAmount.toInt()}",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = UzhavanTextPrimary
                )
              )
            }

            HorizontalDivider(color = Color(0xFFE2E8E4), modifier = Modifier.padding(vertical = 4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = if (isTamil) StringsTa.totalAmount else StringsEn.totalAmount,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary
                )
              )
              Text(
                text = "₹ ${grandTotal.toInt()}",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanDarkGreen,
                  fontSize = 22.sp
                )
              )
            }
          }
        }
      }

      // Proceed to Payment Button (Screen 10)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 16.dp)
      ) {
        Button(
          onClick = { onProceedToPayment(grandTotal) },
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("proceed_to_payment_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (isTamil) StringsTa.proceedToPayment else StringsEn.proceedToPayment,
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
private fun StepCircle(
  step: String,
  label: String,
  isDone: Boolean,
  isCurrent: Boolean = false
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Box(
      modifier = Modifier
        .size(28.dp)
        .clip(CircleShape)
        .background(
          if (isDone) UzhavanDarkGreen else if (isCurrent) UzhavanLightGreen else Color(0xFFE5E7EB)
        ),
      contentAlignment = Alignment.Center
    ) {
      if (isDone) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
      } else {
        Text(
          text = step,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = if (isCurrent) UzhavanDarkGreen else Color(0xFF6B7280)
          )
        )
      }
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        fontWeight = if (isDone || isCurrent) FontWeight.Bold else FontWeight.Normal,
        color = if (isDone || isCurrent) UzhavanDarkGreen else Color(0xFF6B7280)
      )
    )
  }
}

@Composable
private fun StepLine(isDone: Boolean) {
  Box(
    modifier = Modifier
      .width(46.dp)
      .height(2.dp)
      .background(if (isDone) UzhavanDarkGreen else Color(0xFFE5E7EB))
      .padding(horizontal = 4.dp)
  )
}
