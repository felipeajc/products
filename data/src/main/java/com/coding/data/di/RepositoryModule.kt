package com.coding.data.di

import com.coding.data.repository.ProductsRepositoryImpl
import com.coding.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that binds the actual implementation of the ProductsRepository.
 * This is what makes the DI graph know that when it sees ProductsRepository,
 * it should inject ProductsRepositoryImpl.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the ProductsRepositoryImpl to the ProductsRepository interface.
     * Keeps things clean and testable.
     */
    @Binds
    @Singleton
    abstract fun bindProductsRepository(
        impl: ProductsRepositoryImpl
    ): ProductsRepository
}
