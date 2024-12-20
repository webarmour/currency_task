package ru.webarmour.crypto_task.domain

data class CurrencyModel(
    val chosenCoin: String,
    val ratesModel: List<RatesModel>,
)

data class RatesModel(
    val name: String,
    val price: Double
)



