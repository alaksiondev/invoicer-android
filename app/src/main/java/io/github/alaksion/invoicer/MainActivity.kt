package io.github.alaksion.invoicer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import foundation.design.system.theme.InvoicerTheme
import foundation.navigation.InvoicerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InvoicerTheme {
                Navigator(
                    screens = listOf(ScreenRegistry.get(InvoicerScreen.Auth.AuthMenu)),
                )
            }
        }
    }
}