package io.github.alaksion.invoicer

import android.app.Application
import io.github.alaksion.invoicer.sharedApp.init.AppInit
import io.github.alaksion.invoicer.sharedApp.init.modules.platform.KoinModule

class InvoicerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInit(
            KoinModule(this)
        ).startAppModules()
    }
}