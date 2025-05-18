package io.github.alaksion.invoicer.foundation.network.di

import io.github.alaksion.invoicer.foundation.network.client.HttpWrapper
import org.koin.dsl.module

val networkDiModule = module {
    single { HttpWrapper }
}
