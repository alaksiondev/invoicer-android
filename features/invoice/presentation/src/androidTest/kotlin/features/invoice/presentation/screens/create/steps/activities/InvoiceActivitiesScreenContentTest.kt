package features.invoice.presentation.screens.create.steps.activities

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import features.auth.design.system.components.preview.ThemeContainer
import features.invoice.presentation.screens.create.CreateInvoiceManager
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseFormTestTags
import features.invoice.presentation.screens.create.steps.activities.components.AddActivityBottomSheetTestTag
import features.invoice.presentation.screens.create.steps.activities.components.NewActivityCardTestTags
import features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import foundation.date.FakeDateProvider
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import kotlin.test.assertTrue

class InvoiceActivitiesScreenContentTest : KoinTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var manager: CreateInvoiceManager
    private lateinit var dateProvider: FakeDateProvider

    @Before
    fun setUp() {
        dateProvider = FakeDateProvider()
        manager = CreateInvoiceManager(dateProvider)
    }


    @Test
    fun startingState() {
        composeRule.setContent {
            ThemeContainer {
                InvoiceActivitiesScreen()
                    .ScreenContent(
                        screenModel = InvoiceActivitiesScreenModel(
                            dispatcher = dispatcher,
                            createInvoiceManager = manager
                        ),
                        onBack = {},
                        onContinue = {}

                    )
            }
        }

        composeRule.onNodeWithTag(InvoiceActivitiesScreen.TestTags.LIST)
            .assertExists()

        composeRule.onAllNodesWithTag(InvoiceActivitiesScreen.TestTags.LIST_ITEM)
            .assertCountEquals(0)
    }

    @Test
    fun onBackBehavior() {
        var onBackCalled = true

        composeRule.setContent {
            ThemeContainer {
                InvoiceActivitiesScreen()
                    .ScreenContent(
                        screenModel = InvoiceActivitiesScreenModel(
                            dispatcher = dispatcher,
                            createInvoiceManager = manager
                        ),
                        onBack = { onBackCalled = true },
                        onContinue = {}
                    )
            }
        }

        composeRule.onNodeWithTag(CreateInvoiceBaseFormTestTags.BACK_BUTTON)
            .assertExists()
            .performClick()

        assertTrue { onBackCalled }
    }

    @Test
    fun addNewActivity() {
        composeRule.setContent {
            ThemeContainer {
                InvoiceActivitiesScreen()
                    .ScreenContent(
                        screenModel = InvoiceActivitiesScreenModel(
                            dispatcher = dispatcher,
                            createInvoiceManager = manager
                        ),
                        onBack = {},
                        onContinue = {}
                    )
            }
        }

        composeRule.onNodeWithTag(InvoiceActivitiesScreen.TestTags.ADD_ACTIVITY)
            .assertExists()
            .performClick()

        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.CONTENT)
            .assertIsDisplayed()

        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.FIELD_DESCRIPTION)
            .performTextInput("Test Activity")

        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.FIELD_DESCRIPTION)
            .performImeAction()

        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.FIELD_UNIT_PRICE)
            .performTextInput("123")

        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.FIELD_UNIT_PRICE)
            .performImeAction()

        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.FIELD_QUANTITY)
            .performTextInput("10")

        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.FIELD_QUANTITY)
            .performImeAction()

        // Bug: Perform click não funciona dentro de bottomsheets - https://issuetracker.google.com/issues/372512084?pli=1
        composeRule.onNodeWithTag(AddActivityBottomSheetTestTag.SUBMIT_BUTTON)
            .assertIsDisplayed()
            .performClick()

        composeRule.onAllNodesWithTag(InvoiceActivitiesScreen.TestTags.LIST_ITEM)
            .assertCountEquals(1)
    }

    @Test
    fun startWithItems() {
        manager.activities.add(
            CreateInvoiceActivityUiModel(
                description = "Test Activity",
                unitPrice = 123,
                quantity = 10
            )
        )

        composeRule.setContent {
            ThemeContainer {
                InvoiceActivitiesScreen()
                    .ScreenContent(
                        screenModel = InvoiceActivitiesScreenModel(
                            dispatcher = dispatcher,
                            createInvoiceManager = manager
                        ),
                        onBack = {},
                        onContinue = {}
                    )
            }
        }

        composeRule.onAllNodesWithTag(InvoiceActivitiesScreen.TestTags.LIST_ITEM)
            .assertCountEquals(1)
    }

    @Test
    fun removeItem() {
        manager.activities.add(
            CreateInvoiceActivityUiModel(
                description = "Test Activity",
                unitPrice = 123,
                quantity = 10
            )
        )

        composeRule.setContent {
            ThemeContainer {
                InvoiceActivitiesScreen()
                    .ScreenContent(
                        screenModel = InvoiceActivitiesScreenModel(
                            dispatcher = dispatcher,
                            createInvoiceManager = manager
                        ),
                        onBack = {},
                        onContinue = {}
                    )
            }
        }

        composeRule.onAllNodesWithTag(NewActivityCardTestTags.DELETE)
            .onFirst()
            .performClick()

        composeRule.onAllNodesWithTag(InvoiceActivitiesScreen.TestTags.LIST_ITEM)
            .assertCountEquals(0)
    }

}