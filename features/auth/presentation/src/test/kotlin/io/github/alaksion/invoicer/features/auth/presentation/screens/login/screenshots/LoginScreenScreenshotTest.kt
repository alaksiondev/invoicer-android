package io.github.alaksion.invoicer.features.auth.presentation.screens.login.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreen
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreenCallbacks
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreenState
import org.junit.Rule
import org.junit.Test

class LoginScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        maxPercentDifference = 0.01,
    )


    @Test
    fun loginScreen_default() {
        paparazzi.snapshot {
            TestContent(
                state = LoginScreenState()
            )

        }
    }

    @Test
    fun loginScreen_fields_filled() {
        paparazzi.snapshot {
            InvoicerTheme {
                TestContent(
                    state = LoginScreenState(
                        email = "john@done.gmail",
                        password = "12312312321"
                    )
                )
            }
        }
    }

    @Test
    fun loginScreen_password_visible() {
        paparazzi.snapshot {
            InvoicerTheme {
                TestContent(
                    state = LoginScreenState(
                        email = "john@done.gmail",
                        password = "12312312321",
                        censored = false
                    )
                )
            }
        }
    }

    @Test
    fun loginScreen_login_loading() {
        paparazzi.snapshot {
            InvoicerTheme {
                TestContent(
                    state = LoginScreenState(
                        email = "john@done.gmail",
                        password = "12312312321",
                        isSignInLoading = true
                    )
                )
            }
        }
    }

    @Test
    fun loginScreen_google_loading() {
        paparazzi.snapshot {
            InvoicerTheme {
                TestContent(
                    state = LoginScreenState(
                        isGoogleLoading = true
                    )
                )
            }
        }
    }

    @Composable
    private fun TestContent(
        state: LoginScreenState
    ) {
        InvoicerTheme {
            LoginScreen().StateContent(
                snackbarHostState = SnackbarHostState(),
                state = state,
                callBacks = LoginScreenCallbacks(
                    onEmailChanged = { },
                    onPasswordChanged = { },
                    onSubmit = { },
                    toggleCensorship = { },
                    onBack = { },
                    onSignUpClick = { },
                    onLaunchGoogle = { }
                ),
            )
        }
    }

}