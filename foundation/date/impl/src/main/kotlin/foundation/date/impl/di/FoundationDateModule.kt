package foundation.date.impl.di

import foundation.date.impl.DateProvider
import foundation.date.impl.DateProviderImpl
import org.koin.dsl.module

val foundationDateModule = module {
    factory<DateProvider> { DateProviderImpl }
}