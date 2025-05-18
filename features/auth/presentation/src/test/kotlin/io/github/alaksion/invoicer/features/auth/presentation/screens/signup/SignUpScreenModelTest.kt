package io.github.alaksion.invoicer.features.auth.presentation.screens.signup

import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.features.auth.presentation.utils.EmailValidator
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthValidator
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
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

    private val authRepository = mockk<AuthRepository>()
    private val dispatcher = StandardTestDispatcher()
    private val emailValidator = mockk<EmailValidator>()
    private val passwordStrengthValidator = mockk<PasswordStrengthValidator>()
    private val analyticsTracker = mockk<AnalyticsTracker>()

    private lateinit var viewModel: SignUpScreenModel

    private fun defaultMocks() {
        every { passwordStrengthValidator.validate(any()) } returns PasswordStrengthResult(
            true, true, true, true, true
        )
        every { emailValidator.validate(any()) } returns true
    }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        coEvery { analyticsTracker.track(any()) } just Runs

        defaultMocks()
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

        every { passwordStrengthValidator.validate(any()) } returns strength

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
        every { emailValidator.validate(any()) } returns false
        viewModel.onEmailChange("invalid")

        viewModel.createAccount()

        assertFalse { viewModel.state.value.emailValid }
    }

    @Test
    fun `should not call endpoint when submit is clicked and form is invalid `() {
        viewModel.onEmailChange("invalid")
        viewModel.onPasswordChange("")

        viewModel.createAccount()

        coVerify(exactly = 0) { authRepository.signUp(any(), any(), any()) }
    }

    @Test
    fun `should create use successfully`() = runTest {
        coEvery { authRepository.signUp(any(), any(), any()) }.returns(Unit)
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
        coEvery { authRepository.signUp(any(), any(), any()) } throws RequestError.Http(
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
            coEvery { authRepository.signUp(any(), any(), any()) } throws RequestError.Http(
                401,
                null,
                "message"
            )
            viewModel.onEmailChange("invalid")
            viewModel.onPasswordChange("1234")

            viewModel.createAccount()

            assertEquals(
                expected = SignUpEvents.Failure("message"),
                actual = viewModel.events.first()
            )
        }

    @Test
    fun `should send generic event if request fails with http code non 409 without message`() =
        runTest {
            coEvery { authRepository.signUp(any(), any(), any()) } throws RequestError.Http(
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
        coEvery { authRepository.signUp(any(), any(), any()) } throws RequestError.Other(
            IllegalStateException()
        )

        viewModel.onEmailChange("invalid")
        viewModel.onPasswordChange("1234")

        viewModel.createAccount()

        assertEquals(
            expected = SignUpEvents.GenericFailure,
            actual = viewModel.events.first()
        )
    }
}
