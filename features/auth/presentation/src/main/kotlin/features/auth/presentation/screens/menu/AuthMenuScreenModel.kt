package features.auth.presentation.screens.menu

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

import features.auth.presentation.firebase.FirebaseHelper
import features.auth.presentation.firebase.GoogleResult
import foundation.auth.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

internal class AuthMenuScreenModel(
    private val firebaseHelper: FirebaseHelper,
    private val dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository
) : ScreenModel {

    fun getGoogleClient() = firebaseHelper.getGoogleClient()

    fun handleGoogleTask(task: Task<GoogleSignInAccount>) {
        screenModelScope.launch(dispatcher) {
            val result = firebaseHelper.handleGoogleResult(task)

            when (result) {
                is GoogleResult.Error -> Log.d(
                    "AuthMenuScreenModel",
                    "Google sign in failure: ${result.error}"
                )

                is GoogleResult.Success -> {
                    val response = authRepository.googleSignIn(result.token)
                    Log.d("AuthMenuScreenModel", "Google sign in response: $response")
                }
            }
        }
    }
}