package com.example.cryptocurrencytracker.utils

interface DomainEntityMapper<DomainModel, Entity> {
    fun mapToEntity(domainModel: DomainModel): Entity
    fun mapToDomain(entity: Entity): DomainModel
}