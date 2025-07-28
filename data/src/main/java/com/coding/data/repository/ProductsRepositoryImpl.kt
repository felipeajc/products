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

/**
 * Concrete implementation of [ProductsRepository].
 *
 * Handles API calls, Room caching, metadata, and paging.
 * Used across the app to isolate data layer from domain.
 */
@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val dao: ProductDao,
    private val metadataDao: MetadataDao
) : ProductsRepository {

    /**
     * Returns a paged list of products.
     * If query is blank, returns everything from local DB.
     * If there's a query, it tries to normalize and match against the DB.
     *
     * Everything is emitted as PagingData (even though we build it manually).
     */
    override fun getPagedProducts(
        query: String?,
        category: String?
    ): Flow<PagingData<ProductDomainModel>> = flow {
        val products = if (query.isNullOrBlank()) {
            dao.getAll()
        } else {
            val normalizedTerms = normalizeText(query)
                .split(" ")
                .filter { it.isNotBlank() }

            normalizedTerms
                .flatMap { dao.searchNormalized(it) }
                .distinctBy { it.id }
        }

        emit(PagingData.from(products.map { it.toDomain() }))
    }.flowOn(Dispatchers.IO)

    /**
     * Makes a paged request to the API and caches locally,
     * but only if we haven’t already done it before (based on metadata).
     *
     * It stores the total count in a Metadata table so we don’t re-fetch everything again.
     */
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

    /**
     * Fetches a product by ID from local DB.
     * Used in detail screen – assumes cache is already filled.
     */
    override suspend fun getProductById(id: Int): ProductDomainModel? {
        return dao.getById(id)?.toDomain()
    }
}
