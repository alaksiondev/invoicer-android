package io.github.alaksion.invoicer.foundation.auth.firebase

interface IosGoogleFirebaseHelper {
    suspend fun getGoogleIdToken(): IosGoogleResult
}

sealed interface IosGoogleResult {
    data class Success(val token: String) : IosGoogleResult
    data class Error(val exception: Exception) : IosGoogleResult
    data object Cancelled : IosGoogleResult
}