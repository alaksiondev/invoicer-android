package features.intermediary.domain.repository

import features.intermediary.domain.model.IntermediaryModel

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

    suspend fun deleteBeneficiary(
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