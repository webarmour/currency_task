package ru.webarmour.crypto_task.presentation

import ru.webarmour.crypto_task.domain.util.CurrencyOrder

sealed class Screen(open val route: String) {
    data class Currencies(val baseCurrency: String) : Screen("currencies")
    object Filters : Screen("filters")
    object Favourites : Screen("favourites")
}