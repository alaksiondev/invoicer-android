package features.intermediary.presentation.screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Subject
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.LoadingState
import features.auth.design.system.components.buttons.BackButton
import features.auth.design.system.components.dialog.DefaultInvoicerDialog
import features.auth.design.system.components.feedback.Feedback
import features.intermediary.presentation.R
import features.intermediary.presentation.screen.details.components.IntermediaryDetailsField
import features.intermediary.presentation.screen.update.UpdateIntermediaryScreen
import foundation.design.system.tokens.Spacing
import foundation.events.EventEffect
import kotlinx.coroutines.launch

internal data class IntermediaryDetailsScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<IntermediaryDetailsScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current
        val snackbarHostState = remember { SnackbarHostState() }
        var showDialog by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        EventEffect(screenModel) {
            when (it) {
                is IntermediaryDetailsEvent.DeleteError -> scope.launch {
                    snackbarHostState.showSnackbar(
                        message = it.message
                    )
                }

                IntermediaryDetailsEvent.DeleteSuccess -> navigator?.pop()
            }
        }

        LaunchedEffect(Unit) { screenModel.initState(id) }

        StateContent(
            state = state,
            onBack = { navigator?.pop() },
            snackbarHost = snackbarHostState,
            onRetry = { screenModel.initState(id) },
            showDeleteDialog = showDialog,
            onConfirmDelete = {
                showDialog = false
                screenModel.deleteBeneficiary(id)
            },
            onDismissDelete = { showDialog = false },
            onRequestDelete = { showDialog = true },
            onRequestEdit = { navigator?.push(UpdateIntermediaryScreen(id)) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: IntermediaryDetailsState,
        onBack: () -> Unit,
        onRetry: () -> Unit,
        snackbarHost: SnackbarHostState,
        showDeleteDialog: Boolean,
        onRequestDelete: () -> Unit,
        onConfirmDelete: () -> Unit,
        onDismissDelete: () -> Unit,
        onRequestEdit: () -> Unit,
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHost)
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.intermediary_details_title))
                    },
                    navigationIcon = {
                        BackButton(onBackClick = onBack)
                    },
                    actions = {
                        if (state.mode == IntermediaryDetailsMode.Content) {
                            IconButton(
                                onClick = onRequestEdit
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                )
                            }

                            IconButton(
                                onClick = onRequestDelete
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                )
            }
        ) {
            when (val mode = state.mode) {
                IntermediaryDetailsMode.Loading -> LoadingState(Modifier.fillMaxSize())

                IntermediaryDetailsMode.Content -> {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(Spacing.medium)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                    ) {
                        IntermediaryDetailsField(
                            label = stringResource(R.string.intermediary_details_name_label),
                            value = state.name,
                            icon = Icons.Default.Business,
                        )

                        IntermediaryDetailsField(
                            label = stringResource(R.string.intermediary_details_bank_name_label),
                            value = state.bankName,
                            icon = Icons.Default.AccountBalance
                        )

                        IntermediaryDetailsField(
                            label = stringResource(R.string.intermediary_details_bank_address_label),
                            value = state.bankAddress,
                            icon = Icons.Default.LocationOn
                        )

                        IntermediaryDetailsField(
                            label = stringResource(R.string.intermediary_details_swift_label),
                            value = state.swift,
                            icon = Icons.AutoMirrored.Default.Subject
                        )

                        IntermediaryDetailsField(
                            label = stringResource(R.string.intermediary_details_iban_label),
                            value = state.iban,
                            icon = Icons.AutoMirrored.Default.Subject
                        )

                        IntermediaryDetailsField(
                            label = stringResource(R.string.intermediary_details_created_at_label),
                            value = state.createdAt,
                            icon = Icons.Default.CalendarMonth
                        )

                        IntermediaryDetailsField(
                            label = stringResource(R.string.intermediary_details_updated_at_label),
                            value = state.updatedAt,
                            icon = Icons.Default.CalendarMonth
                        )
                    }

                    if (showDeleteDialog) {
                        DefaultInvoicerDialog(
                            onDismiss = onDismissDelete,
                            title = stringResource(R.string.intermediary_details_delete_title),
                            description = stringResource(R.string.intermediary_details_delete_description),
                            confirmButtonText = stringResource(R.string.intermediary_details_delete_cta),
                            cancelButtonText = stringResource(R.string.intermediary_details_delete_cancel_cta),
                            confirmButtonClick = onConfirmDelete,
                            icon = Icons.Rounded.WarningAmber
                        )
                    }
                }

                is IntermediaryDetailsMode.Error -> {
                    Feedback(
                        modifier = Modifier.fillMaxSize(),
                        title = stringResource(mode.type.titleResource),
                        description = mode.type.descriptionResource?.let { stringResource(it) },
                        icon = mode.type.icon,
                        primaryActionText = stringResource(mode.type.ctaResource),
                        onPrimaryAction = {
                            when (mode.type) {
                                IntermediaryErrorType.NotFound -> onBack()
                                IntermediaryErrorType.Generic -> onRetry()
                            }
                        }
                    )
                }
            }
        }
    }
}