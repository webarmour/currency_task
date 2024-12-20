package ru.webarmour.crypto_task.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.webarmour.crypto_task.CryptoApp
import ru.webarmour.crypto_task.data.local.CurrencyDao
import ru.webarmour.crypto_task.data.local.CurrencyDatabase


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideDao(): CurrencyDao {
        return CryptoApp.INSTANCE.database.getDao()
    }
}
