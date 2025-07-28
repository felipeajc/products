package com.coding.products.productdetails

import com.coding.products.model.ProductUiModel

data class ProductDetailUiState(
    val product: ProductUiModel? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
