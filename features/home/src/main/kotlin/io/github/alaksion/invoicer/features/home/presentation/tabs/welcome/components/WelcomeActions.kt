package io.github.alaksion.invoicer.features.home.presentation.tabs.welcome.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.designsystem.components.preview.ThemeContainer
import features.home.presentation.R
import foundation.designsystem.tokens.Spacing

private enum class WelcomeItems(
    val icon: ImageVector,
    @StringRes val text: Int
) {
    Invoice(
        icon = Icons.Outlined.AttachMoney,
        text = R.string.welcome_icon_invoice
    ),
    Beneficiary(
        icon = Icons.Outlined.ArrowOutward,
        text = R.string.welcome_icon_beneficiary
    ),
    Intermediary(
        icon = Icons.Outlined.Handshake,
        text = R.string.welcome_icon_intermediary
    )
}

private val CardSize = 150.dp

@Composable
internal fun WelcomeActions(
    modifier: Modifier = Modifier,
    onInvoiceClick: () -> Unit,
    onIntermediaryClick: () -> Unit,
    onBeneficiaryClick: () -> Unit,
) {
    val items = remember {
        mapOf(
            WelcomeItems.Invoice to onInvoiceClick,
            WelcomeItems.Intermediary to onIntermediaryClick,
            WelcomeItems.Beneficiary to onBeneficiaryClick
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        items.forEach { (item, onClick) ->
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = onClick
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.xSmall),
                    verticalArrangement = Arrangement.spacedBy(Spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Text(
                        text = stringResource(item.text),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    ThemeContainer {
        WelcomeActions(
            modifier = Modifier.fillMaxWidth(),
            onInvoiceClick = {},
            onIntermediaryClick = {},
            onBeneficiaryClick = {}
        )
    }
}