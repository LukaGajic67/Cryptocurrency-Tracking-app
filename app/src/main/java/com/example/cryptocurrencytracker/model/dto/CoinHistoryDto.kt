package com.example.cryptocurrencytracker.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CoinHistoryDto(
    @SerializedName("time")
    @Expose
    val time: Long,
    @SerializedName("open")
    @Expose
    val open: Double,
    @SerializedName("close")
    @Expose
    val close: Double
)