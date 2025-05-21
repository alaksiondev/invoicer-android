plugins {
    id("invoicer.multiplatform.library")
    id("invoicer.compose")
}

android {
    namespace = "io.github.alaksion.invoicer.sharedApp"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.animationGraphics)
            implementation(compose.foundation)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Foundation
            implementation(projects.foundation.navigation)
            implementation(projects.foundation.designSystem)
            implementation(projects.foundation.network)
            implementation(projects.foundation.validator)
            implementation(projects.foundation.storage)
            implementation(projects.foundation.auth)
            implementation(projects.foundation.network)
            implementation(projects.foundation.utils)

            // Features
            implementation(projects.features.auth.presentation)
            implementation(projects.features.home)
            implementation(projects.features.invoice)
            implementation(projects.foundation.watchers)
            implementation(projects.features.beneficiary.services)
            implementation(projects.features.beneficiary.presentation)
            implementation(projects.foundation.watchers)
            implementation(projects.foundation.analytics)
            implementation(projects.features.intermediary.services)
            implementation(projects.features.intermediary.presentation)
            implementation(projects.foundation.watchers)
            implementation(projects.features.qrcodeSession)

            // Voyager
            implementation(libs.bundles.voyager)
            implementation(libs.voyager.transitions)
        }
    }
}
