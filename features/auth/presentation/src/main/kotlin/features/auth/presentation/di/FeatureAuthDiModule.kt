package features.auth.presentation.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import features.auth.presentation.firebase.FirebaseHelper
import features.auth.presentation.firebase.FirebaseHelperImpl
import features.auth.presentation.firebase.GoogleFirebaseHelper
import features.auth.presentation.screens.login.LoginScreenModel
import features.auth.presentation.screens.signup.SignUpScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

val featureAuthPresentationDiModule = module {
    viewModelBindings()
}

private fun Module.viewModelBindings() {
    factory {
        SignUpScreenModel(
            authRepository = get(),
            dispatcher = Dispatchers.Default,
            emailValidator = get()
        )
    }

    factory {
        LoginScreenModel(
            authRepository = get(),
            authEventPublisher = get(),
            firebaseHelper = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        GoogleFirebaseHelper(
            context = get(),
            firebaseAuth = Firebase.auth
        )
    }

    factory<FirebaseHelper> {
        FirebaseHelperImpl(
            googleFirebaseHelper = get()
        )
    }
}