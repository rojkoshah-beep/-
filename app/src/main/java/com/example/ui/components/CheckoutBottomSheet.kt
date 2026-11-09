package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.LocalAtm
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark
import com.example.viewmodel.CheckoutFormState

data class PaymentOption(
  val id: String,
  val titleAr: String,
  val subtitleAr: String,
  val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutBottomSheet(
  initialForm: CheckoutFormState,
  subtotal: Double,
  discount: Double,
  deliveryFee: Double,
  total: Double,
  onConfirmOrder: (CheckoutFormState) -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var fullName by remember { mutableStateOf(initialForm.fullName) }
  var phone by remember { mutableStateOf(initialForm.phone) }
  var city by remember { mutableStateOf(initialForm.city) }
  var street by remember { mutableStateOf(initialForm.street) }
  var selectedPayment by remember { mutableStateOf(initialForm.paymentMethod) }

  val paymentOptions = listOf(
    PaymentOption("mada", "بطاقة مدى (Mada)", "دفع فوري وآمن", Icons.Outlined.CreditCard),
    PaymentOption("apple_pay", "Apple Pay", "الدفع السريع بلمسة واحدة", Icons.Outlined.CreditCard),
    PaymentOption("cash_on_delivery", "الدفع عند الاستلام", "ادفع نقداً عند استلام طلبك", Icons.Outlined.LocalAtm)
  )

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface,
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    dragHandle = {
      Box(
        modifier = Modifier
          .padding(vertical = 10.dp)
          .width(48.dp)
          .height(4.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.outlineVariant)
      )
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp)
        .padding(bottom = 32.dp)
        .testTag("checkout_bottom_sheet")
    ) {
      Text(
        text = "إتمام الطلب والشحن",
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = "يرجى ملء بيانات التوصيل واختيار طريقة الدفع المفضلة",
        fontSize = 12.5.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Section: Shipping Address
      Text(
        text = "عنوان الشحن والتوصيل",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = fullName,
        onValueChange = { fullName = it },
        label = { Text("الاسم الكامل") },
        leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("input_checkout_name"),
        shape = RoundedCornerShape(12.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("رقم الجوال") },
        leadingIcon = { Icon(Icons.Outlined.Phone, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("input_checkout_phone"),
        shape = RoundedCornerShape(12.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
          value = city,
          onValueChange = { city = it },
          label = { Text("المدينة") },
          singleLine = true,
          modifier = Modifier.weight(1f).testTag("input_checkout_city"),
          shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
          value = street,
          onValueChange = { street = it },
          label = { Text("الحي والشارع") },
          leadingIcon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
          singleLine = true,
          modifier = Modifier.weight(1.8f).testTag("input_checkout_street"),
          shape = RoundedCornerShape(12.dp)
        )
      }

      Spacer(modifier = Modifier.height(18.dp))
      HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
      Spacer(modifier = Modifier.height(14.dp))

      // Section: Payment Methods
      Text(
        text = "طريقة الدفع",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(8.dp))

      paymentOptions.forEach { opt ->
        val isOptSelected = selectedPayment == opt.id
        Surface(
          onClick = { selectedPayment = opt.id },
          shape = RoundedCornerShape(14.dp),
          color = if (isOptSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
          border = androidx.compose.foundation.BorderStroke(
            width = if (isOptSelected) 1.5.dp else 1.dp,
            color = if (isOptSelected) BrandGold else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .testTag("payment_opt_${opt.id}")
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = if (isOptSelected) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircleOutline,
              contentDescription = null,
              tint = if (isOptSelected) BrandGold else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = opt.titleAr,
                fontSize = 13.5.sp,
                fontWeight = if (isOptSelected) FontWeight.Bold else FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = opt.subtitleAr,
                fontSize = 11.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))
      HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
      Spacer(modifier = Modifier.height(14.dp))

      // Order Summary breakdown
      Text(
        text = "ملخص الفاتورة",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("المجموع الفرعي", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("${subtotal.toInt()} ر.س", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      }

      if (discount > 0) {
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("خصم الكوبون", fontSize = 13.sp, color = AccentGreen)
          Text("-${discount.toInt()} ر.س", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AccentGreen)
        }
      }

      Spacer(modifier = Modifier.height(4.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("رسوم التوصيل", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
          if (deliveryFee == 0.0) "مجاني" else "${deliveryFee.toInt()} ر.س",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = if (deliveryFee == 0.0) AccentGreen else MaterialTheme.colorScheme.onSurface
        )
      }

      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("الإجمالي الكلي", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        Text(
          "${total.toInt()} ر.س",
          fontSize = 20.sp,
          fontWeight = FontWeight.Black,
          color = BrandGold
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Confirm Order button
      Button(
        onClick = {
          val updatedForm = initialForm.copy(
            fullName = fullName.trim(),
            phone = phone.trim(),
            city = city.trim(),
            street = street.trim(),
            paymentMethod = selectedPayment
          )
          onConfirmOrder(updatedForm)
        },
        enabled = fullName.isNotBlank() && phone.isNotBlank() && city.isNotBlank(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = LuxuryDark,
          contentColor = BrandGoldLight
        ),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("confirm_order_btn")
      ) {
        Text(
          text = "تأكيد الطلب والدفع • ${total.toInt()} ر.س",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
