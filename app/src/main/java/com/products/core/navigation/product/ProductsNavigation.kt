package com.products.core.navigation.product

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.products.features.products.ProductRoute

const val productsGraphRoutePattern = "products_graph"
const val productsNavigationRoute = "products_route"

fun NavController.navigateToProductsGraph(navOptions: NavOptions? = null) {
    this.navigate(productsGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.productsGraph(
    onItemClick: (Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
    navController: NavController
) {
    navigation(
        route = productsGraphRoutePattern,
        startDestination = productsNavigationRoute
    ) {
        composable(route = productsNavigationRoute) {
            ProductRoute(navController = navController, onItemClick = onItemClick)
        }
        nestedGraphs()
    }
}