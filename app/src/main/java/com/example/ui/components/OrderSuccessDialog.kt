package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Order
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

@Composable
fun OrderSuccessDialog(
  order: Order,
  onDismiss: () -> Unit,
  onViewOrders: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(24.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    title = null,
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp)
          .testTag("order_success_dialog"),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(AccentGreen.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = AccentGreen,
            modifier = Modifier.size(46.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "تم تأكيد طلبك بنجاح!",
          fontSize = 19.sp,
          fontWeight = FontWeight.Black,
          color = MaterialTheme.colorScheme.onSurface,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "شكراً لتسوقك من أناقة. جاري تحضير طلبك بعناية فائقة وشحنه إليك.",
          fontSize = 12.5.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Receipt Card
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .padding(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("رقم الطلب:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(order.orderNumber, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = BrandGold)
          }

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("إجمالي الفاتورة:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("${order.total.toInt()} ر.س", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("العنوان:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(order.deliveryAddress, fontSize = 12.sp, fontWeight = FontWeight.Medium)
          }

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("طريقة الدفع:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(order.paymentMethodAr, fontSize = 12.sp, fontWeight = FontWeight.Medium)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(Icons.Outlined.LocalShipping, contentDescription = null, tint = BrandGold, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "التوصيل المتوقع خلال 48 إلى 72 ساعة",
            fontSize = 11.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onDismiss()
          onViewOrders()
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = LuxuryDark,
          contentColor = BrandGoldLight
        ),
        modifier = Modifier.fillMaxWidth().testTag("view_my_orders_btn")
      ) {
        Text("عرض تفاصيل طلباتي", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      OutlinedButton(
        onClick = onDismiss,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().testTag("continue_shopping_btn")
      ) {
        Text("متابعة التسوق")
      }
    }
  )
}
