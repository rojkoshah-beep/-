package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Product
import com.example.ui.components.AnaqahBottomBar
import com.example.ui.components.AppScreen
import com.example.ui.components.CheckoutBottomSheet
import com.example.ui.components.OrderSuccessDialog
import com.example.ui.components.ProductDetailSheet
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrdersScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WishlistScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.FashionViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

  private val viewModel: FashionViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      MyApplicationTheme {
        AnaqahApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun AnaqahApp(viewModel: FashionViewModel) {
  var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
  var showCheckoutSheet by remember { mutableStateOf(false) }
  var showOrdersHistoryScreen by remember { mutableStateOf(false) }

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  // State collectors
  val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
  val selectedCategoryId by viewModel.selectedCategoryId.collectAsStateWithLifecycle()
  val selectedSubcategoryId by viewModel.selectedSubcategoryId.collectAsStateWithLifecycle()
  val availableSubcategories by viewModel.availableSubcategories.collectAsStateWithLifecycle()
  val categories by viewModel.categories.collectAsStateWithLifecycle()
  val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
  val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
  val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
  val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
  val appliedCoupon by viewModel.appliedCoupon.collectAsStateWithLifecycle()
  val couponDiscountPercent by viewModel.couponDiscountPercent.collectAsStateWithLifecycle()
  val detailedProduct by viewModel.detailedProduct.collectAsStateWithLifecycle()
  val checkoutForm by viewModel.checkoutForm.collectAsStateWithLifecycle()
  val orders by viewModel.orders.collectAsStateWithLifecycle()
  val lastCompletedOrder by viewModel.lastCompletedOrder.collectAsStateWithLifecycle()
  val uiMessage by viewModel.uiMessage.collectAsStateWithLifecycle()

  // Snackbars for messages
  LaunchedEffect(uiMessage) {
    uiMessage?.let { msg ->
      scope.launch {
        snackbarHostState.showSnackbar(msg)
      }
      viewModel.clearUiMessage()
    }
  }

  // Handle hardware / gesture back button
  BackHandler(enabled = currentScreen != AppScreen.HOME || showOrdersHistoryScreen) {
    if (showOrdersHistoryScreen) {
      showOrdersHistoryScreen = false
    } else {
      currentScreen = AppScreen.HOME
    }
  }

  Scaffold(
    contentWindowInsets = WindowInsets.safeDrawing,
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      AnaqahBottomBar(
        currentScreen = currentScreen,
        cartBadgeCount = cartItems.sumOf { it.quantity },
        wishlistBadgeCount = favoriteIds.size,
        onScreenSelected = { screen ->
          showOrdersHistoryScreen = false
          currentScreen = screen
        }
      )
    },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      if (showOrdersHistoryScreen) {
        OrdersScreen(orders = orders)
      } else {
        when (currentScreen) {
          AppScreen.HOME -> {
            HomeScreen(
              products = filteredProducts,
              selectedCategoryId = selectedCategoryId,
              selectedSubcategoryId = selectedSubcategoryId,
              availableSubcategories = availableSubcategories,
              searchQuery = searchQuery,
              sortOption = sortOption,
              favoriteIds = favoriteIds,
              onSelectCategory = { catId -> viewModel.selectCategory(catId) },
              onSelectSubcategory = { subcatId -> viewModel.selectSubcategory(subcatId) },
              onSearchQueryChange = { query -> viewModel.setSearchQuery(query) },
              onSortOptionChange = { sort -> viewModel.setSortOption(sort) },
              onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
              onProductClick = { prod -> viewModel.viewProductDetails(prod) },
              onQuickAddToCart = { prod -> viewModel.addToCart(prod) },
              onApplyCouponPromo = { code -> viewModel.applyCoupon(code) },
              onNavigateToCategories = { currentScreen = AppScreen.CATEGORIES }
            )
          }

          AppScreen.CATEGORIES -> {
            CategoriesScreen(
              categories = categories,
              onSubcategorySelected = { parentCatId, subcatId ->
                viewModel.selectCategory(parentCatId)
                viewModel.selectSubcategory(subcatId)
                currentScreen = AppScreen.HOME
              },
              onCategorySelected = { catId ->
                viewModel.selectCategory(catId)
                currentScreen = AppScreen.HOME
              },
              onAddSubcategory = { catId, nameAr, nameEn, emoji, desc ->
                viewModel.addSubcategory(catId, nameAr, nameEn, emoji, desc)
              }
            )
          }

          AppScreen.WISHLIST -> {
            val favProducts = filteredProducts.filter { favoriteIds.contains(it.id) }
            WishlistScreen(
              favoriteProducts = favProducts,
              onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
              onProductClick = { prod -> viewModel.viewProductDetails(prod) },
              onQuickAddToCart = { prod -> viewModel.addToCart(prod) },
              onStartShopping = { currentScreen = AppScreen.HOME }
            )
          }

          AppScreen.CART -> {
            CartScreen(
              cartItems = cartItems,
              appliedCoupon = appliedCoupon,
              couponDiscountPercent = couponDiscountPercent,
              subtotal = viewModel.getCartSubtotal(),
              discount = viewModel.getCartDiscount(),
              deliveryFee = viewModel.getCartDeliveryFee(),
              total = viewModel.getCartTotal(),
              onUpdateQuantity = { id, qty -> viewModel.updateCartQuantity(id, qty) },
              onRemoveItem = { id -> viewModel.removeFromCart(id) },
              onApplyCoupon = { code -> viewModel.applyCoupon(code) },
              onRemoveCoupon = { viewModel.removeCoupon() },
              onProceedToCheckout = { showCheckoutSheet = true },
              onStartShopping = { currentScreen = AppScreen.HOME }
            )
          }

          AppScreen.PROFILE -> {
            ProfileScreen(
              ordersCount = orders.size,
              wishlistCount = favoriteIds.size,
              onViewOrders = { showOrdersHistoryScreen = true },
              onViewWishlist = { currentScreen = AppScreen.WISHLIST }
            )
          }
        }
      }

      // Product Detail BottomSheet
      detailedProduct?.let { product ->
        ProductDetailSheet(
          product = product,
          isFavorite = favoriteIds.contains(product.id),
          onToggleFavorite = { viewModel.toggleFavorite(product.id) },
          onAddToCart = { size, color, qty ->
            viewModel.addToCart(product, size, color, qty)
          },
          onDismiss = { viewModel.viewProductDetails(null) }
        )
      }

      // Checkout BottomSheet
      if (showCheckoutSheet) {
        CheckoutBottomSheet(
          initialForm = checkoutForm,
          subtotal = viewModel.getCartSubtotal(),
          discount = viewModel.getCartDiscount(),
          deliveryFee = viewModel.getCartDeliveryFee(),
          total = viewModel.getCartTotal(),
          onConfirmOrder = { updatedForm ->
            viewModel.updateCheckoutForm(updatedForm)
            viewModel.completeOrder()
            showCheckoutSheet = false
          },
          onDismiss = { showCheckoutSheet = false }
        )
      }

      // Order Success Dialog
      lastCompletedOrder?.let { order ->
        OrderSuccessDialog(
          order = order,
          onDismiss = { viewModel.dismissCompletedOrderDialog() },
          onViewOrders = {
            viewModel.dismissCompletedOrderDialog()
            showOrdersHistoryScreen = true
          }
        )
      }
    }
  }
}
