package io.github.alaksion.invoicer.foundation.utils.di

import io.github.alaksion.invoicer.foundation.utils.BuildConfig
import io.github.alaksion.invoicer.foundation.utils.date.DateProvider
import io.github.alaksion.invoicer.foundation.utils.date.DateProviderImpl
import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLogger
import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLoggerImpl
import io.github.alaksion.invoicer.foundation.utils.logger.MutedLogger
import org.koin.dsl.module

val utilsDiModule = module {
    factory<DateProvider> { DateProviderImpl }

    factory<InvoicerLogger> {
        if (BuildConfig.DEBUG) {
            InvoicerLoggerImpl
        } else {
            MutedLogger
        }
    }
}