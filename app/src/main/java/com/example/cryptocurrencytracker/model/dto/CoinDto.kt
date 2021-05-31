package com.example.cryptocurrencytracker.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDto(
    @SerializedName("Id")
    @Expose
    val id: String,
    @SerializedName("ImageUrl")
    @Expose
    val imageUrl: String?,
    @SerializedName("FullName")
    @Expose
    val fullName: String,
    @SerializedName("Symbol")
    @Expose
    val symbol: String,
    @SerializedName("Description")
    @Expose
    val description: String?,
    @SerializedName("Algorithm")
    @Expose
    val algorithm: String?,
    @SerializedName("ProofType")
    @Expose
    val proofType: String?,
    @SerializedName("SortOrder")
    @Expose
    val sortOrder: String?
)
