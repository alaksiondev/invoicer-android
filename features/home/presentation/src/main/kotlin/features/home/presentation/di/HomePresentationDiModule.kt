package features.home.presentation.di

import features.home.presentation.tabs.settings.SettingsScreenModel
import org.koin.dsl.module

val homePresentationDiModule = module {
    factory<SettingsScreenModel> {
        SettingsScreenModel(
            authRepository = get(),
            authEventPublisher = get()
        )
    }
}