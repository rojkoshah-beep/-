package com.example.ui.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.model.Product
import com.example.model.ProductColor
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailSheet(
  product: Product,
  isFavorite: Boolean,
  onToggleFavorite: () -> Unit,
  onAddToCart: (selectedSize: String, selectedColor: ProductColor, quantity: Int) -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val context = LocalContext.current

  var selectedSize by remember { mutableStateOf(product.availableSizes.firstOrNull() ?: "قياس موحد") }
  var selectedColor by remember { mutableStateOf(product.availableColors.firstOrNull() ?: com.example.data.FashionCatalog.Black) }
  var quantity by remember { mutableIntStateOf(1) }

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
        .padding(bottom = 32.dp)
        .testTag("product_detail_sheet")
    ) {
      // Top Image Area
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1.15f)
          .padding(horizontal = 16.dp)
          .clip(RoundedCornerShape(20.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        val fallbackRes = product.imageRes ?: R.drawable.category_men_1794184984460

        AsyncImage(
          model = ImageRequest.Builder(context)
            .data(product.imageUrl)
            .crossfade(true)
            .error(fallbackRes)
            .placeholder(fallbackRes)
            .build(),
          contentDescription = product.titleAr,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Close Button
        IconButton(
          onClick = onDismiss,
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(12.dp)
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.5f))
            .testTag("close_sheet_btn")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
        }

        // Favorite Button
        IconButton(
          onClick = onToggleFavorite,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(12.dp)
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f))
            .testTag("detail_fav_btn")
        ) {
          Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) AccentRed else Color.DarkGray,
            modifier = Modifier.size(20.dp)
          )
        }

        if (product.discountPercent > 0) {
          Box(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(12.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(AccentRed)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(
              text = "وفر ${product.discountPercent}%",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Main Info Section
      Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = product.brand,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = BrandGoldDark
          )

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Filled.Star,
              contentDescription = null,
              tint = BrandGold,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${product.rating} (${product.reviewCount} تقييم)",
              fontSize = 12.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = product.titleAr,
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Price Row
        Row(verticalAlignment = Alignment.Bottom) {
          Text(
            text = "${product.price.toInt()}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "ر.س",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          if (product.originalPrice != null && product.originalPrice > product.price) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = "${product.originalPrice.toInt()} ر.س",
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
              textDecoration = TextDecoration.LineThrough
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
        Spacer(modifier = Modifier.height(14.dp))

        // Color Selection
        if (product.availableColors.isNotEmpty()) {
          Text(
            text = "اللون: ${selectedColor.nameAr}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.testTag("color_selector_row")
          ) {
            product.availableColors.forEach { col ->
              val isColSelected = selectedColor.nameAr == col.nameAr
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .background(col.color)
                  .border(
                    width = if (isColSelected) 2.5.dp else 1.dp,
                    color = if (isColSelected) BrandGold else Color.Gray.copy(alpha = 0.3f),
                    shape = CircleShape
                  )
                  .clickable { selectedColor = col }
                  .testTag("color_option_${col.nameEn}"),
                contentAlignment = Alignment.Center
              ) {
                if (isColSelected) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = if (col.color == Color(0xFFF7F7F7) || col.color == Color(0xFFE6D7C3)) Color.Black else Color.White,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))
        }

        // Size Selection
        if (product.availableSizes.isNotEmpty()) {
          Text(
            text = "المقاس المحدد: $selectedSize",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.testTag("size_selector_row")
          ) {
            product.availableSizes.forEach { sz ->
              val isSizeSelected = selectedSize == sz
              Surface(
                onClick = { selectedSize = sz },
                shape = RoundedCornerShape(12.dp),
                color = if (isSizeSelected) LuxuryDark else MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(
                  width = if (isSizeSelected) 1.5.dp else 1.dp,
                  color = if (isSizeSelected) BrandGold else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                ),
                modifier = Modifier.testTag("size_option_$sz")
              ) {
                Text(
                  text = sz,
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                  fontSize = 13.sp,
                  fontWeight = if (isSizeSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSizeSelected) BrandGoldLight else MaterialTheme.colorScheme.onSurface
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))
        }

        // Quantity Stepper
        Text(
          text = "الكمية",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.testTag("quantity_stepper_row")
        ) {
          OutlinedIconButton(
            onClick = { if (quantity > 1) quantity-- },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(36.dp).testTag("decrease_qty_btn")
          ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
          }

          Text(
            text = "$quantity",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp).testTag("qty_value_text")
          )

          OutlinedIconButton(
            onClick = { if (quantity < 10) quantity++ },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(36.dp).testTag("increase_qty_btn")
          ) {
            Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
          }

          Spacer(modifier = Modifier.weight(1f))

          Text(
            text = "المجموع: ${(product.price * quantity).toInt()} ر.س",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
        Spacer(modifier = Modifier.height(14.dp))

        // Description
        Text(
          text = "تفاصيل المنتج",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = product.descriptionAr,
          fontSize = 13.5.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Shipping & Guarantee Badges
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.LocalShipping, contentDescription = null, tint = BrandGoldDark, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("شحن سريع خلال 48 ساعة", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
          }
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Security, contentDescription = null, tint = BrandGoldDark, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("أصلي 100% وضمان استرجاع", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Add to Cart Button
        Button(
          onClick = {
            onAddToCart(selectedSize, selectedColor, quantity)
            onDismiss()
          },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = LuxuryDark,
            contentColor = BrandGoldLight
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("detail_add_to_cart_btn")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.ShoppingBag, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "إضافة إلى السلة • ${(product.price * quantity).toInt()} ر.س",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
