package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ProductCategory
import com.example.data.model.ProductListing
import com.example.ui.components.NavItem
import com.example.ui.components.UzhavanBottomNav
import com.example.ui.components.UzhavanProductImage
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@Composable
fun BuyerHomeScreen(
  isTamil: Boolean,
  listings: List<ProductListing>,
  cartItemCount: Int,
  onNavigateToProduct: (ProductListing) -> Unit,
  onNavigateToCart: () -> Unit,
  currentTab: NavItem = NavItem.HOME,
  onTabSelected: (NavItem) -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf(ProductCategory.VEGETABLES) }

  val categories = listOf(
    ProductCategory.VEGETABLES,
    ProductCategory.GRAINS,
    ProductCategory.SEEDS,
    ProductCategory.LEAFY_GREENS,
    ProductCategory.OTHERS
  )

  val filteredListings = remember(searchQuery, selectedCategory, listings) {
    listings.filter { listing ->
      (searchQuery.isBlank() || listing.commodityName.contains(searchQuery, ignoreCase = true))
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    Column(
      modifier = Modifier
        .weight(1f)
        .statusBarsPadding()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // Top Bar: Location + Cart Icon (Matching Screen 7)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clip(RoundedCornerShape(8.dp))
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = UzhavanDarkGreen,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Tiruvannamalai",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 16.sp
            )
          )
        }

        IconButton(
          onClick = onNavigateToCart,
          modifier = Modifier.testTag("buyer_cart_icon_button")
        ) {
          BadgedBox(
            badge = {
              if (cartItemCount > 0) {
                Badge(
                  containerColor = UzhavanDarkGreen,
                  contentColor = Color.White
                ) {
                  Text("$cartItemCount")
                }
              }
            }
          ) {
            Icon(
              imageVector = Icons.Default.ShoppingCart,
              contentDescription = "Cart",
              tint = UzhavanDarkGreen,
              modifier = Modifier.size(26.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Search Bar (Matching Screen 7)
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = {
          Text(
            text = if (isTamil) StringsTa.searchPlaceholder else StringsEn.searchPlaceholder,
            style = MaterialTheme.typography.bodyMedium.copy(color = UzhavanTextSecondary)
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = UzhavanTextSecondary
          )
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("buyer_search_bar"),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = UzhavanDarkGreen,
          unfocusedBorderColor = Color(0xFFE2E8E4),
          focusedContainerColor = Color(0xFFF9FAF9),
          unfocusedContainerColor = Color(0xFFF9FAF9)
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Banner: "Fresh & Local - Direct from Farmers" (Matching Screen 7)
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = UzhavanDarkGreen),
        modifier = Modifier
          .fillMaxWidth()
          .height(105.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1.3f)) {
            Text(
              text = if (isTamil) StringsTa.freshLocal else StringsEn.freshLocal,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
              )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = if (isTamil) StringsTa.directFromFarmers else StringsEn.directFromFarmers,
              style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFFD1FAE5),
                fontSize = 13.sp
              )
            )
          }

          Box(
            modifier = Modifier
              .weight(0.7f)
              .height(80.dp)
              .clip(RoundedCornerShape(10.dp))
          ) {
            Image(
              painter = painterResource(id = R.drawable.ic_tomato_verified),
              contentDescription = "Fresh produce cluster",
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Fit
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Horizontal Categories (Vegetables, Grains, Seeds, Leafy Greens, Others)
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        items(categories) { cat ->
          val isSelected = selectedCategory == cat
          val (icon, label) = when (cat) {
            ProductCategory.VEGETABLES -> Icons.Default.Eco to if (isTamil) "காய்கறிகள்" else "Vegetables"
            ProductCategory.GRAINS -> Icons.Default.Grass to if (isTamil) "தானியங்கள்" else "Grains"
            ProductCategory.SEEDS -> Icons.Default.Spa to if (isTamil) "விதைகள்" else "Seeds"
            ProductCategory.LEAFY_GREENS -> Icons.Default.Eco to if (isTamil) "கீரைகள்" else "Greens"
            ProductCategory.OTHERS -> Icons.Default.MoreHoriz to if (isTamil) "மற்றவை" else "Others"
          }

          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .clickable { selectedCategory = cat }
              .testTag("category_${cat.name.lowercase()}")
          ) {
            Box(
              modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(if (isSelected) UzhavanLightGreen else Color(0xFFF3F4F6)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) UzhavanDarkGreen else UzhavanTextSecondary,
                modifier = Modifier.size(26.dp)
              )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = label,
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) UzhavanDarkGreen else UzhavanTextSecondary,
                fontSize = 12.sp
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Section: "Popular Now" (Matching Screen 7)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (isTamil) StringsTa.popularNow else StringsEn.popularNow,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = UzhavanTextPrimary,
            fontSize = 17.sp
          )
        )
        Text(
          text = if (isTamil) StringsTa.viewAll else StringsEn.viewAll,
          style = MaterialTheme.typography.labelLarge.copy(
            color = UzhavanDarkGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 2-Column Grid of Produce Cards (Screen 7)
      LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.weight(1f)
      ) {
        items(filteredListings) { listing ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE5EBE6)),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onNavigateToProduct(listing) }
              .testTag("produce_card_${listing.commodityName.lowercase()}")
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
            ) {
              // Strict Product Image enforcement: verified photo or neutral placeholder
              UzhavanProductImage(
                verifiedDrawableRes = listing.verifiedDrawableRes,
                productName = listing.commodityName,
                altText = "Fresh ${listing.commodityName}",
                modifier = Modifier
                  .fillMaxWidth()
                  .height(110.dp)
              )

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = listing.commodityName,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanTextPrimary,
                  fontSize = 15.sp
                )
              )

              Spacer(modifier = Modifier.height(2.dp))

              Text(
                text = "₹ ${listing.pricePerKg.toInt()} /kg",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = UzhavanDarkGreen,
                  fontSize = 14.sp
                )
              )

              Text(
                text = "${if (isTamil) StringsTa.available else StringsEn.available}: ${listing.quantityKg} kg",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = UzhavanTextSecondary,
                  fontSize = 11.sp
                )
              )
            }
          }
        }
      }
    }

    // Bottom Navigation Bar
    UzhavanBottomNav(
      currentTab = currentTab,
      onTabSelected = onTabSelected,
      isFarmerMode = false,
      isTamil = isTamil
    )
  }
}
