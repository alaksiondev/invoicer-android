package features.auth.domain.di

import features.auth.domain.usecase.SignUpUse
import features.auth.domain.usecase.SignUpUseImpl
import org.koin.dsl.module

val featureAuthDomainModule = module {
    factory<SignUpUse> { SignUpUseImpl(repository = get()) }
}