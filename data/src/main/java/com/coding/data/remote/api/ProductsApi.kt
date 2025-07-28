package com.coding.data.remote.api

import com.coding.data.remote.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for hitting the /products endpoint.
 *
 * Supports paged fetching using limit/skip query params.
 * This is the single source of truth when syncing remote products.
 */
interface ProductsApi {

    /**
     * Calls the /products endpoint with pagination.
     *
     * @param limit Number of items to fetch.
     * @param skip Offset to start from (used for pagination).
     * @return A [ProductResponseDto] containing a list of products + metadata.
     */
    @GET("products")
    suspend fun getAllProductsPaged(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponseDto
}
