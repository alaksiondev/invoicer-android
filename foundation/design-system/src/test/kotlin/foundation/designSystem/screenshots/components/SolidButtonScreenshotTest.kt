package foundation.designSystem.screenshots.components

import foundation.designSystem.screenshots.configs.InvoicerPaparazziConfig
import foundation.designSystem.screenshots.configs.invoicerSnapshot
import foundation.designsystem.components.buttons.InvoicerButtonSize
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.buttons.SecondaryButton
import org.junit.Rule
import kotlin.test.Test

class SolidButtonScreenshotTest {

    @get:Rule
    val paparazzi = InvoicerPaparazziConfig

    @Test
    fun primaryButton() {
        RegularScenario.entries.forEach { scenario ->
            paparazzi.invoicerSnapshot(
                name = "PrimaryButton_${scenario.label}_${scenario.size}_${scenario.enabled}_${scenario.isLoading}"
            ) {
                PrimaryButton(
                    label = scenario.label,
                    size = scenario.size,
                    isEnabled = scenario.enabled,
                    isLoading = scenario.isLoading,
                    onClick = {}
                )
            }
        }

        LargeScenario.entries.forEach { scenario ->
            paparazzi.invoicerSnapshot(
                name = "PrimaryButton_${scenario.label}_${scenario.size}_${scenario.enabled}_${scenario.isLoading}"
            ) {
                PrimaryButton(
                    label = scenario.label,
                    size = scenario.size,
                    isEnabled = scenario.enabled,
                    isLoading = scenario.isLoading,
                    onClick = {}
                )
            }
        }
    }

    @Test
    fun secondaryButton() {
        RegularScenario.entries.forEach { scenario ->
            paparazzi.invoicerSnapshot(
                name = "PrimaryButton_${scenario.label}_${scenario.size}_${scenario.enabled}_${scenario.isLoading}"
            ) {
                SecondaryButton(
                    label = scenario.label,
                    size = scenario.size,
                    isEnabled = scenario.enabled,
                    isLoading = scenario.isLoading,
                    onClick = {}
                )
            }
        }

        LargeScenario.entries.forEach { scenario ->
            paparazzi.invoicerSnapshot(
                name = "PrimaryButton_${scenario.label}_${scenario.size}_${scenario.enabled}_${scenario.isLoading}"
            ) {
                SecondaryButton(
                    label = scenario.label,
                    size = scenario.size,
                    isEnabled = scenario.enabled,
                    isLoading = scenario.isLoading,
                    onClick = {}
                )
            }
        }
    }
}

private enum class RegularScenario(
    val label: String,
    val size: InvoicerButtonSize,
    val enabled: Boolean,
    val isLoading: Boolean,
) {
    Normal("Button Label", InvoicerButtonSize.Regular, true, false),
    Disabled("Button Label", InvoicerButtonSize.Regular, false, false),
    Loading("Button Label", InvoicerButtonSize.Regular, true, true),
    DisabledAndLoading("Button Label", InvoicerButtonSize.Regular, false, true),
}

private enum class LargeScenario(
    val label: String,
    val size: InvoicerButtonSize,
    val enabled: Boolean,
    val isLoading: Boolean,
) {
    Normal("Button Label", InvoicerButtonSize.Large, true, false),
    Disabled("Button Label", InvoicerButtonSize.Large, false, false),
    Loading("Button Label", InvoicerButtonSize.Large, true, true),
    DisabledAndLoading("Button Label", InvoicerButtonSize.Large, false, true),
}
