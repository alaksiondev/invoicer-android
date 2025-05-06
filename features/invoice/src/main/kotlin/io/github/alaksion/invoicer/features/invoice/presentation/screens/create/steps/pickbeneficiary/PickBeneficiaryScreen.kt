package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import foundation.designsystem.components.LoadingState
import foundation.designsystem.components.ScreenTitle
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.feedback.Feedback
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing
import foundation.navigation.InvoicerScreen
import foundation.ui.events.EventEffect
import foundation.watchers.RefreshBeneficiaryPublisher
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary.PickIntermediaryScreen
import io.github.alasion.invoicer.features.invoice.R
import org.koin.mp.KoinPlatform.getKoin

internal class PickBeneficiaryScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PickBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val refreshBeneficiaryPublisher by remember { getKoin().inject<RefreshBeneficiaryPublisher>() }
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) { screenModel.initState() }

        EventEffect(screenModel) {
            when (it) {
                PickBeneficiaryEvents.StartNewBeneficiary -> navigator?.push(
                    ScreenRegistry.get(
                        InvoicerScreen.Beneficiary.Create
                    )
                )

                PickBeneficiaryEvents.Continue -> navigator?.push(PickIntermediaryScreen())
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
                    label = stringResource(R.string.invoice_create_continue_cta),
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
                    title = stringResource(R.string.invoice_create_pick_beneficiary_title),
                    subTitle = stringResource(R.string.invoice_create_pick_beneficiary_subtitle)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                when (state.uiMode) {
                    PickBeneficiaryUiMode.Content -> LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
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
                                        stringResource(R.string.invoice_create_pick_beneficiary_add_new)
                                    )
                                },
                                trailingContent = {
                                    RadioButton(
                                        selected = state.selection is BeneficiarySelection.New,
                                        onClick = { onSelect(BeneficiarySelection.New) }
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
                                        state.selection is BeneficiarySelection.Existing &&
                                                state.selection.id == beneficiary.id
                                    }

                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            onSelect(
                                                BeneficiarySelection.Existing(
                                                    beneficiary.id
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    }

                    PickBeneficiaryUiMode.Error -> Feedback(
                        primaryActionText = stringResource(R.string.invoice_create_pick_beneficiary_retry_cta),
                        onPrimaryAction = onRetry,
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.Error,
                        title = stringResource(R.string.invoice_create_pick_beneficiary_retry_title),
                        description = stringResource(R.string.invoice_create_pick_beneficiary_retry_description)
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