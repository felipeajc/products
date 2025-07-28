package com.coding.data.remote.dto

/**
 * Raw response from the API when fetching a paginated list of products.
 *
 * @property products List of products in the current page.
 * @property total Total number of products available on the server.
 * @property skip Offset used in the current request.
 * @property limit Max number of items returned in this page.
 */
data class ProductResponseDto(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
