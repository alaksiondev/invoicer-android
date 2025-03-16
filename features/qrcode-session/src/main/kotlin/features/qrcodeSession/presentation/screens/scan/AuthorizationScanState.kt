package features.qrcodeSession.presentation.screens.scan

internal data class AuthorizationScanState(
    val screenType: AuthorizationScanMode = AuthorizationScanMode.CameraView,
    val qrCodeAgent: String = "",
    val qrCodeIp: String = "",
    val qrCodeExpiration: String = "",
    val qrCodeEmission: String = "",
)


internal enum class AuthorizationScanMode {
    Loading,
    QrCodeContent,
    CameraView,
}

internal sealed interface AuthorizationScanEvents {
    data object InvalidCode : AuthorizationScanEvents
    data object CodeNotFound : AuthorizationScanEvents
    data object UnknownError : AuthorizationScanEvents
}