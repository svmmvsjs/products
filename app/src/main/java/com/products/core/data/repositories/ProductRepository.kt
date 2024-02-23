package com.products.core.data.repositories

import com.products.arch.data.Repository
import com.products.arch.extensions.mapSuccess
import com.products.core.data.network.Api
import com.products.core.data.network.dtos.toEntity
import com.products.core.domain.model.ProductDetails
import com.products.core.pagination.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: Api,
) : Repository() {

    suspend fun getProducts(page: Int, pageSize: Int): List<Product> {
//        Chips 10.00$ Food
//        Chocolate 15.00$ Food
//        Sauce 50.00$ Food
//        Corn 15.00$ Food
//        Beer 20.00$ Drink
//        Coke 15.00$ Drink
//        Juice 13.00$ Drink
        return listOf(
            Product(1, "Chips", 10, "Food"),
            Product(2, "Chocolate", 15, "Food"),
            Product(3, "Sauce", 50, "Food"),
            Product(4, "Corn", 15, "Food"),
            Product(5, "Beer", 20, "Drink"),
            Product(6, "Coke", 15, "Drink"),
            Product(1, "Juice", 13, "Drink"),

            )
        // fake data
//        return api.getProducts(page, pageSize)
//            .mapSuccess { response -> response.map { it.toEntity() } }
    }

    suspend fun getProductDetails(id: Int): ProductDetails {
        return api.getProductsDetails(id)
            .mapSuccess {
                it[0].toEntity()
            }
    }
}
