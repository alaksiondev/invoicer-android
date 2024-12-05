package features.home.presentation.container

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable

@Composable
internal fun HomeBottomBar(
    selectedTab: HomeContainerTabs,
    onSelectTab: (HomeContainerTabs) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == HomeContainerTabs.Home,
            onClick = { onSelectTab(HomeContainerTabs.Home) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null
                )
            }
        )

        NavigationBarItem(
            selected = selectedTab == HomeContainerTabs.Settings,
            onClick = { onSelectTab(HomeContainerTabs.Settings) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null
                )
            }
        )
    }
}