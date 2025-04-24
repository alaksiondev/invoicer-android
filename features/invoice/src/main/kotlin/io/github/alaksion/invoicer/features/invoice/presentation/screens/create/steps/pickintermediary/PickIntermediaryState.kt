package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary

import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class PickIntermediaryState(
    val beneficiaries: ImmutableList<IntermediaryModel> = persistentListOf(),
    val uiMode: PickIntermediaryUiMode = PickIntermediaryUiMode.Content,
    val selection: IntermediarySelection = IntermediarySelection.None
) {
    val buttonEnabled: Boolean = selection != IntermediarySelection.None
}

internal enum class PickIntermediaryUiMode {
    Content,
    Error,
    Loading,
}

internal enum class PickIntermediaryEvents {
    StartNewIntermediary,
    Continue
}

internal sealed interface IntermediarySelection {
    data object New : IntermediarySelection
    data object Ignore : IntermediarySelection
    data object None : IntermediarySelection
    data class Existing(val id: String) : IntermediarySelection
}