package com.products.core.pagination.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.products.core.pagination.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(product: List<Product>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): PagingSource<Int, Product>

    @Query("DELETE FROM products")
    suspend fun clearAllProducts()
}