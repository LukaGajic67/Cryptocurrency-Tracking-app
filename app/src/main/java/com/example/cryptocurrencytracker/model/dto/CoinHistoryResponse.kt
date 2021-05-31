package com.example.cryptocurrencytracker.model.dto

import com.example.cryptocurrencytracker.model.dto.CoinDto
import com.example.cryptocurrencytracker.model.dto.CoinHistoryDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinHistoryResponse(
    @SerializedName("Data")
    @Expose
    val results: List<CoinHistoryDto>
)