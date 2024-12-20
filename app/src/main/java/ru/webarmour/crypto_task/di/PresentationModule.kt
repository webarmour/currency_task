package ru.webarmour.crypto_task.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.webarmour.crypto_task.data.repository.CurrencyRepositoryImpl
import ru.webarmour.crypto_task.domain.CurrencyRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface PresentationModule {

    @Binds
    @Singleton
    fun bindRepository(repositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}