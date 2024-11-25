package foundation.network.di

import foundation.network.NetworkClientImpl
import foundation.network.okHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkDiModule = module {
    factory {
        NetworkClientImpl(httpClient = get())
    }

    single<HttpClient> { okHttpClient }
}