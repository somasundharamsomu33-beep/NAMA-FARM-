package com.example.data.repository

import com.example.R
import com.example.data.model.Commodity
import com.example.data.model.ProductCategory
import com.example.data.model.VerificationStatus

object CatalogRepository {

  val commodities: List<Commodity> = listOf(
    // ---------------------------------------------
    // CATEGORY A — VEGETABLES
    // ---------------------------------------------
    Commodity(
      id = "veg_tomato",
      name = "Tomato",
      tamilName = "தக்காளி",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = R.drawable.ic_tomato_verified,
      altText = "Fresh ripe red tomatoes with green calyx",
      verificationStatus = VerificationStatus.VERIFIED,
      defaultMandiPrice = 20.0,
      priceRangeMin = 18.0,
      priceRangeMax = 24.0,
      arrivalTonnes = 180,
      demand = "High",
      trend = "Increasing",
      description = "Fresh and chemical-free juicy tomatoes, harvested directly from local fields."
    ),
    Commodity(
      id = "veg_onion",
      name = "Onion",
      tamilName = "வெங்காயம்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = R.drawable.ic_onion_verified,
      altText = "Fresh red and purple farm onions",
      verificationStatus = VerificationStatus.VERIFIED,
      defaultMandiPrice = 16.0,
      priceRangeMin = 14.0,
      priceRangeMax = 19.0,
      arrivalTonnes = 310,
      demand = "High",
      trend = "Stable",
      description = "Pungent and firm medium-sized red onions, cured for good shelf life."
    ),
    Commodity(
      id = "veg_brinjal",
      name = "Brinjal (Eggplant)",
      tamilName = "கத்தரிக்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = R.drawable.ic_brinjal_verified,
      altText = "Fresh purple glossy brinjal eggplant",
      verificationStatus = VerificationStatus.VERIFIED,
      defaultMandiPrice = 25.0,
      priceRangeMin = 22.0,
      priceRangeMax = 28.0,
      arrivalTonnes = 95,
      demand = "Moderate",
      trend = "Increasing",
      description = "Tender purple brinjal with minimal seeds, freshly picked from field."
    ),
    Commodity(
      id = "veg_potato",
      name = "Potato",
      tamilName = "உருளைக்கிழங்கு",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = R.drawable.ic_potato_verified,
      altText = "Fresh earthy farm potatoes",
      verificationStatus = VerificationStatus.VERIFIED,
      defaultMandiPrice = 22.0,
      priceRangeMin = 20.0,
      priceRangeMax = 25.0,
      arrivalTonnes = 420,
      demand = "High",
      trend = "Stable",
      description = "Solid earthy grade A potatoes suitable for chips, curries, and storage."
    ),
    Commodity(
      id = "veg_carrot",
      name = "Carrot",
      tamilName = "கேரட்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = R.drawable.ic_carrot_verified,
      altText = "Fresh crisp orange farm carrots",
      verificationStatus = VerificationStatus.VERIFIED,
      defaultMandiPrice = 35.0,
      priceRangeMin = 30.0,
      priceRangeMax = 42.0,
      arrivalTonnes = 120,
      demand = "High",
      trend = "Increasing",
      description = "Sweet, crunchy Ooty and local variety carrots."
    ),
    Commodity(
      id = "veg_banana",
      name = "Banana (Poovan/Robusta)",
      tamilName = "வாழைப்பழம்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = R.drawable.ic_banana_verified,
      altText = "Fresh golden ripe bananas bunch",
      verificationStatus = VerificationStatus.VERIFIED,
      defaultMandiPrice = 28.0,
      priceRangeMin = 24.0,
      priceRangeMax = 32.0,
      arrivalTonnes = 150,
      demand = "High",
      trend = "Stable",
      description = "Naturally ripened farm bananas with rich aroma and sweetness."
    ),
    // For all other commodities below: verified image is pending/unavailable per rule #8, #44, #45
    // Never substitute an incorrect image! Show clean "Image unavailable" placeholder.
    Commodity(
      id = "veg_beetroot",
      name = "Beetroot",
      tamilName = "பீட்ரூட்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Beetroot",
      defaultMandiPrice = 26.0,
      priceRangeMin = 22.0,
      priceRangeMax = 30.0
    ),
    Commodity(
      id = "veg_radish",
      name = "Radish",
      tamilName = "முள்ளங்கி",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Radish",
      defaultMandiPrice = 18.0,
      priceRangeMin = 15.0,
      priceRangeMax = 22.0
    ),
    Commodity(
      id = "veg_cabbage",
      name = "Cabbage",
      tamilName = "முட்டைக்கோஸ்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Cabbage",
      defaultMandiPrice = 14.0,
      priceRangeMin = 12.0,
      priceRangeMax = 18.0
    ),
    Commodity(
      id = "veg_cauliflower",
      name = "Cauliflower",
      tamilName = "காலிஃபிளவர்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Cauliflower",
      defaultMandiPrice = 30.0,
      priceRangeMin = 25.0,
      priceRangeMax = 36.0
    ),
    Commodity(
      id = "veg_capsicum",
      name = "Capsicum",
      tamilName = "குடைமிளகாய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Capsicum",
      defaultMandiPrice = 45.0,
      priceRangeMin = 38.0,
      priceRangeMax = 52.0
    ),
    Commodity(
      id = "veg_green_chilli",
      name = "Green Chilli",
      tamilName = "பச்சை மிளகாய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Green Chilli",
      defaultMandiPrice = 35.0,
      priceRangeMin = 30.0,
      priceRangeMax = 42.0
    ),
    Commodity(
      id = "veg_ladies_finger",
      name = "Ladies Finger (Okra)",
      tamilName = "வெண்டைக்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Ladies Finger",
      defaultMandiPrice = 28.0,
      priceRangeMin = 24.0,
      priceRangeMax = 34.0
    ),
    Commodity(
      id = "veg_cucumber",
      name = "Cucumber",
      tamilName = "வெள்ளரிக்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Cucumber",
      defaultMandiPrice = 18.0,
      priceRangeMin = 15.0,
      priceRangeMax = 22.0
    ),
    Commodity(
      id = "veg_bottle_gourd",
      name = "Bottle Gourd",
      tamilName = "சுரைக்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Bottle Gourd",
      defaultMandiPrice = 16.0,
      priceRangeMin = 13.0,
      priceRangeMax = 20.0
    ),
    Commodity(
      id = "veg_bitter_gourd",
      name = "Bitter Gourd",
      tamilName = "பாகற்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Bitter Gourd",
      defaultMandiPrice = 32.0,
      priceRangeMin = 28.0,
      priceRangeMax = 38.0
    ),
    Commodity(
      id = "veg_ridge_gourd",
      name = "Ridge Gourd",
      tamilName = "பீர்க்கங்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Ridge Gourd",
      defaultMandiPrice = 30.0,
      priceRangeMin = 26.0,
      priceRangeMax = 35.0
    ),
    Commodity(
      id = "veg_snake_gourd",
      name = "Snake Gourd",
      tamilName = "புடலங்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Snake Gourd",
      defaultMandiPrice = 22.0,
      priceRangeMin = 18.0,
      priceRangeMax = 26.0
    ),
    Commodity(
      id = "veg_pumpkin",
      name = "Pumpkin",
      tamilName = "பூசணிக்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Pumpkin",
      defaultMandiPrice = 15.0,
      priceRangeMin = 12.0,
      priceRangeMax = 19.0
    ),
    Commodity(
      id = "veg_ash_gourd",
      name = "Ash Gourd",
      tamilName = "சாம்பல் பூசணி",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Ash Gourd",
      defaultMandiPrice = 16.0,
      priceRangeMin = 12.0,
      priceRangeMax = 20.0
    ),
    Commodity(
      id = "veg_drumstick",
      name = "Drumstick",
      tamilName = "முருங்கைக்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Drumstick",
      defaultMandiPrice = 40.0,
      priceRangeMin = 30.0,
      priceRangeMax = 55.0
    ),
    Commodity(
      id = "veg_beans",
      name = "Beans",
      tamilName = "பீன்ஸ்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Beans",
      defaultMandiPrice = 48.0,
      priceRangeMin = 40.0,
      priceRangeMax = 58.0
    ),
    Commodity(
      id = "veg_cluster_beans",
      name = "Cluster Beans",
      tamilName = "கொத்தவரங்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Cluster Beans",
      defaultMandiPrice = 30.0,
      priceRangeMin = 25.0,
      priceRangeMax = 36.0
    ),
    Commodity(
      id = "veg_peas",
      name = "Peas",
      tamilName = "பட்டாணி",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Peas",
      defaultMandiPrice = 60.0,
      priceRangeMin = 50.0,
      priceRangeMax = 75.0
    ),
    Commodity(
      id = "veg_broad_beans",
      name = "Broad Beans",
      tamilName = "அவரைக்காய்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Broad Beans",
      defaultMandiPrice = 34.0,
      priceRangeMin = 28.0,
      priceRangeMax = 40.0
    ),
    Commodity(
      id = "veg_sweet_corn",
      name = "Sweet Corn",
      tamilName = "இனிப்பு சோளம்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Sweet Corn",
      defaultMandiPrice = 24.0,
      priceRangeMin = 20.0,
      priceRangeMax = 28.0
    ),
    Commodity(
      id = "veg_sweet_potato",
      name = "Sweet Potato",
      tamilName = "சர்க்கரைவள்ளி",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Sweet Potato",
      defaultMandiPrice = 25.0,
      priceRangeMin = 20.0,
      priceRangeMax = 30.0
    ),
    Commodity(
      id = "veg_tapioca",
      name = "Tapioca",
      tamilName = "மரவள்ளிக்கிழங்கு",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Tapioca",
      defaultMandiPrice = 18.0,
      priceRangeMin = 15.0,
      priceRangeMax = 22.0
    ),
    Commodity(
      id = "veg_garlic",
      name = "Garlic",
      tamilName = "பூண்டு",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Garlic",
      defaultMandiPrice = 160.0,
      priceRangeMin = 140.0,
      priceRangeMax = 190.0
    ),
    Commodity(
      id = "veg_ginger",
      name = "Ginger",
      tamilName = "இஞ்சி",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Ginger",
      defaultMandiPrice = 75.0,
      priceRangeMin = 65.0,
      priceRangeMax = 90.0
    ),
    Commodity(
      id = "veg_turmeric",
      name = "Turmeric",
      tamilName = "மஞ்சள்",
      category = ProductCategory.VEGETABLES,
      verifiedDrawableRes = null,
      altText = "Turmeric",
      defaultMandiPrice = 85.0,
      priceRangeMin = 75.0,
      priceRangeMax = 100.0
    ),

    // ---------------------------------------------
    // CATEGORY B — CEREALS / GRAINS / MILLETS
    // (Normalized: Maize (Corn))
    // ---------------------------------------------
    Commodity(
      id = "grain_rice",
      name = "Rice (Ponni)",
      tamilName = "பொன்னி அரிசி",
      category = ProductCategory.GRAINS,
      verifiedDrawableRes = null,
      altText = "Rice",
      defaultMandiPrice = 52.0,
      priceRangeMin = 48.0,
      priceRangeMax = 58.0
    ),
    Commodity(
      id = "grain_paddy",
      name = "Paddy",
      tamilName = "நெல்",
      category = ProductCategory.GRAINS,
      verifiedDrawableRes = null,
      altText = "Paddy",
      defaultMandiPrice = 24.0,
      priceRangeMin = 22.0,
      priceRangeMax = 27.0
    ),
    Commodity(
      id = "grain_wheat",
      name = "Wheat",
      tamilName = "கோதுமை",
      category = ProductCategory.GRAINS,
      verifiedDrawableRes = null,
      altText = "Wheat",
      defaultMandiPrice = 30.0,
      priceRangeMin = 28.0,
      priceRangeMax = 34.0
    ),
    Commodity(
      id = "grain_maize",
      name = "Maize (Corn)",
      tamilName = "மக்காச்சோளம்",
      category = ProductCategory.GRAINS,
      verifiedDrawableRes = null,
      altText = "Maize Corn",
      defaultMandiPrice = 22.0,
      priceRangeMin = 20.0,
      priceRangeMax = 25.0
    ),
    Commodity(
      id = "grain_ragi",
      name = "Finger Millet (Ragi)",
      tamilName = "கேழ்வரகு",
      category = ProductCategory.GRAINS,
      verifiedDrawableRes = null,
      altText = "Finger Millet Ragi",
      defaultMandiPrice = 38.0,
      priceRangeMin = 34.0,
      priceRangeMax = 44.0
    ),
    Commodity(
      id = "grain_kodo",
      name = "Kodo Millet (Varagu)",
      tamilName = "வரகு",
      category = ProductCategory.GRAINS,
      verifiedDrawableRes = null,
      altText = "Kodo Millet",
      defaultMandiPrice = 65.0,
      priceRangeMin = 58.0,
      priceRangeMax = 72.0
    ),
    Commodity(
      id = "grain_bajra",
      name = "Pearl Millet (Bajra/Kambu)",
      tamilName = "கம்பு",
      category = ProductCategory.GRAINS,
      verifiedDrawableRes = null,
      altText = "Pearl Millet",
      defaultMandiPrice = 32.0,
      priceRangeMin = 28.0,
      priceRangeMax = 36.0
    ),

    // ---------------------------------------------
    // CATEGORY C — SEEDS
    // ---------------------------------------------
    Commodity(
      id = "seed_paddy",
      name = "Paddy Seeds (CR 1009 / BPT)",
      tamilName = "நெல் விதைகள்",
      category = ProductCategory.SEEDS,
      verifiedDrawableRes = null,
      altText = "Paddy Seeds",
      defaultMandiPrice = 45.0,
      priceRangeMin = 40.0,
      priceRangeMax = 52.0
    ),
    Commodity(
      id = "seed_tomato",
      name = "Tomato Seeds (Hybrid)",
      tamilName = "தக்காளி விதைகள்",
      category = ProductCategory.SEEDS,
      verifiedDrawableRes = null,
      altText = "Tomato Seeds",
      defaultMandiPrice = 350.0,
      priceRangeMin = 300.0,
      priceRangeMax = 420.0
    ),
    Commodity(
      id = "seed_groundnut",
      name = "Groundnut Seeds",
      tamilName = "நிலக்கடலை விதைகள்",
      category = ProductCategory.SEEDS,
      verifiedDrawableRes = null,
      altText = "Groundnut Seeds",
      defaultMandiPrice = 90.0,
      priceRangeMin = 82.0,
      priceRangeMax = 100.0
    ),

    // ---------------------------------------------
    // CATEGORY D — LEAFY GREENS
    // ---------------------------------------------
    Commodity(
      id = "leaf_spinach",
      name = "Spinach (Palak)",
      tamilName = "பாலக் கீரை",
      category = ProductCategory.LEAFY_GREENS,
      verifiedDrawableRes = null,
      altText = "Spinach Palak",
      unit = "bundle",
      defaultMandiPrice = 15.0,
      priceRangeMin = 12.0,
      priceRangeMax = 20.0
    ),
    Commodity(
      id = "leaf_moringa",
      name = "Moringa / Drumstick Leaves",
      tamilName = "முருங்கை கீரை",
      category = ProductCategory.LEAFY_GREENS,
      verifiedDrawableRes = null,
      altText = "Drumstick Leaves",
      unit = "bundle",
      defaultMandiPrice = 18.0,
      priceRangeMin = 14.0,
      priceRangeMax = 22.0
    ),
    Commodity(
      id = "leaf_coriander",
      name = "Coriander Leaves",
      tamilName = "கொத்தமல்லி",
      category = ProductCategory.LEAFY_GREENS,
      verifiedDrawableRes = null,
      altText = "Coriander Leaves",
      unit = "bundle",
      defaultMandiPrice = 20.0,
      priceRangeMin = 15.0,
      priceRangeMax = 30.0
    ),
    Commodity(
      id = "leaf_mint",
      name = "Mint Leaves (Pudina)",
      tamilName = "புதினா",
      category = ProductCategory.LEAFY_GREENS,
      verifiedDrawableRes = null,
      altText = "Mint Leaves",
      unit = "bundle",
      defaultMandiPrice = 15.0,
      priceRangeMin = 12.0,
      priceRangeMax = 18.0
    )
  )

  fun getById(id: String): Commodity? = commodities.firstOrNull { it.id == id }

  fun getByCategory(category: ProductCategory): List<Commodity> =
    commodities.filter { it.category == category }
}
