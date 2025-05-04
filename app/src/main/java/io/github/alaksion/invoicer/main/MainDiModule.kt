package io.github.alaksion.invoicer.main

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val mainDiModule = module {
    viewModel {
        MainViewModel(
            logger = get(),
            signInCommandManager = get(),
            signOutService = get()
        )
    }
}