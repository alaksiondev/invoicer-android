package features.home.presentation.tabs.welcome

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import features.home.presentation.tabs.welcome.components.WelcomeActions
import foundation.design.system.tokens.Spacing

internal object WelcomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "",
            icon = null
        )

    @Composable
    override fun Content() {
        StateContent()
    }

    @Composable
    fun StateContent() {
        Scaffold {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(Spacing.medium)
            ) {
                item {
                    WelcomeActions(modifier = Modifier.fillParentMaxWidth())
                }
            }
        }
    }

}