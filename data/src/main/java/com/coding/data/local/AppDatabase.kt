package com.coding.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coding.data.local.dao.MetadataDao
import com.coding.data.local.dao.ProductDao
import com.coding.data.local.entity.MetadataEntity
import com.coding.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class, MetadataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun metadataDao(): MetadataDao
}
