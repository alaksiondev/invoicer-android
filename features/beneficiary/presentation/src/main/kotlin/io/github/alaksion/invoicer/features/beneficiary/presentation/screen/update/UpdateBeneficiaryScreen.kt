package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component3
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component4
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component5
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.designsystem.components.InputField
import foundation.designsystem.components.LoadingState
import foundation.designsystem.components.ScreenTitle
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.feedback.Feedback
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.beneficiary.presentation.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal data class UpdateBeneficiaryScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<UpdateBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) { screenModel.initState(id) }

        LaunchedEffect(screenModel) {
            screenModel.events.collectLatest {
                when (it) {
                    is UpdateBeneficiaryEvent.Error ->
                        scope.launch { snackbarHostState.showSnackbar(it.message) }

                    UpdateBeneficiaryEvent.Success -> navigator?.pop()
                }
            }
        }

        StateContent(
            state = state,
            onBack = { navigator?.pop() },
            onChangeName = screenModel::updateName,
            onChangeBankName = screenModel::updateBankName,
            onChangeBankAddress = screenModel::updateBankAddress,
            onChangeSwift = screenModel::updateSwift,
            onChangeIban = screenModel::updateIban,
            onSubmit = {
                screenModel.submit(id)
            },
            snackbarHostState = snackbarHostState,
            onRetry = { screenModel.initState(id) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: UpdateBeneficiaryState,
        snackbarHostState: SnackbarHostState,
        onBack: () -> Unit,
        onChangeName: (String) -> Unit,
        onChangeBankName: (String) -> Unit,
        onChangeBankAddress: (String) -> Unit,
        onChangeSwift: (String) -> Unit,
        onChangeIban: (String) -> Unit,
        onSubmit: () -> Unit,
        onRetry: () -> Unit,
    ) {
        val scrollState = rememberScrollState()

        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    navigationIcon = { BackButton(onBackClick = onBack) },
                    title = { }
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            },
            bottomBar = {
                PrimaryButton(
                    onClick = onSubmit,
                    isEnabled = state.isButtonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    label = stringResource(R.string.beneficiary_update_cta),
                    isLoading = state.isButtonLoading
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(Spacing.medium),
            ) {
                when (state.mode) {
                    UpdateBeneficiaryMode.Loading -> LoadingState(
                        modifier = Modifier.fillMaxSize()
                    )

                    UpdateBeneficiaryMode.Content -> {
                        val (
                            nameRef, bankNameRef, bankAddressRef, swiftRef, ibanRef
                        ) = FocusRequester.createRefs()

                        val keyboard = LocalSoftwareKeyboardController.current
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                        ) {
                            ScreenTitle(
                                title = stringResource(R.string.update_beneficiary_title),
                                subTitle = null
                            )
                            VerticalSpacer(SpacerSize.XLarge3)

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(nameRef),
                                value = state.name,
                                onValueChange = onChangeName,
                                label = {
                                    Text(
                                        text = stringResource(R.string.beneficiary_update_name_label)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { bankNameRef.requestFocus() }
                                ),
                                maxLines = 1
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(bankNameRef),
                                value = state.bankName,
                                onValueChange = onChangeBankName,
                                label = {
                                    Text(
                                        text = stringResource(R.string.beneficiary_update_bank_name_label)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { bankAddressRef.requestFocus() }
                                ),
                                maxLines = 1
                            )

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(bankAddressRef),
                                value = state.bankAddress,
                                onValueChange = onChangeBankAddress,
                                label = {
                                    Text(
                                        text = stringResource(R.string.beneficiary_update_bank_address_label)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { swiftRef.requestFocus() }
                                ),
                                maxLines = 1
                            )

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(swiftRef),
                                value = state.swift,
                                onValueChange = onChangeSwift,
                                label = {
                                    Text(
                                        text = stringResource(R.string.beneficiary_update_swift_label)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { ibanRef.requestFocus() }
                                ),
                                maxLines = 1
                            )

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(ibanRef),
                                value = state.iban,
                                onValueChange = onChangeIban,
                                label = {
                                    Text(
                                        text = stringResource(R.string.beneficiary_update_iban_label)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { keyboard?.hide() }
                                ),
                                maxLines = 1
                            )
                        }
                    }

                    UpdateBeneficiaryMode.Error -> Feedback(
                        title = stringResource(R.string.beneficiary_update_load_error_title),
                        description = stringResource(R.string.beneficiary_update_load_error_description),
                        primaryActionText = stringResource(R.string.beneficiary_update_load_error_cta),
                        onPrimaryAction = onRetry,
                        modifier = Modifier.fillMaxSize(),
                        icon = Icons.Outlined.ErrorOutline
                    )
                }
            }
        }
    }
}