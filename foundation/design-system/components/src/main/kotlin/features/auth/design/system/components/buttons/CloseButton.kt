package features.auth.design.system.components.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onBackClick
    ) {
        Icon(
            painter = rememberVectorPainter(
                image = Icons.Outlined.Close
            ),
            contentDescription = null
        )
    }
}