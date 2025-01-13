package features.invoice.presentation.screens.create.steps.sendercompany

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class SenderCompanyState(
    val name: String = "",
    val address: String = ""
) {
    val isFormValid = name.isNotBlank() && address.isNotBlank()
}

internal enum class SenderCompanyEvents {
    Continue
}

internal interface SenderCompanyCallbacks {
    fun onBack()
    fun onSubmit()
    fun updateName(value: String)
    fun updateAddress(value: String)
}

@Composable
internal fun rememberSenderCompanyCallbacks(
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    onUpdateName: (String) -> Unit,
    onUpdateAddress: (String) -> Unit,
) = remember {
    object : SenderCompanyCallbacks {
        override fun onBack() = onBack()

        override fun onSubmit() = onSubmit()

        override fun updateName(value: String) = onUpdateName(value)

        override fun updateAddress(value: String) = onUpdateAddress(value)
    }
}
