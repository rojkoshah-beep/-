package com.example.model

import androidx.compose.ui.graphics.Color

data class ProductColor(
  val nameAr: String,
  val nameEn: String,
  val color: Color
)

data class Product(
  val id: String,
  val titleAr: String,
  val titleEn: String,
  val brand: String,
  val categoryId: String,       // "men", "women", "accessories"
  val subcategoryId: String,
  val price: Double,
  val originalPrice: Double? = null,
  val discountPercent: Int = 0,
  val rating: Float = 4.8f,
  val reviewCount: Int = 42,
  val imageRes: Int? = null,
  val imageUrl: String? = null,
  val descriptionAr: String,
  val availableSizes: List<String> = listOf("S", "M", "L", "XL"),
  val availableColors: List<ProductColor> = emptyList(),
  val isFeatured: Boolean = false,
  val isNewArrival: Boolean = false,
  val inStock: Boolean = true
)

data class CartItem(
  val id: String,
  val product: Product,
  val selectedSize: String,
  val selectedColor: ProductColor,
  val quantity: Int = 1
) {
  val totalPrice: Double get() = product.price * quantity
}

enum class OrderStatus(val titleAr: String, val stepIndex: Int) {
  PREPARING("قيد التجهيز", 1),
  ON_THE_WAY("جاري التوصيل", 2),
  DELIVERED("تم التوصيل بنجاح", 3)
}

data class Order(
  val id: String,
  val orderNumber: String,
  val date: String,
  val items: List<CartItem>,
  val subtotal: Double,
  val discount: Double,
  val deliveryFee: Double,
  val total: Double,
  val status: OrderStatus,
  val customerName: String,
  val deliveryAddress: String,
  val paymentMethodAr: String
)
