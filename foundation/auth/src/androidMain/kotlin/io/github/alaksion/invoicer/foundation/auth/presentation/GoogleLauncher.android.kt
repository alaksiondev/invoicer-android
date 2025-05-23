package io.github.alaksion.invoicer.foundation.auth.presentation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.github.alaksion.invoicer.foundation.auth.firebase.GoogleFirebaseHelper
import io.github.alaksion.invoicer.foundation.auth.firebase.GoogleResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform

internal class AndroidGoogleLauncher : GoogleLauncher {

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

    val launcher = remember { AndroidGoogleLauncher() }
    val scope = rememberCoroutineScope()
    val fireBaseHelper: GoogleFirebaseHelper = remember { KoinPlatform.getKoin().get() }

    val firebaseLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            scope.launch {
                val authResult = fireBaseHelper.handleAuthResult(task)
                when (authResult) {
                    is GoogleResult.Success -> {
                        onSuccess(authResult.token)
                    }

                    is GoogleResult.Error -> {
                        onFailure(
                            authResult.error ?: Throwable("Unknown error with Google Sign In")
                        )
                    }
                }
            }
        } else {
            onCancel()
        }
    }

    LaunchedEffect(launcher, fireBaseHelper) {
        launcher.subscribe().collect {
            val signInClient = fireBaseHelper.getSignInClient()
            val intent = signInClient.signInIntent
            firebaseLauncher.launch(intent)
        }
    }

    return launcher
}