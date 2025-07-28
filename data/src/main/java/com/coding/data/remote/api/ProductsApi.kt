package com.coding.data.remote.api

import com.coding.data.remote.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    @GET("products")
    suspend fun getAllProductsPaged(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponseDto
}
