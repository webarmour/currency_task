package ru.webarmour.crypto_task.presentation.filter_screen

import ru.webarmour.crypto_task.domain.util.CurrencyOrder

data class SortOption(
    val label: String,
    val currencyOrder: CurrencyOrder
)
