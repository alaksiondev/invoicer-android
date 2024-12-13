package features.home.presentation.tabs.welcome.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
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
import features.auth.design.system.components.preview.PreviewContainer
import features.home.presentation.R
import foundation.design.system.tokens.Spacing

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
    modifier: Modifier = Modifier
) {
    val items = remember { WelcomeItems.entries }
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.horizontalScroll(scrollState),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        items.forEach {
            Card(
                modifier = Modifier
                    .widthIn(min = CardSize)
                    .clickable(
                        onClick = {}
                    )
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Text(
                        text = stringResource(it.text),
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    PreviewContainer {
        WelcomeActions(Modifier.fillMaxWidth())
    }
}