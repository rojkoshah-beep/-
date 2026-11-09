package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FashionCatalog
import com.example.model.AppCategories
import com.example.model.CartItem
import com.example.model.Category
import com.example.model.Order
import com.example.model.OrderStatus
import com.example.model.Product
import com.example.model.ProductColor
import com.example.model.Subcategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class SortOption(val titleAr: String) {
  FEATURED("الأكثر تميزاً"),
  PRICE_LOW_HIGH("السعر: من الأقل للأعلى"),
  PRICE_HIGH_LOW("السعر: من الأعلى للأقل"),
  TOP_RATED("الأعلى تقييماً")
}

data class CheckoutFormState(
  val fullName: String = "أحمد محمد",
  val phone: String = "0501234567",
  val city: String = "الرياض",
  val street: String = "شارع التحلية، حي العليا",
  val paymentMethod: String = "mada", // "mada", "apple_pay", "cash_on_delivery"
  val notes: String = ""
)

class FashionViewModel : ViewModel() {

  // Selected Category ID (null = All)
  private val _selectedCategoryId = MutableStateFlow<String?>(null)
  val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

  // Selected Subcategory ID (null = All in current category)
  private val _selectedSubcategoryId = MutableStateFlow<String?>(null)
  val selectedSubcategoryId: StateFlow<String?> = _selectedSubcategoryId.asStateFlow()

  // Search Query
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  // Sort Option
  private val _sortOption = MutableStateFlow(SortOption.FEATURED)
  val sortOption: StateFlow<SortOption> = _sortOption.asStateFlow()

  // Wishlist / Favorites product IDs
  private val _favoriteIds = MutableStateFlow<Set<String>>(setOf("m1", "w1", "a1"))
  val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

  // Shopping Cart items
  private val _cartItems = MutableStateFlow<List<CartItem>>(
    listOf(
      CartItem(
        id = "cart_init_1",
        product = FashionCatalog.products.first { it.id == "m1" },
        selectedSize = "L",
        selectedColor = FashionCatalog.products.first { it.id == "m1" }.availableColors.first(),
        quantity = 1
      )
    )
  )
  val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

  // Coupon code
  private val _appliedCoupon = MutableStateFlow<String?>(null)
  val appliedCoupon: StateFlow<String?> = _appliedCoupon.asStateFlow()
  private val _couponDiscountPercent = MutableStateFlow(0)
  val couponDiscountPercent: StateFlow<Int> = _couponDiscountPercent.asStateFlow()

  // Selected Product for quick view / sheet
  private val _detailedProduct = MutableStateFlow<Product?>(null)
  val detailedProduct: StateFlow<Product?> = _detailedProduct.asStateFlow()

  // Checkout form state
  private val _checkoutForm = MutableStateFlow(CheckoutFormState())
  val checkoutForm: StateFlow<CheckoutFormState> = _checkoutForm.asStateFlow()

  // Order history
  private val _orders = MutableStateFlow<List<Order>>(
    listOf(
      Order(
        id = "ord_sample_1",
        orderNumber = "ANQ-98421",
        date = "2026-11-05",
        items = listOf(
          CartItem(
            id = "prev_1",
            product = FashionCatalog.products.first { it.id == "w2" },
            selectedSize = "56",
            selectedColor = FashionCatalog.products.first { it.id == "w2" }.availableColors.first(),
            quantity = 1
          )
        ),
        subtotal = 480.0,
        discount = 0.0,
        deliveryFee = 0.0,
        total = 480.0,
        status = OrderStatus.DELIVERED,
        customerName = "أحمد محمد",
        deliveryAddress = "الرياض - شارع التحلية",
        paymentMethodAr = "مدى (Mada)"
      )
    )
  )
  val orders: StateFlow<List<Order>> = _orders.asStateFlow()

  // Last completed order for confirmation dialog
  private val _lastCompletedOrder = MutableStateFlow<Order?>(null)
  val lastCompletedOrder: StateFlow<Order?> = _lastCompletedOrder.asStateFlow()

  // Toast / Snack message
  private val _uiMessage = MutableStateFlow<String?>(null)
  val uiMessage: StateFlow<String?> = _uiMessage.asStateFlow()

