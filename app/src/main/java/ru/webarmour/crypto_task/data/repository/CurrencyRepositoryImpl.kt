package ru.webarmour.crypto_task.data.repository

import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.webarmour.crypto_task.data.local.CurrencyDao
import ru.webarmour.crypto_task.data.mappers.toDbModel
import ru.webarmour.crypto_task.data.mappers.toDomain
import ru.webarmour.crypto_task.data.mappers.toDomainModel
import ru.webarmour.crypto_task.data.remote.RetrofitInstance
import ru.webarmour.crypto_task.domain.CurrencyModel
import ru.webarmour.crypto_task.domain.CurrencyRepository
import ru.webarmour.crypto_task.domain.RatesModel
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val dao: CurrencyDao
) : CurrencyRepository {


    override suspend fun getCurrency(base: String): CurrencyModel {
        return RetrofitInstance.retrofit.getCurrency(base = base).toDomainModel()
    }

    override suspend fun insertToDb(model: RatesModel) {
        dao.insertCurrency(model.toDbModel())
    }

    override suspend fun deleteFromDb(model: RatesModel) {
        dao.deleteCurrency(model.toDbModel())
    }

    override fun getAllFromDb(): Flow<List<RatesModel>> {
        return dao.getAll().map {
            it.toDomain()
        }
    }

    override suspend fun isFavourite(name:String,price:Double): Boolean {
        return dao.isFavourite(
            name,
            price
        )
    }

}