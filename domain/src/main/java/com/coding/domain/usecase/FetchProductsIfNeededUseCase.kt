package com.coding.domain.usecase

import com.coding.domain.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchProductsIfNeededUseCase(private val repository: ProductsRepository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        repository.fetchAndCacheAllProductsIfNeeded()
    }
}
