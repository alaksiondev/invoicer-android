package io.github.alaksion.invoicer.features.intermediary.services.domain.repository

import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel

interface IntermediaryRepository {

    suspend fun createIntermediary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String,
    )

    suspend fun getIntermediaries(
        page: Long,
        limit: Long
    ): List<IntermediaryModel>

    suspend fun getIntermediaryDetails(
        id: String
    ): IntermediaryModel

    suspend fun deleteIntermediary(
        id: String
    )

    suspend fun updateIntermediary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String,
    )
}