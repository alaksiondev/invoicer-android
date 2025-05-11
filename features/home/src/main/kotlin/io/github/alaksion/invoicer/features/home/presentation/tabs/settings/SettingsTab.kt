package io.github.alaksion.invoicer.features.home.presentation.tabs.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import features.home.presentation.R
import foundation.designsystem.components.spacer.Spacer
import foundation.designsystem.tokens.Spacing
import foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.home.presentation.tabs.settings.components.SettingsItem
import io.github.alaksion.invoicer.features.home.presentation.tabs.settings.components.SignOutDialog

internal object SettingsTab : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "",
            icon = null
        )


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current?.parent
        val viewModel = koinScreenModel<SettingsScreenModel>()
        var signOutDialogState by remember { mutableStateOf(false) }

        val callBacks = rememberSettingsCallbacks(
            onSignOut = { signOutDialogState = true },
            onConfirmSignOut = {
                signOutDialogState = false
                viewModel.signOut()
            },
            onCancelSignOut = { signOutDialogState = false },
            goToAuthorizations = {
                navigator?.push(ScreenRegistry.get(InvoicerScreen.Authorization.Home))
            }
        )

        StateContent(
            callbacks = callBacks,
            isDialogVisible = signOutDialogState
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        callbacks: SettingsCallbacks,
        isDialogVisible: Boolean,
    ) {
        Scaffold(
            modifier = Modifier.background(Color.Red),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.home_settings_label))
                    },
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(Spacing.medium)
            ) {
                SettingsItem(
                    modifier = Modifier.fillMaxWidth(),
                    content = stringResource(R.string.home_settings_authorization),
                    icon = Icons.Outlined.QrCodeScanner,
                    onClick = callbacks.goToAuthorizations
                )
                Spacer(1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = callbacks.onRequestSignOut,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Logout,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.home_settings_sign_out)
                    )
                }
            }
        }
        SignOutDialog(
            onDismiss = callbacks.onCancelSignOut,
            onConfirm = callbacks.onConfirmSignOut,
            isVisible = isDialogVisible
        )
    }
}
