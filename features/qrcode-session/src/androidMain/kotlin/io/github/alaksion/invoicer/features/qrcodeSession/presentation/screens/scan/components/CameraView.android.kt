package io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.scan.components

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import io.github.alaksion.invoicer.features.qrcodeSession.presentation.analyzer.rememberQrCodeAnalyzer
import java.util.concurrent.Executors

@Composable
actual fun QrCodeCameraView(
    modifier: Modifier,
    onScan: (String) -> Unit,
    onFail: (Throwable) -> Unit,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }

    val analyzer = rememberQrCodeAnalyzer(
        onSuccess = onScan,
        onError = onFail
    )

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val previewView = PreviewView(context)
            with(previewView) {
                scaleType = PreviewView.ScaleType.FIT_START
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
            previewView
        },
        update = { view ->
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor = Executors.newSingleThreadScheduledExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.surfaceProvider = view.surfaceProvider
                }

                val cameraProvider: ProcessCameraProvider =
                    cameraProviderFuture.get()

                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, analyzer)
                    }
                @Suppress("TooGenericExceptionCaught")
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycle,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}