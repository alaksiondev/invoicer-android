package io.github.alaksion.invoicer.features.intermediary.presentation.fakes

import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import kotlinx.datetime.Instant

class FakeIntermediaryRepository : IntermediaryRepository {

    var createFails = false
    var deleteFails = false
    var listFails = false
    var detailsError: Throwable? = null
    var updateFails = false

    override suspend fun createIntermediary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String
    ) = if (createFails) {
        throw IllegalStateException("Failed to create beneficiary")
    } else {
        Unit
    }

    override suspend fun getIntermediaries(
        page: Long,
        limit: Long
    ): List<IntermediaryModel> = if (listFails) {
        throw IllegalStateException("Failed to get beneficiaries")
    } else {
        DEFAULT_INTERMEDIARIES
    }

    override suspend fun getIntermediaryDetails(id: String): IntermediaryModel =
        detailsError?.let { throw it } ?: DEFAULT_INTERMEDIARY

    override suspend fun deleteIntermediary(id: String) = if (deleteFails) {
        throw IllegalStateException("Failed to delete beneficiary")
    } else {
        Unit
    }

    override suspend fun updateIntermediary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String
    ) = if (updateFails) {
        throw IllegalStateException("Failed to update beneficiary")
    } else {
        Unit
    }

    companion object {
        val DEFAULT_INTERMEDIARY = IntermediaryModel(
            name = "John Doe",
            iban = "DE89370400440532013000",
            swift = "DEUTDEDBFRA",
            bankName = "Deutsche Bank",
            bankAddress = "Frankfurt am Main, Germany",
            id = "1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
        )

        val DEFAULT_INTERMEDIARIES = listOf(DEFAULT_INTERMEDIARY)
    }
}