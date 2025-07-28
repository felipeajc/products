package com.coding.products.productlist

import androidx.paging.PagingData
import com.coding.products.model.ProductUiModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Holds everything the product list screen needs to render.
 *
 * @param products paginated list of products (already mapped to UI model).
 * @param searchQuery current search input (for UI + search logic).
 * @param isLoading tells if the initial data is still loading.
 */
data class ProductListUiState(
    val products: StateFlow<PagingData<ProductUiModel>>,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)
