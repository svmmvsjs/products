package com.products.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductDetails(
    val id: Int,
    val name: String,
    val imageUrl: String
) : Parcelable