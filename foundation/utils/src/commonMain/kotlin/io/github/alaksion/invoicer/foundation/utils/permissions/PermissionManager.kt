package io.github.alaksion.invoicer.foundation.utils.permissions

import androidx.compose.runtime.Composable

interface PermissionRequester {
    suspend fun requestPermissionDialog(
        permissionType: PermissionType,
    )
}

enum class PermissionType {
    CAMERA,
}

data class PermissionResult(
    val permissionType: PermissionType,
    val isGranted: Boolean,
)

@Composable
expect fun rememberPermissionRequester(
    onResult: (PermissionResult) -> Unit
): PermissionRequester

@Composable
expect fun checkPermission(permissionType: PermissionType): Boolean