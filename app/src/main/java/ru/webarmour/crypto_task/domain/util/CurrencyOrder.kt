package ru.webarmour.crypto_task.domain.util

sealed class CurrencyOrder(val orderType: OrderType) {

    class Name(orderType:OrderType): CurrencyOrder(orderType)

    class Listing(orderType:OrderType): CurrencyOrder(orderType)

    fun copy(orderType: OrderType):CurrencyOrder {
        return when(this){
            is Name -> Name(orderType)
            is Listing -> Listing(orderType)
        }
    }
}

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

