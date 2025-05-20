package io.github.alaksion.invoicer.features.auth.presentation.screens.signup.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.auth.presentation.screens.signup.SignUpCallbacks
import io.github.alaksion.invoicer.features.auth.presentation.screens.signup.SignUpScreen
import io.github.alaksion.invoicer.features.auth.presentation.screens.signup.SignUpScreenState
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import org.junit.Rule
import org.junit.Test

class SignUpScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        maxPercentDifference = 0.01,
    )

    @Test
    fun signUpScreen_default() {
        paparazzi.snapshot {
            TestContent(
                state = SignUpScreenState()
            )
        }
    }

    @Test
    fun signUpScreen_fieldsFilled() {
        paparazzi.snapshot {
            TestContent(
                state = SignUpScreenState(
                    email = "johndona@gmail.com",
                    password = "123123123"
                )
            )
        }
    }

    @Test
    fun signUpScreen_passwordVisible() {
        paparazzi.snapshot {
            TestContent(
                state = SignUpScreenState(
                    email = "johndona@gmail.com",
                    password = "123123123",
                    censored = false
                )
            )
        }
    }

    @Test
    fun signUpScreen_passwordWeak() {
        paparazzi.snapshot {
            TestContent(
                state = SignUpScreenState(
                    email = "johndona@gmail.com",
                    password = "123123123",
                    passwordStrength = PasswordStrengthResult(
                        lengthValid = false,
                        upperCaseValid = false,
                        lowerCaseValid = false,
                        digitValid = false,
                        specialCharacterValid = false
                    )
                )
            )
        }
    }

    @Test
    fun signUpScreen_passwordStrong() {
        paparazzi.snapshot {
            TestContent(
                state = SignUpScreenState(
                    email = "johndona@gmail.com",
                    password = "123123123",
                    passwordStrength = PasswordStrengthResult(
                        lengthValid = true,
                        upperCaseValid = true,
                        lowerCaseValid = true,
                        digitValid = true,
                        specialCharacterValid = true
                    )
                )
            )
        }
    }

    @Test
    fun signUpScreen_requestLoading() {
        paparazzi.snapshot {
            TestContent(
                state = SignUpScreenState(
                    email = "johndona@gmail.com",
                    password = "123123123",
                    passwordStrength = PasswordStrengthResult(
                        lengthValid = true,
                        upperCaseValid = true,
                        lowerCaseValid = true,
                        digitValid = true,
                        specialCharacterValid = true
                    ),
                    requestLoading = true
                )
            )
        }
    }

    @Test
    fun signUpScreen_dialogUp() {
        paparazzi.snapshot {
            TestContent(
                state = SignUpScreenState(),
                showDuplicateAccountDialog = true
            )
        }
    }

    @Composable
    private fun TestContent(
        state: SignUpScreenState,
        showDuplicateAccountDialog: Boolean = false
    ) {
        MultiplatformSnapshot {


            InvoicerTheme {
                SignUpScreen().StateContent(
                    state = state,
                    snackBarState = SnackbarHostState(),
                    callbacks = SignUpCallbacks(
                        onEmailChange = { },
                        onPasswordChange = { },
                        toggleCensorship = { },
                        onBackClick = { },
                        onSubmitClick = { },
                        onSignInClick = { },
                        onDismissDialog = { },
                    ),
                    showDuplicateAccountDialog = showDuplicateAccountDialog
                )
            }
        }
    }
}
