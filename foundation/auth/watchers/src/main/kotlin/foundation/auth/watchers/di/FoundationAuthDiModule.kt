package foundation.auth.watchers.di

import foundation.auth.watchers.AuthEventPublisher
import foundation.auth.watchers.AuthEventSubscriber
import foundation.auth.watchers.AuthManager
import org.koin.dsl.module

val foundationAuthPresentationDiModule = module {
    single<AuthEventSubscriber> { AuthManager }
    single<AuthEventPublisher> { AuthManager }
}