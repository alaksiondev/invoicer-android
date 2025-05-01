package features.auth.presentation.screens.menu

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import features.auth.presentation.firebase.FirebaseHelperImpl
import features.auth.presentation.firebase.GoogleResult
import foundation.auth.domain.repository.AuthRepository
import foundation.auth.watchers.AuthEvent
import foundation.auth.watchers.AuthEventPublisher
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AuthMenuScreenModel(
    private val firebaseHelper: FirebaseHelperImpl,
    private val dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
    private val authEventPublisher: AuthEventPublisher
) : ScreenModel, EventAware<AuthMenuEvents> by EventPublisher() {

    private val _state = MutableStateFlow(AuthMenuState())
    val state: StateFlow<AuthMenuState> = _state

    fun getGoogleClient() = firebaseHelper.getGoogleClient()

    fun handleGoogleTask(task: Task<GoogleSignInAccount>) {
        screenModelScope.launch(dispatcher) {
            val result = firebaseHelper.handleGoogleResult(task)

            when (result) {
                is GoogleResult.Error -> {
                    publish(
                        AuthMenuEvents.GoogleAuthFailure(
                            error = result.error?.message.orEmpty()
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
                                    isSocialLoginLoading = true
                                )
                            }
                        },
                        onFinish = {
                            _state.update {
                                it.copy(
                                    isSocialLoginLoading = false
                                )
                            }
                        },
                        onFailure = {
                            publish(
                                AuthMenuEvents.GoogleAuthFailure(
                                    error = it.message.orEmpty()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}