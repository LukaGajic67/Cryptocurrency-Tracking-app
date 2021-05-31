package com.example.cryptocurrencytracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coin(
    @PrimaryKey
    val id: String,
    val imageUrl: String?,
    val fullName: String,
    val symbol: String,
    val description: String?,
    val algorithm: String?,
    val proofType: String?,
    val sortOrder: Int?
)