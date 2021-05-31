package com.example.cryptocurrencytracker.di

import android.content.Context
import androidx.room.Room
import com.example.cryptocurrencytracker.data.local.AppDatabase
import com.example.cryptocurrencytracker.data.local.CoinDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {

        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideCoinDao(appDatabase: AppDatabase): CoinDao {
        return appDatabase.coinDao()
    }
}