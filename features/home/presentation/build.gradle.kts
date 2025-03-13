plugins {
    id("invoicer.library")
    id("invoicer.compose")
}

android {
    namespace = "features.home.presentation"
}

dependencies {
    // Compose
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
    implementation(projects.foundation.network)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem)
    implementation(projects.foundation.auth.domain)
    implementation(projects.foundation.auth.watchers)
    implementation(projects.foundation.ui)
}