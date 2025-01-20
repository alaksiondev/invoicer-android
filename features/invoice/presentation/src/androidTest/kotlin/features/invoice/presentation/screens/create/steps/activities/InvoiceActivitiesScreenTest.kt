package features.invoice.presentation.screens.create.steps.activities

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import features.auth.design.system.components.preview.ThemeContainer
import features.invoice.presentation.screens.create.CreateInvoiceManager
import foundation.date.FakeDateProvider
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class InvoiceActivitiesScreenTest : KoinTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var manager: CreateInvoiceManager
    private lateinit var dateProvider: FakeDateProvider

    @Before
    fun setUp() {
        dateProvider = FakeDateProvider()
        manager = CreateInvoiceManager(dateProvider)

        startKoin {
            modules(
                module {
                    factory {
                        InvoiceActivitiesScreenModel(
                            dispatcher = dispatcher,
                            createInvoiceManager = manager
                        )
                    }
                }
            )
        }

    }


    @Test
    fun startingState() {
        composeRule.setContent {
            ThemeContainer {
                InvoiceActivitiesScreen().Content()
            }
        }

        composeRule.onNodeWithTag(InvoiceActivitiesScreen.TestTags.LIST)
            .assertExists()

        composeRule.onAllNodesWithTag(InvoiceActivitiesScreen.TestTags.LIST_ITEM)
            .assertCountEquals(0)
    }

    @Test
    fun addNewActivity() {

    }

}