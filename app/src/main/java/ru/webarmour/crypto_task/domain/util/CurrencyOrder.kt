package ru.webarmour.crypto_task.domain.util

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver

sealed class CurrencyOrder(open val orderType: OrderType) {
    data class Name(override val orderType: OrderType) : CurrencyOrder(orderType)
    data class Listing(override val orderType: OrderType) : CurrencyOrder(orderType)

}

sealed class OrderType {
    data object Ascending : OrderType() {
        override fun toString(): String = "Ascending"
    }
    data object Descending : OrderType() {
        override fun toString(): String = "Descending"
    }

    companion object {
        fun fromString(value: String): OrderType {
            return when (value) {
                "Ascending" -> Ascending
                "Descending" -> Descending
                else -> throw IllegalArgumentException("Unknown OrderType: $value")
            }
        }
    }
}

