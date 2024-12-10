package features.auth.design.system.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import foundation.design.system.tokens.Spacing

@Composable
fun InvoicerDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(modifier = Modifier.padding(Spacing.small)) {
                content()
            }
        }
    }
}