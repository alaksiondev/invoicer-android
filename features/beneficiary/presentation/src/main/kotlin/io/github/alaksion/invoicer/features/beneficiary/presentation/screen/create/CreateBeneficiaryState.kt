package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create

internal data class CreateBeneficiaryState(
    val name: String = "",
    val iban: String = "",
    val swift: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val isSubmitting: Boolean = false,
) {
    val nameIsValid = name.isNotBlank()
    val ibanIsValid = iban.isNotBlank()
    val swiftIsValid = swift.isNotBlank()
    val bankInfoIsValid = bankName.isNotBlank() && bankAddress.isNotBlank()

    val formIsValid = nameIsValid && ibanIsValid && swiftIsValid && bankInfoIsValid
}

internal sealed interface CreateBeneficiaryEvents {
    data object Success : CreateBeneficiaryEvents
    data class Error(val message: String) : CreateBeneficiaryEvents
}
