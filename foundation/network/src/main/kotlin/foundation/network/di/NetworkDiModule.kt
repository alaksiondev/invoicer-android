package foundation.network.di

import foundation.network.client.HttpWrapper
import org.koin.dsl.module

val networkDiModule = module {
    factory {
        HttpWrapper(
            tokenRepository = get()
        )
    }
}