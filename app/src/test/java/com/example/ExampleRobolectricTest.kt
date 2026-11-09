package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.FashionCatalog
import com.example.model.AppCategories
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("أناقة", appName)
  }

  @Test
  fun `verify categories structure and products`() {
    val categories = AppCategories.all
    assertEquals(3, categories.size)

    val menCat = categories.find { it.id == "men" }
    val womenCat = categories.find { it.id == "women" }
    val accCat = categories.find { it.id == "accessories" }

    assertNotNull(menCat)
    assertNotNull(womenCat)
    assertNotNull(accCat)

    assertTrue(menCat!!.subcategories.isNotEmpty())
    assertTrue(womenCat!!.subcategories.isNotEmpty())
    assertTrue(accCat!!.subcategories.isNotEmpty())

    // Check catalog products
    assertTrue(FashionCatalog.products.any { it.categoryId == "men" })
    assertTrue(FashionCatalog.products.any { it.categoryId == "women" })
    assertTrue(FashionCatalog.products.any { it.categoryId == "accessories" })
  }
}
