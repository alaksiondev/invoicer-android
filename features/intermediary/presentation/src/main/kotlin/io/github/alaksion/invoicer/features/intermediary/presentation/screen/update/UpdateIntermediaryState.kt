package io.github.alaksion.invoicer.features.intermediary.presentation.screen.update

internal data class UpdateIntermediaryState(
    val mode: UpdateIntermediaryMode = UpdateIntermediaryMode.Content,
    val name: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val swift: String = "",
    val iban: String = "",
    val isButtonLoading: Boolean = false,
) {
    val isButtonEnabled =
        name.isNotBlank() &&
                bankName.isNotBlank() &&
                bankAddress.isNotBlank() &&
                swift.isNotBlank() &&
                iban.isNotBlank()
}

internal enum class UpdateIntermediaryMode {
    Loading,
    Content,
    Error
}

internal sealed interface UpdateIntermediaryEvent {
    data object Success : UpdateIntermediaryEvent
    data class Error(val message: String) : UpdateIntermediaryEvent
}
