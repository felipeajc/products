package com.coding.products.productlist

import androidx.paging.PagingData
import com.coding.products.model.ProductUiModel
import kotlinx.coroutines.flow.StateFlow

data class ProductListUiState(
    val products: StateFlow<PagingData<ProductUiModel>>,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)
