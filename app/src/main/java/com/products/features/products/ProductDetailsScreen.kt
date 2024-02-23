package com.products.features.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.loadingFlow
import coil.compose.rememberAsyncImagePainter
import com.products.arch.extensions.collectAsStateLifecycleAware
import com.products.core.domain.model.ProductDetails
import com.products.core.ui.CircularProgressBar


@Composable
internal fun ProductDetailsRoute(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    productId: String?
) {
    val resource = viewModel.productDetails.collectAsStateLifecycleAware().value
    val isLoading = viewModel.loadingFlow.collectAsStateLifecycleAware().value
    LaunchedEffect(key1 = Unit) {
        productId?.toInt()?.let { viewModel.getProductDetails(it) }
    }
    if (isLoading) {
        CircularProgressBar()
    }
    ProductDetailsScreen(resource, onBackClick)
}

@Composable
fun ProductDetailsScreen(resource: ProductDetails, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(resource.imageUrl),
            contentDescription = resource.name,
            modifier = Modifier.fillMaxSize()
        )
    }
}
