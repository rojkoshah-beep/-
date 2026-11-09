package com.example.model

import androidx.annotation.DrawableRes
import com.example.R

data class Category(
  val id: String,
  val nameAr: String,
  val nameEn: String,
  val descriptionAr: String,
  @DrawableRes val bannerRes: Int,
  val subcategories: List<Subcategory>
)

data class Subcategory(
  val id: String,
  val parentCategoryId: String,
  val nameAr: String,
  val nameEn: String,
  val iconEmoji: String,
  val descriptionAr: String
)

val MenSubcategories = listOf(
  Subcategory(
    id = "men_shirts",
    parentCategoryId = "men",
    nameAr = "قمصان وتيشرتات",
    nameEn = "Shirts & Polos",
    iconEmoji = "👔",
    descriptionAr = "قمصان رسمية وكاجوال وتيشيرتات قطنية فاخرة"
  ),
  Subcategory(
    id = "men_pants",
    parentCategoryId = "men",
    nameAr = "بنطلونات وجينز",
    nameEn = "Pants & Chinos",
    iconEmoji = "👖",
    descriptionAr = "جينزات عالية الجودة وبنطلونات قماشية راقية"
  ),
  Subcategory(
    id = "men_jackets",
    parentCategoryId = "men",
    nameAr = "سترات وجواكيت",
    nameEn = "Jackets & Coats",
    iconEmoji = "🧥",
    descriptionAr = "جواكيت شتوية ومعاطف وبلايزرات أنيقة"
  ),
  Subcategory(
    id = "men_suits",
    parentCategoryId = "men",
    nameAr = "بدلات رسمية",
    nameEn = "Formal Suits",
    iconEmoji = "🤵",
    descriptionAr = "بدلات كاملة وأزياء للمناسبات والأعمال"
  ),
  Subcategory(
    id = "men_shoes",
    parentCategoryId = "men",
    nameAr = "أحذية رجالية",
    nameEn = "Men's Shoes",
    iconEmoji = "👞",
    descriptionAr = "أحذية جلدية كلاسيكية وسنيكرز كاجوال"
  )
)

val WomenSubcategories = listOf(
  Subcategory(
    id = "women_dresses",
    parentCategoryId = "women",
    nameAr = "فساتين وسهرة",
    nameEn = "Dresses & Evening",
    iconEmoji = "👗",
    descriptionAr = "فساتين سهرة راقية وفساتين كاجوال أنيقة"
  ),
  Subcategory(
    id = "women_abayas",
    parentCategoryId = "women",
    nameAr = "عبايات وجلابيات",
    nameEn = "Abayas & Kaftans",
    iconEmoji = "🧕",
    descriptionAr = "عبايات خليجية فاخرة وتطريزات عصرية"
  ),
  Subcategory(
    id = "women_tops",
    parentCategoryId = "women",
    nameAr = "بلايز وقمصان",
    nameEn = "Tops & Blouses",
    iconEmoji = "👚",
    descriptionAr = "بلوزات حريرية وقمصان بتصاميم ناعمة"
  ),
  Subcategory(
    id = "women_sets",
    parentCategoryId = "women",
    nameAr = "أطقم وتنانير",
    nameEn = "Sets & Skirts",
    iconEmoji = "💃",
    descriptionAr = "أطقم قطعتين وتنانير عصرية لكافة الأوقات"
  ),
  Subcategory(
    id = "women_shoes",
    parentCategoryId = "women",
    nameAr = "أحذية نسائية",
    nameEn = "Women's Shoes",
    iconEmoji = "👠",
    descriptionAr = "كعوب عالية وصنادل وأحذية مريحة"
  )
)

val AccessoriesSubcategories = listOf(
  Subcategory(
    id = "acc_watches",
    parentCategoryId = "accessories",
    nameAr = "ساعات فاخرة",
    nameEn = "Luxury Watches",
    iconEmoji = "⌚",
    descriptionAr = "ساعات كلاسيكية ورقمية بتصاميم سويسرية"
  ),
  Subcategory(
    id = "acc_bags",
    parentCategoryId = "accessories",
    nameAr = "حقائب ومحافظ",
    nameEn = "Bags & Wallets",
    iconEmoji = "👜",
    descriptionAr = "حقائب يد جلدية ومحافظ أنيقة رجالية ونسائية"
  ),
  Subcategory(
    id = "acc_sunglasses",
    parentCategoryId = "accessories",
    nameAr = "نظارات شمسية",
    nameEn = "Sunglasses",
    iconEmoji = "🕶️",
    descriptionAr = "نظارات بتصاميم حديثة وحماية فائقة من الشمس"
  ),
  Subcategory(
    id = "acc_jewelry",
    parentCategoryId = "accessories",
    nameAr = "مجوهرات وأساور",
    nameEn = "Jewelry & Bracelets",
    iconEmoji = "💍",
    descriptionAr = "سلاسل وأقراط وأساور مطلية بلمسة ذهبية"
  ),
  Subcategory(
    id = "acc_belts",
    parentCategoryId = "accessories",
    nameAr = "أحزمة وإكسسوارات",
    nameEn = "Belts & Others",
    iconEmoji = "🎩",
    descriptionAr = "أحزمة جلد طبيعي وقبعات وإكسسوارات إضافية"
  )
)

object AppCategories {
  val Men = Category(
    id = "men",
    nameAr = "ملابس رجالية",
    nameEn = "Men's Clothing",
    descriptionAr = "تشكيلة راقية من أحدث صيحات الموضة الرجالية العصرية والرسمية",
    bannerRes = R.drawable.category_men_1794184984460,
    subcategories = MenSubcategories
  )

  val Women = Category(
    id = "women",
    nameAr = "ملابس نسائية",
    nameEn = "Women's Clothing",
    descriptionAr = "إطلالات أنثوية متجددة وأرقى الفساتين والعبايات والأزياء العالمية",
    bannerRes = R.drawable.category_women_1794184996948,
    subcategories = WomenSubcategories
  )

  val Accessories = Category(
    id = "accessories",
    nameAr = "إكسسوارات",
    nameEn = "Accessories",
    descriptionAr = "لمسات الفخامة المتكاملة من ساعات وحقائب ومجوهرات ونظارات",
    bannerRes = R.drawable.category_accessories_1794185008860,
    subcategories = AccessoriesSubcategories
  )

  val all = listOf(Men, Women, Accessories)
}
