package io.github.alaksion.invoicer.features.home.presentation.container

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import io.github.alaksion.invoicer.features.home.presentation.tabs.settings.SettingsTab
import io.github.alaksion.invoicer.features.home.presentation.tabs.welcome.HomeTab

@Composable
internal fun HomeBottomBar(
    selectedTab: Tab,
    onSelectTab: (Tab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab is HomeTab,
            onClick = { onSelectTab(HomeTab) },
            label = {
                Text(
                    text = "Home",
                )
            },
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
            },
            label = {
                Text(
                    text = "Home",
                )
            },
        )
    }
}