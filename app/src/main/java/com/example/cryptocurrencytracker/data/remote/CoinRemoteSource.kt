package com.example.cryptocurrencytracker.data.remote

import com.example.cryptocurrencytracker.model.Result
import com.example.cryptocurrencytracker.model.dto.AllCoinsResponse
import com.example.cryptocurrencytracker.model.dto.CoinHistoryResponseOuterClass
import com.example.cryptocurrencytracker.network.CryptocompareService
import com.example.cryptocurrencytracker.utils.ErrorUtils
import com.example.cryptocurrencytracker.utils.HistoryType
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class CoinRemoteSource @Inject constructor(private val retrofit: Retrofit) {

    /**
     * Makes an API call to get a list of coins and returns the response as flow
     */
    suspend fun fetchCoins(): Result<AllCoinsResponse> {
        val cryptocompareService = retrofit.create(CryptocompareService::class.java)
        return getResponse(
            request = { cryptocompareService.getAllCoins() },
            defaultErrorMessage = "Error fetching coins list"
        )
    }

    suspend fun fetchCoinPrice(sym: String): Result<Map<String, Double>> {
        val cryptocompareService = retrofit.create(CryptocompareService::class.java)
        return getResponse(
            request = { cryptocompareService.getCoinPrices(sym) },
            defaultErrorMessage = "Error fetching coins list"
        )
    }

    suspend fun fetchCoinHistory(
        type: HistoryType,
        sym: String,
        limit: String
    ): Result<CoinHistoryResponseOuterClass> {
        val cryptocompareService = retrofit.create(CryptocompareService::class.java)
        when (type) {
            HistoryType.DAY -> return getResponse(
                request = { cryptocompareService.getCoinHistoryDay(sym, limit) },
                defaultErrorMessage = "Error fetching coins list"
            )
            HistoryType.HOUR -> return getResponse(
                request = { cryptocompareService.getCoinHistoryHour(sym, limit) },
                defaultErrorMessage = "Error fetching coins list"
            )
            HistoryType.MINUTE -> return getResponse(
                request = { cryptocompareService.getCoinHistoryMinute(sym, limit) },
                defaultErrorMessage = "Error fetching coins list"
            )
        }

    }

    /**
     * Formats the API response
     */
    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}