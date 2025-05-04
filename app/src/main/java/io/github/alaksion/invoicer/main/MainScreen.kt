package io.github.alaksion.invoicer.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class MainScreen : Screen {

    @Composable
    override fun Content() {
        Scaffold {
            Box(Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}