package features.invoice.presentation.screens.create.steps.pickbeneficiary

import features.beneficiary.domain.model.BeneficiaryModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class PickBeneficiaryState(
    val beneficiaries: ImmutableList<BeneficiaryModel> = persistentListOf(),
    val uiMode: PickBeneficiaryUiMode = PickBeneficiaryUiMode.Content,
    val selection: BeneficiarySelection = BeneficiarySelection.None
) {
    val buttonEnabled: Boolean = selection != BeneficiarySelection.None
}

internal enum class PickBeneficiaryUiMode {
    Content,
    Error,
    Loading,
}

internal enum class PickBeneficiaryEvents {
    StartNewBeneficiary,
    Continue
}

internal sealed interface BeneficiarySelection {
    data object New : BeneficiarySelection
    data object None : BeneficiarySelection
    data class Existing(val id: String) : BeneficiarySelection
}