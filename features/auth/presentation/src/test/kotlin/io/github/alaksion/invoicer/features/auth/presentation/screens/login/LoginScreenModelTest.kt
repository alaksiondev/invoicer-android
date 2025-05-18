package io.github.alaksion.invoicer.features.auth.presentation.screens.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.features.auth.presentation.firebase.FirebaseHelper
import io.github.alaksion.invoicer.features.auth.presentation.firebase.GoogleResult
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommand
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommandManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
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

    private val signInCommander: SignInCommandManager = mockk()
    private val firebaseHelper: FirebaseHelper = mockk()
    private val analyticsTracker: AnalyticsTracker = mockk()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: LoginScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = LoginScreenModel(signInCommander, firebaseHelper, dispatcher, analyticsTracker)
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

        coEvery { signInCommander.resolveCommand(any()) } returns Unit
        coEvery { analyticsTracker.track(any()) } just Runs

        viewModel.submit()

        advanceUntilIdle()
        coVerify { signInCommander.resolveCommand(any<SignInCommand.Credential>()) }
    }

    @Test
    fun `getGoogleClient updates state and returns GoogleSignInClient`() {
        val googleClient: GoogleSignInClient = mockk()
        every { firebaseHelper.getGoogleClient() } returns googleClient
        every { analyticsTracker.track(any()) } just Runs

        val result = viewModel.getGoogleClient()

        assertEquals(googleClient, result)
        assertEquals(true, viewModel.state.value.isGoogleLoading)
        verify { analyticsTracker.track(LoginAnalytics.GoogleLoginStarted) }
    }

    @Test
    fun `handleGoogleTask handles GoogleResult success`() = runTest {
        val task: Task<GoogleSignInAccount> = mockk()
        val token = "google_token"
        val googleResult = GoogleResult.Success(token)

        coEvery { firebaseHelper.handleGoogleResult(task) } returns googleResult
        coEvery { signInCommander.resolveCommand(any()) } returns Unit
        coEvery { analyticsTracker.track(any()) } just Runs

        viewModel.handleGoogleTask(task)

        advanceUntilIdle()
        coVerify { signInCommander.resolveCommand(SignInCommand.Google(token)) }
        coVerify { analyticsTracker.track(LoginAnalytics.GoogleLoginSuccess) }
    }

    @Test
    fun `handleGoogleTask handles GoogleResult error`() = runTest {
        val task: Task<GoogleSignInAccount> = mockk()
        val errorMessage = "Google sign-in failed"
        val googleResult = GoogleResult.Error(Exception(errorMessage))

        coEvery { firebaseHelper.handleGoogleResult(task) } returns googleResult
        coEvery { analyticsTracker.track(any()) } just Runs

        viewModel.handleGoogleTask(task)

        advanceUntilIdle()
        coVerify { analyticsTracker.track(LoginAnalytics.GoogleLoginFailure) }
    }

    @Test
    fun `handleGoogleTask success but login request fails`() = runTest {
        val task: Task<GoogleSignInAccount> = mockk()
        val googleResult = GoogleResult.Success("123")

        coEvery { firebaseHelper.handleGoogleResult(task) } returns googleResult
        coEvery { analyticsTracker.track(any()) } just Runs
        coEvery { signInCommander.resolveCommand(any()) } throws IllegalStateException()

        viewModel.handleGoogleTask(task)

        assertIs<LoginScreenEvents.Failure>(viewModel.events.first())
    }

    @Test
    fun `handle signin request fails with http error`() = runTest {
        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password123")

        coEvery { signInCommander.resolveCommand(any()) } throws
                RequestError.Http(
                    httpCode = 401,
                    message = "Unauthorized",
                )

        coEvery { analyticsTracker.track(any()) } just Runs

        viewModel.submit()

        assertIs<LoginScreenEvents.Failure>(viewModel.events.first())
    }

    @Test
    fun `handle signin request fails with http error without message`() = runTest {
        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password123")

        coEvery { signInCommander.resolveCommand(any()) } throws
                RequestError.Http(
                    httpCode = 401,
                    message = null,
                )

        coEvery { analyticsTracker.track(any()) } just Runs

        viewModel.submit()

        assertIs<LoginScreenEvents.GenericFailure>(viewModel.events.first())
    }

    @Test
    fun `handle signin request fails with untracked exception error`() = runTest {
        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password123")

        coEvery { signInCommander.resolveCommand(any()) } throws
                RequestError.Other(IllegalStateException())

        coEvery { analyticsTracker.track(any()) } just Runs

        viewModel.submit()

        assertIs<LoginScreenEvents.GenericFailure>(viewModel.events.first())
    }

    @Test
    fun `cancelGoogleSignIn updates state and tracks analytics`() {
        every { analyticsTracker.track(any()) } just Runs

        viewModel.cancelGoogleSignIn()

        assertEquals(false, viewModel.state.value.isGoogleLoading)
        verify { analyticsTracker.track(LoginAnalytics.GoogleLoginFailure) }
    }
}
