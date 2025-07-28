package com.coding.products.di

import com.coding.domain.repository.ProductsRepository
import com.coding.domain.usecase.FetchProductsIfNeededUseCase
import com.coding.domain.usecase.GetPagedProductsUseCase
import com.coding.domain.usecase.GetProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that provides all the use cases needed throughout the app.
 * Binds each use case to the ProductsRepository implementation.
 *
 * Installed in SingletonComponent, so everything here is available app-wide.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * Provides the use case for fetching and caching products only if needed.
     */
    @Provides
    fun provideFetchProductsIfNeededUseCase(
        repository: ProductsRepository
    ): FetchProductsIfNeededUseCase {
        return FetchProductsIfNeededUseCase(repository)
    }

    /**
     * Provides the use case for paginating products based on optional filters.
     */
    @Provides
    fun provideGetPagedProductsUseCase(
        repository: ProductsRepository
    ): GetPagedProductsUseCase {
        return GetPagedProductsUseCase(repository)
    }

    /**
     * Provides the use case for fetching a single product by ID.
     */
    @Provides
    fun provideGetProductByIdUseCase(
        repository: ProductsRepository
    ): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }
}
