package features.home.presentation.container

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

internal class HomeContainerScreen : Screen {
    @Composable
    override fun Content() {
        val currentTab = remember { mutableStateOf(HomeContainerTabs.Home) }

        StateContent(
            selectedTab = currentTab.value,
            onSelectTab = { currentTab.value = it }
        )
    }

    @Composable
    internal fun StateContent(
        selectedTab: HomeContainerTabs,
        onSelectTab: (HomeContainerTabs) -> Unit
    ) {
        Scaffold(
            bottomBar = {
                HomeBottomBar(
                    selectedTab = selectedTab,
                    onSelectTab = onSelectTab
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Text("I'm home screen")
            }
        }
    }
}

internal enum class HomeContainerTabs {
    Home,
    Settings
}