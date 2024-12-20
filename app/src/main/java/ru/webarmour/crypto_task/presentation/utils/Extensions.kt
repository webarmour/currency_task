package ru.webarmour.crypto_task.presentation.utils




fun Double.format(decimalPlaces:Int = 6): String {
    return String.format("%.${decimalPlaces}f", this)
}