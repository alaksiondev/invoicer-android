package features.intermediary.presentation.screen.create

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import features.intermediary.presentation.screen.create.steps.IntermediaryNameStep

internal class CreateIntermediaryFlow : Screen {

    @Composable
    override fun Content() {
        Navigator(IntermediaryNameStep()) { SlideTransition(it) }
    }
}