package com.coding.data.di

import android.content.Context
import androidx.room.Room
import com.coding.data.BuildConfig
import com.coding.data.local.AppDatabase
import com.coding.data.local.dao.MetadataDao
import com.coding.data.local.dao.ProductDao
import com.coding.data.remote.api.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt module that wires up everything needed for networking and local DB access.
 * Retrofit client, Room DB, and all related DAOs get configured here.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides the Retrofit service interface to hit the products API.
     */
    @Provides
    @Singleton
    fun provideProductsApi(): ProductsApi =
        provideRetrofit().create(ProductsApi::class.java)

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // Comes from buildConfigField
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // --- Database setup ---

    /**
     * Creates and provides a Room database instance.
     */
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = buildDatabase(context)

    private fun buildDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "products.db"
        ).build()

    /**
     * Provides the ProductDao to query products.
     */
    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao =
        database.productDao()

    /**
     * Provides the MetadataDao to store app metadata (e.g. product count).
     */
    @Provides
    @Singleton
    fun provideMetadataDao(db: AppDatabase): MetadataDao =
        db.metadataDao()
}
