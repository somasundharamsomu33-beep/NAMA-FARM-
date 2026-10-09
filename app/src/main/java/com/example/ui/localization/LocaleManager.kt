package com.example.ui.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppLanguage(val code: String, val label: String, val nativeLabel: String) {
  ENGLISH("en", "English", "English"),
  TAMIL("ta", "Tamil", "தமிழ்"),
  HINDI("hi", "Hindi", "हिन्दी"),
  TELUGU("te", "Telugu", "తెలుగు"),
  KANNADA("kn", "Kannada", "ಕನ್ನಡ")
}

object StringsEn {
  const val appName = "Uzhavan Market"
  const val tagline = "From Farmer to Market, Fairly."
  const val corePromise = "Plan Better. Grow Better. Sell Better. Earn Better."
  const val splashSub = "Fresh Produce. Direct Buyers. Better Prices."
  const val getStarted = "Get Started"
  const val alreadyAccountLogin = "Already have an account? Login"
  const val welcomeHeader = "Welcome to Uzhavan Market"
  const val chooseRoleSubtitle = "Choose your role to continue"
  const val roleFarmer = "Farmer"
  const val roleFarmerDesc = "List your produce and get better prices"
  const val roleBuyer = "Buyer"
  const val roleBuyerDesc = "Find fresh produce directly from farmers"
  const val buildingAgriEcosystem = "Building a stronger agri ecosystem"

  const val farmerRegTitle = "Farmer Registration"
  const val step1of2 = "Step 1 of 2"
  const val fullName = "Full Name"
  const val phoneNumber = "Phone Number"
  const val district = "District"
  const val village = "Village"
  const val password = "Password"
  const val next = "Next"

  const val goodMorning = "Good Morning"
  const val heroBannerTitle = "Better Price for Your Produce"
  const val listNow = "List now"
  const val listProduce = "List Produce"
  const val myListings = "My Listings"
  const val orders = "Orders"
  const val payments = "Payments"
  const val recentActivity = "Recent Activity"
  const val listingLiveAgo = "Your tomato listing is live • 2 hours ago"

  const val navHome = "Home"
  const val navListings = "Listings"
  const val navMarket = "Market"
  const val navOrders = "Orders"
  const val navEarnings = "Earnings"
  const val navProfile = "Profile"

  const val voiceTab = "Voice"
  const val manualTab = "Manual"
  const val tapToSpeak = "Tap to speak"
  const val voiceHint = "\"500 kg tomato, ready tomorrow\""
  const val cropLabel = "Crop"
  const val quantityLabel = "Quantity (kg)"
  const val expectedPriceLabel = "Expected Price (per kg)"
  const val availableFromLabel = "Available From"

  const val priceAdvisor = "Price Advisor"
  const val marketPrice = "Market Price"
  const val todaysAverage = "Today's average"
  const val yourExpectedPrice = "Your Expected Price"
  const val goodPrice = "Good price!"
  const val priceDataSourceNote = "Based on current market data (Agmarknet, eNAM)"

  const val searchPlaceholder = "Search for vegetables, fruits, etc."
  const val freshLocal = "Fresh & Local"
  const val directFromFarmers = "Direct from Farmers"
  const val popularNow = "Popular Now"
  const val viewAll = "View All"
  const val perKg = "/kg"
  const val available = "Available"

  const val addToCart = "Add to Cart"
  const val farmerDetails = "Farmer Details"
  const val description = "Description"
  const val cart = "Cart"
  const val clearAll = "Clear All"
  const val subtotal = "Subtotal"
  const val totalAmount = "Total Amount"
  const val proceedToCheckout = "Proceed to Checkout"
  const val orderSummary = "Order Summary"
  const val deliveryAddress = "Delivery Address"
  const val delivery = "Delivery"
  const val platformCommission = "Platform Commission (5%)"
  const val proceedToPayment = "Proceed to Payment"
  const val paymentSuccessful = "Payment Successful!"
  const val trackOrder = "Track Order"
  const val backToHome = "Back to Home"
  const val imageUnavailable = "Image unavailable"
}

object StringsTa {
  const val appName = "உழவன் மார்க்கெட்"
  const val tagline = "விவசாயியிடமிருந்து சந்தைக்கு, நேர்மையாக."
  const val corePromise = "சிறப்பாக திட்டமிடுங்கள். சிறப்பாக வளருங்கள். சிறப்பாக விற்பனை செய்யுங்கள்."
  const val splashSub = "புதிய விளைபொருட்கள். நேரடி வாங்குவோர். சிறந்த விலைகள்."
  const val getStarted = "தொடங்கவும்"
  const val alreadyAccountLogin = "ஏற்கனவே கணக்கு உள்ளதா? உள்நுழையவும்"
  const val welcomeHeader = "உழவன் மார்க்கெட்டிற்கு வரவேற்கிறோம்"
  const val chooseRoleSubtitle = "தொடர உங்கள் பங்கைத் தேர்ந்தெடுக்கவும்"
  const val roleFarmer = "விவசாயி"
  const val roleFarmerDesc = "உங்கள் விளைபொருட்களைப் பட்டியலிட்டு சிறந்த விலையைப் பெறுங்கள்"
  const val roleBuyer = "வாங்குபவர்"
  const val roleBuyerDesc = "விவசாயிகளிடமிருந்து நேரடியாக புதிய விளைபொருட்களை வாங்கவும்"
  const val buildingAgriEcosystem = "வலுவான விவசாய கட்டமைப்பை உருவாக்குதல்"

