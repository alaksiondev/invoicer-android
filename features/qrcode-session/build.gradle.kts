plugins {
    id("invoicer.multiplatform.library")
    id("invoicer.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.alaksion.invoicer.features.qrcodeSession"
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

            // Ktor
            implementation(libs.ktor.client.core)

            // Kotlin
            implementation(libs.datetime)
            implementation(libs.coroutines.core)

            // Foundation
            implementation(projects.foundation.network)
            implementation(projects.foundation.navigation)
            implementation(projects.foundation.designSystem)
            implementation(projects.foundation.ui)
            implementation(projects.foundation.validator)
            implementation(projects.foundation.utils)

            // Voyager
            implementation(libs.bundles.voyager)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }

        androidMain.dependencies {
            // Camera
            implementation(libs.bundles.camerax)
            implementation(libs.google.mlkit)
        }
    }
}