  // Filtered Products stream
  val filteredProducts: StateFlow<List<Product>> = combine(
    _selectedCategoryId,
    _selectedSubcategoryId,
    _searchQuery,
    _sortOption
  ) { catId, subcatId, query, sort ->
    var list = FashionCatalog.products

    // Category filter
    if (catId != null) {
      list = list.filter { it.categoryId == catId }
    }

    // Subcategory filter
    if (subcatId != null) {
      list = list.filter { it.subcategoryId == subcatId }
    }

    // Search query
    if (query.isNotBlank()) {
      val trimmed = query.trim().lowercase(Locale.ROOT)
      list = list.filter {
        it.titleAr.contains(trimmed, ignoreCase = true) ||
          it.titleEn.contains(trimmed, ignoreCase = true) ||
          it.brand.contains(trimmed, ignoreCase = true) ||
          it.descriptionAr.contains(trimmed, ignoreCase = true)
      }
    }

    // Sorting
    when (sort) {
      SortOption.FEATURED -> list.sortedByDescending { it.isFeatured }
      SortOption.PRICE_LOW_HIGH -> list.sortedBy { it.price }
      SortOption.PRICE_HIGH_LOW -> list.sortedByDescending { it.price }
      SortOption.TOP_RATED -> list.sortedByDescending { it.rating }
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FashionCatalog.products)

  // Categories with dynamic subcategories
  private val _categories = MutableStateFlow<List<Category>>(AppCategories.all)
  val categories: StateFlow<List<Category>> = _categories.asStateFlow()

  // Subcategories for current selected category
  val availableSubcategories: StateFlow<List<Subcategory>> = combine(
    _selectedCategoryId,
    _categories
  ) { catId, allCats ->
    if (catId == null) {
      emptyList()
    } else {
      allCats.find { it.id == catId }?.subcategories ?: emptyList()
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun addSubcategory(categoryId: String, nameAr: String, nameEn: String, emoji: String, descriptionAr: String) {
    val current = _categories.value.toMutableList()
    val index = current.indexOfFirst { it.id == categoryId }
    if (index >= 0) {
      val cat = current[index]
      val newSub = Subcategory(
        id = "${categoryId}_${System.currentTimeMillis()}",
        parentCategoryId = categoryId,
        nameAr = nameAr.trim(),
        nameEn = nameEn.trim().ifBlank { nameAr.trim() },
        iconEmoji = emoji.ifBlank { "✨" },
        descriptionAr = descriptionAr.trim().ifBlank { "تشكيلة جديدة مميزة" }
      )
      val updatedSubs = cat.subcategories + newSub
      current[index] = cat.copy(subcategories = updatedSubs)
      _categories.value = current
      _uiMessage.value = "تمت إضافة التصنيف الفرعي \"$nameAr\" بنجاح ✨"
    }
  }

  fun selectCategory(categoryId: String?) {
    _selectedCategoryId.value = categoryId
    _selectedSubcategoryId.value = null // reset subcategory when parent changes
  }

  fun selectSubcategory(subcategoryId: String?) {
    if (_selectedSubcategoryId.value == subcategoryId) {
      _selectedSubcategoryId.value = null // toggle off
    } else {
      _selectedSubcategoryId.value = subcategoryId
    }
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setSortOption(sort: SortOption) {
    _sortOption.value = sort
  }

  fun toggleFavorite(productId: String) {
    val current = _favoriteIds.value.toMutableSet()
    if (current.contains(productId)) {
      current.remove(productId)
      _uiMessage.value = "تمت الإزالة من المفضلة"
    } else {
      current.add(productId)
      _uiMessage.value = "تمت الإضافة إلى قائمة المفضلة ❤️"
    }
    _favoriteIds.value = current
  }

  fun viewProductDetails(product: Product?) {
    _detailedProduct.value = product
  }

  fun addToCart(
    product: Product,
    selectedSize: String = product.availableSizes.firstOrNull() ?: "Standard",
    selectedColor: ProductColor = product.availableColors.firstOrNull() ?: FashionCatalog.Black,
    quantity: Int = 1
  ) {
    val current = _cartItems.value.toMutableList()
    val existingIndex = current.indexOfFirst {
      it.product.id == product.id && it.selectedSize == selectedSize && it.selectedColor.nameAr == selectedColor.nameAr
    }

    if (existingIndex >= 0) {
      val item = current[existingIndex]
      current[existingIndex] = item.copy(quantity = item.quantity + quantity)
    } else {
      current.add(
        CartItem(
          id = UUID.randomUUID().toString(),
          product = product,
          selectedSize = selectedSize,
          selectedColor = selectedColor,
          quantity = quantity
        )
      )
    }
    _cartItems.value = current
    _uiMessage.value = "تمت إضافة \"${product.titleAr}\" إلى السلة 🛍️"
  }

  fun updateCartQuantity(cartItemId: String, newQuantity: Int) {
    val current = _cartItems.value.toMutableList()
    val index = current.indexOfFirst { it.id == cartItemId }
    if (index >= 0) {
      if (newQuantity <= 0) {
        current.removeAt(index)
      } else {
        current[index] = current[index].copy(quantity = newQuantity)
      }
      _cartItems.value = current
    }
  }

  fun removeFromCart(cartItemId: String) {
    _cartItems.value = _cartItems.value.filter { it.id != cartItemId }
    _uiMessage.value = "تم حذف المنتج من السلة"
  }

  fun applyCoupon(code: String): Boolean {
    val normalized = code.trim().uppercase(Locale.ROOT)
    return when (normalized) {
      "ANAQAH20", "ELEGANCE20" -> {
        _appliedCoupon.value = normalized
        _couponDiscountPercent.value = 20
        _uiMessage.value = "تم تطبيق كود الخصم بنجاح: خصم 20% 🎉"
        true
      }
      "WELCOME10" -> {
        _appliedCoupon.value = normalized
        _couponDiscountPercent.value = 10
        _uiMessage.value = "تم تطبيق كود الخصم الترحيبي: خصم 10% 🎉"
        true
      }
      else -> {
        _uiMessage.value = "كود الخصم غير صالح أو منتهي الصلاحية"
        false
      }
    }
  }

  fun removeCoupon() {
    _appliedCoupon.value = null
    _couponDiscountPercent.value = 0
  }

  // Cart financial calculations
  fun getCartSubtotal(): Double {
    return _cartItems.value.sumOf { it.totalPrice }
  }

  fun getCartDiscount(): Double {
    val subtotal = getCartSubtotal()
    val percent = _couponDiscountPercent.value
    return if (percent > 0) subtotal * (percent / 100.0) else 0.0
  }

  fun getCartDeliveryFee(): Double {
    val subtotal = getCartSubtotal()
    return if (subtotal == 0.0 || subtotal >= 300.0) 0.0 else 25.0
  }

  fun getCartTotal(): Double {
    return (getCartSubtotal() - getCartDiscount() + getCartDeliveryFee()).coerceAtLeast(0.0)
  }

  fun updateCheckoutForm(form: CheckoutFormState) {
    _checkoutForm.value = form
  }

  fun completeOrder(): Order? {
    val items = _cartItems.value
    if (items.isEmpty()) return null

    val subtotal = getCartSubtotal()
    val discount = getCartDiscount()
    val deliveryFee = getCartDeliveryFee()
    val total = getCartTotal()
    val form = _checkoutForm.value

    val paymentMethodName = when (form.paymentMethod) {
      "mada" -> "بطاقة مدى (Mada)"
      "apple_pay" -> "Apple Pay"
      "card" -> "بطاقة ائتمانية (Visa / Mastercard)"
      else -> "الدفع عند الاستلام (COD)"
    }

    val orderNum = "ANQ-" + (10000..99999).random()
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    val newOrder = Order(
      id = UUID.randomUUID().toString(),
      orderNumber = orderNum,
      date = currentDate,
      items = items,
      subtotal = subtotal,
      discount = discount,
      deliveryFee = deliveryFee,
      total = total,
      status = OrderStatus.PREPARING,
      customerName = form.fullName,
      deliveryAddress = "${form.city} - ${form.street}",
      paymentMethodAr = paymentMethodName
    )

    // Add to history & clear cart
    _orders.value = listOf(newOrder) + _orders.value
    _lastCompletedOrder.value = newOrder
    _cartItems.value = emptyList()
    removeCoupon()

    return newOrder
  }

  fun dismissCompletedOrderDialog() {
    _lastCompletedOrder.value = null
  }

  fun clearUiMessage() {
    _uiMessage.value = null
  }
}
