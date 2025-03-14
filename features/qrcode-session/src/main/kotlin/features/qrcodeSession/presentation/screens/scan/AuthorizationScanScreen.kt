package features.qrcodeSession.presentation.screens.scan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

internal class AuthorizationScanScreen : Screen {

    @Composable
    override fun Content() {
        Scaffold { scaffoldPadding ->
            Column(
                modifier = Modifier.padding(scaffoldPadding)
            ) {

            }
        }
    }
}