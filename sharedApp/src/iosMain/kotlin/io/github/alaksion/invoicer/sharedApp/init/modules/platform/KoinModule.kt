package io.github.alaksion.invoicer.sharedApp.init.modules.platform

import io.github.alaksion.invoicer.sharedApp.init.di.appModule
import io.github.alaksion.invoicer.sharedApp.init.modules.ModuleInitializer
import org.koin.core.context.startKoin

actual class KoinModule : ModuleInitializer {
    override fun onStart() {
        startKoin {
            modules(appModule)
        }
    }
}