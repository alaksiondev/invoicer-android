package features.home.presentation.tabs.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
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
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import features.auth.design.system.components.spacer.Spacer
import features.home.presentation.R
import features.home.presentation.tabs.settings.components.SignOutDialog
import foundation.design.system.tokens.Spacing

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
        val viewModel = koinScreenModel<SettingsScreenModel>()
        var signOutDialogState by remember { mutableStateOf(false) }

        val callBacks = rememberSettingsCallbacks(
            onSignOut = { signOutDialogState = true },
            onConfirmSignOut = {
                signOutDialogState = false
                viewModel.signOut()
            },
            onCancelSignOut = { signOutDialogState = false }
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