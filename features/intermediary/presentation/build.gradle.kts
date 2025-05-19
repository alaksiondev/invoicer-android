plugins {
    id("invoicer.multiplatform.library")
    id("invoicer.compose")
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "io.github.alaksion.invoicer.features.intermediary.presentation"
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
            implementation(libs.voyager.transitions)

            // Kotlin
            implementation(libs.immutable.collections)
            implementation(libs.datetime)

            // Foundation
            implementation(projects.foundation.network)
            implementation(projects.foundation.navigation)
            implementation(projects.foundation.designSystem)
            implementation(projects.foundation.ui)
            implementation(projects.foundation.utils)

            // Libs
            implementation(projects.features.intermediary.services)
            implementation(projects.foundation.watchers)
        }

        androidUnitTest.dependencies {
            implementation(projects.foundation.testUtil)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }
    }
}