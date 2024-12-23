package foundation.network.client.di

import foundation.network.client.HttpWrapper
import org.koin.dsl.module

val networkDiModule = module {
    factory {
        HttpWrapper(
            tokenRepository = get()
        )
    }
}