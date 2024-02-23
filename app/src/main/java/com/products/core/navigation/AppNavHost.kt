package com.products.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.products.core.navigation.home.homeScreen
import com.products.core.navigation.product.navigateToProductDetails
import com.products.core.navigation.product.productsDetailsScreen
import com.products.core.navigation.product.productsGraph
import com.products.core.navigation.product.productsGraphRoutePattern


@Composable
fun AppNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = productsGraphRoutePattern
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen()
        productsGraph(
            navController = navController,
            onItemClick = { navController.navigateToProductDetails(it) },
            nestedGraphs = {
                productsDetailsScreen(navController, onBackClick)
            }
        )
    }
}