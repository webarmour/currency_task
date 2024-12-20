package ru.webarmour.crypto_task.presentation.core_ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.webarmour.crypto_task.presentation.Screen
import ru.webarmour.crypto_task.presentation.currencies_screen.CurrenciesScreen
import ru.webarmour.crypto_task.presentation.favourites_screen.FavouritesScreen
import ru.webarmour.crypto_task.presentation.filter_screen.FilterScreen

private const val DEFAULT_CURRENCY = "USD"

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Screen.Currencies(DEFAULT_CURRENCY).route,
        ) {
            CurrenciesScreen(navController,DEFAULT_CURRENCY )
        }
        composable(Screen.Filters.route) {
            Log.d("AppNavHost", "Navigated to FiltersScreen")
            FilterScreen(navController)
        }
        composable(Screen.Favourites.route) {
            Log.d("AppNavHost", "Navigated to FavouritesScreen")
            FavouritesScreen(navController)
        }
    }
}