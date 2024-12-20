package ru.webarmour.crypto_task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies", primaryKeys = ["name", "price"])
data class RatesModelDb(
    val id: Int = 0,
    val name: String,
    val price: Double
)
