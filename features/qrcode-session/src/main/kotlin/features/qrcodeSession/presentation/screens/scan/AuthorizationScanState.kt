package features.qrcodeSession.presentation.screens.scan

internal data class AuthorizationScanState(
    val screenType: AuthorizationScanMode = AuthorizationScanMode.CameraView,
)


internal enum class AuthorizationScanMode {
    Loading,
    CameraView,
}

internal sealed interface AuthorizationScanEvents {
    data object InvalidCode : AuthorizationScanEvents
    data object CodeNotFound : AuthorizationScanEvents
    data class ProceedToConfirmation(val contentId: String) : AuthorizationScanEvents
}