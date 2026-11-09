package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

@Composable
fun HeroBanner(
  onExploreClick: () -> Unit,
  onApplyCouponPromo: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(22.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    modifier = modifier
      .fillMaxWidth()
      .height(190.dp)
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .testTag("hero_banner_card")
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      // Background Hero Image
      Image(
        painter = painterResource(id = R.drawable.hero_fashion_banner_1794184973111),
        contentDescription = "New Season Collection",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
      )

      // Dark Luxury Gradient
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.horizontalGradient(
              colors = listOf(
                LuxuryDark.copy(alpha = 0.92f),
                LuxuryDark.copy(alpha = 0.75f),
                Color.Transparent
              )
            )
          )
      )

      // Content Overlay
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(18.dp),
        verticalArrangement = Arrangement.Center
      ) {
        // Tag
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BrandGold.copy(alpha = 0.25f))
            .border(1.dp, BrandGold.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Outlined.LocalOffer,
              contentDescription = null,
              tint = BrandGoldLight,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "تشكيلة خريف وشتاء 2026",
              color = BrandGoldLight,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "أناقة لا متناهية\nلكل مناسباتك",
          fontSize = 18.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color.White,
          lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = onExploreClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = BrandGold,
              contentColor = LuxuryDark
            ),
            modifier = Modifier.testTag("hero_shop_now_btn")
          ) {
            Text(
              text = "تسوق الآن",
              fontWeight = FontWeight.Bold,
              fontSize = 12.5.sp
            )
          }

          // Coupon code tag clickable
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(Color.White.copy(alpha = 0.15f))
              .clickable { onApplyCouponPromo("ANAQAH20") }
              .padding(horizontal = 10.dp, vertical = 8.dp)
              .testTag("coupon_pill_anaqah20")
          ) {
            Text(
              text = "كود: ANAQAH20 (-20%)",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }
  }
}
