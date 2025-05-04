package io.github.alaksion.invoicer.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import foundation.designsystem.theme.InvoicerTheme
import foundation.navigation.InvoicerScreen
import foundation.watchers.AuthEvent
import foundation.watchers.AuthEventBus
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val authEventBus: AuthEventBus by inject()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        forceLightStatusBar()
        mainViewModel.startApp()
        setContent { AppContent() }
    }

    private fun forceLightStatusBar() {
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false
    }

    @Composable
    private fun AuthEventEffect(
        bus: AuthEventBus,
        onSignIn: () -> Unit,
        onSignOff: () -> Unit
    ) {
        LaunchedEffect(Unit) {
            bus.subscribe().collect {
                when (it) {
                    AuthEvent.SignedIn -> onSignIn()
                    AuthEvent.SignedOut -> onSignOff()
                }
            }
        }
    }

    @Composable
    private fun AppContent() {
        InvoicerTheme {
            Navigator(
                screens = listOf(MainScreen()),
            ) { navigator ->
                SlideTransition(navigator)

                AuthEventEffect(
                    bus = authEventBus,
                    onSignIn = {
                        navigator.replaceAll(
                            ScreenRegistry.get(InvoicerScreen.Home)
                        )
                    },
                    onSignOff = {
                        navigator.replaceAll(
                            ScreenRegistry.get(InvoicerScreen.Auth.AuthMenu)
                        )
                    }
                )
            }
        }
    }
}