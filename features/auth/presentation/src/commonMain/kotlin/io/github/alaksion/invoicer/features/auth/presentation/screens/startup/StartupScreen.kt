package io.github.alaksion.invoicer.features.auth.presentation.screens.startup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class StartupScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<StartupScreenModel>()
        LaunchedEffect(viewModel) { viewModel.startApp() }

        Scaffold {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(it), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}