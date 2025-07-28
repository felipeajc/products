package com.coding.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coding.data.local.entity.MetadataEntity

/**
 * Quick key-value storage used for app-level metadata.
 * Backed by Room, this is usually for caching flags, counters, etc.
 */
@Dao
interface MetadataDao {

    /**
     * Gets the stored value for a given key.
     * Returns null if not found.
     */
    @Query("SELECT value FROM metadata WHERE key = :key")
    suspend fun getValue(key: String): String?

    /**
     * Saves a metadata entry.
     * Replaces the value if the key already exists.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(metadata: MetadataEntity)
}
