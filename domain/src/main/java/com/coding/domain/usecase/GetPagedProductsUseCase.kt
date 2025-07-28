package com.coding.domain.usecase

import androidx.paging.PagingData
import com.coding.domain.model.ProductDomainModel
import com.coding.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetPagedProductsUseCase(private val repository: ProductsRepository) {
    operator fun invoke(
        query: String? = null,
        category: String? = null
    ): Flow<PagingData<ProductDomainModel>> {
        return repository.getPagedProducts(query, category)
    }
}
