package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.feedback

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import invoicer.features.beneficiary.presentation.generated.resources.Res
import invoicer.features.beneficiary.presentation.generated.resources.create_success_cta
import invoicer.features.beneficiary.presentation.generated.resources.create_success_description
import invoicer.features.beneficiary.presentation.generated.resources.create_success_title
import io.github.alaksion.invoicer.foundation.designSystem.components.feedback.Feedback
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

internal enum class BeneficiaryFeedbackType(
    val primaryActionText: StringResource,
    val description: StringResource,
    val title: StringResource,
    val icon: ImageVector

) {
    CreateSuccess(
        primaryActionText = Res.string.create_success_cta,
        description = Res.string.create_success_description,
        title = Res.string.create_success_title,
        icon = Icons.Outlined.Check
    )
}

internal class BeneficiaryFeedbackScreen(
    private val type: BeneficiaryFeedbackType
) : Screen {

    override val key: ScreenKey = ScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current?.parent
        StateContent(onClearFlow = { navigator?.pop() })
    }

    @Composable
    fun StateContent(
        onClearFlow: () -> Unit
    ) {
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

    companion object {
        const val ScreenKey = "BeneficiaryFeedbackScreen"
    }

}
