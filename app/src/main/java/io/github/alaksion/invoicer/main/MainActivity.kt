package io.github.alaksion.invoicer.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import foundation.auth.impl.watcher.AuthEvent
import foundation.auth.impl.watcher.AuthEventSubscriber
import foundation.design.system.theme.InvoicerTheme
import foundation.navigation.InvoicerScreen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val authSubscriber: AuthEventSubscriber by inject()
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
        subscriber: AuthEventSubscriber,
        onSignIn: () -> Unit,
        onSignOff: () -> Unit
    ) {
        LaunchedEffect(Unit) {
            subscriber.events.collect {
                when (it) {
                    AuthEvent.SignIn -> onSignIn()
                    is AuthEvent.SignOff -> onSignOff()
                }
            }
        }
    }

    @Composable
    private fun AppContent() {
        InvoicerTheme {
            val isLogged by mainViewModel.isUserLoggedIN.collectAsStateWithLifecycle()

            if (isLogged != null) {
                val startScreen = if (isLogged == true) {
                    InvoicerScreen.Home
                } else {
                    InvoicerScreen.Auth.AuthMenu
                }

                Navigator(
                    screens = listOf(ScreenRegistry.get(startScreen)),
                ) { navigator ->
                    SlideTransition(navigator)

                    AuthEventEffect(
                        subscriber = authSubscriber,
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
}