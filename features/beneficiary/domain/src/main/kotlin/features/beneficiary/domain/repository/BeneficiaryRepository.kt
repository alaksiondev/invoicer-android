package features.beneficiary.domain.repository

import features.beneficiary.domain.model.BeneficiariesModel
import features.beneficiary.domain.model.BeneficiaryModel

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
}