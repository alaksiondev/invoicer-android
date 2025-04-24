package features.auth.presentation.screens.menu

internal data class AuthMenuState(
    val isSocialLoginLoading: Boolean = false
)

internal sealed interface AuthMenuEvents {
    data class GoogleAuthFailure(val error: String) : AuthMenuEvents
}