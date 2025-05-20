package io.github.alaksion.invoicer.features.auth.presentation.screens.login.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreen
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreenCallbacks
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreenState
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.junit.Rule
import kotlin.test.Test

class LoginScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        maxPercentDifference = 0.01,
    )


    @OptIn(ExperimentalResourceApi::class)
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
            TestContent(
                state = LoginScreenState(
                    email = "john@done.gmail",
                    password = "12312312321"
                )
            )
        }
    }

    @Test
    fun loginScreen_password_visible() {
        paparazzi.snapshot {
            TestContent(
                state = LoginScreenState(
                    email = "john@done.gmail",
                    password = "12312312321",
                    censored = false
                )
            )
        }
    }

    @Test
    fun loginScreen_login_loading() {
        paparazzi.snapshot {
            TestContent(
                state = LoginScreenState(
                    email = "john@done.gmail",
                    password = "12312312321",
                    isSignInLoading = true
                )
            )
        }
    }

    @Test
    fun loginScreen_google_loading() {
        paparazzi.snapshot {
            TestContent(
                state = LoginScreenState(
                    isGoogleLoading = true
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: LoginScreenState
    ) {
        MultiplatformSnapshot {
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

}
