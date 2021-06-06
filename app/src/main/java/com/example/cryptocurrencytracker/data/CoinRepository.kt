package com.example.cryptocurrencytracker.data

import com.example.cryptocurrencytracker.model.Coin
import com.example.cryptocurrencytracker.model.Result
import com.example.cryptocurrencytracker.model.dto.CoinHistoryDto
import com.example.cryptocurrencytracker.utils.HistoryType
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    /**
     * Returns a list of coins from the database as Flow and refreshes the database
     */
    suspend fun getAllCoins(): Flow<List<Coin>>

    suspend fun deleteAllCoins()

    suspend fun insertAllCoins(coins: List<Coin>)

    /**
     * Returns a coin with a given symbol from the database as Flow
     */
    suspend fun getCoin(sym: String): Flow<Coin>

    /**
     * Fetches coin price from the remote source
     */
    suspend fun getCoinPrice(sym: String): Flow<Result<Map<String, Double>>>

    /**
     * Fetches coin daily history from the remote source
     */
    suspend fun getCoinHistory(
        type: HistoryType,
        sym: String,
        limit: String
    ): Flow<List<CoinHistoryDto>?>
}