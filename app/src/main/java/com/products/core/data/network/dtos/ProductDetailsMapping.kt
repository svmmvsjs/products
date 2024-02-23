package com.products.core.data.network.dtos

import com.products.core.domain.model.ProductDetails

fun ProductDetailsDto.toEntity(): ProductDetails {
    return ProductDetails(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}