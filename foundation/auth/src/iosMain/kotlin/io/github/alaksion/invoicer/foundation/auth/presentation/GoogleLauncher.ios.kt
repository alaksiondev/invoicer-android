package io.github.alaksion.invoicer.foundation.auth.presentation

import androidx.compose.runtime.Composable

internal class IosGoogleLauncher : GoogleLauncher {
    override suspend fun launch() {
        // No-op
    }
}

@Composable
actual fun rememberGoogleLauncher(
    onSuccess: (String) -> Unit,
    onFailure: (Throwable) -> Unit,
    onCancel: () -> Unit
): GoogleLauncher {
    return IosGoogleLauncher()
}