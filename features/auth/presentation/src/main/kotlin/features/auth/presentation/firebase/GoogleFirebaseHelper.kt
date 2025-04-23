package features.auth.presentation.firebase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import features.auth.presentation.BuildConfig


class GoogleFirebaseHelper(
    private val context: Context
) {
    fun getSignInClient(): GoogleSignInClient {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.FIREBASE_WEB_ID)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(
            context,
            options
        )
    }
}