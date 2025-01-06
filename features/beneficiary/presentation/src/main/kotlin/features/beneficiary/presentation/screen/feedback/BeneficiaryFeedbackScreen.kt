package features.beneficiary.presentation.screen.feedback

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.feedback.Feedback
import features.beneficiary.presentation.R
import foundation.design.system.tokens.Spacing

internal enum class BeneficiaryFeedbackType(
    @StringRes val primaryActionText: Int,
    @StringRes val description: Int,
    @StringRes val title: Int,
    val icon: ImageVector

) {
    CreateSuccess(
        primaryActionText = R.string.create_success_cta,
        description = R.string.create_success_description,
        title = R.string.create_success_title,
        icon = Icons.Outlined.Check
    )
}

internal class BeneficiaryFeedbackScreen(
    private val type: BeneficiaryFeedbackType
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        StateContent(onClearFlow = { navigator?.popAll() })
    }

    @Composable
    fun StateContent(
        onClearFlow: () -> Unit
    ) {
        BackHandler {
            // no-op: Disable back button
        }

        Scaffold {
            Feedback(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.medium)
                    .padding(it),
                primaryActionText = stringResource(type.primaryActionText),
                title = stringResource(type.title),
                description = stringResource(type.description),
                onPrimaryAction = {
                    when (type) {
                        BeneficiaryFeedbackType.CreateSuccess -> onClearFlow()
                    }
                },
                icon = type.icon
            )
        }
    }

}
