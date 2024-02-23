package com.products.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.products.arch.extensions.LoadingAware
import com.products.arch.extensions.ViewErrorAware
import com.products.arch.extensions.onSuccess
import com.products.core.domain.model.ProductDetails
import com.products.core.usecases.productDetails.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel(), ViewErrorAware, LoadingAware {
    val productDetails = MutableStateFlow(ProductDetails(-1, "", ""))

    fun getProductDetails(id: Int) {
        getProductDetailsUseCase(id).onSuccess {
            productDetails.value = it
        }.launchIn(viewModelScope)
    }
}
