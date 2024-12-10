package features.home.presentation.tabs.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import features.auth.design.system.components.dialog.InvoicerDialog
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.home.presentation.R
import foundation.design.system.tokens.Spacing


@Composable
internal fun SignOutDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (isVisible) {
        InvoicerDialog(
            onDismiss = onDismiss,
        ) {
            Icon(
                imageVector = Icons.Rounded.WarningAmber,
                contentDescription = null
            )
            VerticalSpacer(SpacerSize.Medium)
            Text(
                text = stringResource(R.string.home_settings_sing_out_title),
                style = MaterialTheme.typography.titleLarge
            )
            VerticalSpacer(SpacerSize.XSmall)
            Text(
                text = stringResource(R.string.home_settings_sign_out_confirmation),
            )
            VerticalSpacer(SpacerSize.Large3)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onDismiss
                ) {
                    Text(
                        text = stringResource(R.string.home_settings_sign_out_negative)
                    )
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onConfirm
                ) {
                    Text(
                        text = stringResource(R.string.home_settings_sign_out_positive)
                    )
                }
            }
        }
    }
}