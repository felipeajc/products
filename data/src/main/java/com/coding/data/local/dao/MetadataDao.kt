package com.coding.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coding.data.local.entity.MetadataEntity

@Dao
interface MetadataDao {

    @Query("SELECT value FROM metadata WHERE key = :key")
    suspend fun getValue(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(metadata: MetadataEntity)
}
