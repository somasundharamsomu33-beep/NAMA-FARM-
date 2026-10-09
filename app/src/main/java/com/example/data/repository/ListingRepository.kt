package com.example.data.repository

import com.example.R
import com.example.data.model.NotificationItem
import com.example.data.model.Order
import com.example.data.model.OrderItem
import com.example.data.model.OrderStatus
import com.example.data.model.PaymentTransaction
import com.example.data.model.ProductListing
import com.example.data.model.TrackingStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ListingRepository {

  private val _listings = MutableStateFlow<List<ProductListing>>(
    listOf(
      ProductListing(
        id = "list_1",
        commodityId = "veg_tomato",
        commodityName = "Tomato",
        farmerId = "farmer_ramasamy",
        farmerName = "Ramasamy",
        farmerDistrict = "Tiruvannamalai",
        farmerVillage = "Vellore",
        farmerRating = 4.8,
        reviewCount = 12,
        quantityKg = 500,
        pricePerKg = 18.0,
        qualityGrade = "Grade A",
        availableFrom = "Today",
        description = "Fresh and chemical-free tomatoes, harvested today.",
        verifiedDrawableRes = R.drawable.ic_tomato_verified,
        status = "Live"
      ),
      ProductListing(
        id = "list_2",
        commodityId = "veg_onion",
        commodityName = "Onion",
        farmerId = "farmer_ramasamy",
        farmerName = "Ramasamy",
        farmerDistrict = "Tiruvannamalai",
        farmerVillage = "Vellore",
        farmerRating = 4.8,
        reviewCount = 12,
        quantityKg = 300,
        pricePerKg = 16.0,
        qualityGrade = "Grade A",
        availableFrom = "Tomorrow",
        description = "Graded Bellary & local pink onions, dry and firm.",
        verifiedDrawableRes = R.drawable.ic_onion_verified,
        status = "Live"
      ),
      ProductListing(
        id = "list_3",
        commodityId = "veg_brinjal",
        commodityName = "Brinjal (Eggplant)",
        farmerId = "farmer_selvam",
        farmerName = "Selvam K.",
        farmerDistrict = "Vellore",
        farmerVillage = "Katpadi",
        farmerRating = 4.7,
        reviewCount = 8,
        quantityKg = 250,
        pricePerKg = 24.0,
        qualityGrade = "Grade A",
        availableFrom = "Today",
        description = "Dark purple tender brinjals freshly picked.",
        verifiedDrawableRes = R.drawable.ic_brinjal_verified,
        status = "Live"
      ),
      ProductListing(
        id = "list_4",
        commodityId = "veg_potato",
        commodityName = "Potato",
        farmerId = "farmer_muthu",
        farmerName = "Muthu Raman",
        farmerDistrict = "Dharmapuri",
        farmerVillage = "Palacode",
        farmerRating = 4.9,
        reviewCount = 19,
        quantityKg = 800,
        pricePerKg = 22.0,
        qualityGrade = "Grade A",
        availableFrom = "Today",
        description = "Clean sorted hill potatoes, high starch quality.",
        verifiedDrawableRes = R.drawable.ic_potato_verified,
        status = "Live"
      )
    )
  )
  val listings: StateFlow<List<ProductListing>> = _listings.asStateFlow()

  fun addListing(listing: ProductListing) {
    _listings.value = listOf(listing) + _listings.value
  }

  // Active / Past Orders matching screens 10, 12, 13, 14
  private val _orders = MutableStateFlow<List<Order>>(
    listOf(
      Order(
        id = "ord_1",
        orderNumber = "UM12345",
        datePlaced = "12 Apr 2025, 10:15 AM",
        buyerName = "Hotel Sunrise",
        deliveryAddress = "Hotel Sunrise, 123, Main Road, Tiruvannamalai - 606601",
        items = listOf(
          OrderItem(
            commodityId = "veg_tomato",
            commodityName = "Tomato",
            quantityKg = 100,
            pricePerKg = 18.0,
            verifiedDrawableRes = R.drawable.ic_tomato_verified
          )
        ),
        deliveryFee = 150.0,
        platformCommission = 90.0,
        status = OrderStatus.OUT_FOR_DELIVERY,
        trackingSteps = listOf(
          TrackingStep("Order Confirmed", "10:20 AM", isCompleted = true),
          TrackingStep("Pickup Scheduled", "Today, 2:00 PM", isCompleted = true),
          TrackingStep("Out for Delivery", "Today, 4:00 PM", isCompleted = true, isCurrent = true),
          TrackingStep("Delivered", "Expected by 6:00 PM", isCompleted = false)
        )
      ),
      Order(
        id = "ord_2",
        orderNumber = "UM12290",
        datePlaced = "5 Apr 2025",
        buyerName = "Amman Mess",
        deliveryAddress = "Amman Mess, Gandhi Nagar, Tiruvannamalai",
        items = listOf(
          OrderItem(
            commodityId = "veg_onion",
            commodityName = "Onion",
            quantityKg = 50,
            pricePerKg = 17.0,
            verifiedDrawableRes = R.drawable.ic_onion_verified
          )
        ),
        deliveryFee = 120.0,
        platformCommission = 45.0,
        status = OrderStatus.DELIVERED,
        trackingSteps = listOf(
          TrackingStep("Order Confirmed", "5 Apr, 9:00 AM", isCompleted = true),
          TrackingStep("Pickup Scheduled", "5 Apr, 11:00 AM", isCompleted = true),
          TrackingStep("Out for Delivery", "5 Apr, 1:00 PM", isCompleted = true),
          TrackingStep("Delivered", "5 Apr, 2:30 PM", isCompleted = true)
        )
      ),
      Order(
        id = "ord_3",
        orderNumber = "UM12180",
        datePlaced = "3 Apr 2025",
        buyerName = "Sri Krishna Sweets",
        deliveryAddress = "Sri Krishna Sweets, Car Street, Tiruvannamalai",
        items = listOf(
          OrderItem(
            commodityId = "veg_banana",
            commodityName = "Banana",
            quantityKg = 200,
            pricePerKg = 14.0,
            verifiedDrawableRes = R.drawable.ic_banana_verified
          )
        ),
        deliveryFee = 200.0,
        platformCommission = 140.0,
        status = OrderStatus.DELIVERED,
        trackingSteps = listOf(
          TrackingStep("Order Confirmed", "3 Apr, 8:00 AM", isCompleted = true),
          TrackingStep("Delivered", "3 Apr, 12:00 PM", isCompleted = true)
        )
      )
    )
  )
  val orders: StateFlow<List<Order>> = _orders.asStateFlow()

  fun placeOrder(order: Order) {
    _orders.value = listOf(order) + _orders.value
  }

  // Payment History matching screen 15
  val payments: List<PaymentTransaction> = listOf(
    PaymentTransaction(
      id = "pay_1",
      orderNumber = "Order #UM12345",
      date = "12 Apr 2025",
      amount = 2040.0,
      method = "UPI",
      status = "Paid"
    ),
    PaymentTransaction(
      id = "pay_2",
      orderNumber = "Order #UM12290",
      date = "8 Apr 2025",
      amount = 850.0,
      method = "UPI",
      status = "Paid"
    ),
    PaymentTransaction(
      id = "pay_3",
      orderNumber = "Order #UM12180",
      date = "3 Apr 2025",
      amount = 2800.0,
      method = "Bank Transfer",
      status = "Paid"
    ),
    PaymentTransaction(
      id = "pay_4",
      orderNumber = "Order #UM12042",
      date = "28 Mar 2025",
      amount = 1250.0,
      method = "UPI",
      status = "Paid"
    )
  )

  // Notifications matching screen 17
  val notifications: List<NotificationItem> = listOf(
    NotificationItem(
      id = "notif_1",
      title = "Payment received",
      message = "₹2,040 for Order #UM12345",
      timestamp = "12 Apr, 10:45 AM",
      category = "Payments"
    ),
    NotificationItem(
      id = "notif_2",
      title = "Pickup scheduled",
      message = "Your produce will be picked up at 2:00 PM today.",
      timestamp = "12 Apr, 1:30 PM",
      category = "Orders"
    ),
    NotificationItem(
      id = "notif_3",
      title = "New buyer message",
      message = "Hotel Sunrise is interested in your tomato listing.",
      timestamp = "12 Apr, 11:30 AM",
      category = "Offers"
    ),
    NotificationItem(
      id = "notif_4",
      title = "Price update",
      message = "Tomato price increased to ₹22/kg in your area.",
      timestamp = "11 Apr, 9:15 AM",
      category = "Price"
    )
  )
}
