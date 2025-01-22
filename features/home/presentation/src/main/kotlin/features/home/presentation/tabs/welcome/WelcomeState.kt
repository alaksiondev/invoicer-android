package features.home.presentation.tabs.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class WelcomeCallbacks(
    val onInvoiceClick: () -> Unit,
    val onBeneficiaryClick: () -> Unit,
    val onIntermediaryClick: () -> Unit,
)

@Composable
internal fun rememberWelcomeCallbacks(
    onInvoiceClick: () -> Unit,
    onBeneficiaryClick: () -> Unit,
    onIntermediaryClick: () -> Unit,
): WelcomeCallbacks {
    return remember {
        WelcomeCallbacks(
            onInvoiceClick = onInvoiceClick,
            onBeneficiaryClick = onBeneficiaryClick,
            onIntermediaryClick = onIntermediaryClick
        )
    }
}