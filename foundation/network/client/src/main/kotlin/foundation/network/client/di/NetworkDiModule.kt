package foundation.network.client.di

import foundation.network.client.okHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkDiModule = module {
    single<HttpClient> { okHttpClient }
}