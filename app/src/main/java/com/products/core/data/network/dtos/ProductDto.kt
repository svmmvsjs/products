package com.products.core.data.network.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val price: Int,
    val category: String,
)