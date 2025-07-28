package com.coding.data.repository

import androidx.paging.PagingData
import com.coding.data.local.dao.MetadataDao
import com.coding.data.local.dao.ProductDao
import com.coding.data.local.entity.MetadataEntity
import com.coding.data.local.entity.normalizeText
import com.coding.data.local.entity.toDomain
import com.coding.data.local.entity.toEntity
import com.coding.data.remote.api.ProductsApi
import com.coding.domain.model.ProductDomainModel
import com.coding.domain.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

private const val METADATA_KEY_PRODUCTS_TOTAL = "products_total"
private const val PAGE_SIZE = 100

@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val dao: ProductDao,
    private val metadataDao: MetadataDao
) : ProductsRepository {

    override fun getPagedProducts(
        query: String?,
        category: String?
    ): Flow<PagingData<ProductDomainModel>> = flow {
        val products = if (query.isNullOrBlank()) {
            dao.getAll()
        } else {
            val normalizedTerms = normalizeText(query).split(" ").filter { it.isNotBlank() }
            normalizedTerms
                .flatMap { dao.searchNormalized(it) }
                .distinctBy { it.id }
        }

        emit(PagingData.from(products.map { it.toDomain() }))
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchAndCacheAllProductsIfNeeded() {
        val existingTotal = metadataDao.getValue(METADATA_KEY_PRODUCTS_TOTAL)?.toIntOrNull()
        if (existingTotal != null) return

        var skip = 0
        var total = 0

        while (true) {
            val response = api.getAllProductsPaged(limit = PAGE_SIZE, skip = skip)
            val products = response.products

            if (products.isEmpty()) break

            dao.insertAll(products.map { it.toEntity() })

            if (skip == 0) {
                total = response.total
            }

            skip += products.size

            if (skip >= response.total) break
        }

        metadataDao.insert(MetadataEntity(METADATA_KEY_PRODUCTS_TOTAL, total.toString()))
    }

    // +++++++ Details +++++++++//

    override suspend fun getProductById(id: Int): ProductDomainModel? {
        return dao.getById(id)?.toDomain()
    }
}
