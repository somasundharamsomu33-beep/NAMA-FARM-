package com.example.data.repository

import com.example.data.model.MandiPriceInfo

object MandiRepository {

  // Demo market prices clearly identified as Demo Data per requirement
  val demoMarketComparisons: List<MandiPriceInfo> = listOf(
    MandiPriceInfo(
      marketName = "Tiruvannamalai Uzhavar Sandhai",
      district = "Tiruvannamalai",
      commodityName = "Tomato",
      minPrice = 18.0,
      maxPrice = 22.0,
      modalPrice = 20.0,
      arrivalTonnes = 180,
      arrivalChangePercent = -26,
      distanceKm = 12,
      transportCostPerKg = 0.5,
      estimatedNetReturnPerKg = 19.5,
      date = "Today",
      isLive = false
    ),
    MandiPriceInfo(
      marketName = "Vellore APMC Mandi",
      district = "Vellore",
      commodityName = "Tomato",
      minPrice = 22.0,
      maxPrice = 26.0,
      modalPrice = 24.0,
      arrivalTonnes = 240,
      arrivalChangePercent = -15,
      distanceKm = 85,
      transportCostPerKg = 1.8,
      estimatedNetReturnPerKg = 22.2,
      date = "Today",
      isLive = false
    ),
    MandiPriceInfo(
      marketName = "Chennai Koyambedu Market",
      district = "Chennai",
      commodityName = "Tomato",
      minPrice = 25.0,
      maxPrice = 30.0,
      modalPrice = 27.0,
      arrivalTonnes = 620,
      arrivalChangePercent = -10,
      distanceKm = 195,
      transportCostPerKg = 2.9,
      estimatedNetReturnPerKg = 24.1,
      date = "Today",
      isLive = false
    ),
    MandiPriceInfo(
      marketName = "Bengaluru Yeshwanthpur APMC",
      district = "Bengaluru",
      commodityName = "Tomato",
      minPrice = 26.0,
      maxPrice = 32.0,
      modalPrice = 29.0,
      arrivalTonnes = 850,
      arrivalChangePercent = 5,
      distanceKm = 240,
      transportCostPerKg = 3.4,
      estimatedNetReturnPerKg = 25.6,
      date = "Today",
      isLive = false
    ),
    MandiPriceInfo(
      marketName = "Chittoor Wholesale Market",
      district = "Chittoor",
      commodityName = "Tomato",
      minPrice = 21.0,
      maxPrice = 25.0,
      modalPrice = 23.0,
      arrivalTonnes = 190,
      arrivalChangePercent = -8,
      distanceKm = 130,
      transportCostPerKg = 2.2,
      estimatedNetReturnPerKg = 20.8,
      date = "Today",
      isLive = false
    )
  )

  fun getBestMarket(product: String): MandiPriceInfo {
    return demoMarketComparisons.maxByOrNull { it.estimatedNetReturnPerKg }
      ?: demoMarketComparisons.first()
  }
}
