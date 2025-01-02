package features.beneficiary.presentation.screen.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.buttons.CloseButton
import features.beneficiary.presentation.R

internal class BeneficiaryListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        StateContent(
            onClose = { navigator?.pop() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        onClose: () -> Unit
    ) {
        Scaffold(
            topBar = {
                MediumTopAppBar(
                    title = { Text(stringResource(R.string.beneficiary_list_title)) },
                    navigationIcon = { CloseButton(onBackClick = onClose) },
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) { }
        }
    }

}