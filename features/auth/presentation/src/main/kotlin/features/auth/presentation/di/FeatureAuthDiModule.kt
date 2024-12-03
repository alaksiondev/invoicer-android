package features.auth.presentation.di

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
}