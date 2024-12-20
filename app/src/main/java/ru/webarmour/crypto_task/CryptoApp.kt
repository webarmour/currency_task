package ru.webarmour.crypto_task

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.webarmour.crypto_task.data.local.CurrencyDatabase

@HiltAndroidApp
class CryptoApp : Application() {
    lateinit var database: CurrencyDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        database = CurrencyDatabase.getInstance(this)

    }

    companion object {
        lateinit var INSTANCE: CryptoApp
            private set
    }
}