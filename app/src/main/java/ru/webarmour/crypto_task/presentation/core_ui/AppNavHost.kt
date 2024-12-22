package ru.webarmour.crypto_task.presentation.core_ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.webarmour.crypto_task.presentation.Screen
import ru.webarmour.crypto_task.presentation.currencies_screen.CurrenciesScreen
import ru.webarmour.crypto_task.presentation.favourites_screen.FavouritesScreen
import ru.webarmour.crypto_task.presentation.filter_screen.FilterScreen



@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    showBottomNavigation: MutableState<Boolean>,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Screen.Currencies.route,
        ) {
            CurrenciesScreen(navController, showBottomNavigation = showBottomNavigation)
        }
        composable(
            route = Screen.Filters.route + "/{base}",
            arguments = listOf(navArgument("base") { type = NavType.StringType })
        ) {
            val base = it.arguments?.getString("base") ?: "JPY"
            Log.d("AppNavHost", "Navigated to FiltersScreen")
            FilterScreen(navController, _baseCoin = base, showBottomNavigation = showBottomNavigation)
        }
        composable(
            route = Screen.Favourites.route
        ) {
            Log.d("AppNavHost", "Navigated to FavouritesScreen")
            FavouritesScreen(showBottomNavigation = showBottomNavigation)
        }
    }
}