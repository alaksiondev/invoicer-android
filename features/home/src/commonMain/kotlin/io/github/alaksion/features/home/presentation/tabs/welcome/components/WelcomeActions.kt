package io.github.alaksion.features.home.presentation.tabs.welcome.components

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
import androidx.compose.ui.unit.dp
import invoicer.features.home.generated.resources.Res
import invoicer.features.home.generated.resources.welcome_icon_beneficiary
import invoicer.features.home.generated.resources.welcome_icon_intermediary
import invoicer.features.home.generated.resources.welcome_icon_invoice
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

private enum class WelcomeItems(
    val icon: ImageVector,
    val text: StringResource
) {
    Invoice(
        icon = Icons.Outlined.AttachMoney,
        text = Res.string.welcome_icon_invoice
    ),
    Beneficiary(
        icon = Icons.Outlined.ArrowOutward,
        text = Res.string.welcome_icon_beneficiary
    ),
    Intermediary(
        icon = Icons.Outlined.Handshake,
        text = Res.string.welcome_icon_intermediary
    )
}

@Composable
internal fun WelcomeActions(
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