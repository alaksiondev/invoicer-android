package io.github.alaksion.invoicer.foundation.auth.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import io.github.alaksion.invoicer.foundation.auth.firebase.AndroidFirebaseHelper
import io.github.alaksion.invoicer.foundation.auth.firebase.FirebaseHelper
import org.koin.core.module.Module
import org.koin.dsl.module

actual val authPlatformModule: Module = module {
    factory<FirebaseHelper> {
        AndroidFirebaseHelper(
            firebaseAuth = Firebase.auth
        )
    }
}