package com.coding.domain.usecase

import com.coding.domain.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case to fetch a product by its ID.
 * Runs on IO thread since it might hit DB or network.
 */
class GetProductByIdUseCase constructor(
    private val repository: ProductsRepository
) {
    /**
     * Gets a single product by ID.
     *
     * @param id ID of the product to fetch.
     * @return ProductDomainModel or null if not found.
     */
    suspend operator fun invoke(id: Int) = withContext(Dispatchers.IO) {
        repository.getProductById(id)
    }
}
