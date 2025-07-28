package com.coding.domain.usecase

import com.coding.domain.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case that fetches products from the API if they’re not already cached.
 * This runs on IO dispatcher since it hits the network + DB.
 */
class FetchProductsIfNeededUseCase(
    private val repository: ProductsRepository
) {
    /**
     * Triggers the fetch-and-cache logic.
     * No return value — either it fetches or skips based on cache state.
     */
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        repository.fetchAndCacheAllProductsIfNeeded()
    }
}
