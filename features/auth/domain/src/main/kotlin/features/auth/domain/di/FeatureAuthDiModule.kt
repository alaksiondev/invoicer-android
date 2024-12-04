package features.auth.domain.di

import features.auth.domain.storage.AuthStorage
import features.auth.domain.storage.AuthStorageImpl
import org.koin.dsl.module

val featureAuthDomainModule = module {
    factory<AuthStorage> {
        AuthStorageImpl(
            localStorage = get()
        )
    }
}