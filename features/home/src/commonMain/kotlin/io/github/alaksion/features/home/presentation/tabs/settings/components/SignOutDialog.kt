package io.github.alaksion.features.home.presentation.tabs.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.runtime.Composable
import invoicer.features.home.generated.resources.Res
import invoicer.features.home.generated.resources.home_settings_sign_out_confirmation
import invoicer.features.home.generated.resources.home_settings_sign_out_negative
import invoicer.features.home.generated.resources.home_settings_sign_out_positive
import invoicer.features.home.generated.resources.home_settings_sing_out_title
import io.github.alaksion.invoicer.foundation.designSystem.components.dialog.DefaultInvoicerDialog
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun SignOutDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (isVisible) {
        DefaultInvoicerDialog(
            onDismiss = onDismiss,
            title = stringResource(Res.string.home_settings_sing_out_title),
            description = stringResource(Res.string.home_settings_sign_out_confirmation),
            confirmButtonText = stringResource(Res.string.home_settings_sign_out_positive),
            cancelButtonText = stringResource(Res.string.home_settings_sign_out_negative),
            confirmButtonClick = onConfirm,
            icon = Icons.Rounded.WarningAmber
        )
    }
}
