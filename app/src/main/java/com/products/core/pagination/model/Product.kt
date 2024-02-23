package com.products.core.pagination.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Int,
    val category: String,
)