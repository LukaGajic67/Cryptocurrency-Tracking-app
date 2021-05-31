package com.example.cryptocurrencytracker.data.local

import androidx.room.*
import com.example.cryptocurrencytracker.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin WHERE symbol =:sym")
    fun getCoin(sym: String): Flow<Coin>

    @Query("SELECT * FROM coin ORDER BY sortOrder")
    fun getAllFlow(): Flow<List<Coin>>

    @Query("SELECT * FROM coin ORDER BY sortOrder")
    fun getAll(): List<Coin>

    @Delete
    suspend fun delete(coin: Coin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: Coin): Long

    @Query("DELETE FROM coin")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)
}