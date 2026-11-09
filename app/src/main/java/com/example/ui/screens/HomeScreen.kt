package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.model.Subcategory
import com.example.ui.components.HeroBanner
import com.example.ui.components.MainCategoryTabs
import com.example.ui.components.ProductCard
import com.example.ui.components.SubcategoryFilterChips
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark
import com.example.viewmodel.SortOption

@Composable
fun HomeScreen(
  products: List<Product>,
  selectedCategoryId: String?,
  selectedSubcategoryId: String?,
  availableSubcategories: List<Subcategory>,
  searchQuery: String,
  sortOption: SortOption,
  favoriteIds: Set<String>,
  onSelectCategory: (String?) -> Unit,
  onSelectSubcategory: (String?) -> Unit,
  onSearchQueryChange: (String) -> Unit,
  onSortOptionChange: (SortOption) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onProductClick: (Product) -> Unit,
  onQuickAddToCart: (Product) -> Unit,
  onApplyCouponPromo: (String) -> Unit,
  onNavigateToCategories: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showSortMenu by remember { mutableStateOf(false) }

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    modifier = modifier
      .fillMaxSize()
      .testTag("home_product_grid"),
    contentPadding = PaddingValues(bottom = 90.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // 1. Search Bar & Branding Header
    item(span = { GridItemSpan(2) }) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.surface)
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        // Top Brand Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(LuxuryDark),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "أ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = BrandGoldLight
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "أناقة",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = 1.sp
              )
              Text(
                text = "متجر الأزياء والإكسسوارات الفاخرة",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          // Explore All Categories button
          Surface(
            onClick = onNavigateToCategories,
            shape = RoundedCornerShape(20.dp),
            color = BrandGold.copy(alpha = 0.15f),
            modifier = Modifier.testTag("home_explore_cats_btn")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Outlined.FilterList,
                contentDescription = null,
                tint = BrandGoldDark,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "دليل الأقسام",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = BrandGoldDark
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Input Field
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChange,
          placeholder = { Text("ابحث عن قميص، فستان، ساعة، حقيبة...") },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = "Search",
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { onSearchQueryChange("") }) {
                Icon(Icons.Default.Clear, contentDescription = "Clear search")
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(16.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = BrandGold
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("home_search_input")
        )
      }
    }

    // 2. Hero Banner
    if (searchQuery.isEmpty() && selectedCategoryId == null) {
      item(span = { GridItemSpan(2) }) {
        HeroBanner(
          onExploreClick = onNavigateToCategories,
          onApplyCouponPromo = onApplyCouponPromo
        )
      }
    }

    // 3. Main Category Tabs
    item(span = { GridItemSpan(2) }) {
      MainCategoryTabs(
        selectedCategoryId = selectedCategoryId,
        onSelectCategory = onSelectCategory
      )
    }

    // 4. Subcategory Filter Chips (if any)
    if (availableSubcategories.isNotEmpty()) {
      item(span = { GridItemSpan(2) }) {
        SubcategoryFilterChips(
          subcategories = availableSubcategories,
          selectedSubcategoryId = selectedSubcategoryId,
          onSelectSubcategory = onSelectSubcategory
        )
      }
    }

    // 5. Section Header & Sorting Bar
    item(span = { GridItemSpan(2) }) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          val sectionTitle = when {
            selectedCategoryId == "men" -> "تشكيلة الملابس الرجالية"
            selectedCategoryId == "women" -> "تشكيلة الملابس النسائية"
            selectedCategoryId == "accessories" -> "تشكيلة الإكسسوارات الفاخرة"
            searchQuery.isNotBlank() -> "نتائج البحث عن: \"$searchQuery\""
            else -> "أحدث التشكيلات والمنتجات"
          }

          Text(
            text = sectionTitle,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "${products.size} منتج متوفر",
            fontSize = 11.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Sort Selector
        Box {
          Surface(
            onClick = { showSortMenu = true },
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.testTag("sort_menu_btn")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = sortOption.titleAr,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }

          DropdownMenu(
            expanded = showSortMenu,
            onDismissRequest = { showSortMenu = false }
          ) {
            SortOption.values().forEach { opt ->
              DropdownMenuItem(
                text = { Text(opt.titleAr, fontSize = 13.sp) },
                onClick = {
                  onSortOptionChange(opt)
                  showSortMenu = false
                }
              )
            }
          }
        }
      }
    }

    // 6. Products Grid
    if (products.isEmpty()) {
      item(span = { GridItemSpan(2) }) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(
            imageVector = Icons.Outlined.ShoppingBag,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.size(54.dp)
          )
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "لا توجد منتجات مطابقة لهذا التصنيف أو البحث",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    } else {
      items(products, key = { it.id }) { product ->
        ProductCard(
          product = product,
          isFavorite = favoriteIds.contains(product.id),
          onToggleFavorite = { onToggleFavorite(product.id) },
          onClick = { onProductClick(product) },
          onQuickAddToCart = { onQuickAddToCart(product) },
          modifier = Modifier.padding(horizontal = 8.dp)
        )
      }
    }
  }
}
