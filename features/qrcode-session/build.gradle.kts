plugins {
    id("invoicer.library")
    id("invoicer.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "feature.qrcodeSession"
}

dependencies {
    // Compose
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.bundles.compose.debug)

    implementation(platform(libs.koin.bom))
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.core)
    implementation(libs.koin.core)
    implementation(libs.datetime)

    // Camera
    implementation(libs.bundles.camerax)
    implementation(libs.google.mlkit)

    implementation(projects.foundation.network)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem)
    implementation(projects.foundation.ui)
    implementation(projects.foundation.validator)

    implementation(libs.bundles.voyager)

    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
}