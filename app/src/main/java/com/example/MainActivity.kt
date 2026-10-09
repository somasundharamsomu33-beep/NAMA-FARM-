package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.data.model.Order
import com.example.data.model.OrderItem
import com.example.data.model.OrderStatus
import com.example.data.model.ProductListing
import com.example.data.model.TrackingStep
import com.example.data.repository.CatalogRepository
import com.example.data.repository.ListingRepository
import com.example.ui.components.NavItem
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.LocaleManager
import com.example.ui.screens.BuyerHomeScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.FarmerDashboardScreen
import com.example.ui.screens.FarmerRegistrationScreen
import com.example.ui.screens.FarmingGuideScreen
import com.example.ui.screens.ListProduceScreen
import com.example.ui.screens.LiveMandiScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.OrderSummaryScreen
import com.example.ui.screens.OrderTrackingScreen
import com.example.ui.screens.OrdersListScreen
import com.example.ui.screens.PaymentHistoryScreen
import com.example.ui.screens.PaymentScreen
import com.example.ui.screens.PaymentSuccessScreen
import com.example.ui.screens.PlanAndProfitScreen
import com.example.ui.screens.PriceAdvisorScreen
import com.example.ui.screens.PriceDashboardScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RoleSelectionScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.VoiceLiveAssistantScreen
import com.example.ui.theme.UzhavanMarketTheme

sealed class AppScreen {
  data object Splash : AppScreen()
  data object RoleSelection : AppScreen()
  data object FarmerRegistration : AppScreen()
  data object FarmerHome : AppScreen()
  data object ListProduce : AppScreen()
  data class PriceAdvisor(val crop: String, val quantity: Int, val expectedPrice: Double) : AppScreen()
  data object PriceDashboard : AppScreen()
  data object VoiceLiveAssistant : AppScreen()
  data object BuyerHome : AppScreen()
  data class ProductDetail(val listing: ProductListing) : AppScreen()
  data object Cart : AppScreen()
  data class OrderSummary(val totalAmount: Double) : AppScreen()
  data class Payment(val totalAmount: Double) : AppScreen()
  data class PaymentSuccess(val orderNumber: String, val totalAmount: Double) : AppScreen()
  data class OrderTracking(val orderNumber: String) : AppScreen()
  data object OrdersList : AppScreen()
  data object PaymentHistory : AppScreen()
  data object Profile : AppScreen()
  data object Notifications : AppScreen()
  data object PlanAndProfit : AppScreen()
  data object LiveMandi : AppScreen()
  data object FarmingGuide : AppScreen()
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      UzhavanMarketTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          UzhavanAppRoot()
        }
      }
    }
  }
}

