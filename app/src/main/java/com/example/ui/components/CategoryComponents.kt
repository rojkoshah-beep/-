package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppCategories
import com.example.model.Subcategory
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

data class MainCategoryItem(
  val id: String?,
  val titleAr: String,
  val icon: ImageVector,
  val badgeText: String
)

@Composable
fun MainCategoryTabs(
  selectedCategoryId: String?,
  onSelectCategory: (String?) -> Unit,
  modifier: Modifier = Modifier
) {
  val items = listOf(
    MainCategoryItem(null, "جميع الأقسام", Icons.Outlined.Checkroom, "15"),
    MainCategoryItem("men", "ملابس رجالية", Icons.Outlined.Man, "5"),
    MainCategoryItem("women", "ملابس نسائية", Icons.Outlined.Woman, "5"),
    MainCategoryItem("accessories", "إكسسوارات", Icons.Outlined.Diamond, "5")
  )

  LazyRow(
    modifier = modifier
      .fillMaxWidth()
      .testTag("main_categories_row"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    items(items) { item ->
      val isSelected = selectedCategoryId == item.id
      val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) LuxuryDark else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 200),
        label = "tab_bg"
      )
      val contentColor by animateColorAsState(
        targetValue = if (isSelected) BrandGoldLight else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(durationMillis = 200),
        label = "tab_content"
      )
      val borderColor = if (isSelected) BrandGold else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)

      Surface(
        onClick = { onSelectCategory(item.id) },
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor,
        shadowElevation = if (isSelected) 4.dp else 1.dp,
        modifier = Modifier
          .border(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = borderColor,
            shape = RoundedCornerShape(24.dp)
          )
          .testTag("category_tab_${item.id ?: "all"}")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = item.titleAr,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 13.5.sp
          )
          Spacer(modifier = Modifier.width(6.dp))
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(
                if (isSelected) BrandGold.copy(alpha = 0.25f)
                else MaterialTheme.colorScheme.surfaceVariant
              )
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = item.badgeText,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (isSelected) BrandGold else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }
  }
}

@Composable
fun SubcategoryFilterChips(
  subcategories: List<Subcategory>,
  selectedSubcategoryId: String?,
  onSelectSubcategory: (String?) -> Unit,
  modifier: Modifier = Modifier
) {
  if (subcategories.isEmpty()) return

  Column(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 2.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "التصنيفات الفرعية",
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.weight(1f))
      if (selectedSubcategoryId != null) {
        Text(
          text = "إلغاء التحديد",
          fontSize = 12.sp,
          color = BrandGoldDark,
          fontWeight = FontWeight.Medium,
          modifier = Modifier
            .clickable { onSelectSubcategory(null) }
            .padding(4.dp)
        )
      }
    }

    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("subcategories_row"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(subcategories) { sub ->
        val isSelected = selectedSubcategoryId == sub.id
        FilterChip(
          selected = isSelected,
          onClick = { onSelectSubcategory(sub.id) },
          label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = sub.iconEmoji, fontSize = 14.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = sub.nameAr,
                fontSize = 12.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            }
          },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = BrandGold.copy(alpha = 0.2f),
            selectedLabelColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
            selectedBorderColor = BrandGold,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.5.dp
          ),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.testTag("subcat_chip_${sub.id}")
        )
      }
    }
  }
}
