package com.example.cryptocurrencytracker.model.dto

import com.google.gson.annotations.Expose

class CoinPriceResponse(
    @Expose
    val results: Map<String, Double>?
)