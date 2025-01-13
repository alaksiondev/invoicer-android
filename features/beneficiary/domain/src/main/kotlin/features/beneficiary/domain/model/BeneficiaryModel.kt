package features.beneficiary.domain.model

import kotlinx.datetime.Instant

data class BeneficiaryModel(
    val name: String,
    val iban: String,
    val swift: String,
    val bankName: String,
    val bankAddress: String,
    val id: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
