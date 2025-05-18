package io.github.alaksion.invoicer.foundation.storage.di

import io.github.alaksion.invoicer.foundation.storage.AndroidLocalStorage
import io.github.alaksion.invoicer.foundation.storage.LocalStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformLocalStorageDiModule: Module = module {
    factory<LocalStorage> {
        AndroidLocalStorage(
            context = androidContext()
        )
    }
}