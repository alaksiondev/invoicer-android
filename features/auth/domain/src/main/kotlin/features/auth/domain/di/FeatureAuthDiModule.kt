package features.auth.domain.di

import features.auth.domain.usecase.SignUpUseCase
import features.auth.domain.usecase.SignUpUseCaseImpl
import org.koin.dsl.module

val featureAuthDomainModule = module {
    factory<SignUpUseCase> { SignUpUseCaseImpl(repository = get()) }
}