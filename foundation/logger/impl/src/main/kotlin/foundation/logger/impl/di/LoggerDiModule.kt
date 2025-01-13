package foundation.logger.impl.di

import foundation.logger.BuildConfig
import foundation.logger.impl.InvoicerLogger
import foundation.logger.impl.InvoicerLoggerImpl
import foundation.logger.impl.MutedLogger
import org.koin.dsl.module

val foundationLoggerModule = module {
    factory<InvoicerLogger> {
        if (BuildConfig.DEBUG) {
            InvoicerLoggerImpl
        } else {
            MutedLogger
        }
    }
}