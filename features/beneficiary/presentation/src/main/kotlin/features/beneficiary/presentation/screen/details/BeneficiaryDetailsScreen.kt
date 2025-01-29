package features.beneficiary.presentation.screen.details

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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.LoadingState
import features.auth.design.system.components.buttons.BackButton
import features.beneficiary.presentation.R
import features.beneficiary.presentation.screen.details.components.BeneficiaryDetailsField
import foundation.design.system.tokens.Spacing

internal data class BeneficiaryDetailsScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<BeneficiaryDetailsScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            screenModel.initState(id)
        }

        StateContent(
            state = state,
            onBack = { navigator?.pop() },
            snackbarHost = snackbarHostState
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: BeneficiaryDetailsState,
        onBack: () -> Unit,
        snackbarHost: SnackbarHostState
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHost)
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.beneficiary_details_title))
                    },
                    navigationIcon = {
                        BackButton(onBackClick = onBack)
                    }
                )
            }
        ) {
            when (state.mode) {
                BeneficiaryDetailsMode.Loading -> LoadingState(Modifier.fillMaxSize())

                BeneficiaryDetailsMode.Content -> {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(Spacing.medium)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                    ) {
                        BeneficiaryDetailsField(
                            label = stringResource(R.string.beneficiary_details_name_label),
                            value = state.name,
                            icon = Icons.Default.Business,
                        )

                        BeneficiaryDetailsField(
                            label = stringResource(R.string.beneficiary_details_bank_name_label),
                            value = state.bankName,
                            icon = Icons.Default.AccountBalance
                        )

                        BeneficiaryDetailsField(
                            label = stringResource(R.string.beneficiary_details_bank_address_label),
                            value = state.bankAddress,
                            icon = Icons.Default.LocationOn
                        )

                        BeneficiaryDetailsField(
                            label = stringResource(R.string.beneficiary_details_swift_label),
                            value = state.swift,
                            icon = Icons.AutoMirrored.Default.Subject
                        )

                        BeneficiaryDetailsField(
                            label = stringResource(R.string.beneficiary_details_iban_label),
                            value = state.iban,
                            icon = Icons.AutoMirrored.Default.Subject
                        )

                        BeneficiaryDetailsField(
                            label = stringResource(R.string.beneficiary_details_created_at_label),
                            value = state.createdAt,
                            icon = Icons.Default.CalendarMonth
                        )

                        BeneficiaryDetailsField(
                            label = stringResource(R.string.beneficiary_details_updated_at_label),
                            value = state.updatedAt,
                            icon = Icons.Default.CalendarMonth
                        )
                    }
                }

                BeneficiaryDetailsMode.Error -> {

                }
            }
        }
    }
}