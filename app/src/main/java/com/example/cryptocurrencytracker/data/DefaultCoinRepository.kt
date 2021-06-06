package com.example.cryptocurrencytracker.data

import com.example.cryptocurrencytracker.data.local.CoinDao
import com.example.cryptocurrencytracker.data.remote.CoinRemoteSource
import com.example.cryptocurrencytracker.model.Coin
import com.example.cryptocurrencytracker.model.Result
import com.example.cryptocurrencytracker.model.dto.CoinHistoryDto
import com.example.cryptocurrencytracker.utils.CoinMapper
import com.example.cryptocurrencytracker.utils.HistoryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultCoinRepository @Inject constructor(
    private val coinDao: CoinDao,
    private val coinRemoteSource: CoinRemoteSource
) : CoinRepository {

    override suspend fun deleteAllCoins() {
        coinDao.deleteAll()
    }

    override suspend fun insertAllCoins(coins: List<Coin>) {
        coinDao.insertAll(coins)
    }

    override suspend fun getCoin(sym: String): Flow<Coin> {
        return coinDao.getCoin(sym).flowOn(Dispatchers.IO)
    }

    override suspend fun getCoinPrice(sym: String): Flow<Result<Map<String, Double>>> {
        return flow {
            emit(Result.loading(null))
            emit(coinRemoteSource.fetchCoinPrice(sym))
        }.flowOn(Dispatchers.IO).conflate()
    }

    override suspend fun getCoinHistory(
        type: HistoryType,
        sym: String,
        limit: String
    ): Flow<List<CoinHistoryDto>?> {
        return flow {
            val result = coinRemoteSource.fetchCoinHistory(type, sym, limit)
            if (result.status == Result.Status.SUCCESS) {
                emit(result.data?.results?.results)
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getAllCoins(): Flow<List<Coin>> {
        refreshDB()
        return coinDao.getAllFlow().flowOn(Dispatchers.IO).conflate()
    }

    private suspend fun refreshDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = coinRemoteSource.fetchCoins()
            if (result.status == Result.Status.SUCCESS) {
                val coinsDto = result.data?.results
                val coins = mutableListOf<Coin>()
                coinsDto?.let {
                    for (coinDto in it)
                        coins.add(CoinMapper.mapToEntity(coinDto.value))
                }
                deleteAllCoins()
                insertAllCoins(coins)
            }
        }
    }
}