package com.example.cryptocurrencytracker.di

import com.example.cryptocurrencytracker.data.CoinRepository
import com.example.cryptocurrencytracker.data.DefaultCoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun provideMainRepositoryImpl(repository: DefaultCoinRepository): CoinRepository
}