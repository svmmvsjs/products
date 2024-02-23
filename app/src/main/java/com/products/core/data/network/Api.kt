package com.products.core.data.network

import com.products.core.data.network.dtos.ProductDetailsDto
import com.products.core.data.network.dtos.ProductDto
import com.products.core.data.network.responses.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("users/2")
    suspend fun getUser(): Response<UserResponse>

    @GET("beers")
    suspend fun getProducts(
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): Response<List<ProductDto>>

    @GET("beers/{id}")
    suspend fun getProductsDetails(@Path("id") id: Int): Response<List<ProductDetailsDto>>
}
