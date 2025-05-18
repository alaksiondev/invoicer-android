package io.github.alaksion.invoicer.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import io.github.alaksion.invoicer.foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.foundation.watchers.AuthEvent
import io.github.alaksion.invoicer.foundation.watchers.AuthEventBus
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val authEventBus: AuthEventBus by inject()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainViewModel.startApp()
        setContent { AppContent() }
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

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    private fun AppContent() {
        InvoicerTheme {
            Navigator(
                screens = listOf(MainScreen()),
                disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false)
            ) { navigator ->
                SlideTransition(
                    navigator = navigator,
                    disposeScreenAfterTransitionEnd = true
                )

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