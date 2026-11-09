package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.ui.components.ProductCard
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

@Composable
fun WishlistScreen(
  favoriteProducts: List<Product>,
  onToggleFavorite: (String) -> Unit,
  onProductClick: (Product) -> Unit,
  onQuickAddToCart: (Product) -> Unit,
  onStartShopping: () -> Unit,
  modifier: Modifier = Modifier
) {
  if (favoriteProducts.isEmpty()) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(24.dp)
        .testTag("empty_wishlist_view"),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .background(AccentRed.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Outlined.FavoriteBorder,
          contentDescription = null,
          tint = AccentRed,
          modifier = Modifier.size(44.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "قائمة المفضلة فارغة",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "احفظ القطع التي تعجبك بالضغط على علامة القلب للرجوع إليها لاحقاً بسهولة.",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(horizontal = 24.dp)
      )

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = onStartShopping,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = LuxuryDark,
          contentColor = BrandGoldLight
        ),
        modifier = Modifier.testTag("empty_wishlist_explore_btn")
      ) {
        Text("استكشف الأزياء الآن", fontWeight = FontWeight.Bold)
      }
    }
  } else {
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = modifier
        .fillMaxSize()
        .testTag("wishlist_grid"),
      contentPadding = PaddingValues(bottom = 100.dp, start = 8.dp, end = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      item(span = { GridItemSpan(2) }) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(AccentRed.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Filled.Favorite, contentDescription = null, tint = AccentRed, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "قائمتي المفضلة",
              fontSize = 20.sp,
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "${favoriteProducts.size} منتجات محفوظة",
              fontSize = 11.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      items(favoriteProducts, key = { it.id }) { product ->
        ProductCard(
          product = product,
          isFavorite = true,
          onToggleFavorite = { onToggleFavorite(product.id) },
          onClick = { onProductClick(product) },
          onQuickAddToCart = { onQuickAddToCart(product) }
        )
      }
    }
  }
}
