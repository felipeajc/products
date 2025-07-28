package com.coding.domain.usecase

import com.coding.domain.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetProductByIdUseCase constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) = withContext(Dispatchers.IO) {
        repository.getProductById(id)
    }
}
