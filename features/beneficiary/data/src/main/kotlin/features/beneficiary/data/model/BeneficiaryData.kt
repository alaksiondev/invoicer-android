package features.beneficiary.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
internal data class BeneficiaryData(
    val name: String,
    val iban: String,
    val swift: String,
    val bankName: String,
    val bankAddress: String,
    val id: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate
)
