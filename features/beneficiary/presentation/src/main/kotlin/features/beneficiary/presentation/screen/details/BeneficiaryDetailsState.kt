package features.beneficiary.presentation.screen.details

internal data class BeneficiaryDetailsState(
    val name: String = "",
    val iban: String = "",
    val swift: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
)
