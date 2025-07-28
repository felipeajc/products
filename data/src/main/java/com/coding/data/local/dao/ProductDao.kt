package com.coding.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coding.data.local.entity.ProductEntity

/**
 * Handles all DB interactions for product-related data.
 * Backed by Room and used as local cache.
 */
@Dao
interface ProductDao {

    /**
     * Gets all products from local DB, sorted by ID.
     */
    @Query("SELECT * FROM products ORDER BY id ASC")
    suspend fun getAll(): List<ProductEntity>

    /**
     * Searches products by a normalized term.
     * Relies on the 'searchable' column (precomputed lowercase/no-accent string).
     */
    @Query("SELECT * FROM products WHERE searchable LIKE '%' || :term || '%' ORDER BY id ASC")
    suspend fun searchNormalized(term: String): List<ProductEntity>

    /**
     * Returns a single product by ID.
     */
    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ProductEntity?

    /**
     * Inserts a list of products.
     * Replaces on conflict to ensure up-to-date info.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    /**
     * Nukes all product rows. Careful.
     */
    @Query("DELETE FROM products")
    suspend fun clearAll()
}
