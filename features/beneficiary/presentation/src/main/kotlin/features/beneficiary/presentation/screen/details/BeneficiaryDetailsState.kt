package features.beneficiary.presentation.screen.details

internal data class BeneficiaryDetailsState(
    val name: String = "",
    val iban: String = "",
    val swift: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val mode: BeneficiaryDetailsMode = BeneficiaryDetailsMode.Content
)

internal sealed interface BeneficiaryDetailsMode {
    data object Loading : BeneficiaryDetailsMode
    data object Content : BeneficiaryDetailsMode
    data object Error : BeneficiaryDetailsMode
}
