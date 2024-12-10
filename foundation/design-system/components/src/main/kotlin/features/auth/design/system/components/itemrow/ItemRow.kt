package features.auth.design.system.components.itemrow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import foundation.design.system.tokens.Spacing

@Composable
fun ItemRow(
    leading: (() -> Unit)? = null,
    content: @Composable () -> Unit,
    trailing: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(
            vertical = Spacing.xSmall
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.small)
    ) {
        leading?.let { it() }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            content()
        }
        content()
        trailing?.let { it() }
    }
}