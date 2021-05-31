package com.example.cryptocurrencytracker.model.dto

import com.example.cryptocurrencytracker.model.dto.CoinDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AllCoinsResponse(
    @SerializedName("Data")
    @Expose
    val results: Map<String, CoinDto>
)