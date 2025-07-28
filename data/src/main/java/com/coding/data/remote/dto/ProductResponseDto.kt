package com.coding.data.remote.dto

data class ProductResponseDto(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
