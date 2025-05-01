package features.auth.presentation.screens.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import features.auth.presentation.firebase.FirebaseHelper
import features.auth.presentation.firebase.GoogleResult
import foundation.auth.domain.repository.AuthRepository
import foundation.auth.watchers.AuthEvent
import foundation.auth.watchers.AuthEventPublisher
import foundation.network.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginScreenModel(
    private val authRepository: AuthRepository,
    private val authEventPublisher: AuthEventPublisher,
    private val firebaseHelper: FirebaseHelper,
    private val dispatcher: CoroutineDispatcher
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

    fun getGoogleClient() = firebaseHelper.getGoogleClient()

    private fun handleSignInRequest() {
        screenModelScope.launch {
            launchRequest {
                authRepository.signIn(
                    email = _state.value.email,
                    password = _state.value.password
                )
            }.handle(
                onStart = { _state.update { it.copy(isSignInLoading = true) } },
                onFinish = { _state.update { it.copy(isSignInLoading = false) } },
                onFailure = {
                    sendErrorEvent(it)
                },
                onSuccess = { authEventPublisher.publish(event = AuthEvent.SignIn) }
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

    fun handleGoogleTask(task: Task<GoogleSignInAccount>) {
        screenModelScope.launch(dispatcher) {
            val result = firebaseHelper.handleGoogleResult(task)

            when (result) {
                is GoogleResult.Error -> {
                    publish(
                        LoginScreenEvents.Failure(
                            message = result.error?.message.orEmpty()
                        )
                    )
                }

                is GoogleResult.Success -> {
                    launchRequest {
                        authRepository.googleSignIn(result.token)
                    }.handle(
                        onSuccess = {
                            authEventPublisher.publish(AuthEvent.SignIn)
                        },
                        onStart = {
                            _state.update {
                                it.copy(
                                    isGoogleLoading = true
                                )
                            }
                        },
                        onFinish = {
                            _state.update {
                                it.copy(
                                    isGoogleLoading = false
                                )
                            }
                        },
                        onFailure = { result ->
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