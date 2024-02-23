package com.products.core.data.network.dtos

import com.products.core.pagination.model.Product

fun ProductDto.toEntity(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        category = category,
    )
}