package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update

internal data class UpdateBeneficiaryCallbacks(
    val onBack: () -> Unit,
    val onChangeName: (String) -> Unit,
    val onChangeBankName: (String) -> Unit,
    val onChangeBankAddress: (String) -> Unit,
    val onChangeSwift: (String) -> Unit,
    val onChangeIban: (String) -> Unit,
    val onSubmit: () -> Unit,
    val onRetry: () -> Unit,
)
