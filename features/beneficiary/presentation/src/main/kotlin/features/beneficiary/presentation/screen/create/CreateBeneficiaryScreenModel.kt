package features.beneficiary.presentation.screen.create

import cafe.adriel.voyager.core.model.ScreenModel
import features.beneficiary.domain.repository.BeneficiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class CreateBeneficiaryScreenModel(
    private val beneficiaryRepository: BeneficiaryRepository
) : ScreenModel {

    private val _state = MutableStateFlow(CreateBeneficiaryState())
    val state: StateFlow<CreateBeneficiaryState> = _state

    fun updateName(value: String) {
        _state.update { oldState ->
            oldState.copy(name = value)
        }
    }

    fun updateSwift(value: String) {
        _state.update { oldState ->
            oldState.copy(swift = value)
        }
    }

    fun updateIban(value: String) {
        _state.update { oldState ->
            oldState.copy(iban = value)
        }
    }

    fun updateBankAddress(value: String) {
        _state.update { oldState ->
            oldState.copy(bankAddress = value)
        }
    }

    fun updateBankName(value: String) {
        _state.update { oldState ->
            oldState.copy(bankName = value)
        }
    }

}