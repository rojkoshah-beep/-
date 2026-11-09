package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.model.Order
import com.example.model.OrderStatus
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark

@Composable
fun OrdersScreen(
  orders: List<Order>,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  if (orders.isEmpty()) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(24.dp)
        .testTag("empty_orders_view"),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .background(BrandGold.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Outlined.ReceiptLong,
          contentDescription = null,
          tint = BrandGoldDark,
          modifier = Modifier.size(44.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "لا توجد طلبات سابقة",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "ستظهر هنا جميع طلباتك السابقة مع تفاصيل التتبع ومواعيد التسليم.",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )
    }
  } else {
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .testTag("orders_screen_list"),
      contentPadding = PaddingValues(start = 16.dp, top = 14.dp, end = 16.dp, bottom = 100.dp)
    ) {
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(BrandGold.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Outlined.ReceiptLong, contentDescription = null, tint = BrandGoldDark, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "سجل طلباتي",
              fontSize = 20.sp,
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "${orders.size} طلبات مسجلة",
              fontSize = 11.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      items(orders, key = { it.id }) { order ->
        val statusColor = when (order.status) {
          OrderStatus.DELIVERED -> AccentGreen
          OrderStatus.ON_THE_WAY -> BrandGold
          OrderStatus.PREPARING -> AccentAmber
        }

        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
              1.dp,
              MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f),
              RoundedCornerShape(18.dp)
            )
            .testTag("order_card_${order.id}")
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            // Header: Order Number & Status
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(
                  text = order.orderNumber,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Black,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = order.date,
                  fontSize = 11.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(statusColor.copy(alpha = 0.15f))
                  .padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Text(
                  text = order.status.titleAr,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = statusColor
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f))
            Spacer(modifier = Modifier.height(10.dp))

            // Items Thumbnails & Titles
            order.items.forEach { cartItem ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                  val fallbackRes = cartItem.product.imageRes ?: R.drawable.category_men_1794184984460
                  AsyncImage(
                    model = ImageRequest.Builder(context)
                      .data(cartItem.product.imageUrl)
                      .crossfade(true)
                      .error(fallbackRes)
                      .placeholder(fallbackRes)
                      .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                  )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = cartItem.product.titleAr,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                  )
                  Text(
                    text = "المقاس: ${cartItem.selectedSize} • اللون: ${cartItem.selectedColor.nameAr} (الكمية: ${cartItem.quantity})",
                    fontSize = 10.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }

                Text(
                  text = "${cartItem.totalPrice.toInt()} ر.س",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f))
            Spacer(modifier = Modifier.height(10.dp))

            // Footer: Address & Total
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(
                  text = "العنوان: ${order.deliveryAddress}",
                  fontSize = 11.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = "الدفع: ${order.paymentMethodAr}",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "الإجمالي",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = "${order.total.toInt()} ر.س",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Black,
                  color = BrandGold
                )
              }
            }
          }
        }
      }
    }
  }
}
