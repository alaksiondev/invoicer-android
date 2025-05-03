package io.github.alaksion.invoicer.main

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val mainDiModule = module {
    viewModel {
        MainViewModel(
            authRepository = get(),
            authTokenRepository = get(),
            logger = get()
        )
    }
}