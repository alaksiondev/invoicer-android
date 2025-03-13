plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "feature.qrcodeSession"
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)

    implementation(platform(libs.koin.bom))
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.core)
    implementation(libs.koin.core)
    implementation(libs.datetime)

    implementation(projects.foundation.network)
    implementation(projects.features.invoice.domain)
    implementation(projects.foundation.navigation)

    implementation(libs.bundles.voyager)

    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
}