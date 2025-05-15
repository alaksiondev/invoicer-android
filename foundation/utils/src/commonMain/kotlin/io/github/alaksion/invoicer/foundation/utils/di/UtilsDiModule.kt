package io.github.alaksion.invoicer.foundation.utils.di

import io.github.alaksion.invoicer.foundation.utils.date.DateProvider
import io.github.alaksion.invoicer.foundation.utils.date.DateProviderImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val utilsDiModule = module {
    factory<DateProvider> { DateProviderImpl }
    includes(utilPlatformModule)
}

internal expect val utilPlatformModule: Module
