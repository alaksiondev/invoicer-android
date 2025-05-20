package io.github.alaksion.invoicer.features.auth.presentation.screens.login

import io.github.alaksion.invoicer.features.auth.presentation.fakes.FakeAnalyticsTracker
import io.github.alaksion.invoicer.features.auth.presentation.fakes.FakeSignInCommander
import io.github.alaksion.invoicer.foundation.auth.domain.services.SignInCommand
import io.github.alaksion.invoicer.foundation.network.RequestError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class LoginScreenModelTest {

    private lateinit var signInCommander: FakeSignInCommander
    private lateinit var analyticsTracker: FakeAnalyticsTracker
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: LoginScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        signInCommander = FakeSignInCommander()
        analyticsTracker = FakeAnalyticsTracker()
        viewModel = LoginScreenModel(signInCommander, dispatcher, analyticsTracker)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEmailChanged updates email in state`() = runTest {
        val email = "test@example.com"
        viewModel.onEmailChanged(email)
        assertEquals(email, viewModel.state.value.email)
    }

    @Test
    fun `onPasswordChanged updates password in state`() = runTest {
        val password = "password123"
        viewModel.onPasswordChanged(password)
        assertEquals(password, viewModel.state.value.password)
    }

    @Test
    fun `toggleCensorship toggles censored state`() = runTest {
        val initialCensored = viewModel.state.value.censored
        viewModel.toggleCensorship()
        assertEquals(!initialCensored, viewModel.state.value.censored)
    }

    @Test
    fun `submit triggers handleSignInRequest when button is enabled`() = runTest {
        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password123")

        viewModel.submitIdentityLogin()

        advanceUntilIdle()
        assertIs<SignInCommand.Credential>(signInCommander.signInCommand)
    }

    @Test
    fun `launch google login sends event`() = runTest {
        viewModel.launchGoogleLogin()

        assertEquals(
            expected = LoginScreenEvents.LaunchGoogleLogin,
            actual = viewModel.events.first()
        )
        assertEquals(true, viewModel.state.value.isGoogleLoading)
        assertEquals(
            expected = LoginAnalytics.GoogleLoginStarted,
            actual = analyticsTracker.lastEvent
        )
    }

    @Test
    fun `handle signin request fails with http error`() = runTest {
        signInCommander.failure = RequestError.Http(
            httpCode = 401,
            message = "Unauthorized",
        )

        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password123")

        viewModel.submitIdentityLogin()

        assertIs<LoginScreenEvents.Failure>(viewModel.events.first())
    }

    @Test
    fun `handle signin request fails with http error without message`() = runTest {
        signInCommander.failure = RequestError.Http(
            httpCode = 401,
            message = null,
        )

        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password123")

        viewModel.submitIdentityLogin()

        assertIs<LoginScreenEvents.GenericFailure>(viewModel.events.first())
    }

    @Test
    fun `handle signin request fails with untracked exception error`() = runTest {
        signInCommander.failure = Exception()
        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password123")

        viewModel.submitIdentityLogin()

        assertIs<LoginScreenEvents.GenericFailure>(viewModel.events.first())
    }

    @Test
    fun `cancelGoogleSignIn updates state and tracks analytics`() {
        viewModel.cancelGoogleSignIn()
        assertEquals(false, viewModel.state.value.isGoogleLoading)
        assertEquals(
            expected = LoginAnalytics.GoogleLoginFailure,
            actual = analyticsTracker.lastEvent
        )
    }
}