@Composable
fun UzhavanAppRoot() {
  val currentLanguage by LocaleManager.currentLanguage.collectAsState()
  val isTamil = currentLanguage == AppLanguage.TAMIL

  val listings by ListingRepository.listings.collectAsState()
  val orders by ListingRepository.orders.collectAsState()

  // Navigation Stack State
  var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Splash) }
  var screenHistory by remember { mutableStateOf(listOf<AppScreen>()) }

  fun navigateTo(screen: AppScreen) {
    screenHistory = screenHistory + currentScreen
    currentScreen = screen
  }

  fun navigateBack() {
    if (screenHistory.isNotEmpty()) {
      currentScreen = screenHistory.last()
      screenHistory = screenHistory.dropLast(1)
    }
  }

  // Active Mode (Farmer vs Buyer)
  var isFarmerMode by remember { mutableStateOf(true) }
  var farmerName by remember { mutableStateOf("Ramasamy") }
  var farmerDistrict by remember { mutableStateOf("Tiruvannamalai") }

  // Shared Cart State
  val cartItems = remember {
    mutableStateListOf(
      OrderItem(
        commodityId = "veg_tomato",
        commodityName = "Tomato",
        quantityKg = 100,
        pricePerKg = 18.0,
        verifiedDrawableRes = R.drawable.ic_tomato_verified
      )
    )
  }

  // Bottom Nav Tab tracking
  var currentFarmerTab by remember { mutableStateOf(NavItem.HOME) }
  var currentBuyerTab by remember { mutableStateOf(NavItem.HOME) }

  // Manage Screen with BackHandler
  when (val screen = currentScreen) {
    is AppScreen.Splash -> {
      SplashScreen(
        isTamil = isTamil,
        onLanguageToggle = {
          LocaleManager.setLanguage(if (isTamil) AppLanguage.ENGLISH else AppLanguage.TAMIL)
        },
        onGetStarted = { navigateTo(AppScreen.RoleSelection) },
        onLoginClick = { navigateTo(AppScreen.RoleSelection) }
      )
    }

    is AppScreen.RoleSelection -> {
      BackHandler { navigateBack() }
      RoleSelectionScreen(
        isTamil = isTamil,
        onSelectFarmer = {
          isFarmerMode = true
          navigateTo(AppScreen.FarmerRegistration)
        },
        onSelectBuyer = {
          isFarmerMode = false
          navigateTo(AppScreen.BuyerHome)
        }
      )
    }

    is AppScreen.FarmerRegistration -> {
      BackHandler { navigateBack() }
      FarmerRegistrationScreen(
        isTamil = isTamil,
        onBack = { navigateBack() },
        onCompleteRegistration = { name, dist ->
          farmerName = name
          farmerDistrict = dist
          isFarmerMode = true
          currentFarmerTab = NavItem.HOME
          navigateTo(AppScreen.FarmerHome)
        },
        onLoginClick = {
          isFarmerMode = true
          currentFarmerTab = NavItem.HOME
          navigateTo(AppScreen.FarmerHome)
        }
      )
    }

    is AppScreen.FarmerHome -> {
      FarmerDashboardScreen(
        isTamil = isTamil,
        farmerName = farmerName,
        onNavigateToListProduce = { navigateTo(AppScreen.ListProduce) },
        onNavigateToListings = {
          currentFarmerTab = NavItem.LISTINGS
          navigateTo(AppScreen.BuyerHome) // view active listings
        },
        onNavigateToOrders = {
          currentFarmerTab = NavItem.ORDERS
          navigateTo(AppScreen.OrdersList)
        },
        onNavigateToPayments = {
          currentFarmerTab = NavItem.EARNINGS
          navigateTo(AppScreen.PaymentHistory)
        },
        onNavigateToPriceAdvisor = {
          navigateTo(AppScreen.PriceAdvisor("Tomato", 500, 22.0))
        },
        onNavigateToPriceDashboard = {
          navigateTo(AppScreen.PriceDashboard)
        },
        onNavigateToVoiceAssistant = {
          navigateTo(AppScreen.VoiceLiveAssistant)
        },
        onNavigateToPlanProfit = { navigateTo(AppScreen.PlanAndProfit) },
        onNavigateToLiveMandi = { navigateTo(AppScreen.LiveMandi) },
        onNavigateToFarmingGuide = { navigateTo(AppScreen.FarmingGuide) },
        onNavigateToNotifications = { navigateTo(AppScreen.Notifications) },
        onNavigateToProfile = {
          currentFarmerTab = NavItem.PROFILE
          navigateTo(AppScreen.Profile)
        },
        currentTab = currentFarmerTab,
        onTabSelected = { tab ->
          currentFarmerTab = tab
          when (tab) {
            NavItem.HOME -> {}
            NavItem.LISTINGS -> navigateTo(AppScreen.BuyerHome)
            NavItem.ORDERS -> navigateTo(AppScreen.OrdersList)
            NavItem.EARNINGS -> navigateTo(AppScreen.PaymentHistory)
            NavItem.PROFILE -> navigateTo(AppScreen.Profile)
            else -> {}
          }
        }
      )
    }

    is AppScreen.ListProduce -> {
      BackHandler { navigateBack() }
      ListProduceScreen(
        isTamil = isTamil,
        onBack = { navigateBack() },
        onProceedToAdvisor = { crop, qty, price ->
          navigateTo(AppScreen.PriceAdvisor(crop, qty, price))
        }
      )
    }

    is AppScreen.PriceAdvisor -> {
      BackHandler { navigateBack() }
      PriceAdvisorScreen(
        cropName = screen.crop,
        quantityKg = screen.quantity,
        expectedPrice = screen.expectedPrice,
        isTamil = isTamil,
        onBack = { navigateBack() },
        onPublishListing = {
          val verifiedRes = CatalogRepository.commodities.find {
            it.name.equals(screen.crop, ignoreCase = true)
          }?.verifiedDrawableRes

          ListingRepository.addListing(
            ProductListing(
              id = "list_${System.currentTimeMillis()}",
              commodityId = "crop_${screen.crop.lowercase()}",
              commodityName = screen.crop,
              farmerName = farmerName,
              farmerDistrict = farmerDistrict,
              quantityKg = screen.quantity,
              pricePerKg = screen.expectedPrice,
              verifiedDrawableRes = verifiedRes
            )
          )
          currentFarmerTab = NavItem.HOME
          navigateTo(AppScreen.FarmerHome)
        }
      )
    }

    is AppScreen.PriceDashboard -> {
      BackHandler { navigateBack() }
      PriceDashboardScreen(
        isTamil = isTamil,
        onBack = { navigateBack() },
        onListProduceWithPrice = { crop, price ->
          navigateTo(AppScreen.PriceAdvisor(crop, 500, price))
        },
        onOpenVoiceAssistant = {
          navigateTo(AppScreen.VoiceLiveAssistant)
        }
      )
    }

    is AppScreen.VoiceLiveAssistant -> {
      BackHandler { navigateBack() }
      VoiceLiveAssistantScreen(
        isTamil = isTamil,
        onBack = { navigateBack() }
      )
    }

    is AppScreen.BuyerHome -> {
      if (isFarmerMode) {
        BackHandler {
          currentFarmerTab = NavItem.HOME
          navigateTo(AppScreen.FarmerHome)
        }
      }
      BuyerHomeScreen(
        isTamil = isTamil,
        listings = listings,
        cartItemCount = cartItems.size,
        onNavigateToProduct = { listing -> navigateTo(AppScreen.ProductDetail(listing)) },
        onNavigateToCart = { navigateTo(AppScreen.Cart) },
        currentTab = if (isFarmerMode) NavItem.LISTINGS else currentBuyerTab,
        onTabSelected = { tab ->
          if (isFarmerMode) {
            currentFarmerTab = tab
            when (tab) {
              NavItem.HOME -> navigateTo(AppScreen.FarmerHome)
              NavItem.ORDERS -> navigateTo(AppScreen.OrdersList)
              NavItem.EARNINGS -> navigateTo(AppScreen.PaymentHistory)
              NavItem.PROFILE -> navigateTo(AppScreen.Profile)
              else -> {}
            }
          } else {
            currentBuyerTab = tab
            when (tab) {
              NavItem.HOME -> {}
              NavItem.SEARCH -> {}
              NavItem.ORDERS -> navigateTo(AppScreen.OrdersList)
              NavItem.PROFILE -> navigateTo(AppScreen.Profile)
              else -> {}
            }
          }
        }
      )
    }

    is AppScreen.ProductDetail -> {
      BackHandler { navigateBack() }
      ProductDetailScreen(
        listing = screen.listing,
        isTamil = isTamil,
        onBack = { navigateBack() },
        onAddToCart = { qty ->
          val existing = cartItems.indexOfFirst { it.commodityId == screen.listing.commodityId }
          if (existing >= 0) {
            val curr = cartItems[existing]
            cartItems[existing] = curr.copy(quantityKg = curr.quantityKg + qty)
          } else {
            cartItems.add(
              OrderItem(
                commodityId = screen.listing.commodityId,
                commodityName = screen.listing.commodityName,
                quantityKg = qty,
                pricePerKg = screen.listing.pricePerKg,
                verifiedDrawableRes = screen.listing.verifiedDrawableRes
              )
            )
          }
          navigateTo(AppScreen.Cart)
        }
      )
    }

    is AppScreen.Cart -> {
      BackHandler { navigateBack() }
      CartScreen(
        cartItems = cartItems,
        isTamil = isTamil,
        onBack = { navigateBack() },
        onUpdateQuantity = { commodityId, newQty ->
          val idx = cartItems.indexOfFirst { it.commodityId == commodityId }
          if (idx >= 0) {
            cartItems[idx] = cartItems[idx].copy(quantityKg = newQty)
          }
        },
        onRemoveItem = { commodityId ->
          cartItems.removeAll { it.commodityId == commodityId }
        },
        onClearAll = { cartItems.clear() },
        onProceedToCheckout = {
          val subtotal = cartItems.sumOf { it.subtotal }
          navigateTo(AppScreen.OrderSummary(subtotal))
        }
      )
    }

    is AppScreen.OrderSummary -> {
      BackHandler { navigateBack() }
      OrderSummaryScreen(
        orderItems = cartItems,
        isTamil = isTamil,
        onBack = { navigateBack() },
        onProceedToPayment = { total ->
          navigateTo(AppScreen.Payment(total))
        }
      )
    }

    is AppScreen.Payment -> {
      BackHandler { navigateBack() }
      PaymentScreen(
        amountToPay = screen.totalAmount,
        isTamil = isTamil,
        onBack = { navigateBack() },
        onPaymentSuccess = {
          val orderNum = "UM${(10000..99999).random()}"
          ListingRepository.placeOrder(
            Order(
              id = "ord_${System.currentTimeMillis()}",
              orderNumber = orderNum,
              datePlaced = "Just now",
              items = cartItems.toList(),
              status = OrderStatus.CONFIRMED,
              trackingSteps = listOf(
                TrackingStep("Order Confirmed", "Just now", isCompleted = true, isCurrent = true),
                TrackingStep("Pickup Scheduled", "Today, 2:00 PM", isCompleted = false),
                TrackingStep("Out for Delivery", "Today, 4:00 PM", isCompleted = false),
                TrackingStep("Delivered", "Expected by 6:00 PM", isCompleted = false)
              )
            )
          )
          cartItems.clear()
          navigateTo(AppScreen.PaymentSuccess(orderNum, screen.totalAmount))
        }
      )
    }

    is AppScreen.PaymentSuccess -> {
      PaymentSuccessScreen(
        orderNumber = screen.orderNumber,
        totalAmount = screen.totalAmount,
        isTamil = isTamil,
        onTrackOrder = { navigateTo(AppScreen.OrderTracking(screen.orderNumber)) },
        onBackToHome = {
          if (isFarmerMode) {
            currentFarmerTab = NavItem.HOME
            navigateTo(AppScreen.FarmerHome)
          } else {
            currentBuyerTab = NavItem.HOME
            navigateTo(AppScreen.BuyerHome)
          }
        }
      )
    }

    is AppScreen.OrderTracking -> {
      BackHandler { navigateBack() }
      OrderTrackingScreen(
        orderNumber = screen.orderNumber,
        isTamil = isTamil,
        onBack = { navigateBack() },
        onContactSupport = { navigateBack() }
      )
    }

    is AppScreen.OrdersList -> {
      BackHandler {
        if (isFarmerMode) {
          currentFarmerTab = NavItem.HOME
          navigateTo(AppScreen.FarmerHome)
        } else {
          currentBuyerTab = NavItem.HOME
          navigateTo(AppScreen.BuyerHome)
        }
      }
      OrdersListScreen(
        orders = orders,
        isFarmerMode = isFarmerMode,
        isTamil = isTamil,
        onOrderClick = { order -> navigateTo(AppScreen.OrderTracking(order.orderNumber)) },
        currentTab = if (isFarmerMode) currentFarmerTab else currentBuyerTab,
        onTabSelected = { tab ->
          if (isFarmerMode) {
            currentFarmerTab = tab
            when (tab) {
              NavItem.HOME -> navigateTo(AppScreen.FarmerHome)
              NavItem.LISTINGS -> navigateTo(AppScreen.BuyerHome)
              NavItem.ORDERS -> {}
              NavItem.EARNINGS -> navigateTo(AppScreen.PaymentHistory)
              NavItem.PROFILE -> navigateTo(AppScreen.Profile)
              else -> {}
            }
          } else {
            currentBuyerTab = tab
            when (tab) {
              NavItem.HOME -> navigateTo(AppScreen.BuyerHome)
              NavItem.SEARCH -> navigateTo(AppScreen.BuyerHome)
              NavItem.ORDERS -> {}
              NavItem.PROFILE -> navigateTo(AppScreen.Profile)
              else -> {}
            }
          }
        }
      )
    }

    is AppScreen.PaymentHistory -> {
      BackHandler {
        currentFarmerTab = NavItem.HOME
        navigateTo(AppScreen.FarmerHome)
      }
      PaymentHistoryScreen(
        isTamil = isTamil,
        currentTab = currentFarmerTab,
        onTabSelected = { tab ->
          currentFarmerTab = tab
          when (tab) {
            NavItem.HOME -> navigateTo(AppScreen.FarmerHome)
            NavItem.LISTINGS -> navigateTo(AppScreen.BuyerHome)
            NavItem.ORDERS -> navigateTo(AppScreen.OrdersList)
            NavItem.EARNINGS -> {}
            NavItem.PROFILE -> navigateTo(AppScreen.Profile)
            else -> {}
          }
        }
      )
    }

    is AppScreen.Profile -> {
      BackHandler {
        if (isFarmerMode) {
          currentFarmerTab = NavItem.HOME
          navigateTo(AppScreen.FarmerHome)
        } else {
          currentBuyerTab = NavItem.HOME
          navigateTo(AppScreen.BuyerHome)
        }
      }
      ProfileScreen(
        farmerName = farmerName,
        district = farmerDistrict,
        isFarmerMode = isFarmerMode,
        isTamil = isTamil,
        onLanguageToggle = {
          LocaleManager.setLanguage(if (isTamil) AppLanguage.ENGLISH else AppLanguage.TAMIL)
        },
        onSwitchMode = {
          isFarmerMode = !isFarmerMode
          if (isFarmerMode) {
            currentFarmerTab = NavItem.HOME
            navigateTo(AppScreen.FarmerHome)
          } else {
            currentBuyerTab = NavItem.HOME
            navigateTo(AppScreen.BuyerHome)
          }
        },
        onLogout = {
          screenHistory = emptyList()
          navigateTo(AppScreen.Splash)
        },
        currentTab = if (isFarmerMode) currentFarmerTab else currentBuyerTab,
        onTabSelected = { tab ->
          if (isFarmerMode) {
            currentFarmerTab = tab
            when (tab) {
              NavItem.HOME -> navigateTo(AppScreen.FarmerHome)
              NavItem.LISTINGS -> navigateTo(AppScreen.BuyerHome)
              NavItem.ORDERS -> navigateTo(AppScreen.OrdersList)
              NavItem.EARNINGS -> navigateTo(AppScreen.PaymentHistory)
              NavItem.PROFILE -> {}
              else -> {}
            }
          } else {
            currentBuyerTab = tab
            when (tab) {
              NavItem.HOME -> navigateTo(AppScreen.BuyerHome)
              NavItem.SEARCH -> navigateTo(AppScreen.BuyerHome)
              NavItem.ORDERS -> navigateTo(AppScreen.OrdersList)
              NavItem.PROFILE -> {}
              else -> {}
            }
          }
        }
      )
    }

    is AppScreen.Notifications -> {
      BackHandler { navigateBack() }
      NotificationsScreen(
        isTamil = isTamil,
        onBack = { navigateBack() }
      )
    }

    is AppScreen.PlanAndProfit -> {
      BackHandler { navigateBack() }
      PlanAndProfitScreen(
        isTamil = isTamil,
        onBack = { navigateBack() }
      )
    }

    is AppScreen.LiveMandi -> {
      BackHandler { navigateBack() }
      LiveMandiScreen(
        isTamil = isTamil,
        onBack = { navigateBack() },
        onOpenPriceDashboard = { navigateTo(AppScreen.PriceDashboard) }
      )
    }

    is AppScreen.FarmingGuide -> {
      BackHandler { navigateBack() }
      FarmingGuideScreen(
        isTamil = isTamil,
        onBack = { navigateBack() }
      )
    }
  }
}
