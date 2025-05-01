package features.auth.presentation.firebase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task

internal interface FirebaseHelper {
    fun getGoogleClient(): GoogleSignInClient
    suspend fun handleGoogleResult(task: Task<GoogleSignInAccount>): GoogleResult
}

internal class FirebaseHelperImpl(
    private val googleFirebaseHelper: GoogleFirebaseHelper
) : FirebaseHelper {
    override fun getGoogleClient() = googleFirebaseHelper.getSignInClient()

    override suspend fun handleGoogleResult(task: Task<GoogleSignInAccount>) =
        googleFirebaseHelper.handleAuthResult(task)
}