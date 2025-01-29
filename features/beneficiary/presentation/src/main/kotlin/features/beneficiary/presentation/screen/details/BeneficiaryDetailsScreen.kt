package features.beneficiary.presentation.screen.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

internal data class BeneficiaryDetailsScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<BeneficiaryDetailsScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            screenModel.initState(id)
        }

        Text(
            text = state.name
        )
    }
}