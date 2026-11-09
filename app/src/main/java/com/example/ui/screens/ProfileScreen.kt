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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

data class ProfileMenuItem(
  val title: String,
  val subtitle: String,
  val icon: ImageVector,
  val actionTag: String
)

@Composable
fun ProfileScreen(
  ordersCount: Int,
  wishlistCount: Int,
  onViewOrders: () -> Unit,
  onViewWishlist: () -> Unit,
  modifier: Modifier = Modifier
) {
  val menuItems = listOf(
    ProfileMenuItem("العناوين المحفوظة", "الرياض - حي العليا، شارع التحلية", Icons.Filled.LocationOn, "menu_addresses"),
    ProfileMenuItem("طرق الدفع", "مدى (Mada)، بطاقة فيزا، Apple Pay", Icons.Filled.CreditCard, "menu_payments"),
    ProfileMenuItem("الإشعارات والعروض", "تفعيل تنبيهات التخفيضات والكوليكشن الجديد", Icons.Filled.Notifications, "menu_notifications"),
    ProfileMenuItem("خدمة العملاء والدعم", "فريق المساعدة متاح على مدار الساعة", Icons.Filled.Headphones, "menu_support"),
    ProfileMenuItem("عن متجر أناقة", "الإصدار 1.0.0 - أرقى الملابس والإكسسوارات", Icons.Filled.Info, "menu_about")
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("profile_screen"),
    contentPadding = PaddingValues(start = 16.dp, top = 14.dp, end = 16.dp, bottom = 100.dp)
  ) {
    // User Profile Header Card
    item {
      Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = LuxuryDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(76.dp)
              .clip(CircleShape)
              .background(
                Brush.linearGradient(listOf(BrandGoldLight, BrandGoldDark))
              )
              .border(2.dp, BrandGold, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Outlined.Person,
              contentDescription = null,
              tint = LuxuryDark,
              modifier = Modifier.size(42.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "أحمد محمد العتيبي",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
          )

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = "ahmed.m@example.com",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // VIP Badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(BrandGold.copy(alpha = 0.25f))
              .border(1.dp, BrandGold, RoundedCornerShape(12.dp))
              .padding(horizontal = 12.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = BrandGoldLight,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "عضوية أناقة الذهبية (VIP)",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = BrandGoldLight
              )
            }
          }
        }
      }
    }

    // Stats Row (Orders, Wishlist, Reward Points)
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Surface(
          onClick = onViewOrders,
          shape = RoundedCornerShape(16.dp),
          color = MaterialTheme.colorScheme.surface,
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
          modifier = Modifier.weight(1f)
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "$ordersCount",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = BrandGoldDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "الطلبات",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Surface(
          onClick = onViewWishlist,
          shape = RoundedCornerShape(16.dp),
          color = MaterialTheme.colorScheme.surface,
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
          modifier = Modifier.weight(1f)
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "$wishlistCount",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = BrandGoldDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "المفضلة",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = MaterialTheme.colorScheme.surface,
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
          modifier = Modifier.weight(1f)
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "450",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = BrandGoldDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "نقاط المكافأة",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // Settings Menu List
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
      ) {
        Column {
          menuItems.forEachIndexed { index, item ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp)
                .testTag(item.actionTag),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(BrandGold.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = item.icon,
                  contentDescription = null,
                  tint = BrandGoldDark,
                  modifier = Modifier.size(18.dp)
                )
              }

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = item.title,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = item.subtitle,
                  fontSize = 11.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
              )
            }

            if (index < menuItems.lastIndex) {
              HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f)
              )
            }
          }
        }
      }
    }
  }
}
