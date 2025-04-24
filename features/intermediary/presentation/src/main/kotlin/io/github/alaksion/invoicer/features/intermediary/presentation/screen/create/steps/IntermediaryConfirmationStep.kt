package io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Ballot
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.MapsHomeWork
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.features.intermediary.presentation.R
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryEvents
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryScreenModel
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.components.IntermediaryBaseForm
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.components.IntermediaryFieldCard
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.feedback.IntermediaryFeedbackScreen
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.feedback.IntermediaryFeedbackType
import kotlinx.coroutines.launch

internal class IntermediaryConfirmationStep : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateIntermediaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope(

        )
        val snackbarHostState = remember {
            SnackbarHostState()
        }

        foundation.ui.events.EventEffect(
            publisher = screenModel
        ) {
            when (it) {
                is CreateIntermediaryEvents.Error -> scope.launch {
                    snackbarHostState.showSnackbar(message = it.message)
                }

                CreateIntermediaryEvents.Success -> navigator.push(
                    IntermediaryFeedbackScreen(
                        type = IntermediaryFeedbackType.CreateSuccess
                    )
                )
            }
        }

        StateContent(
            bankAddress = state.bankAddress,
            bankName = state.bankName,
            onBack = navigator::pop,
            onContinue = screenModel::submit,
            name = state.name,
            swift = state.swift,
            iban = state.iban,
            buttonEnabled = state.isSubmitting.not(),
            snackbarHostState = snackbarHostState
        )
    }

    @Composable
    fun StateContent(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String,
        buttonEnabled: Boolean,
        onContinue: () -> Unit,
        onBack: () -> Unit,
        snackbarHostState: SnackbarHostState,
    ) {
        val scrollState = rememberScrollState()

        IntermediaryBaseForm(
            title = stringResource(R.string.create_intermediary_confirm_title),
            buttonText = stringResource(R.string.create_intermediary_submit_cta),
            buttonEnabled = buttonEnabled,
            onContinue = onContinue,
            onBack = onBack,
            snackbarHostState = snackbarHostState
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                VerticalSpacer(SpacerSize.Medium)
                IntermediaryFieldCard(
                    title = stringResource(R.string.confirm_intermediary_name),
                    value = name,
                    icon = Icons.Outlined.Business
                )
                IntermediaryFieldCard(
                    title = stringResource(R.string.confirm_intermediary_swift),
                    value = swift,
                    icon = Icons.Outlined.Ballot
                )
                IntermediaryFieldCard(
                    title = stringResource(R.string.confirm_intermediary_iban),
                    value = iban,
                    icon = Icons.Outlined.Ballot
                )
                IntermediaryFieldCard(
                    title = stringResource(R.string.confirm_intermediary_bank_name),
                    value = bankName,
                    icon = Icons.Outlined.AccountBalance
                )
                IntermediaryFieldCard(
                    title = stringResource(R.string.confirm_intermediary_bank_address),
                    value = bankAddress,
                    icon = Icons.Outlined.MapsHomeWork
                )
            }
        }
    }
}
