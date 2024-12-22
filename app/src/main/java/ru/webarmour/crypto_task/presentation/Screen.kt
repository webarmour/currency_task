package ru.webarmour.crypto_task.presentation

import ru.webarmour.crypto_task.domain.util.CurrencyOrder

sealed class Screen(val route: String) {
    object Currencies : Screen("currencies")
    object Filters : Screen("filters")
    object Favourites : Screen("favourites")
}