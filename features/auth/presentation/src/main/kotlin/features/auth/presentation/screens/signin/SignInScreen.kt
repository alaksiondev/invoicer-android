package features.auth.presentation.screens.signin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

internal class SignInScreen : Screen {
    @Composable
    override fun Content() {
        StateContent()
    }

    @Composable
    internal fun StateContent() {
        Text("Hello sign in screen")
    }
}