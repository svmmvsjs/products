package com.products.core.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.products.core.navigation.Destination
import com.products.core.navigation.home.navigateToHome
import com.products.core.navigation.product.navigateToProductsGraph
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(
        navController,
        windowSizeClass,
        coroutineScope
    ) {
        AppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    val coroutineScope: CoroutineScope
) {
    val currentDestinationAsState: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val destinationWithBottomBars: List<Destination>
        get() = Destination.values().asList()
            .filter { it.isTopLevelDestination }

    fun navigateToTopLevelDestination(destination: Destination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (destination) {
            Destination.HOME -> navController.navigateToHome(topLevelNavOptions)
            Destination.PRODUCTS -> navController.navigateToProductsGraph(
                topLevelNavOptions
            )

            else -> {}
        }

    }

    fun onBackClick() {
        navController.popBackStack()
    }
}