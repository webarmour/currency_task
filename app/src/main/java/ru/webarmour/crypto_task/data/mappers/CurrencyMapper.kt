package ru.webarmour.crypto_task.data.mappers

import ru.webarmour.crypto_task.data.local.RatesModelDb
import ru.webarmour.crypto_task.data.remote.CurrencyDto
import ru.webarmour.crypto_task.domain.CurrencyModel
import ru.webarmour.crypto_task.domain.RatesModel


fun CurrencyDto.toDomainModel(): CurrencyModel {
    return CurrencyModel(
        chosenCoin = this.base,
        ratesModel = this.rates.map { it ->
            RatesModel(
                name = it.key,
                price = it.value
            )
        }
    )
}

fun RatesModelDb.toDomain() = RatesModel(
    name = this.name,
    price = this.price
)

fun RatesModel.toDbModel() = RatesModelDb(
    name = this.name,
    price = this.price
)

fun List<RatesModelDb>.toDomain() = this.map {
    it.toDomain()
}



