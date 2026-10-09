package com.example.data.model

import androidx.annotation.DrawableRes

enum class ProductCategory(val displayName: String, val tamilName: String) {
  VEGETABLES("Vegetables", "காய்கறிகள்"),
  GRAINS("Grains & Millets", "தானியங்கள் & சிறுதானியங்கள்"),
  SEEDS("Seeds", "விதைகள்"),
  LEAFY_GREENS("Leafy Greens", "கீரைகள்"),
  OTHERS("Others", "மற்றவை")
}

enum class VerificationStatus {
  VERIFIED,
  PENDING,
  REJECTED
}

data class Commodity(
  val id: String,
  val name: String,
  val tamilName: String,
  val category: ProductCategory,
  @DrawableRes val verifiedDrawableRes: Int? = null,
  val altText: String,
  val verificationStatus: VerificationStatus = if (verifiedDrawableRes != null) VerificationStatus.VERIFIED else VerificationStatus.PENDING,
  val unit: String = "kg",
  val defaultMandiPrice: Double = 20.0,
  val priceRangeMin: Double = 18.0,
  val priceRangeMax: Double = 24.0,
  val arrivalTonnes: Int = 180,
  val demand: String = "High",
  val trend: String = "Increasing", // "Increasing", "Stable", "Decreasing"
  val description: String = ""
)

data class ProductListing(
  val id: String,
  val commodityId: String,
  val commodityName: String,
  val farmerId: String = "farmer_1",
  val farmerName: String = "Ramasamy",
  val farmerDistrict: String = "Tiruvannamalai",
  val farmerVillage: String = "Vellore",
  val farmerRating: Double = 4.8,
  val reviewCount: Int = 12,
  val quantityKg: Int,
  val pricePerKg: Double,
  val qualityGrade: String = "Grade A",
  val availableFrom: String = "Today",
  val description: String = "Fresh and chemical-free produce, harvested directly from farm.",
  @DrawableRes val verifiedDrawableRes: Int? = null,
  val status: String = "Active"
)

data class MandiPriceInfo(
  val marketName: String,
  val district: String,
  val state: String = "Tamil Nadu",
  val commodityName: String,
  val minPrice: Double,
  val maxPrice: Double,
  val modalPrice: Double,
  val arrivalTonnes: Int,
  val arrivalChangePercent: Int,
  val distanceKm: Int,
  val transportCostPerKg: Double,
  val estimatedNetReturnPerKg: Double,
  val date: String,
  val isLive: Boolean = false // Clearly labelled LIVE vs DEMO DATA per requirement
)

enum class OrderStatus(val label: String) {
  CONFIRMED("Order Confirmed"),
  PICKUP_SCHEDULED("Pickup Scheduled"),
  OUT_FOR_DELIVERY("Out for Delivery"),
  DELIVERED("Delivered")
}

data class TrackingStep(
  val title: String,
  val timeOrDate: String,
  val isCompleted: Boolean,
  val isCurrent: Boolean = false
)

data class OrderItem(
  val commodityId: String,
  val commodityName: String,
  val quantityKg: Int,
  val pricePerKg: Double,
  @DrawableRes val verifiedDrawableRes: Int?
) {
  val subtotal: Double get() = quantityKg * pricePerKg
}

data class Order(
  val id: String,
  val orderNumber: String,
  val datePlaced: String,
  val buyerName: String = "Hotel Sunrise",
  val deliveryAddress: String = "Hotel Sunrise, 123, Main Road, Tiruvannamalai - 606601",
  val items: List<OrderItem>,
  val deliveryFee: Double = 150.0,
  val platformCommission: Double = 90.0,
  val status: OrderStatus = OrderStatus.OUT_FOR_DELIVERY,
  val trackingSteps: List<TrackingStep> = emptyList()
) {
  val itemsTotal: Double get() = items.sumOf { it.subtotal }
  val grandTotal: Double get() = itemsTotal + deliveryFee + platformCommission
}

data class PaymentTransaction(
  val id: String,
  val orderNumber: String,
  val date: String,
  val amount: Double,
  val method: String, // "UPI", "Bank Transfer"
  val status: String = "Paid",
  val isCredit: Boolean = true
)

data class NotificationItem(
  val id: String,
  val title: String,
  val message: String,
  val timestamp: String,
  val category: String, // "Orders", "Payments", "Offers", "Price"
  val isRead: Boolean = false
)

data class CropPlanResult(
  val cropName: String,
  val landAreaAcres: Double,
  val seedCost: Double,
  val fertilizerCost: Double,
  val labourCost: Double,
  val irrigationCost: Double,
  val pestControlCost: Double,
  val equipmentCost: Double,
  val transportCost: Double,
  val otherCost: Double,
  val expectedYieldKg: Double,
  val expectedPricePerKg: Double
) {
  val totalInvestment: Double
    get() = seedCost + fertilizerCost + labourCost + irrigationCost + pestControlCost + equipmentCost + transportCost + otherCost
  val expectedRevenue: Double get() = expectedYieldKg * expectedPricePerKg
  val expectedProfit: Double get() = expectedRevenue - totalInvestment
  val profitMarginPercent: Double get() = if (totalInvestment > 0) (expectedProfit / totalInvestment) * 100 else 0.0
}
