package io.github.alaksion.invoicer.features.invoice.presentation.fakes

import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiariesModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import kotlinx.datetime.Instant

class FakeBeneficiaryRepository : BeneficiaryRepository {

    var createFails = false
    var deleteFails = false
    var beneficiariesFails = false
    var detailsError: Throwable? = null
    var updateFails = false

    override suspend fun createBeneficiary(
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

    override suspend fun getBeneficiaries(
        page: Long,
        limit: Long
    ): BeneficiariesModel = if (beneficiariesFails) {
        throw IllegalStateException("Failed to get beneficiaries")
    } else {
        DEFAULT_BENEFICIARIES
    }

    override suspend fun getBeneficiaryDetails(id: String): BeneficiaryModel =
        detailsError?.let { throw it } ?: DEFAULT_BENEFICIARY

    override suspend fun deleteBeneficiary(id: String) = if (deleteFails) {
        throw IllegalStateException("Failed to delete beneficiary")
    } else {
        Unit
    }

    override suspend fun updateBeneficiary(
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
        val DEFAULT_BENEFICIARY = BeneficiaryModel(
            name = "John Doe",
            iban = "DE89370400440532013000",
            swift = "DEUTDEDBFRA",
            bankName = "Deutsche Bank",
            bankAddress = "Frankfurt am Main, Germany",
            id = "1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
        )

        val DEFAULT_BENEFICIARIES = BeneficiariesModel(
            itemCount = 1,
            nextPage = null,
            items = listOf(DEFAULT_BENEFICIARY),
        )
    }
}