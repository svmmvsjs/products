package com.products.core.navigation.product

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.products.features.products.ProductDetailsRoute
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
internal const val productIdArg = "productId"
internal const val productDetailsRoute = "product_details_route"
internal const val productDetailsNavigationRoute = "$productDetailsRoute/{$productIdArg}"

fun NavController.navigateToProductDetails(productId: Int) {
    this.navigate("$productDetailsRoute/$productId")
}

fun NavGraphBuilder.productsDetailsScreen(
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    composable(
        route = productDetailsNavigationRoute,
        arguments = listOf(
            navArgument(productIdArg) { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val productId = arguments.getString(productIdArg)
        ProductDetailsRoute(productId = productId, onBackClick = onBackClick)
    }
}