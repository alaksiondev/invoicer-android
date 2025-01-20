package features.invoice.presentation.screens.create.steps.activities

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseForm
import features.invoice.presentation.screens.create.steps.activities.components.AddActivityBottomSheet
import features.invoice.presentation.screens.create.steps.activities.components.NewActivityCard
import kotlinx.coroutines.launch

internal class InvoiceActivitiesScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceActivitiesScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        StateContent(
            state = state,
            onChangeDescription = screenModel::updateFormDescription,
            onChangeUnitPrice = screenModel::updateFormUnitPrice,
            onChangeQuantity = screenModel::updateFormQuantity,
            onClearForm = screenModel::clearForm,
            onAddActivity = screenModel::addActivity
        )
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun StateContent(
        state: InvoiceActivitiesState,
        onChangeDescription: (String) -> Unit,
        onChangeUnitPrice: (String) -> Unit,
        onChangeQuantity: (String) -> Unit,
        onClearForm: () -> Unit,
        onAddActivity: () -> Unit,
    ) {
        val sheetState = rememberModalBottomSheetState()
        var showSheet by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()

        CreateInvoiceBaseForm(
            title = stringResource(R.string.invoice_create_activity_title),
            buttonText = stringResource(R.string.invoice_create_continue_cta),
            buttonEnabled = state.continueEnabled,
            onBack = {},
            onContinue = {}
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                stickyHeader {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            showSheet = true
                        }
                    ) {
                        Text(stringResource(R.string.invoice_create_activity_add_cta))
                    }
                }

                items(
                    items = state.activities,
                    key = { it.id }
                ) { activity ->
                    val swipeState = rememberSwipeToDismissBoxState()

                    SwipeToDismissBox(
                        state = swipeState,
                        backgroundContent = {
                            Text("Hello World")
                        }
                    ) {
                        NewActivityCard(
                            quantity = activity.quantity,
                            description = activity.description,
                            unitPrice = activity.unitPrice
                        )
                    }
                }
            }

            if (showSheet) {
                AddActivityBottomSheet(
                    sheetState = sheetState,
                    formState = state.formState,
                    onChangeQuantity = onChangeQuantity,
                    onChangeUnitPrice = onChangeUnitPrice,
                    onChangeDescription = onChangeDescription,
                    onDismiss = {
                        showSheet = false
                        onClearForm()
                    },
                    onAddActivity = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            showSheet = false
                            onAddActivity()
                        }
                    }
                )
            }
        }
    }
}