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
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    implementation(libs.bundles.voyager)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem.tokens)
    implementation(projects.foundation.designSystem.components)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}