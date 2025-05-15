import java.util.Properties

plugins {
    id("invoicer.library")
    id("invoicer.compose")
    alias(libs.plugins.paparazzi)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "io.github.alaksion.invoicer.features.auth.presentation"

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            buildConfigField(
                "String",
                "FIREBASE_WEB_ID",
                properties.getProperty("FIREBASE_WEB_ID")
            )
        }
        debug {
            buildConfigField(
                "String",
                "FIREBASE_WEB_ID",
                properties.getProperty("FIREBASE_WEB_ID")
            )
        }
    }
}

dependencies {
    // Compose
    implementation(compose.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(compose.material3)
    implementation(compose.components.resources)
    debugImplementation(libs.bundles.compose.debug)
    implementation(libs.androidx.activity.compose)
    implementation(projects.foundation.testUtil)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Voyager
    implementation(libs.bundles.voyager)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.bundles.identity)
    implementation(libs.google.services.auth)

    // Foundation
    implementation(projects.foundation.network)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem)
    implementation(projects.foundation.validator.impl)
    implementation(projects.foundation.ui)
    implementation(projects.foundation.exception)
    implementation(projects.foundation.auth)
    implementation(projects.foundation.analytics)

    // Test
    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
}