package com.example.cryptocurrencytracker.network

import com.example.cryptocurrencytracker.model.dto.AllCoinsResponse
import com.example.cryptocurrencytracker.model.dto.CoinHistoryResponseOuterClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptocompareService {
    @GET("all/coinlist")
    suspend fun getAllCoins(): Response<AllCoinsResponse>

    @GET("price")
    suspend fun getCoinPrices(
        @Query("fsym") sym: String,
        @Query("tsyms") tsyms: String = "BTC,ETH,EVN,DOGE,ZEC,USD,EUR"
    ): Response<Map<String, Double>>

    @GET("v2/histoday")
    suspend fun getCoinHistoryDay(
        @Query("fsym") sym: String,
        @Query("tsym") tsym: String = "BTC",
        @Query("limit") limit: String = "10"
    ): Response<CoinHistoryResponseOuterClass>
}