package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary

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
import invoicer.features.invoice.generated.resources.invoice_create_pick_beneficiary_add_new
import invoicer.features.invoice.generated.resources.invoice_create_pick_beneficiary_retry_cta
import invoicer.features.invoice.generated.resources.invoice_create_pick_beneficiary_retry_description
import invoicer.features.invoice.generated.resources.invoice_create_pick_beneficiary_retry_title
import invoicer.features.invoice.generated.resources.invoice_create_pick_beneficiary_subtitle
import invoicer.features.invoice.generated.resources.invoice_create_pick_beneficiary_title
import invoicer.features.invoice.generated.resources.invoice_pick_beneficiary_swift_label
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.components.SelectableItem
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary.PickIntermediaryScreen
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
import io.github.alaksion.invoicer.foundation.watchers.RefreshBeneficiaryPublisher
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

internal class PickBeneficiaryScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PickBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsState()
        val refreshBeneficiaryPublisher by remember { getKoin().inject<RefreshBeneficiaryPublisher>() }
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) { screenModel.initState() }

        LaunchedEffect(screenModel) {
            screenModel.events.collectLatest {
                when (it) {
                    PickBeneficiaryEvents.StartNewBeneficiary -> navigator?.push(
                        ScreenRegistry.get(
                            InvoicerScreen.Beneficiary.Create
                        )
                    )

                    PickBeneficiaryEvents.Continue -> navigator?.push(PickIntermediaryScreen())
                }
            }
        }

        EventEffect(refreshBeneficiaryPublisher) {
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
        state: PickBeneficiaryState,
        onBack: () -> Unit,
        onContinue: () -> Unit,
        onSelect: (BeneficiarySelection) -> Unit,
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
                    title = stringResource(Res.string.invoice_create_pick_beneficiary_title),
                    subTitle = stringResource(Res.string.invoice_create_pick_beneficiary_subtitle)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                when (state.uiMode) {
                    PickBeneficiaryUiMode.Content -> LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                    ) {
                        item(key = "123") {
                            SelectableItem(
                                label = stringResource(Res.string.invoice_create_pick_beneficiary_add_new),
                                isSelected = state.selection is BeneficiarySelection.New,
                                onSelect = {
                                    onSelect(BeneficiarySelection.New)
                                },
                                leading = {
                                    Icon(
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = null
                                    )
                                }
                            )
                        }

                        items(
                            items = state.beneficiaries,
                            key = { it.id }
                        ) { beneficiary ->
                            val isSelected = remember(state.selection) {
                                state.selection is BeneficiarySelection.Existing &&
                                        state.selection.id == beneficiary.id
                            }

                            SelectableItem(
                                label = beneficiary.name,
                                subLabel = stringResource(
                                    Res.string.invoice_pick_beneficiary_swift_label,
                                    beneficiary.swift
                                ),
                                isSelected = isSelected,
                                onSelect = {
                                    onSelect(
                                        BeneficiarySelection.Existing(
                                            beneficiary.id
                                        )
                                    )
                                }
                            )
                        }
                    }

                    PickBeneficiaryUiMode.Error -> Feedback(
                        primaryActionText = stringResource(Res.string.invoice_create_pick_beneficiary_retry_cta),
                        onPrimaryAction = onRetry,
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.Error,
                        title = stringResource(Res.string.invoice_create_pick_beneficiary_retry_title),
                        description = stringResource(Res.string.invoice_create_pick_beneficiary_retry_description)
                    )

                    PickBeneficiaryUiMode.Loading -> LoadingState(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
