package io.github.alaksion.invoicer.foundation.utils.di

import io.github.alaksion.invoicer.foundation.utils.BuildConfig
import io.github.alaksion.invoicer.foundation.utils.logger.AndroidInvoicerLogger
import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLogger
import io.github.alaksion.invoicer.foundation.utils.logger.MutedLogger
import org.koin.core.module.Module
import org.koin.dsl.module

actual val utilPlatformModule: Module = module {
    factory<InvoicerLogger> {
        if (BuildConfig.DEBUG) MutedLogger
        else AndroidInvoicerLogger()
    }
}