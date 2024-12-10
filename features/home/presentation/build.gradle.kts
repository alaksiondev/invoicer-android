plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "features.home.presentation"
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    implementation(libs.androidx.activity.compose)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Voyager
    implementation(libs.bundles.voyager)
    implementation(libs.voyager.tabs)

    // Foundation
    implementation(projects.foundation.network.request)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem.tokens)
    implementation(projects.foundation.designSystem.components)
    implementation(projects.foundation.auth.domain)
    implementation(projects.foundation.auth.watchers)
    implementation(projects.foundation.events)
}