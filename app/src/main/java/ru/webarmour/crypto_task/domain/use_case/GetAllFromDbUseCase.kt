package ru.webarmour.crypto_task.domain.use_case

import ru.webarmour.crypto_task.domain.CurrencyRepository
import ru.webarmour.crypto_task.domain.RatesModel
import javax.inject.Inject

class GetAllFromDbUseCase  @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke() = repository.getAllFromDb()

}