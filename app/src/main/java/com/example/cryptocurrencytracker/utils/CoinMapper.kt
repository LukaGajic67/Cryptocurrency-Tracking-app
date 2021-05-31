package com.example.cryptocurrencytracker.utils

import com.example.cryptocurrencytracker.model.Coin
import com.example.cryptocurrencytracker.model.dto.CoinDto

object CoinMapper : DomainEntityMapper<CoinDto, Coin> {
    override fun mapToEntity(coinDto: CoinDto): Coin = Coin(
        coinDto.id,
        coinDto.imageUrl,
        coinDto.fullName,
        coinDto.symbol,
        coinDto.description,
        coinDto.algorithm,
        coinDto.proofType,
        coinDto.sortOrder?.toInt()
    )

    override fun mapToDomain(coin: Coin): CoinDto = CoinDto(
        coin.id,
        coin.imageUrl,
        coin.fullName,
        coin.symbol,
        coin.description,
        coin.algorithm,
        coin.proofType,
        coin.sortOrder.toString()
    )
}