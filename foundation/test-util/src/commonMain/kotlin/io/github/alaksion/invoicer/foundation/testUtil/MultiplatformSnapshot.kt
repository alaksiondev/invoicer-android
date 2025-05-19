package io.github.alaksion.invoicer.foundation.testUtil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.PreviewContextConfigurationEffect

// Workaround because android context fails to initialize when the test relies on compose resources.
@OptIn(ExperimentalResourceApi::class)
@Composable
fun MultiplatformSnapshot(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewContextConfigurationEffect()
        content()
    }
}