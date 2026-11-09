package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Category
import com.example.model.Subcategory
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGoldDark
import com.example.ui.theme.BrandGoldLight
import com.example.ui.theme.LuxuryDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesScreen(
  categories: List<Category>,
  onSubcategorySelected: (parentCategoryId: String, subcategoryId: String) -> Unit,
  onCategorySelected: (categoryId: String) -> Unit,
  onAddSubcategory: (parentCategoryId: String, nameAr: String, nameEn: String, emoji: String, descAr: String) -> Unit,
  modifier: Modifier = Modifier
) {
  var showAddDialog by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier.fillMaxWidth(),
    floatingActionButton = {
      FloatingActionButton(
        onClick = { showAddDialog = true },
        containerColor = LuxuryDark,
        contentColor = BrandGoldLight,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.testTag("fab_add_subcategory")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Add, contentDescription = "Add Subcategory", modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "إضافة تصنيف فرعي",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  ) { padding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .testTag("categories_screen_list")
    ) {
      // Screen Header
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BrandGold.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Outlined.Category,
                contentDescription = null,
                tint = BrandGoldDark,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "دليل وتصنيفات الأزياء",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "استكشف الأقسام الرئيسية والتصنيفات الفرعية بكل تفاصيلها",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }

      // Categories Cards
      items(categories) { category ->
        var isExpanded by remember { mutableStateOf(true) }

        val icon = when (category.id) {
          "men" -> Icons.Outlined.Man
          "women" -> Icons.Outlined.Woman
          else -> Icons.Outlined.Diamond
        }

        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
              width = 1.dp,
              color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
              shape = RoundedCornerShape(20.dp)
            )
            .testTag("category_card_${category.id}")
        ) {
          Column {
            // Category Hero Banner Card
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clickable { onCategorySelected(category.id) }
            ) {
              Image(
                painter = painterResource(id = category.bannerRes),
                contentDescription = category.nameAr,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )

              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .background(
                    Brush.verticalGradient(
                      colors = listOf(
                        Color.Black.copy(alpha = 0.35f),
                        Color.Black.copy(alpha = 0.78f)
                      )
                    )
                  )
              )

              Row(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = icon,
                      contentDescription = null,
                      tint = BrandGoldLight,
                      modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = category.nameAr,
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Black,
                      color = Color.White
                    )
                  }
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = category.descriptionAr,
                    fontSize = 11.5.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    maxLines = 1
                  )
                }

                Surface(
                  shape = CircleShape,
                  color = LuxuryDark.copy(alpha = 0.7f),
                  modifier = Modifier.size(34.dp)
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Icon(
                      imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                      contentDescription = "Toggle",
                      tint = Color.White,
                      modifier = Modifier
                        .size(20.dp)
                        .clickable { isExpanded = !isExpanded }
                    )
                  }
                }
              }
            }

            // Subcategories Section
            AnimatedVisibility(visible = isExpanded) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = "التصنيفات الفرعية (${category.subcategories.size})",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )

                  Text(
                    text = "تصفح كل منتجات القسم ←",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandGoldDark,
                    modifier = Modifier.clickable { onCategorySelected(category.id) }
                  )
                }

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                  verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  category.subcategories.forEach { sub ->
                    Surface(
                      onClick = { onSubcategorySelected(category.id, sub.id) },
                      shape = RoundedCornerShape(12.dp),
                      color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                      border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
                      ),
                      modifier = Modifier.testTag("subcat_item_${sub.id}")
                    ) {
                      Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Text(text = sub.iconEmoji, fontSize = 15.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                          text = sub.nameAr,
                          fontSize = 12.5.sp,
                          fontWeight = FontWeight.Medium,
                          color = MaterialTheme.colorScheme.onSurface
                        )
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      item {
        Spacer(modifier = Modifier.height(80.dp))
      }
    }
  }

  // Dialog to Add a New Subcategory
  if (showAddDialog) {
    AddSubcategoryDialog(
      categories = categories,
      onDismiss = { showAddDialog = false },
      onConfirm = { catId, nameAr, nameEn, emoji, desc ->
        onAddSubcategory(catId, nameAr, nameEn, emoji, desc)
        showAddDialog = false
      }
    )
  }
}

@Composable
fun AddSubcategoryDialog(
  categories: List<Category>,
  onDismiss: () -> Unit,
  onConfirm: (catId: String, nameAr: String, nameEn: String, emoji: String, desc: String) -> Unit
) {
  var selectedCatId by remember { mutableStateOf(categories.firstOrNull()?.id ?: "men") }
  var nameAr by remember { mutableStateOf("") }
  var nameEn by remember { mutableStateOf("") }
  var emoji by remember { mutableStateOf("✨") }
  var desc by remember { mutableStateOf("") }

  val commonEmojis = listOf("👔", "👗", "💍", "⌚", "👜", "🕶️", "🧥", "👞", "👠", "🧢", "🧕", "✨")

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(22.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    title = {
      Text(
        text = "إضافة تصنيف فرعي جديد",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth().testTag("add_subcat_dialog_content")) {
        Text(
          text = "اختر القسم الرئيسي:",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          categories.forEach { cat ->
            val isSelected = selectedCatId == cat.id
            Surface(
              onClick = { selectedCatId = cat.id },
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) LuxuryDark else MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.weight(1f)
            ) {
              Text(
                text = cat.nameAr.replace("ملابس ", ""),
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) BrandGoldLight else MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = nameAr,
          onValueChange = { nameAr = it },
          label = { Text("اسم التصنيف الفرعي (بالعربية)") },
          placeholder = { Text("مثال: كوفيات وأوشحة") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_subcat_name_ar"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = nameEn,
          onValueChange = { nameEn = it },
          label = { Text("الاسم بالإنجليزية (اختياري)") },
          placeholder = { Text("مثال: Scarves & Shawls") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_subcat_name_en"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "الأيقونة الرمزية:",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          commonEmojis.take(6).forEach { em ->
            Surface(
              onClick = { emoji = em },
              shape = RoundedCornerShape(8.dp),
              color = if (emoji == em) BrandGold.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.size(34.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = em, fontSize = 16.sp)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = desc,
          onValueChange = { desc = it },
          label = { Text("وصف مختصر") },
          placeholder = { Text("أحدث الموديلات لهذا الموسم") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_subcat_desc"),
          shape = RoundedCornerShape(12.dp)
        )
      }
    },
    confirmButton = {
      Button(
        onClick = { onConfirm(selectedCatId, nameAr, nameEn, emoji, desc) },
        enabled = nameAr.isNotBlank(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = LuxuryDark,
          contentColor = BrandGoldLight
        ),
        modifier = Modifier.testTag("dialog_submit_subcat_btn")
      ) {
        Text("حفظ التصنيف", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      OutlinedButton(
        onClick = onDismiss,
        shape = RoundedCornerShape(10.dp)
      ) {
        Text("إلغاء")
      }
    }
  )
}
