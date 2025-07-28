package com.coding.domain.repository

import androidx.paging.PagingData
import com.coding.domain.model.ProductDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository that defines all product-related operations.
 * Abstracts away the data source (remote API, local DB, etc.).
 */
interface ProductsRepository {

    /**
     * Returns a paginated flow of products.
     *
     * @param query Optional filter by product name.
     * @param category Optional filter by product category.
     * @return A Flow emitting paged product data.
     */
    fun getPagedProducts(
        query: String? = null,
        category: String? = null
    ): Flow<PagingData<ProductDomainModel>>

    /**
     * Fetches all products from the API and stores them locally,
     * but only if they’re not already cached.
     */
    suspend fun fetchAndCacheAllProductsIfNeeded()

    /**
     * Returns a single product by its ID.
     *
     * @param id The ID of the product.
     * @return The product, or null if not found.
     */
    suspend fun getProductById(id: Int): ProductDomainModel?
}
