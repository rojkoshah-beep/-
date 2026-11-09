package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

enum class AppScreen(
  val titleAr: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector
) {
  HOME("الرئيسية", Icons.Filled.Home, Icons.Outlined.Home),
  CATEGORIES("التصنيفات", Icons.Filled.Category, Icons.Outlined.Category),
  WISHLIST("المفضلة", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
  CART("السلة", Icons.Filled.ShoppingBag, Icons.Outlined.ShoppingBag),
  PROFILE("حسابي", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun AnaqahBottomBar(
  currentScreen: AppScreen,
  cartBadgeCount: Int,
  wishlistBadgeCount: Int,
  onScreenSelected: (AppScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    windowInsets = WindowInsets.navigationBars,
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 6.dp,
    modifier = modifier.testTag("app_bottom_bar")
  ) {
    AppScreen.values().forEach { screen ->
      val isSelected = currentScreen == screen

      NavigationBarItem(
        selected = isSelected,
        onClick = { onScreenSelected(screen) },
        icon = {
          when (screen) {
            AppScreen.CART -> {
              BadgedBox(
                badge = {
                  if (cartBadgeCount > 0) {
                    Badge(
                      containerColor = BrandGoldDark,
                      contentColor = Color.White
                    ) {
                      Text(
                        text = "$cartBadgeCount",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                      )
                    }
                  }
                }
              ) {
                Icon(
                  imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                  contentDescription = screen.titleAr,
                  modifier = Modifier.size(22.dp)
                )
              }
            }

            AppScreen.WISHLIST -> {
              BadgedBox(
                badge = {
                  if (wishlistBadgeCount > 0) {
                    Badge(
                      containerColor = AccentRed,
                      contentColor = Color.White
                    ) {
                      Text(
                        text = "$wishlistBadgeCount",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                      )
                    }
                  }
                }
              ) {
                Icon(
                  imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                  contentDescription = screen.titleAr,
                  modifier = Modifier.size(22.dp)
                )
              }
            }

            else -> {
              Icon(
                imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                contentDescription = screen.titleAr,
                modifier = Modifier.size(22.dp)
              )
            }
          }
        },
        label = {
          Text(
            text = screen.titleAr,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = BrandGoldLight,
          selectedTextColor = MaterialTheme.colorScheme.onSurface,
          indicatorColor = LuxuryDark,
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("bottom_nav_tab_${screen.name.lowercase()}")
      )
    }
  }
}
