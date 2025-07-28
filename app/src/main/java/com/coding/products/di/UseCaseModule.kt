package com.coding.products.di

import com.coding.domain.repository.ProductsRepository
import com.coding.domain.usecase.FetchProductsIfNeededUseCase
import com.coding.domain.usecase.GetPagedProductsUseCase
import com.coding.domain.usecase.GetProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideFetchProductsIfNeededUseCase(
        repository: ProductsRepository
    ): FetchProductsIfNeededUseCase {
        return FetchProductsIfNeededUseCase(repository)
    }

    @Provides
    fun provideGetPagedProductsUseCase(
        repository: ProductsRepository
    ): GetPagedProductsUseCase {
        return GetPagedProductsUseCase(repository)
    }

    @Provides
    fun provideGetProductByIdUseCase(
        repository: ProductsRepository
    ): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }
}
