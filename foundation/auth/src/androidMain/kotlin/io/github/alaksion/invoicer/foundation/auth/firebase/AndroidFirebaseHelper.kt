package io.github.alaksion.invoicer.foundation.auth.firebase

import com.google.firebase.auth.FirebaseAuth

internal class AndroidFirebaseHelper(
    private val firebaseAuth: FirebaseAuth
) : FirebaseHelper {
    override fun signOut() {
        firebaseAuth.signOut()
    }
}