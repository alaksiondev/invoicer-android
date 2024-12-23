package foundation.network.client.di

import foundation.network.client.HttpWrapper
import foundation.network.client.RefreshHttpWrapper
import org.koin.dsl.module

val networkDiModule = module {
    factory {
        HttpWrapper(
            tokenRepository = get()
        )
    }
    factory {
        RefreshHttpWrapper()
    }
}