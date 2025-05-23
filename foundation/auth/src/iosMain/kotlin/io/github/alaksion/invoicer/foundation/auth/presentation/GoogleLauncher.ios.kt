package io.github.alaksion.invoicer.foundation.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import io.github.alaksion.invoicer.foundation.auth.firebase.IosGoogleFirebaseHelper
import io.github.alaksion.invoicer.foundation.auth.firebase.IosGoogleResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.mp.KoinPlatform

internal class IosGoogleLauncher : GoogleLauncher {

    private val commandFlow = MutableSharedFlow<Unit>()

    override suspend fun launch() {
        commandFlow.emit(Unit)
    }

    fun subscribe(): Flow<Unit> = commandFlow
}

@Composable
actual fun rememberGoogleLauncher(
    onSuccess: (String) -> Unit,
    onFailure: (Throwable) -> Unit,
    onCancel: () -> Unit
): GoogleLauncher {
    val launcher = remember { IosGoogleLauncher() }
    val helper: IosGoogleFirebaseHelper = remember { KoinPlatform.getKoin().get() }

    LaunchedEffect(launcher) {
        launcher.subscribe().collect {
            val result = helper.getGoogleIdToken()
            when (result) {
                IosGoogleResult.Cancelled -> onCancel()
                is IosGoogleResult.Error -> onFailure(result.exception)
                is IosGoogleResult.Success -> onSuccess(result.token)
            }
        }
    }

    return launcher
}