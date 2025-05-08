package io.github.alaksion.invoicer.features.auth.presentation.firebase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import io.github.alasion.invoicer.features.auth.presentation.BuildConfig
import kotlinx.coroutines.tasks.await


internal class GoogleFirebaseHelper(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth
) {
    fun getSignInClient(): GoogleSignInClient {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.FIREBASE_WEB_ID)
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(
            context,
            options
        )

        client.revokeAccess()

        return client
    }

    suspend fun handleAuthResult(task: Task<GoogleSignInAccount>): GoogleResult {
        return runCatching {
            task.getResult(ApiException::class.java)
        }.fold(
            onSuccess = { account ->
                val credential = GoogleAuthProvider.getCredential(
                    account.idToken,
                    null
                )
                firebaseAuth.signInWithCredential(credential).await()

                val user = firebaseAuth.currentUser ?: return@fold GoogleResult.Error(null)
                val token = getToken(user)

                if (token == null) {
                    GoogleResult.Error(null)
                } else {
                    GoogleResult.Success(token)
                }
            },
            onFailure = {
                GoogleResult.Error(it)
            }
        )
    }

    suspend fun getToken(user: FirebaseUser): String? {
        return runCatching {
            user.getIdToken(false).await().token
        }.fold(
            onSuccess = { it },
            onFailure = { null }
        )
    }
}

internal sealed interface GoogleResult {
    data class Success(val token: String) : GoogleResult
    data class Error(val error: Throwable?) : GoogleResult
}