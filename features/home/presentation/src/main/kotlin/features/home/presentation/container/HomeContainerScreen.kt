package features.home.presentation.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import features.home.presentation.tabs.welcome.WelcomeTab

internal class HomeContainerScreen : Screen {
    @Composable
    override fun Content() {
        TabNavigator(WelcomeTab) { navigator ->
            Scaffold(
                bottomBar = {
                    HomeBottomBar(
                        selectedTab = navigator.current,
                        onSelectTab = { newTab ->
                            navigator.current = newTab
                        }
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    CurrentTab()
                }
            }
        }
    }
}