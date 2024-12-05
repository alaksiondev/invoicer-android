package features.auth.domain.di

import features.auth.domain.usecase.SignInUseCase
import features.auth.domain.usecase.SignInUseCaseImpl
import features.auth.domain.usecase.SignOutUseCase
import features.auth.domain.usecase.SignOutUseCaseImpl
import org.koin.dsl.module

val featureAuthDomainModule = module {
    factory<SignInUseCase> {
        SignInUseCaseImpl(
            authRepository = get(),
            authStorage = get()
        )
    }

    factory<SignOutUseCase> {
        SignOutUseCaseImpl(
            authStorage = get()
        )
    }
}