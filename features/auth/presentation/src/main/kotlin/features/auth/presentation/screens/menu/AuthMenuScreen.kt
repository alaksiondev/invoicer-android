package features.auth.presentation.screens.menu

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import features.auth.presentation.screens.signin.SignInScreen
import features.auth.presentation.screens.signup.SignUpScreen
import foundation.designsystem.tokens.Spacing

internal class AuthMenuScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<AuthMenuScreenModel>()

        val firebaseLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                screenModel.handleGoogleTask(task)
            }
        }

        StateContent(
            onSignInClick = {
                navigator?.push(SignInScreen())
            },
            onSignUpClick = {
                navigator?.push(SignUpScreen())
            },
            onGoogleSignTap = {
                firebaseLauncher.launch(
                    screenModel.getGoogleClient().signInIntent
                )
            }
        )
    }

    @Composable
    internal fun StateContent(
        onSignInClick: () -> Unit,
        onSignUpClick: () -> Unit,
        onGoogleSignTap: () -> Unit
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

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onGoogleSignTap
                ) {
                    Text("Google")
                }
            }
        }
    }
}