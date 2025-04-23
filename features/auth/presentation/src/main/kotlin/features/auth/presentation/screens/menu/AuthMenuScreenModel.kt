package features.auth.presentation.screens.menu

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

import features.auth.presentation.firebase.FirebaseHelper
import features.auth.presentation.firebase.GoogleResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

internal class AuthMenuScreenModel(
    private val firebaseHelper: FirebaseHelper,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel {

    fun getGoogleClient() = firebaseHelper.getGoogleClient()

    fun handleGoogleTask(task: Task<GoogleSignInAccount>) {
        screenModelScope.launch(dispatcher) {
            val result = firebaseHelper.handleGoogleResult(task)

            when (result) {
                is GoogleResult.Error -> print(result)
                is GoogleResult.Success -> print(result)
            }
        }
    }
}