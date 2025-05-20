package io.github.alaksion.invoicer.features.auth.presentation.screens.signup

import io.github.alaksion.invoicer.features.auth.presentation.fakes.FakeAnalyticsTracker
import io.github.alaksion.invoicer.features.auth.presentation.fakes.FakeAuthRepository
import io.github.alaksion.invoicer.features.auth.presentation.fakes.FakeEmailValidator
import io.github.alaksion.invoicer.features.auth.presentation.fakes.FakePasswordStrengthValidator
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult
import io.github.alaksion.invoicer.foundation.network.RequestError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpScreenModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var authRepository: FakeAuthRepository
    private lateinit var emailValidator: FakeEmailValidator
    private lateinit var passwordStrengthValidator: FakePasswordStrengthValidator
    private lateinit var analyticsTracker: FakeAnalyticsTracker

    private lateinit var viewModel: SignUpScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        emailValidator = FakeEmailValidator()
        passwordStrengthValidator = FakePasswordStrengthValidator()
        analyticsTracker = FakeAnalyticsTracker()
        authRepository = FakeAuthRepository()

        viewModel = SignUpScreenModel(
            authRepository = authRepository,
            dispatcher = dispatcher,
            emailValidator = emailValidator,
            passwordStrengthValidator = passwordStrengthValidator,
            analyticsTracker = analyticsTracker
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update email`() {
        viewModel.onEmailChange("new@email.com")
        assertEquals(
            expected = "new@email.com",
            actual = viewModel.state.value.email
        )
    }

    @Test
    fun `should update password`() {
        viewModel.onPasswordChange("Abc")
        assertEquals(
            expected = "Abc",
            actual = viewModel.state.value.password
        )
    }

    @Test
    fun `should update password strength alongisde password`() {
        val strength = PasswordStrengthResult(
            false, false, false, false, false
        )

        passwordStrengthValidator.response = strength

        viewModel.onPasswordChange("Abc")

        assertEquals(
            expected = strength,
            actual = viewModel.state.value.passwordStrength
        )
    }

    @Test
    fun `toggle password should update state`() {
        val current = viewModel.state.value.censored

        viewModel.toggleCensorship()

        val new = viewModel.state.value.censored

        assertTrue { new == current.not() }
    }

    @Test
    fun `should set e-mail error when submit is clicked and e-amil is invalid`() {
        emailValidator.response = false

        viewModel.onEmailChange("invalid")

        viewModel.createAccount()

        assertFalse { viewModel.state.value.emailValid }
    }

    @Test
    fun `should not call endpoint when submit is clicked and form is invalid `() {
        viewModel.onEmailChange("invalid")
        viewModel.onPasswordChange("")

        viewModel.createAccount()

        assertEquals(
            expected = 0,
            actual = authRepository.signUpCalls
        )
    }

    @Test
    fun `should create account successfully`() = runTest {
        viewModel.onEmailChange("invalid")
        viewModel.onPasswordChange("1234")

        viewModel.createAccount()

        assertEquals(
            expected = SignUpEvents.Success,
            actual = viewModel.events.first()
        )
    }

    @Test
    fun `should send duplicate user event when request fails with http 409 code`() = runTest {
        authRepository.signUpError = RequestError.Http(
            409,
            null,
            ""
        )

        viewModel.onEmailChange("invalid")
        viewModel.onPasswordChange("1234")

        viewModel.createAccount()

        assertEquals(
            expected = SignUpEvents.DuplicateAccount,
            actual = viewModel.events.first()
        )
    }

    @Test
    fun `should send failure event if request fails with http code non 409 with message`() =
        runTest {
            authRepository.signUpError = RequestError.Http(
                401,
                null,
                ""
            )
            viewModel.onEmailChange("invalid")
            viewModel.onPasswordChange("1234")

            viewModel.createAccount()

            assertEquals(
                expected = SignUpEvents.Failure(""),
                actual = viewModel.events.first()
            )
        }

    @Test
    fun `should send generic event if request fails with http code non 409 without message`() =
        runTest {
            authRepository.signUpError = RequestError.Http(
                401,
                null,
                null
            )

            viewModel.onEmailChange("invalid")
            viewModel.onPasswordChange("1234")

            viewModel.createAccount()

            assertEquals(
                expected = SignUpEvents.GenericFailure,
                actual = viewModel.events.first()
            )
        }

    @Test
    fun `should send generic error event when request fails with untracked error`() = runTest {
        authRepository.signUpError = Exception()

        viewModel.onEmailChange("invalid")
        viewModel.onPasswordChange("1234")

        viewModel.createAccount()

        assertEquals(
            expected = SignUpEvents.GenericFailure,
            actual = viewModel.events.first()
        )
    }
}
