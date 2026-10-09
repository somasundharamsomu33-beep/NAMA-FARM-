package com.example.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanTextSecondary

/**
 * Strict product image component enforcing Uzhavan Market verification rules:
 * 1. If verified image drawable is provided, displays the verified asset with correct alt text.
 * 2. If image is unavailable or pending verification, strictly displays the neutral placeholder
 *    with product name and "Image unavailable" label. Never substitutes another product!
 */
@Composable
fun UzhavanProductImage(
  @DrawableRes verifiedDrawableRes: Int?,
  productName: String,
  altText: String,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Fit
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(Color(0xFFF7FAF8))
      .testTag("product_image_${productName.lowercase().replace(" ", "_")}"),
    contentAlignment = Alignment.Center
  ) {
    if (verifiedDrawableRes != null) {
      Image(
        painter = painterResource(id = verifiedDrawableRes),
        contentDescription = altText,
        modifier = Modifier
          .fillMaxSize()
          .padding(8.dp),
        contentScale = contentScale
      )
    } else {
      // Rule #45 Fallback: Clean neutral placeholder with Product Name & "Image unavailable"
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE8F5E9)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Eco,
            contentDescription = null,
            tint = UzhavanDarkGreen,
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = productName,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp
          ),
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          textAlign = TextAlign.Center
        )
        Text(
          text = "Image unavailable",
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 9.sp,
            color = UzhavanTextSecondary
          ),
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
