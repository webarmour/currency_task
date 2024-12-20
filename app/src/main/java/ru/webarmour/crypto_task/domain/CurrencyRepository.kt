package ru.webarmour.crypto_task.domain

import android.provider.Telephony.Mms.Rate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {


    suspend fun getCurrency(base: String) : CurrencyModel

    suspend fun insertToDb(model: RatesModel)

    suspend fun deleteFromDb(model: RatesModel)

    fun getAllFromDb(): Flow<List<RatesModel>>

    suspend fun isFavourite(name:String,price:Double):Boolean
}