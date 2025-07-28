package com.coding.domain.repository

import androidx.paging.PagingData
import com.coding.domain.model.ProductDomainModel
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getPagedProducts(
        query: String? = null,
        category: String? = null
    ): Flow<PagingData<ProductDomainModel>>

    suspend fun fetchAndCacheAllProductsIfNeeded()

    suspend fun getProductById(id: Int): ProductDomainModel?
}
