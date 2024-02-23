package com.products.core.navigation

import com.products.R
import com.products.core.navigation.home.homeNavigationRoute
import com.products.core.navigation.product.productDetailsNavigationRoute
import com.products.core.navigation.product.productsNavigationRoute
import com.products.core.ui.AppIcons
import com.products.core.ui.Icon

enum class Destination(
    val isTopLevelDestination: Boolean,
    val selectedIcon: Icon? = null,
    val unselectedIcon: Icon? = null,
    val titleTextId: Int,
    val route: String
) {
    HOME(
        isTopLevelDestination = true,
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Home),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.HomeBorder),
        titleTextId = R.string.home,
        route = homeNavigationRoute
    ),
    PRODUCTS(
        isTopLevelDestination = true,
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Products),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.ProductsBorder),
        titleTextId = R.string.products,
        route = productsNavigationRoute
    ),
    PRODUCT_DETAILS(
        isTopLevelDestination = false,
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Products),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.ProductsBorder),
        titleTextId = R.string.products,
        route = productDetailsNavigationRoute
    )
}
