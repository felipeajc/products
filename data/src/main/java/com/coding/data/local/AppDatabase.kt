package com.coding.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coding.data.local.dao.MetadataDao
import com.coding.data.local.dao.ProductDao
import com.coding.data.local.entity.MetadataEntity
import com.coding.data.local.entity.ProductEntity

/**
 * Main Room database for the app.
 *
 * Holds local cache of product data and internal metadata.
 * This includes:
 * - [ProductEntity]: full product info
 * - [MetadataEntity]: small key-value entries (like total count)
 */
@Database(entities = [ProductEntity::class, MetadataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * DAO for products.
     */
    abstract fun productDao(): ProductDao

    /**
     * DAO for metadata (internal info like pagination state, totals, etc).
     */
    abstract fun metadataDao(): MetadataDao
}
