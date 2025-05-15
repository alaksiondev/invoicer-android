package io.github.alaksion.invoicer.foundation.utils.di

import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLogger
import io.github.alaksion.invoicer.foundation.utils.logger.IosInvoicerLogger
import org.koin.core.module.Module
import org.koin.dsl.module

actual val utilPlatformModule: Module = module {
    factory<InvoicerLogger> {
        IosInvoicerLogger()
    }
}