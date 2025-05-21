package io.github.alaksion.invoicer.features.qrcodeSession.presentation.analyzer

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@Composable
internal fun rememberQrCodeAnalyzer(
    onSuccess: (String) -> Unit,
    onError: (Throwable) -> Unit
): QrCodeAnalyzer {
    val listener = remember {
        object : QrCodeAnalyzerListener {
            override fun onSuccess(rawValue: String) = onSuccess(rawValue)

            override fun onFailure(error: Throwable) = onError(error)
        }
    }

    return remember {
        QrCodeAnalyzer().apply {
            addListener(listener)
        }
    }
}

internal interface QrCodeAnalyzerListener {
    fun onSuccess(rawValue: String)
    fun onFailure(error: Throwable)
}

internal class QrCodeAnalyzer : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()
    private val listeners = mutableListOf<QrCodeAnalyzerListener>()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->
                    listeners.forEach { listener ->
                        barcode.rawValue?.let { barcodeContent ->
                            listener.onSuccess(barcodeContent)
                        }
                    }
                }
            }
            .addOnFailureListener { error ->
                Log.e("QRScanner", "Error scanning QR code: $error")
                listeners.forEach { listener ->
                    listener.onFailure(error)
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    }

    fun addListener(listener: QrCodeAnalyzerListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: QrCodeAnalyzerListener) {
        listeners.remove(listener)
    }
}
