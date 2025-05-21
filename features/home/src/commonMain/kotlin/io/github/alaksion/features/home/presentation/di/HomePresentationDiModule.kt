package io.github.alaksion.features.home.presentation.di

import io.github.alaksion.features.home.presentation.tabs.settings.SettingsScreenModel
import org.koin.dsl.module

val homePresentationDiModule = module {
    factory<SettingsScreenModel> {
        SettingsScreenModel(
            signOutService = get()
        )
    }
}
