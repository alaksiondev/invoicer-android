package io.github.alaksion.invoicer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import foundation.design.system.theme.InvoicerTheme
import foundation.navigation.InvoicerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        forceLightStatusBar()
        setContent {
            InvoicerTheme {
                Navigator(
                    screens = listOf(ScreenRegistry.get(InvoicerScreen.Auth.AuthMenu)),
                ) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }

    private fun forceLightStatusBar() {
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false
    }
}