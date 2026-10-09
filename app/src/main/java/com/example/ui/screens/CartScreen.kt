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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Remove
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
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun CartScreen(
  cartItems: List<OrderItem>,
  isTamil: Boolean,
  onBack: () -> Unit,
  onUpdateQuantity: (commodityId: String, newQty: Int) -> Unit,
  onRemoveItem: (commodityId: String) -> Unit,
  onClearAll: () -> Unit,
  onProceedToCheckout: () -> Unit
) {
  val subtotal = cartItems.sumOf { it.subtotal }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 20.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
        // Top Header: Back, Cart, Clear All (Screen 9)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = onBack,
              modifier = Modifier.testTag("cart_back_button")
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = UzhavanDarkGreen
              )
            }
            Text(
              text = if (isTamil) StringsTa.cart else StringsEn.cart,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary,
                fontSize = 20.sp
              )
            )
          }

          if (cartItems.isNotEmpty()) {
            Text(
              text = if (isTamil) StringsTa.clearAll else StringsEn.clearAll,
              style = MaterialTheme.typography.bodyMedium.copy(
                color = UzhavanDarkGreen,
                fontWeight = FontWeight.SemiBold
              ),
              modifier = Modifier
                .clickable { onClearAll() }
                .padding(8.dp)
                .testTag("clear_all_button")
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (isTamil) "உங்கள் கூடை காலியாக உள்ளது" else "Your cart is empty",
              style = MaterialTheme.typography.bodyLarge.copy(color = UzhavanTextSecondary)
            )
          }
        } else {
          LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            items(cartItems) { item ->
              Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAF9)),
                border = BorderStroke(1.dp, Color(0xFFE2E8E4)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // Strict verified image or neutral placeholder
                  UzhavanProductImage(
                    verifiedDrawableRes = item.verifiedDrawableRes,
                    productName = item.commodityName,
                    altText = item.commodityName,
                    modifier = Modifier.size(68.dp)
                  )

                  Spacer(modifier = Modifier.width(14.dp))

                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = item.commodityName,
                      style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = UzhavanTextPrimary,
                        fontSize = 16.sp
                      )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                      text = "₹ ${item.pricePerKg.toInt()} /kg",
                      style = MaterialTheme.typography.bodySmall.copy(
                        color = UzhavanTextSecondary
                      )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Stepper: [-] 100 kg [+]
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                      IconButton(
                        onClick = {
                          if (item.quantityKg > 20) {
                            onUpdateQuantity(item.commodityId, item.quantityKg - 20)
                          }
                        },
                        modifier = Modifier.size(28.dp)
                      ) {
                        Icon(
                          imageVector = Icons.Default.Remove,
                          contentDescription = "Decrease",
                          tint = UzhavanDarkGreen,
                          modifier = Modifier.size(16.dp)
                        )
                      }

                      Text(
                        text = "${item.quantityKg} kg",
                        style = MaterialTheme.typography.bodyMedium.copy(
                          fontWeight = FontWeight.Bold,
                          color = UzhavanTextPrimary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                      )

                      IconButton(
                        onClick = {
                          onUpdateQuantity(item.commodityId, item.quantityKg + 20)
                        },
                        modifier = Modifier.size(28.dp)
                      ) {
                        Icon(
                          imageVector = Icons.Default.Add,
                          contentDescription = "Increase",
                          tint = UzhavanDarkGreen,
                          modifier = Modifier.size(16.dp)
                        )
                      }
                    }
                  }

                  Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween
                  ) {
                    IconButton(
                      onClick = { onRemoveItem(item.commodityId) },
                      modifier = Modifier.size(28.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = Color(0xFFEF4444)
                      )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                      text = "₹ ${item.subtotal.toInt()}",
                      style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = UzhavanDarkGreen,
                        fontSize = 16.sp
                      )
                    )
                  }
                }
              }
            }
          }
        }
      }

      // Bottom Summary & Checkout Button (Screen 9)
      if (cartItems.isNotEmpty()) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 12.dp)
        ) {
          HorizontalDivider(color = Color(0xFFEEEEEE))
          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = if (isTamil) StringsTa.subtotal else StringsEn.subtotal,
              style = MaterialTheme.typography.bodyLarge.copy(color = UzhavanTextSecondary)
            )
            Text(
              text = "₹ ${subtotal.toInt()}",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary
              )
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = if (isTamil) StringsTa.totalAmount else StringsEn.totalAmount,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanTextPrimary
              )
            )
            Text(
              text = "₹ ${subtotal.toInt()}",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = UzhavanDarkGreen,
                fontSize = 22.sp
              )
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          Button(
            onClick = onProceedToCheckout,
            modifier = Modifier
              .fillMaxWidth()
              .height(54.dp)
              .testTag("proceed_to_checkout_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = UzhavanDarkGreen,
              contentColor = Color.White
            )
          ) {
            Text(
              text = if (isTamil) StringsTa.proceedToCheckout else StringsEn.proceedToCheckout,
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
}
