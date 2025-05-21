package io.github.alaksion.invoicer.foundation.utils.permissions

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class AndroidPermissionRequester : PermissionRequester {

    private val commandFlow = MutableSharedFlow<PermissionType>()

    override suspend fun requestPermissionDialog(permissionType: PermissionType) {
        commandFlow.emit(permissionType)
    }

    fun subscribe(): Flow<PermissionType> = commandFlow

}

@Composable
actual fun rememberPermissionRequester(
    onResult: (PermissionResult) -> Unit,
): PermissionRequester {
    var currentRequest = remember { mutableStateOf<PermissionType?>(null) }

    val contract =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
        }

    val requester = remember { AndroidPermissionRequester() }

    LaunchedEffect(requester) {
        requester.subscribe().collect { permission ->
            currentRequest.value = permission
            contract.launch(permission.toAndroidPermission())
        }
    }

    return requester
}

@Composable
actual fun checkPermission(permissionType: PermissionType): Boolean {
    val context = LocalContext.current

    return ContextCompat.checkSelfPermission(
        context,
        permissionType.toAndroidPermission()
    ) == PackageManager.PERMISSION_GRANTED
}

private fun PermissionType.toAndroidPermission(): String {
    return when (this) {
        PermissionType.CAMERA -> android.Manifest.permission.CAMERA
    }
}