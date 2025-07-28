package com.coding.products.ui.navigation

/**
 * Central place to define navigation routes for the app.
 * Keeps things type-safe and avoids hardcoding routes in multiple places.
 */
sealed class Screen(val route: String) {

    // List screen – entry point
    object ProductList : Screen("productList")

    // Detail screen, expects a productId param in the route
    object ProductDetail : Screen("productDetail/{productId}") {

        // Builds the actual route string with the passed productId
        fun createRoute(productId: Int) = "productDetail/$productId"
    }
}
