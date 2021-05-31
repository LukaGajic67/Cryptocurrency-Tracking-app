package com.example.cryptocurrencytracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocurrencytracker.model.Coin

@Database(entities = [Coin::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}