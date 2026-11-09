package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.RemoveShoppingCart
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.model.CartItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

@Composable
fun CartScreen(
  cartItems: List<CartItem>,
  appliedCoupon: String?,
  couponDiscountPercent: Int,
  subtotal: Double,
  discount: Double,
  deliveryFee: Double,
  total: Double,
  onUpdateQuantity: (cartItemId: String, newQuantity: Int) -> Unit,
  onRemoveItem: (cartItemId: String) -> Unit,
  onApplyCoupon: (code: String) -> Boolean,
  onRemoveCoupon: () -> Unit,
  onProceedToCheckout: () -> Unit,
  onStartShopping: () -> Unit,
  modifier: Modifier = Modifier
) {
  var couponInput by remember { mutableStateOf("") }
  val context = LocalContext.current

  if (cartItems.isEmpty()) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(24.dp)
        .testTag("empty_cart_view"),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(90.dp)
          .clip(CircleShape)
          .background(BrandGold.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Outlined.RemoveShoppingCart,
          contentDescription = null,
          tint = BrandGoldDark,
          modifier = Modifier.size(48.dp)
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      Text(
        text = "سلة المشتريات فارغة",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "لم تقم بإضافة أي قطع بعد. تصفح تشكيلتنا المميزة من الملابس والإكسسوارات الفاخرة.",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(horizontal = 24.dp)
      )

      Spacer(modifier = Modifier.height(24.dp))

      Button(
        onClick = onStartShopping,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = LuxuryDark,
          contentColor = BrandGoldLight
        ),
        modifier = Modifier.testTag("empty_cart_shop_btn")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Outlined.ShoppingBag, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("تصفح الأزياء الآن", fontWeight = FontWeight.Bold)
        }
      }
    }
  } else {
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .testTag("cart_items_list"),
      contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 100.dp)
    ) {
      // Top Title Bar
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(
              text = "سلة المشتريات",
              fontSize = 22.sp,
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "${cartItems.sumOf { it.quantity }} قطعة في سلتك",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          if (deliveryFee == 0.0) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(AccentGreen.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "مؤهل للشحن المجاني ✨",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGreen
              )
            }
          }
        }
      }

      // Cart Items List
      items(cartItems, key = { it.id }) { item ->
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .border(
              width = 1.dp,
              color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
              shape = RoundedCornerShape(16.dp)
            )
            .testTag("cart_item_${item.id}")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Product Thumbnail
            Box(
              modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
              val fallbackRes = item.product.imageRes ?: R.drawable.category_men_1794184984460
              AsyncImage(
                model = ImageRequest.Builder(context)
                  .data(item.product.imageUrl)
                  .crossfade(true)
                  .error(fallbackRes)
                  .placeholder(fallbackRes)
                  .build(),
                contentDescription = item.product.titleAr,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Info Column
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = item.product.titleAr,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
              )

              Spacer(modifier = Modifier.height(4.dp))

              Row(verticalAlignment = Alignment.CenterVertically) {
                // Size Badge
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "المقاس: ${item.selectedSize}",
                    fontSize = 10.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Color Circle
                Box(
                  modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(item.selectedColor.color)
                    .border(0.5.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = item.selectedColor.nameAr,
                  fontSize = 10.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Spacer(modifier = Modifier.height(8.dp))

              // Price & Stepper Row
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "${item.totalPrice.toInt()} ر.س",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = MaterialTheme.colorScheme.onSurface
                )

                // Stepper
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
                ) {
                  IconButton(
                    onClick = { onUpdateQuantity(item.id, item.quantity - 1) },
                    modifier = Modifier.size(30.dp).testTag("cart_dec_qty_${item.id}")
                  ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(14.dp))
                  }

                  Text(
                    text = "${item.quantity}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                  )

                  IconButton(
                    onClick = { onUpdateQuantity(item.id, item.quantity + 1) },
                    modifier = Modifier.size(30.dp).testTag("cart_inc_qty_${item.id}")
                  ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(14.dp))
                  }
                }
              }
            }

            // Remove Button
            IconButton(
              onClick = { onRemoveItem(item.id) },
              modifier = Modifier.size(32.dp).testTag("cart_remove_item_${item.id}")
            ) {
              Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Remove",
                tint = AccentRed.copy(alpha = 0.8f),
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }

      // Coupon Section
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
              1.dp,
              MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
              RoundedCornerShape(16.dp)
            )
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Outlined.LocalOffer, contentDescription = null, tint = BrandGoldDark, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("كود الخصم الترويجي", fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (appliedCoupon != null) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(AccentGreen.copy(alpha = 0.12f))
                  .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "$appliedCoupon (خصم $couponDiscountPercent%)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGreen
                  )
                }
                IconButton(
                  onClick = onRemoveCoupon,
                  modifier = Modifier.size(24.dp).testTag("remove_coupon_btn")
                ) {
                  Icon(Icons.Default.Close, contentDescription = "Remove", tint = AccentRed, modifier = Modifier.size(16.dp))
                }
              }
            } else {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                OutlinedTextField(
                  value = couponInput,
                  onValueChange = { couponInput = it },
                  placeholder = { Text("أدخل الكوبون هنا") },
                  singleLine = true,
                  shape = RoundedCornerShape(12.dp),
                  modifier = Modifier.weight(1f).testTag("coupon_input_field")
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                  onClick = {
                    if (onApplyCoupon(couponInput)) {
                      couponInput = ""
                    }
                  },
                  shape = RoundedCornerShape(12.dp),
                  colors = ButtonDefaults.buttonColors(
                    containerColor = LuxuryDark,
                    contentColor = BrandGoldLight
                  ),
                  modifier = Modifier.testTag("apply_coupon_btn")
                ) {
                  Text("تطبيق", fontWeight = FontWeight.Bold)
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                  onClick = { onApplyCoupon("ANAQAH20") },
                  shape = RoundedCornerShape(8.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  modifier = Modifier.testTag("quick_coupon_anaqah20")
                ) {
                  Text(
                    text = "كود: ANAQAH20 (20%)",
                    fontSize = 11.sp,
                    color = BrandGoldDark,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }

                Surface(
                  onClick = { onApplyCoupon("WELCOME10") },
                  shape = RoundedCornerShape(8.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  modifier = Modifier.testTag("quick_coupon_welcome10")
                ) {
                  Text(
                    text = "كود: WELCOME10 (10%)",
                    fontSize = 11.sp,
                    color = BrandGoldDark,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }
            }
          }
        }
      }

      // Invoice Breakdown Card
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
              1.dp,
              MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
              RoundedCornerShape(16.dp)
            )
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("تفاصيل الدفع", fontSize = 14.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("المجموع الفرعي", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text("${subtotal.toInt()} ر.س", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }

            if (discount > 0) {
              Spacer(modifier = Modifier.height(6.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("خصم الكوبون ($couponDiscountPercent%)", fontSize = 13.sp, color = AccentGreen)
                Text("-${discount.toInt()} ر.س", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AccentGreen)
              }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("تكلفة التوصيل", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(
                if (deliveryFee == 0.0) "مجاناً" else "${deliveryFee.toInt()} ر.س",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (deliveryFee == 0.0) AccentGreen else MaterialTheme.colorScheme.onSurface
              )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
              onClick = onProceedToCheckout,
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = LuxuryDark,
                contentColor = BrandGoldLight
              ),
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("checkout_proceed_btn")
            ) {
              Text(
                text = "متابعة الشراء • ${total.toInt()} ر.س",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}
