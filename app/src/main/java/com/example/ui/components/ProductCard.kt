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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.model.Product
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

@Composable
fun ProductCard(
  product: Product,
  isFavorite: Boolean,
  onToggleFavorite: () -> Unit,
  onClick: () -> Unit,
  onQuickAddToCart: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f),
        shape = RoundedCornerShape(18.dp)
      )
      .clickable(onClick = onClick)
      .testTag("product_card_${product.id}")
  ) {
    Column {
      // Image Container with badges and favorite button
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1f)
          .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
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

        // Gradient overlay at top for contrast
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
              Brush.verticalGradient(
                colors = listOf(Color.Black.copy(alpha = 0.45f), Color.Transparent)
              )
            )
        )

        // Discount Tag
        if (product.discountPercent > 0) {
          Box(
            modifier = Modifier
              .padding(start = 10.dp, top = 10.dp)
              .align(Alignment.TopStart)
              .clip(RoundedCornerShape(8.dp))
              .background(AccentRed)
              .padding(horizontal = 7.dp, vertical = 3.dp)
          ) {
            Text(
              text = "-${product.discountPercent}%",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // New Arrival Pill
        if (product.isNewArrival) {
          Box(
            modifier = Modifier
              .padding(start = if (product.discountPercent > 0) 64.dp else 10.dp, top = 10.dp)
              .align(Alignment.TopStart)
              .clip(RoundedCornerShape(8.dp))
              .background(BrandGold)
              .padding(horizontal = 7.dp, vertical = 3.dp)
          ) {
            Text(
              text = "جديد",
              color = LuxuryDark,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Favorite Button
        IconButton(
          onClick = onToggleFavorite,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(6.dp)
            .size(34.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.85f))
            .testTag("fav_btn_${product.id}")
        ) {
          Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) AccentRed else Color.DarkGray,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      // Product Details Section
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp)
      ) {
        // Brand & Rating Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = product.brand,
            fontSize = 11.sp,
            color = BrandGoldDark,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
          )

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Filled.Star,
              contentDescription = null,
              tint = BrandGold,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = product.rating.toString(),
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Title
        Text(
          text = product.titleAr,
          fontSize = 13.5.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Price Row with Add to Cart button
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = "${product.price.toInt()}",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "ر.س",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            if (product.originalPrice != null && product.originalPrice > product.price) {
              Text(
                text = "${product.originalPrice.toInt()} ر.س",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                textDecoration = TextDecoration.LineThrough
              )
            }
          }

          // Quick Add to Cart button
          FilledIconButton(
            onClick = onQuickAddToCart,
            shape = RoundedCornerShape(12.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
              containerColor = LuxuryDark,
              contentColor = BrandGoldLight
            ),
            modifier = Modifier
              .size(36.dp)
              .testTag("add_to_cart_btn_${product.id}")
          ) {
            Icon(
              imageVector = Icons.Outlined.ShoppingBag,
              contentDescription = "Add to Cart",
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}