  const val farmerRegTitle = "விவசாயி பதிவு"
  const val step1of2 = "படி 1 / 2"
  const val fullName = "முழுப் பெயர்"
  const val phoneNumber = "தொலைபேசி எண்"
  const val district = "மாவட்டம்"
  const val village = "கிராமம்"
  const val password = "கடவுச்சொல்"
  const val next = "அடுத்து"

  const val goodMorning = "காலை வணக்கம்"
  const val heroBannerTitle = "உங்கள் விளைபொருளுக்கு சிறந்த விலை"
  const val listNow = "பட்டியலிடவும்"
  const val listProduce = "விளைபொருளை பட்டியலிடுக"
  const val myListings = "என் பட்டியல்கள்"
  const val orders = "ஆர்டர்கள்"
  const val payments = "பணப்பரிவர்த்தனைகள்"
  const val recentActivity = "சமீபத்திய செயல்பாடு"
  const val listingLiveAgo = "உங்கள் தக்காளி பட்டியல் நேரலையில் உள்ளது • 2 மணி நேரம் முன்பு"

  const val navHome = "முகப்பு"
  const val navListings = "பட்டியல்கள்"
  const val navMarket = "சந்தை"
  const val navOrders = "ஆர்டர்கள்"
  const val navEarnings = "வருவாய்"
  const val navProfile = "சுயவிவரம்"

  const val voiceTab = "குரல்"
  const val manualTab = "கைமுறை"
  const val tapToSpeak = "பேச தட்டவும்"
  const val voiceHint = "\"500 கிலோ தக்காளி, நாளை தயார்\""
  const val cropLabel = "பயிர்"
  const val quantityLabel = "அளவு (கிலோ)"
  const val expectedPriceLabel = "எதிர்பார்க்கும் விலை (கிலோவுக்கு)"
  const val availableFromLabel = "கிடைக்கும் நாள்"

  const val priceAdvisor = "விலை ஆலோசகர்"
  const val marketPrice = "சந்தை விலை"
  const val todaysAverage = "இன்றைய சராசரி"
  const val yourExpectedPrice = "உங்கள் எதிர்பார்க்கும் விலை"
  const val goodPrice = "சிறந்த விலை!"
  const val priceDataSourceNote = "தற்போதைய சந்தை தரவுகளின் அடிப்படையில் (Agmarknet, eNAM)"

  const val searchPlaceholder = "காய்கறிகள், பழங்கள், தானியங்களைத் தேடுங்கள்..."
  const val freshLocal = "புதிய & உள்ளூர்"
  const val directFromFarmers = "விவசாயிகளிடமிருந்து நேரடியாக"
  const val popularNow = "பிரபலமானவை"
  const val viewAll = "அனைத்தும் பார்"
  const val perKg = "/கிலோ"
  const val available = "கிடைக்கிறது"

  const val addToCart = "கூடையில் சேர்க்கவும்"
  const val farmerDetails = "விவசாயி விவரங்கள்"
  const val description = "விவரம்"
  const val cart = "கூடை"
  const val clearAll = "அனைத்தையும் நீக்கு"
  const val subtotal = "கூட்டுத்தொகை"
  const val totalAmount = "மொத்தத் தொகை"
  const val proceedToCheckout = "செக்அவுட் தொடர்க"
  const val orderSummary = "ஆர்டர் சுருக்கம்"
  const val deliveryAddress = "டெலிவரி முகவரி"
  const val delivery = "டெலிவரி"
  const val platformCommission = "தள கட்டணம் (5%)"
  const val proceedToPayment = "பணம் செலுத்த தொடரவும்"
  const val paymentSuccessful = "பணம் செலுத்துதல் வெற்றிகரமாக முடிந்தது!"
  const val trackOrder = "ஆர்டரைக் கண்காணிக்கவும்"
  const val backToHome = "முகப்புக்கு திரும்பவும்"
  const val imageUnavailable = "படம் கிடைக்கவில்லை"
}

object LocaleManager {
  private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
  val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

  fun setLanguage(lang: AppLanguage) {
    _currentLanguage.value = lang
  }

  fun isTamil(): Boolean = _currentLanguage.value == AppLanguage.TAMIL
}
