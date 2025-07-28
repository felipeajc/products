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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideProductsApi(): ProductsApi =
        provideRetrofit().create(ProductsApi::class.java)

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // --- Database ---

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

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao =
        database.productDao()


    @Provides
    @Singleton
    fun provideMetadataDao(db: AppDatabase): MetadataDao = db.metadataDao()

}
