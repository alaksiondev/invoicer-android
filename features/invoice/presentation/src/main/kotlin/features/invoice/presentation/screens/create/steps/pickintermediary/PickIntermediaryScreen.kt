package features.invoice.presentation.screens.create.steps.pickintermediary

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.LoadingState
import features.auth.design.system.components.feedback.Feedback
import features.intermediary.publisher.NewIntermediaryPublisher
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseForm
import features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesScreen
import foundation.events.EventEffect
import foundation.navigation.InvoicerScreen
import org.koin.mp.KoinPlatform.getKoin

internal class PickIntermediaryScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PickIntermediaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val newIntermediaryPublisher by remember { getKoin().inject<NewIntermediaryPublisher>() }
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) { screenModel.initState() }

        EventEffect(screenModel) {
            when (it) {
                PickIntermediaryEvents.StartNewIntermediary -> navigator?.push(
                    ScreenRegistry.get(
                        InvoicerScreen.Beneficiary.Create
                    )
                )

                PickIntermediaryEvents.Continue -> navigator?.push(InvoiceActivitiesScreen())
            }
        }

        EventEffect(newIntermediaryPublisher) {
            screenModel.initState(force = true)
        }

        StateContent(
            state = state,
            onBack = { navigator?.pop() },
            onContinue = screenModel::submit,
            onRetry = { screenModel.initState(force = true) },
            onSelect = screenModel::updateSelection
        )
    }

    @Composable
    fun StateContent(
        state: PickIntermediaryState,
        onBack: () -> Unit,
        onContinue: () -> Unit,
        onSelect: (IntermediarySelection) -> Unit,
        onRetry: () -> Unit,
    ) {
        CreateInvoiceBaseForm(
            title = stringResource(R.string.invoice_create_pick_intermediary_title),
            onBack = onBack,
            onContinue = onContinue,
            buttonEnabled = state.buttonEnabled,
            buttonText = stringResource(R.string.invoice_create_continue_cta)
        ) {
            when (state.uiMode) {
                PickIntermediaryUiMode.Content -> LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item(key = "456") {
                        ListItem(
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Outlined.SkipNext,
                                    contentDescription = null
                                )
                            },
                            headlineContent = {
                                Text(
                                    stringResource(R.string.invoice_create_pick_intermediary_ignore)
                                )
                            },
                            trailingContent = {
                                RadioButton(
                                    selected = state.selection is IntermediarySelection.Ignore,
                                    onClick = { onSelect(IntermediarySelection.Ignore) }
                                )
                            }
                        )
                    }


                    item(key = "123") {
                        ListItem(
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = null
                                )
                            },
                            headlineContent = {
                                Text(
                                    stringResource(R.string.invoice_create_pick_intermediary_add_new)
                                )
                            },
                            trailingContent = {
                                RadioButton(
                                    selected = state.selection is IntermediarySelection.New,
                                    onClick = { onSelect(IntermediarySelection.New) }
                                )
                            }
                        )
                    }

                    items(
                        items = state.beneficiaries,
                        key = { it.id }
                    ) { beneficiary ->
                        ListItem(
                            modifier = Modifier.fillMaxWidth(),
                            headlineContent = {
                                Text(
                                    text = beneficiary.name
                                )
                            },
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Outlined.Business,
                                    contentDescription = null
                                )
                            },
                            trailingContent = {
                                val isSelected = remember(state.selection) {
                                    state.selection is IntermediarySelection.Existing &&
                                            state.selection.id == beneficiary.id
                                }

                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        onSelect(
                                            IntermediarySelection.Existing(
                                                beneficiary.id
                                            )
                                        )
                                    }
                                )
                            }
                        )
                    }
                }

                PickIntermediaryUiMode.Loading -> LoadingState(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                PickIntermediaryUiMode.Error -> Feedback(
                    primaryActionText = stringResource(R.string.invoice_create_pick_intermediary_retry_cta),
                    onPrimaryAction = onRetry,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Error,
                    title = stringResource(R.string.invoice_create_pick_intermediary_retry_title),
                    description = stringResource(R.string.invoice_create_pick_intermediary_retry_description)
                )
            }

        }
    }
}