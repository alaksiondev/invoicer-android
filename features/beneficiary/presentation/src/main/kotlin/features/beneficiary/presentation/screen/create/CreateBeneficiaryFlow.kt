package features.beneficiary.presentation.screen.create

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import features.beneficiary.presentation.screen.create.steps.BeneficiaryNameStep

internal class CreateBeneficiaryFlow : Screen {

    @Composable
    override fun Content() {
        Navigator(
            BeneficiaryNameStep()
        )
    }
}