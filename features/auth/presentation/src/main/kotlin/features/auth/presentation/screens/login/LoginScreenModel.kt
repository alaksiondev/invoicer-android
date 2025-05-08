package features.auth.presentation.screens.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import features.auth.presentation.firebase.FirebaseHelper
import features.auth.presentation.firebase.GoogleResult
import foundation.network.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommand
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommandManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginScreenModel(
    private val signInCommander: SignInCommandManager,
    private val firebaseHelper: FirebaseHelper,
    private val dispatcher: CoroutineDispatcher,
    private val analyticsTracker: AnalyticsTracker
) : ScreenModel, EventAware<LoginScreenEvents> by EventPublisher() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun toggleCensorship() {
        _state.value = _state.value.copy(censored = !_state.value.censored)
    }

    fun submit() {
        if (_state.value.buttonEnabled) handleSignInRequest()
    }

    fun getGoogleClient(): GoogleSignInClient {
        analyticsTracker.track(LoginAnalytics.GoogleLoginStarted)
        _state.update {
            it.copy(
                isGoogleLoading = true
            )
        }
        return firebaseHelper.getGoogleClient()
    }

    private fun handleSignInRequest() {
        screenModelScope.launch {
            launchRequest {
                signInCommander.resolveCommand(
                    SignInCommand.Credential(
                        userName = _state.value.email,
                        password = _state.value.password
                    )
                )
            }.handle(
                onStart = {
                    analyticsTracker.track(LoginAnalytics.IdentityLoginStarted)
                    _state.update {
                        it.copy(isSignInLoading = true)
                    }
                },
                onFinish = { _state.update { it.copy(isSignInLoading = false) } },
                onFailure = {
                    analyticsTracker.track(LoginAnalytics.IdentityLoginFailure)
                    sendErrorEvent(it)
                },
                onSuccess = {
                    analyticsTracker.track(LoginAnalytics.IdentityLoginSuccess)
                }
            )
        }
    }

    private suspend fun sendErrorEvent(error: RequestError) {
        val message = when (error) {
            is RequestError.Http -> error.message?.let {
                LoginScreenEvents.Failure(it)
            } ?: LoginScreenEvents.GenericFailure

            is RequestError.Other -> LoginScreenEvents.GenericFailure
        }
        publish(message)
    }

    fun cancelGoogleSignIn() {
        analyticsTracker.track(LoginAnalytics.GoogleLoginFailure)
        _state.update {
            it.copy(
                isGoogleLoading = false
            )
        }
    }

    fun handleGoogleTask(task: Task<GoogleSignInAccount>) {
        screenModelScope.launch(dispatcher) {
            val result = firebaseHelper.handleGoogleResult(task)

            when (result) {
                is GoogleResult.Error -> {
                    analyticsTracker.track(LoginAnalytics.GoogleLoginFailure)
                    publish(
                        LoginScreenEvents.Failure(
                            message = result.error?.message.orEmpty()
                        )
                    )
                }

                is GoogleResult.Success -> {
                    launchRequest {
                        signInCommander.resolveCommand(
                            SignInCommand.Google(result.token)
                        )
                    }.handle(
                        onSuccess = {
                            analyticsTracker.track(LoginAnalytics.GoogleLoginSuccess)
                        },
                        onFinish = {
                            _state.update {
                                it.copy(
                                    isGoogleLoading = false
                                )
                            }
                        },
                        onFailure = { result ->
                            analyticsTracker.track(LoginAnalytics.GoogleLoginFailure)
                            publish(
                                LoginScreenEvents.Failure(
                                    message = result.message.orEmpty()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}