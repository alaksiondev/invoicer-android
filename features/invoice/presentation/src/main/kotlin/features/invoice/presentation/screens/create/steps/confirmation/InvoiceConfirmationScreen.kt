package features.invoice.presentation.screens.create.steps.confirmation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseForm

internal class InvoiceConfirmationScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceConfirmationScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val snackbarHostState = remember { SnackbarHostState() }

        StateContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onBack = {},
            onSubmit = screenModel::submitInvoice
        )
    }

    @Composable
    fun StateContent(
        state: InvoiceConfirmationState,
        snackbarHostState: SnackbarHostState,
        onSubmit: () -> Unit,
        onBack: () -> Unit,
    ) {
        CreateInvoiceBaseForm(
            title = stringResource(R.string.invoice_create_confirmation_title),
            snackbarState = snackbarHostState,
            buttonText = stringResource(R.string.invoice_create_confirmation_continue_cta),
            buttonEnabled = state.isLoading.not(),
            onBack = onBack,
            onContinue = onSubmit
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

            }
        }
    }
}