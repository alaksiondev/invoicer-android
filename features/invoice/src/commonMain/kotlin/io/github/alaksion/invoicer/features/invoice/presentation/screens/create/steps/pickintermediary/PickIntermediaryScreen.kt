package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.SkipNext
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import invoicer.features.invoice.generated.resources.Res
import invoicer.features.invoice.generated.resources.invoice_create_continue_cta
import invoicer.features.invoice.generated.resources.invoice_create_pick_intermediary_add_new
import invoicer.features.invoice.generated.resources.invoice_create_pick_intermediary_ignore
import invoicer.features.invoice.generated.resources.invoice_create_pick_intermediary_retry_cta
import invoicer.features.invoice.generated.resources.invoice_create_pick_intermediary_retry_description
import invoicer.features.invoice.generated.resources.invoice_create_pick_intermediary_retry_title
import invoicer.features.invoice.generated.resources.invoice_create_pick_intermediary_subtitle
import invoicer.features.invoice.generated.resources.invoice_create_pick_intermediary_title
import invoicer.features.invoice.generated.resources.invoice_pick_beneficiary_swift_label
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.components.SelectableItem
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesScreen
import io.github.alaksion.invoicer.foundation.designSystem.components.LoadingState
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.feedback.Feedback
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import io.github.alaksion.invoicer.foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.foundation.ui.events.EventEffect
import io.github.alaksion.invoicer.foundation.watchers.RefreshIntermediaryPublisher
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

internal class PickIntermediaryScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PickIntermediaryScreenModel>()
        val state by screenModel.state.collectAsState()
        val refreshIntermediaryPublisher by remember { getKoin().inject<RefreshIntermediaryPublisher>() }
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) { screenModel.initState() }

        LaunchedEffect(screenModel) {
            screenModel.events.collectLatest {
                when (it) {
                    PickIntermediaryEvents.StartNewIntermediary -> navigator?.push(
                        ScreenRegistry.get(
                            InvoicerScreen.Intermediary.Create
                        )
                    )

                    PickIntermediaryEvents.Continue -> navigator?.push(InvoiceActivitiesScreen())
                }
            }
        }

        EventEffect(refreshIntermediaryPublisher) {
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: PickIntermediaryState,
        onBack: () -> Unit,
        onContinue: () -> Unit,
        onSelect: (IntermediarySelection) -> Unit,
        onRetry: () -> Unit,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        BackButton(onBackClick = onBack)
                    }
                )
            },
            bottomBar = {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    label = stringResource(Res.string.invoice_create_continue_cta),
                    onClick = onContinue,
                    isEnabled = state.buttonEnabled
                )
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
            ) {
                ScreenTitle(
                    title = stringResource(Res.string.invoice_create_pick_intermediary_title),
                    subTitle = stringResource(Res.string.invoice_create_pick_intermediary_subtitle)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                when (state.uiMode) {
                    PickIntermediaryUiMode.Content -> LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                    ) {
                        item(key = "456") {
                            SelectableItem(
                                label = stringResource(Res.string.invoice_create_pick_intermediary_ignore),
                                onSelect = { onSelect(IntermediarySelection.Ignore) },
                                isSelected = state.selection is IntermediarySelection.Ignore,
                                modifier = Modifier.fillMaxWidth(),
                                leading = {
                                    Icon(
                                        imageVector = Icons.Outlined.SkipNext,
                                        contentDescription = null
                                    )
                                }
                            )
                        }


                        item(key = "123") {
                            SelectableItem(
                                label = stringResource(Res.string.invoice_create_pick_intermediary_add_new),
                                onSelect = { onSelect(IntermediarySelection.New) },
                                isSelected = state.selection is IntermediarySelection.New,
                                modifier = Modifier.fillMaxWidth(),
                                leading = {
                                    Icon(
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = null
                                    )
                                }
                            )
                        }

                        items(
                            items = state.intermediaries,
                            key = { it.id }
                        ) { intermediary ->
                            val isSelected = remember(state.selection) {
                                state.selection is IntermediarySelection.Existing &&
                                        state.selection.id == intermediary.id
                            }

                            SelectableItem(
                                label = intermediary.name,
                                subLabel = stringResource(
                                    Res.string.invoice_pick_beneficiary_swift_label,
                                    intermediary.swift
                                ),
                                isSelected = isSelected,
                                onSelect = {
                                    onSelect(
                                        IntermediarySelection.Existing(
                                            intermediary.id
                                        )
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
                        primaryActionText = stringResource(Res.string.invoice_create_pick_intermediary_retry_cta),
                        onPrimaryAction = onRetry,
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.Error,
                        title = stringResource(Res.string.invoice_create_pick_intermediary_retry_title),
                        description = stringResource(Res.string.invoice_create_pick_intermediary_retry_description)
                    )
                }
            }
        }
    }
}
