package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanLightGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary
import kotlin.math.roundToInt

data class PricePoint(
  val dateLabel: String,
  val fullDate: String,
  val price: Double,
  val arrivalTonnes: Int
)

@Composable
fun PriceTrendChart(
  commodityName: String,
  basePrice: Double,
  isTamil: Boolean,
  modifier: Modifier = Modifier
) {
  var selectedTimeRange by remember { mutableIntStateOf(0) } // 0: 7D, 1: 15D, 2: 30D

  // Generate realistic historical trend data based on commodity and base price
  val dataPoints = remember(commodityName, basePrice, selectedTimeRange) {
    when (selectedTimeRange) {
      0 -> listOf(
        PricePoint("6d ago", "6 Oct", (basePrice - 3.5).coerceAtLeast(10.0), 230),
        PricePoint("5d ago", "7 Oct", (basePrice - 2.0).coerceAtLeast(10.0), 215),
        PricePoint("4d ago", "8 Oct", (basePrice - 1.5).coerceAtLeast(10.0), 245),
        PricePoint("3d ago", "9 Oct", (basePrice - 0.5).coerceAtLeast(10.0), 200),
        PricePoint("2d ago", "10 Oct", (basePrice + 1.0).coerceAtLeast(10.0), 190),
        PricePoint("Y'day", "11 Oct", (basePrice + 0.5).coerceAtLeast(10.0), 185),
        PricePoint("Today", "12 Oct", basePrice, 180)
      )
      1 -> listOf(
        PricePoint("15d", "28 Sep", (basePrice - 5.0).coerceAtLeast(10.0), 280),
        PricePoint("12d", "1 Oct", (basePrice - 4.0).coerceAtLeast(10.0), 260),
        PricePoint("9d", "4 Oct", (basePrice - 2.5).coerceAtLeast(10.0), 240),
        PricePoint("6d", "7 Oct", (basePrice - 2.0).coerceAtLeast(10.0), 215),
        PricePoint("3d", "10 Oct", (basePrice + 1.0).coerceAtLeast(10.0), 190),
        PricePoint("Today", "12 Oct", basePrice, 180)
      )
      else -> listOf(
        PricePoint("30d", "13 Sep", (basePrice - 7.0).coerceAtLeast(10.0), 320),
        PricePoint("24d", "19 Sep", (basePrice - 6.0).coerceAtLeast(10.0), 300),
        PricePoint("18d", "25 Sep", (basePrice - 4.5).coerceAtLeast(10.0), 270),
        PricePoint("12d", "1 Oct", (basePrice - 4.0).coerceAtLeast(10.0), 260),
        PricePoint("6d", "7 Oct", (basePrice - 2.0).coerceAtLeast(10.0), 215),
        PricePoint("Today", "12 Oct", basePrice, 180)
      )
    }
  }

  val minPrice = dataPoints.minOf { it.price }
  val maxPrice = dataPoints.maxOf { it.price }
  val priceRange = (maxPrice - minPrice).coerceAtLeast(1.0)

  // Interactive selected point (defaults to the latest)
  var selectedIndex by remember(dataPoints) { mutableIntStateOf(dataPoints.lastIndex) }
  val activePoint = dataPoints.getOrElse(selectedIndex) { dataPoints.last() }

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8E4)),
    modifier = modifier
      .fillMaxWidth()
      .testTag("price_trend_chart_card")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Header: Title & Time Filter Tabs
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = if (isTamil) "வரலாற்று விலை போக்கு" else "Historical Price Trend",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 16.sp
            )
          )
          Text(
            text = "$commodityName • APMC Modal Trend",
            style = MaterialTheme.typography.bodySmall.copy(
              color = UzhavanTextSecondary,
              fontSize = 12.sp
            )
          )
        }

        // Time Filter Selector: 7D, 15D, 30D
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF1F5F2))
            .padding(2.dp)
        ) {
          listOf("7D", "15D", "30D").forEachIndexed { index, label ->
            val isSelected = selectedTimeRange == index
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) UzhavanDarkGreen else Color.Transparent,
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { selectedTimeRange = index }
            ) {
              Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) Color.White else UzhavanTextSecondary,
                  fontSize = 11.sp
                ),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Active Hovered Point Banner
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFFF9FAF9))
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(UzhavanDarkGreen)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "${activePoint.fullDate} (${activePoint.dateLabel}):",
            style = MaterialTheme.typography.bodySmall.copy(
              color = UzhavanTextSecondary,
              fontWeight = FontWeight.Medium
            )
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "₹ ${activePoint.price.roundToInt()} /kg",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanDarkGreen,
              fontSize = 16.sp
            )
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "• ${activePoint.arrivalTonnes}t",
            style = MaterialTheme.typography.bodySmall.copy(
              color = UzhavanTextSecondary,
              fontSize = 12.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Native Canvas Chart with Smooth Bezier Curve & Touch Scrubbing
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .pointerInput(dataPoints) {
            detectTapGestures { offset ->
              val step = size.width / (dataPoints.size - 1).coerceAtLeast(1)
              val index = (offset.x / step).roundToInt().coerceIn(0, dataPoints.lastIndex)
              selectedIndex = index
            }
          }
          .pointerInput(dataPoints) {
            detectDragGestures { change, _ ->
              val step = size.width / (dataPoints.size - 1).coerceAtLeast(1)
              val index = (change.position.x / step).roundToInt().coerceIn(0, dataPoints.lastIndex)
              selectedIndex = index
            }
          }
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val width = size.width
          val height = size.height
          val paddingBottom = 26f
          val paddingTop = 16f
          val availableHeight = height - paddingBottom - paddingTop

          val stepX = width / (dataPoints.size - 1).coerceAtLeast(1)

          // Compute (x, y) coordinates for each point
          val points = dataPoints.mapIndexed { index, item ->
            val x = index * stepX
            val normalizedY = ((item.price - minPrice) / priceRange).toFloat()
            val y = height - paddingBottom - (normalizedY * availableHeight)
            Offset(x, y)
          }

          // Draw horizontal subtle grid lines
          val gridLines = 3
          for (i in 0..gridLines) {
            val y = paddingTop + (i * (availableHeight / gridLines))
            drawLine(
              color = Color(0xFFF1F5F2),
              start = Offset(0f, y),
              end = Offset(width, y),
              strokeWidth = 1.dp.toPx()
            )
          }

          // Build Smooth Bezier Line Path
          val strokePath = Path()
          val fillPath = Path()

          if (points.isNotEmpty()) {
            strokePath.moveTo(points.first().x, points.first().y)
            fillPath.moveTo(points.first().x, height - paddingBottom)
            fillPath.lineTo(points.first().x, points.first().y)

            for (i in 0 until points.size - 1) {
              val p0 = points[i]
              val p1 = points[i + 1]
              val controlX = (p0.x + p1.x) / 2
              strokePath.cubicTo(
                controlX, p0.y,
                controlX, p1.y,
                p1.x, p1.y
              )
              fillPath.cubicTo(
                controlX, p0.y,
                controlX, p1.y,
                p1.x, p1.y
              )
            }

            fillPath.lineTo(points.last().x, height - paddingBottom)
            fillPath.close()

            // Fill area under curve with soft agricultural green gradient
            drawPath(
              path = fillPath,
              brush = Brush.verticalGradient(
                colors = listOf(
                  Color(0xFF2E7D32).copy(alpha = 0.28f),
                  Color(0xFFE8F5E9).copy(alpha = 0.05f)
                ),
                startY = paddingTop,
                endY = height - paddingBottom
              )
            )

            // Draw vibrant green stroke line
            drawPath(
              path = strokePath,
              color = Color(0xFF1B5E20),
              style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
              )
            )
          }

          // Draw active interactive highlight circle & vertical scrubber indicator
          val selectedPoint = points.getOrElse(selectedIndex) { points.last() }

          // Vertical scrubber dash line
          drawLine(
            color = Color(0xFF1B5E20).copy(alpha = 0.4f),
            start = Offset(selectedPoint.x, paddingTop),
            end = Offset(selectedPoint.x, height - paddingBottom),
            strokeWidth = 1.5.dp.toPx()
          )

          // Outer pulse ring
          drawCircle(
            color = Color(0xFF1B5E20).copy(alpha = 0.2f),
            radius = 11.dp.toPx(),
            center = selectedPoint
          )

          // Inner solid circle
          drawCircle(
            color = Color.White,
            radius = 6.dp.toPx(),
            center = selectedPoint
          )
          drawCircle(
            color = Color(0xFF1B5E20),
            radius = 4.dp.toPx(),
            center = selectedPoint
          )
        }
      }

      // X-Axis Date Labels Row
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        dataPoints.forEachIndexed { idx, point ->
          Text(
            text = point.dateLabel,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = if (idx == selectedIndex) FontWeight.Bold else FontWeight.Normal,
              color = if (idx == selectedIndex) UzhavanDarkGreen else UzhavanTextSecondary
            )
          )
        }
      }
    }
  }
}
