package ru.webarmour.crypto_task.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrencyDao {


    @Query("SELECT * FROM currencies")
    fun getAll(): Flow<List<RatesModelDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(model: RatesModelDb)

    @Delete
    suspend fun deleteCurrency(model: RatesModelDb)

    @Query("SELECT EXISTS(SELECT 1 FROM currencies WHERE name = :name AND price = :price)")
    suspend fun isFavourite(name: String, price: Double): Boolean

}