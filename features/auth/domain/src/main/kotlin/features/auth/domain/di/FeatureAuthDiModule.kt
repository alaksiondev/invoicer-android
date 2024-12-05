package features.auth.domain.di

import features.auth.domain.usecase.SignInUseCase
import features.auth.domain.usecase.SignInUseCaseImpl
import org.koin.dsl.module

val featureAuthDomainModule = module {
    factory<SignInUseCase> {
        SignInUseCaseImpl(
            authRepository = get(),
            authStorage = get()
        )
    }
}