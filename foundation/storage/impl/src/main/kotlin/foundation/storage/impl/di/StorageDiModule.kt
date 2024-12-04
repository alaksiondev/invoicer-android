package foundation.storage.impl.di

import foundation.storage.impl.LocalStorage
import foundation.storage.impl.LocalStorageImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageDiModule = module {
    factory<LocalStorage> {
        LocalStorageImpl(
            context = androidContext()
        )
    }
}