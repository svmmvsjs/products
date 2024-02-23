package com.products.core.usecases.products

import com.products.core.data.repositories.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke() = productRepository.getProducts(1, 1)
}
