package features.auth.presentation.firebase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

internal class FirebaseHelper(
    private val googleFirebaseHelper: GoogleFirebaseHelper
) {
    fun getGoogleClient() = googleFirebaseHelper.getSignInClient()

    suspend fun handleGoogleResult(task: Task<GoogleSignInAccount>) =
        googleFirebaseHelper.handleAuthResult(task)
}