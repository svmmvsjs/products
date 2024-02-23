package com.products.core.data.network.dtos

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsDto(
    val id: Int,
    val name: String,
    val tagline: String,
    @SerializedName("image_url")
    val imageUrl: String,
)
