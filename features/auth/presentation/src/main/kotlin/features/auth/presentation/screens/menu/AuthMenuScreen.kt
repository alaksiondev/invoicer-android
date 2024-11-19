package features.auth.presentation.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.design.system.tokens.Spacing
import foundation.navigation.InvoicerScreen

internal class AuthMenuScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val signInScreen = rememberScreen(InvoicerScreen.Auth.AuthMenu)
        val signUpScreen = rememberScreen(InvoicerScreen.Auth.SignUp)

        StateContent(
            onSignInClick = {
                navigator?.push(signInScreen)
            },
            onSignUpClick = {
                navigator?.push(signUpScreen)
            }
        )
    }

    @Composable
    internal fun StateContent(
        onSignInClick: () -> Unit,
        onSignUpClick: () -> Unit
    ) {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(Spacing.medium)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignInClick
                ) {
                    Text("Sign In")
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignUpClick
                ) {
                    Text("Sign Up")
                }
            }
        }
    }
}