package features.intermediary.presentation.di

import features.intermediary.presentation.screen.create.CreateIntermediaryScreenModel
import features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val intermediaryPresentationModule = module {
    factory {
        IntermediaryListScreenModel(
            intermediaryRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        CreateIntermediaryScreenModel(
            intermediaryRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }
}