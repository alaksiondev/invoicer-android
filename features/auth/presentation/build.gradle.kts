plugins {
    id("invoicer.multiplatform.library")
    id("invoicer.compose")
    alias(libs.plugins.paparazzi)

}

android {
    namespace = "io.github.alaksion.invoicer.features.auth.presentation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.components.resources)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Voyager
            implementation(libs.bundles.voyager)

            // Foundation
            implementation(projects.foundation.network)
            implementation(projects.foundation.navigation)
            implementation(projects.foundation.designSystem)
            implementation(projects.foundation.validator)
            implementation(projects.foundation.ui)
            implementation(projects.foundation.auth)
            implementation(projects.foundation.analytics)
            implementation(projects.foundation.utils)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }

        androidUnitTest.dependencies {
            implementation(projects.foundation.testUtil)
        }
    }
}