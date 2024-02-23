package com.products.features.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.products.core.domain.model.ProductDetails
import com.products.core.pagination.model.Product


@Composable
internal fun ProductRoute(
    viewModel: ProductViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
    navController: NavController
) {
    val products by viewModel.uiState.collectAsState()
    ProductScreen(products, onItemClick, navController)
}

@Composable
fun ProductScreen(
    list: List<Product>,
    onItemClick: (Int) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                .systemBarsPadding(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            items(list) { item ->
                ProductItemView(item, onItemClick, navController)
            }
        }

    }

}

@Composable
fun ProductItemView(item: Product?, onItemClick: (Int) -> Unit, navController: NavController) {
//    Chips 10.00$ Food
//    Chocolate 15.00$ Food
//    Sauce 50.00$ Food
//    Corn 15.00$ Food
//    Beer 20.00$ Drink
//    Coke 15.00$ Drink
//    Juice 13.00$ Drink
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.secondary,
                    bounded = true
                ),
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "details",
                        value = item?.id?.let { ProductDetails(it, item.name, "") }
                    )
                    item?.id?.let { onItemClick(it) }
                },
            ),
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            if (item != null) {
                Text(
                    item.name,
                    modifier = Modifier.padding(10.dp),
                )
                Text(
                    "Price: ${item.price}",
                    modifier = Modifier.padding(10.dp),
                )
                Text(
                    "Category: ${item.category}",
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}