package io.github.alaksion.invoicer.sharedApp.init

import io.github.alaksion.invoicer.sharedApp.init.modules.ModuleInitializer
import io.github.alaksion.invoicer.sharedApp.init.modules.common.VoyagerModule

class AppInit(private vararg val initializers: ModuleInitializer) {
    fun startAppModules() {
        VoyagerModule().onStart()
        initializers.forEach { it.onStart() }
    }
}