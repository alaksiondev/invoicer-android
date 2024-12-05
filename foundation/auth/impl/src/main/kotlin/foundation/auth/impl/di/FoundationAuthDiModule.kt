package foundation.auth.impl.di

import foundation.auth.impl.storage.AuthStorage
import foundation.auth.impl.storage.AuthStorageImpl
import foundation.auth.impl.watcher.AuthEventPublisher
import foundation.auth.impl.watcher.AuthEventSubscriber
import foundation.auth.impl.watcher.AuthManager
import org.koin.dsl.module

val foundationAuthDiModule = module {
    factory<AuthStorage> {
        AuthStorageImpl(
            localStorage = get()
        )
    }

    single<AuthEventSubscriber> { AuthManager }
    single<AuthEventPublisher> { AuthManager }
}