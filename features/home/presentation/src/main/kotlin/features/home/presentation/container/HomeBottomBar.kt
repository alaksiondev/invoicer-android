package features.home.presentation.container

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import features.home.presentation.tabs.SettingsTab
import features.home.presentation.tabs.WelcomeTab

@Composable
internal fun HomeBottomBar(
    selectedTab: Tab,
    onSelectTab: (Tab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab is WelcomeTab,
            onClick = { onSelectTab(WelcomeTab) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null
                )
            }
        )

        NavigationBarItem(
            selected = selectedTab is SettingsTab,
            onClick = { onSelectTab(SettingsTab) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null
                )
            }
        )
    }
}