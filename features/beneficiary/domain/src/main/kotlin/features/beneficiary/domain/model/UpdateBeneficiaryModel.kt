package features.beneficiary.domain.model

data class UpdateBeneficiaryViewModel(
    val name: String? = null,
    val iban: String? = null,
    val swift: String? = null,
    val bankName: String? = null,
    val bankAddress: String? = null,
)
