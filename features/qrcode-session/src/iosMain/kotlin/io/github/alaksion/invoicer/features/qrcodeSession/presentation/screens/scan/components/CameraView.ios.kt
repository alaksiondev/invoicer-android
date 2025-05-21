package io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.scan.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun QrCodeCameraView(
    modifier: Modifier,
    onScan: (String) -> Unit,
    onFail: (Throwable) -> Unit,
) {
    Text("I'm not ready yet XD")
}