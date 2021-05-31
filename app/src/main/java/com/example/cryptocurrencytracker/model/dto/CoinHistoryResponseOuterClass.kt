package com.example.cryptocurrencytracker.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinHistoryResponseOuterClass(
    @SerializedName("Data")
    @Expose
    val results: CoinHistoryResponse
)