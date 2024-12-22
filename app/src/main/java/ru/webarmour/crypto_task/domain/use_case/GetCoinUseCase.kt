package ru.webarmour.crypto_task.domain.use_case


import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.webarmour.crypto_task.domain.CurrencyModel
import ru.webarmour.crypto_task.domain.CurrencyRepository
import ru.webarmour.crypto_task.domain.util.CurrencyOrder
import ru.webarmour.crypto_task.domain.util.OrderType
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CurrencyRepository,
) {


    operator fun invoke(
        currencyOrder: CurrencyOrder,
        baseCoin: String,
    ): Flow<CurrencyModel> {
        return flow {
            try {
                val currencyModel = repository.getCurrency(baseCoin)
                val sortedRatesModel = when (currencyOrder.orderType) {
                    OrderType.Ascending -> {
                        when (currencyOrder) {
                            is CurrencyOrder.Listing -> currencyModel.ratesModel.sortedBy { it.price }

                            is CurrencyOrder.Name -> currencyModel.ratesModel.sortedBy { it.name.lowercase() }
                        }
                    }

                    OrderType.Descending -> {
                        when (currencyOrder) {
                            is CurrencyOrder.Listing -> currencyModel.ratesModel.sortedByDescending { it.price }

                            is CurrencyOrder.Name -> currencyModel.ratesModel.sortedByDescending { it.name.lowercase() }
                        }
                    }
                }
                emit(CurrencyModel(ratesModel = sortedRatesModel, chosenCoin = baseCoin))
            } catch (e: Exception) {
                Log.d("MainScreenViewModel", "${e.message}")
            }
        }
    }
}