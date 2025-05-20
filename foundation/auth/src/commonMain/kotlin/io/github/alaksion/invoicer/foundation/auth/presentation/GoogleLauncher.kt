package io.github.alaksion.invoicer.foundation.auth.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGoogleLauncher(
    onSuccess: (String) -> Unit,
    onFailure: (Throwable) -> Unit,
    onCancel: () -> Unit
): GoogleLauncher


interface GoogleLauncher {
    suspend fun launch()
}