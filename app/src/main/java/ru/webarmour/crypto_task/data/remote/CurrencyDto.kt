package ru.webarmour.crypto_task.data.remote

import com.google.gson.annotations.SerializedName


data class CurrencyDto(
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String,Double>,
)