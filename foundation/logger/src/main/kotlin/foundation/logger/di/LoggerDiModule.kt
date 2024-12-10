package foundation.logger.di

import foundation.logger.BuildConfig
import foundation.logger.InvoicerLogger
import foundation.logger.InvoicerLoggerImpl
import foundation.logger.MutedLogger
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