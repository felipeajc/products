package com.coding.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coding.data.local.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY id ASC")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE searchable LIKE '%' || :term || '%' ORDER BY id ASC")
    suspend fun searchNormalized(term: String): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearAll()
}
