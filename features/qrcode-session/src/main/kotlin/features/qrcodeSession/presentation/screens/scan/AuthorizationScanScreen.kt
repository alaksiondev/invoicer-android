package features.qrcodeSession.presentation.screens.scan

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.google.common.util.concurrent.ListenableFuture
import features.qrcodeSession.presentation.barcodeanalyzer.QrCodeAnalyzer
import features.qrcodeSession.presentation.barcodeanalyzer.rememberQrCodeAnalyzer
import foundation.ui.events.EventEffect
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

internal class AuthorizationScanScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AuthorizationScanScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val snackBarHost = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        val analyzer = rememberQrCodeAnalyzer(
            onSuccess = screenModel::onScanSuccess,
            onError = { screenModel.onScanError() }
        )

        StateContent(
            qrCodeAnalyzer = analyzer,
            state = state,
            snackBarHostState = snackBarHost
        )

        EventEffect(screenModel) { event ->
            when (event) {
                AuthorizationScanEvents.CodeNotFound -> Unit
                AuthorizationScanEvents.InvalidCode -> scope.launch {
                    snackBarHost.showSnackbar(message = "Invalid QrCode")

                }

                AuthorizationScanEvents.UnknownError -> Unit
            }

        }
    }

    @Composable
    fun StateContent(
        qrCodeAnalyzer: QrCodeAnalyzer,
        state: AuthorizationScanState,
        snackBarHostState: SnackbarHostState
    ) {
        val context = LocalContext.current
        val lifecycle = LocalLifecycleOwner.current
        var preview by remember { mutableStateOf<Preview?>(null) }

        var hasCameraPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
        }

        val launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { result ->
                hasCameraPermission = result
            }

        Scaffold(
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier.padding(scaffoldPadding)
            ) {
                if (hasCameraPermission) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
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
                                        it.setAnalyzer(cameraExecutor, qrCodeAnalyzer)
                                    }
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
                } else {
                    Button(
                        onClick = { launcher.launch(Manifest.permission.CAMERA) }
                    ) {
                        Text("Request Camera Permission")
                    }
                }
            }
        }
    }
}