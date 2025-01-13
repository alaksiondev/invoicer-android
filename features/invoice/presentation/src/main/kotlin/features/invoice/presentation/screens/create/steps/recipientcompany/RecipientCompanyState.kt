package features.invoice.presentation.screens.create.steps.recipientcompany

internal data class RecipientCompanyState(
    val name: String = "",
    val address: String = ""
) {
    val isFormValid = name.isNotBlank() && address.isNotBlank()
}

internal enum class RecipientCompanyEvents {
    Continue
}