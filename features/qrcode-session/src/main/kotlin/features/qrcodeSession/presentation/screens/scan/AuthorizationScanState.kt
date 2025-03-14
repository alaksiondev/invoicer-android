package features.qrcodeSession.presentation.screens.scan

internal data class AuthorizationScanState(
    val screenType: AuthorizationScanType = AuthorizationScanType.Scanner,
    val qrCodeAgent: String = "",
    val qrCodeIp: String = "",
    val qrCodeExpiration: String = "",
    val qrCodeEmission: String = "",
    val mode: AuthorizationScanMode = AuthorizationScanMode.Content
)

internal enum class AuthorizationScanType {
    Scanner,
    Confirmation
}

internal enum class AuthorizationScanMode {
    Loading,
    Content,
}

internal sealed interface AuthorizationScanEvents {
    data object InvalidCode : AuthorizationScanEvents
    data object CodeNotFound : AuthorizationScanEvents
    data object UnknownError : AuthorizationScanEvents
}