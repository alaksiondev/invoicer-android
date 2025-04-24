package io.github.alaksion.invoicer.features.beneficiary.services.domain.repository

import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiariesModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel

interface BeneficiaryRepository {
    suspend fun createBeneficiary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String,
    )

    suspend fun getBeneficiaries(
        page: Long,
        limit: Long
    ): BeneficiariesModel

    suspend fun getBeneficiaryDetails(
        id: String
    ): BeneficiaryModel

    suspend fun deleteBeneficiary(
        id: String
    )

    suspend fun updateBeneficiary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String,
    )
}