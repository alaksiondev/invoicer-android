package io.github.alaksion.features.home.presentation.tabs.settings

import androidx.compose.runtime.Composable

internal data class SettingsCallbacks(
    val onRequestSignOut: () -> Unit,
    val onConfirmSignOut: () -> Unit,
    val onCancelSignOut: () -> Unit,
    val goToAuthorizations: () -> Unit
)

@Composable
internal fun rememberSettingsCallbacks(
    onSignOut: () -> Unit,
    onCancelSignOut: () -> Unit,
    onConfirmSignOut: () -> Unit,
    goToAuthorizations: () -> Unit,
): SettingsCallbacks {
    return SettingsCallbacks(
        onRequestSignOut = onSignOut,
        onConfirmSignOut = onConfirmSignOut,
        onCancelSignOut = onCancelSignOut,
        goToAuthorizations = goToAuthorizations
    )
}
