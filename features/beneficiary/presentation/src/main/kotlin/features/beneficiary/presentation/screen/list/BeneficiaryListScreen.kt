package features.beneficiary.presentation.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.buttons.CloseButton
import features.auth.design.system.components.feedback.Feedback
import features.beneficiary.presentation.R
import features.beneficiary.presentation.screen.list.components.BeneficiaryList
import foundation.design.system.tokens.Spacing
import foundation.navigation.InvoicerScreen

internal class BeneficiaryListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<BeneficiaryScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) { viewModel.loadPage() }

        StateContent(
            onClose = { navigator?.pop() },
            onRetry = { viewModel.loadPage() },
            state = state,
            onAddClick = {
                navigator?.push(ScreenRegistry.get(InvoicerScreen.Beneficiary.Create))
            },
            onItemClick = {}
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: BeneficiaryListState,
        onClose: () -> Unit,
        onRetry: () -> Unit,
        onAddClick: () -> Unit,
        onItemClick: (String) -> Unit,
    ) {
        val listState = rememberLazyListState()

        Scaffold(
            topBar = {
                MediumTopAppBar(
                    title = { Text(stringResource(R.string.beneficiary_list_title)) },
                    navigationIcon = { CloseButton(onBackClick = onClose) },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null
                    )
                }
            }
        ) {
            when (state.mode) {
                BeneficiaryListMode.Loading -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                BeneficiaryListMode.Content -> BeneficiaryList(
                    items = state.beneficiaries,
                    listState = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    onItemClick = onItemClick
                )

                BeneficiaryListMode.Error -> Feedback(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(Spacing.medium),
                    primaryActionText = stringResource(R.string.beneficiary_list_error_retry),
                    onPrimaryAction = onRetry,
                    icon = Icons.Outlined.ErrorOutline,
                    title = stringResource(R.string.beneficiary_list_error_title),
                    description = stringResource(R.string.beneficiary_list_error_description)
                )
            }
        }
    }
}