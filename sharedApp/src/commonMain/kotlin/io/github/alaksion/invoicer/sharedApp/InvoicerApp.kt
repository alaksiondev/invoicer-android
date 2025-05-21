package io.github.alaksion.invoicer.sharedApp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.foundation.watchers.AuthEvent
import io.github.alaksion.invoicer.foundation.watchers.AuthEventBus
import org.koin.mp.KoinPlatform

@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun InvoicerApp() {
    val authEventBus: AuthEventBus = remember { KoinPlatform.getKoin().get() }

    InvoicerTheme {
        Navigator(
            screen = ScreenRegistry.get(InvoicerScreen.Auth.Startup),
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