package features.auth.presentation.firebase

internal class FirebaseHelper(
    private val googleFirebaseHelper: GoogleFirebaseHelper
) {
    fun getGoogleClient() = googleFirebaseHelper.getSignInClient()
}