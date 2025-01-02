package features.beneficiary.presentation.screen.list

import features.beneficiary.domain.model.BeneficiaryModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class BeneficiaryListState(
    val isNextPageLoading: Boolean = false,
    val mode: BeneficiaryListMode = BeneficiaryListMode.Loading,
    val beneficiaries: ImmutableList<BeneficiaryModel> = persistentListOf()
)

enum class BeneficiaryListMode {
    Loading,
    Content,
    Error,
}

enum class BeneficiaryListEvents {
    LoadMoreError
}