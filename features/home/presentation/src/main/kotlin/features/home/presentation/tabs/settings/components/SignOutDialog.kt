package features.home.presentation.tabs.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import features.auth.design.system.components.dialog.DefaultInvoicerDialog
import features.home.presentation.R


@Composable
internal fun SignOutDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (isVisible) {
        DefaultInvoicerDialog(
            onDismiss = onDismiss,
            title = stringResource(R.string.home_settings_sing_out_title),
            description = stringResource(R.string.home_settings_sign_out_confirmation),
            confirmButtonText = stringResource(R.string.home_settings_sign_out_positive),
            cancelButtonText = stringResource(R.string.home_settings_sign_out_negative),
            confirmButtonClick = onConfirm,
            icon = Icons.Rounded.WarningAmber
        )
    }
}