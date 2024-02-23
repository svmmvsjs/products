package com.products.core.usecases.productDetails

import com.products.arch.extensions.useCaseFlow
import com.products.core.data.repositories.ProductRepository
import com.products.injection.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    operator fun invoke(id: Int) = useCaseFlow(coroutineDispatcher = coroutineDispatcher) {
        productRepository.getProductDetails(id)
    }
}