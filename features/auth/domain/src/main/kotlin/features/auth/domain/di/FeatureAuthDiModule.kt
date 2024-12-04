package features.auth.domain.di

import features.auth.domain.storage.AuthStorage
import features.auth.domain.storage.AuthStorageImpl
import features.auth.domain.usecase.SignInUseCase
import features.auth.domain.usecase.SignInUseCaseImpl
import org.koin.dsl.module

val featureAuthDomainModule = module {
    factory<AuthStorage> {
        AuthStorageImpl(
            localStorage = get()
        )
    }

    factory<SignInUseCase> {
        SignInUseCaseImpl(
            authRepository = get(),
            authStorage = get()
        )
    }
}