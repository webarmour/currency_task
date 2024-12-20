package ru.webarmour.crypto_task.domain.use_case

import ru.webarmour.crypto_task.domain.CurrencyRepository
import ru.webarmour.crypto_task.domain.RatesModel
import javax.inject.Inject

class DeleteCoinFromDbUseCase  @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(model: RatesModel) = repository.deleteFromDb(model)

}