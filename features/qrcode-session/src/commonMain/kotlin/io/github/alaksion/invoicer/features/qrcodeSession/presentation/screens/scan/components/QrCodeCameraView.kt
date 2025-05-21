package io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.scan.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal expect fun QrCodeCameraView(
    modifier: Modifier = Modifier,
    onScan: (String) -> Unit,
    onFail: (Throwable) -> Unit,
)