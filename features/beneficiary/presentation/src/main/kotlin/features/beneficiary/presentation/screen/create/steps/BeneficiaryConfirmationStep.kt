package features.beneficiary.presentation.screen.create.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Ballot
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.MapsHomeWork
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.beneficiary.presentation.R
import features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import features.beneficiary.presentation.screen.create.components.BeneficiaryBaseForm
import features.beneficiary.presentation.screen.create.components.BeneficiaryFieldCard

internal class BeneficiaryConfirmationStep : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        StateContent(
            bankAddress = state.bankAddress,
            bankName = state.bankName,
            onBack = { navigator.pop() },
            onContinue = {

            },
            name = state.name,
            swift = state.swift,
            iban = state.iban,
            buttonEnabled = state.isSubmitting.not()
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
    ) {
        val scrollState = rememberScrollState()

        BeneficiaryBaseForm(
            title = stringResource(R.string.create_beneficiary_confirm_title),
            buttonText = stringResource(R.string.create_beneficiary_submit_cta),
            buttonEnabled = buttonEnabled,
            onContinue = onContinue,
            onBack = onBack
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                VerticalSpacer(SpacerSize.Medium)
                BeneficiaryFieldCard(
                    title = stringResource(R.string.confirm_beneficiary_name),
                    value = name,
                    icon = Icons.Outlined.Business
                )
                BeneficiaryFieldCard(
                    title = stringResource(R.string.confirm_beneficiary_swift),
                    value = swift,
                    icon = Icons.Outlined.Ballot
                )
                BeneficiaryFieldCard(
                    title = stringResource(R.string.confirm_beneficiary_iban),
                    value = iban,
                    icon = Icons.Outlined.Ballot
                )
                BeneficiaryFieldCard(
                    title = stringResource(R.string.confirm_beneficiary_bank_name),
                    value = bankName,
                    icon = Icons.Outlined.AccountBalance
                )
                BeneficiaryFieldCard(
                    title = stringResource(R.string.confirm_beneficiary_bank_address),
                    value = bankAddress,
                    icon = Icons.Outlined.MapsHomeWork
                )
            }
        }
    }
}
