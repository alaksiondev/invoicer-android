plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "features.auth.presentation"
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Voyager
    implementation(libs.bundles.voyager)

    // Foundation
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem.tokens)
    implementation(projects.foundation.designSystem.components)

    // Features
    implementation(projects.features.auth.domain)
}