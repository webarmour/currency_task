package ru.webarmour.crypto_task.presentation

import androidx.compose.runtime.Immutable
import ru.webarmour.crypto_task.domain.CurrencyModel
import ru.webarmour.crypto_task.domain.RatesModel

@Immutable
data class CurrencyState(
    val isLoading: Boolean = false,
    val coins: CurrencyModel = CurrencyModel(
        chosenCoin = "",
        ratesModel = listOf()
    ),
    val selectedCoin: String? = null,
)