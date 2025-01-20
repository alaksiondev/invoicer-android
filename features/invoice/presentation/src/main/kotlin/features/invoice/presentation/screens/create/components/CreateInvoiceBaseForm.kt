package features.invoice.presentation.screens.create.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import features.auth.design.system.components.buttons.BackButton
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseFormTestTags.BACK_BUTTON
import foundation.design.system.tokens.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateInvoiceBaseForm(
    title: String,
    buttonText: String,
    buttonEnabled: Boolean,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarState: SnackbarHostState? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    BackButton(
                        modifier = Modifier.testTag(BACK_BUTTON),
                        onBackClick = onBack
                    )
                }
            )
        },
        snackbarHost = {
            snackbarState?.let { SnackbarHost(it) }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(Spacing.medium)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
            VerticalSpacer(SpacerSize.Small)
            HorizontalDivider()
            VerticalSpacer(SpacerSize.Small)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                content()
            }
            VerticalSpacer(SpacerSize.Medium)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onContinue,
                enabled = buttonEnabled
            ) {
                Text(
                    text = buttonText
                )
            }
        }
    }
}

internal object CreateInvoiceBaseFormTestTags {
    const val BACK_BUTTON = "BACK_BUTTON"
}