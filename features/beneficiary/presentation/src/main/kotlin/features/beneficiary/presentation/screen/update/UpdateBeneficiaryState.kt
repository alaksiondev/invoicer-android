package features.beneficiary.presentation.screen.update

internal data class UpdateBeneficiaryState(
    val mode: UpdateBeneficiaryMode = UpdateBeneficiaryMode.Content,
    val name: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val swift: String = "",
    val iban: String = ""
) {
    val isButtonEnabled =
        name.isNotBlank() &&
                bankName.isNotBlank() &&
                bankAddress.isNotBlank() &&
                swift.isNotBlank() &&
                iban.isNotBlank()
}

internal enum class UpdateBeneficiaryMode {
    Loading,
    Content,
    Error
}

internal sealed interface UpdateBeneficiaryEvent {
    data object Success : UpdateBeneficiaryEvent
    data class Error(val message: String) : UpdateBeneficiaryEvent
}
