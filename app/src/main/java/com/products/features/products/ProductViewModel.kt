package com.products.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.products.core.pagination.model.Product
import com.products.core.usecases.products.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<List<Product>>(emptyList())
    val uiState: StateFlow<List<Product>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { getProductsUseCase().sortedBy { it.category } }
        }
    }
}