package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.steps.BeneficiaryNameStep
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.feedback.BeneficiaryFeedbackScreen

internal class CreateBeneficiaryFlow : Screen {

    @Composable
    override fun Content() {
        Navigator(
            screen = BeneficiaryNameStep(),
            onBackPressed = {
                it.key != BeneficiaryFeedbackScreen.ScreenKey
            }
        ) {
            SlideTransition(it)
        }
    }
}
