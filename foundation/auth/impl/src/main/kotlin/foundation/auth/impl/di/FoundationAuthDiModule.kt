package foundation.auth.impl.di

import foundation.auth.impl.watcher.AuthEventPublisher
import foundation.auth.impl.watcher.AuthEventSubscriber
import foundation.auth.impl.watcher.AuthManager
import org.koin.dsl.module

val foundationAuthPresentationDiModule = module {
    single<AuthEventSubscriber> { AuthManager }
    single<AuthEventPublisher> { AuthManager }
}