package com.app.mmm.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.app.mmm.view.productitem.ProductItem
import com.app.mmm.view.productitem.ProductItemViewModel
import com.app.mmm.view.productlist.ProductListScreen
import com.app.mmm.view.productlist.ProductListViewModel
import com.app.mmm.view.settings.SettingsScreen
import com.app.mmm.view.settings.SettingsViewModel

@Composable
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.HOME_ROUTE,
        startDestination = BottomBarScreen.PRODUCT_LIST.route,
    ) {
        composable(route = BottomBarScreen.PRODUCT_LIST.route) {
            val viewModel = hiltViewModel<ProductListViewModel>()
            ProductListScreen(
                viewModel = viewModel,
                onAddClick = {
                    navController.navigate(DetailsScreen.PRODUCT_ITEM.route + "/$it")
                }
            )
        }
        composable(route = BottomBarScreen.SETTINGS.route) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(viewModel)
        }

        detailsNavGraph(navController = navController)
    }
}

@ExperimentalComposeUiApi
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PRODUCT_ITEM,
        startDestination = DetailsScreen.PRODUCT_ITEM.route + "/{tag}"
    ) {
        composable(
            route = DetailsScreen.PRODUCT_ITEM.route + "/{tag}",
            arguments = listOf(
                navArgument("tag") { type = NavType.StringType }
            )
        ) {
            val itemTag = it.arguments?.getString("tag")!!
            val viewModel = hiltViewModel<ProductItemViewModel>()
            ProductItem(itemTag = itemTag, viewModel, onBackClick = { navController.navigateUp() })
        }
    }
}

enum class DetailsScreen(
    val route: String,
) {
    PRODUCT_ITEM(route = "product_item"),
}
