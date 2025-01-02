package features.beneficiary.presentation.screen.list

import cafe.adriel.voyager.core.model.ScreenModel
import features.beneficiary.domain.repository.BeneficiaryRepository

internal class BeneficiaryListViewModel(
    private val beneficiaryRepository: BeneficiaryRepository
) : ScreenModel {
}