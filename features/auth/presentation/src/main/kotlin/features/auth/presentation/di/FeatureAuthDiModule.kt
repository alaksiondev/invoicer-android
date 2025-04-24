package features.auth.presentation.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import features.auth.presentation.firebase.FirebaseHelper
import features.auth.presentation.firebase.GoogleFirebaseHelper
import features.auth.presentation.screens.menu.AuthMenuScreenModel
import features.auth.presentation.screens.signin.SignInScreenModel
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
        SignInScreenModel(
            authRepository = get(),
            authEventPublisher = get()
        )
    }

    factory {
        AuthMenuScreenModel(
            firebaseHelper = get(),
            dispatcher = Dispatchers.IO,
            authRepository = get(),
            authEventPublisher = get()
        )
    }

    factory {
        GoogleFirebaseHelper(
            context = get(),
            firebaseAuth = Firebase.auth
        )
    }

    factory {
        FirebaseHelper(
            googleFirebaseHelper = get()
        )
    }
}