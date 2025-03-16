package features.qrcodeSession.presentation.screens.scan.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import feature.qrcodeSession.R
import foundation.designsystem.tokens.Spacing

@Composable
internal fun CodeDetails(
    modifier: Modifier = Modifier,
    qrCodeAgent: String,
    qrCodeIp: String,
    qrCodeExpiration: String,
    qrCodeEmission: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        ListItem(
            headlineContent = { Text(text = qrCodeAgent) },
            overlineContent = {
                Text(stringResource(R.string.qr_code_details_agent))
            }
        )
        ListItem(
            headlineContent = { Text(text = qrCodeIp) },
            overlineContent = {
                Text(stringResource(R.string.qr_code_details_ip))
            }
        )
        ListItem(
            headlineContent = { Text(text = qrCodeExpiration) },
            overlineContent = {
                Text(stringResource(R.string.qr_code_details_expiration))
            }
        )
        ListItem(
            headlineContent = { Text(text = qrCodeEmission) },
            overlineContent = {
                Text(stringResource(R.string.qr_code_details_requested_at))
            }
        )
    }
}