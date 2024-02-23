package com.products.core.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.errorMessage
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.products.arch.extensions.collectAsStateLifecycleAware
import com.products.core.navigation.AppNavHost
import com.products.core.navigation.AppNavigationBar
import com.products.core.navigation.AppNavigationBarItem
import com.products.core.navigation.Destination

@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
)
@Composable
fun ProductApp(
    appState: AppState
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier
                    .systemBarsPadding()
                    .navigationBarsPadding()
            )
        },
        bottomBar = {
            AppBottomBar(
                destinations = appState.destinationWithBottomBars,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestinationAsState
            )
        }
    ) { padding ->
        val message = errorMessage.collectAsStateLifecycleAware().value
        LaunchedEffect(key1 = message.id) {
            if (message.message.isNotEmpty())
                snackbarHostState.showSnackbar(message = message.message)
        }
        AppNavHost(
            navController = appState.navController,
            onBackClick = appState::onBackClick,
            modifier = Modifier
                .padding(padding)
                .consumedWindowInsets(padding)
                .systemBarsPadding()
                .statusBarsPadding()
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun AppBottomBar(
    destinations: List<Destination>,
    onNavigateToDestination: (Destination) -> Unit,
    currentDestination: NavDestination?
) {
    AppNavigationBar {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            AppNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )

                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )

                        else -> {}
                    }
                },
                label = { Text("Label") }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: Destination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false