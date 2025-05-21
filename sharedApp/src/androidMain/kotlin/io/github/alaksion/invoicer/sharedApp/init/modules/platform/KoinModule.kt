package io.github.alaksion.invoicer.sharedApp.init.modules.platform

import android.app.Application
import io.github.alaksion.invoicer.sharedApp.init.di.appModule
import io.github.alaksion.invoicer.sharedApp.init.modules.ModuleInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

actual class KoinModule(
    private val application: Application
) : ModuleInitializer {
    override fun onStart() {
        startKoin {
            androidContext(application)
            modules(appModule)
        }
    }
}