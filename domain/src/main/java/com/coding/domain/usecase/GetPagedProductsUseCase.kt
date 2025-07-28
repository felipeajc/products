package com.coding.domain.usecase

import androidx.paging.PagingData
import com.coding.domain.model.ProductDomainModel
import com.coding.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case that gives you a paginated list of products.
 * Can filter by query and/or category.
 */
class GetPagedProductsUseCase(
    private val repository: ProductsRepository
) {
    /**
     * Returns a Flow of paged product data.
     *
     * @param query Optional search query to filter products.
     * @param category Optional category filter.
     * @return Flow emitting PagingData of ProductDomainModel.
     */
    operator fun invoke(
        query: String? = null,
        category: String? = null
    ): Flow<PagingData<ProductDomainModel>> {
        return repository.getPagedProducts(query, category)
    }
}
