package features.beneficiary.domain.model

import kotlinx.datetime.LocalDate

data class BeneficiaryModel(
    val name: String,
    val iban: String,
    val swift: String,
    val bankName: String,
    val bankAddress: String,
    val id: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
)
