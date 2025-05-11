package features.qrcodeSession.presentation.screens.success

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import features.qrcodeSession.presentation.screens.home.AuthorizationHomeScreen
import foundation.designsystem.components.buttons.BackButton

internal class AuthorizationSuccessScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        BackHandler { }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        BackButton(icon = Icons.Default.Close) {
                            navigator?.popUntil {
                                it is AuthorizationHomeScreen
                            }
                        }
                    }
                )
            }
        ) {
            Text(modifier = Modifier.padding(it), text = "code authorized")
        }
    }
}
