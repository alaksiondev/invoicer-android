import java.util.Properties

plugins {
    id("invoicer.library")
    id("invoicer.compose")
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "features.auth.presentation"

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
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    implementation(libs.androidx.activity.compose)

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
    implementation(projects.foundation.auth.watchers)
    implementation(projects.foundation.auth.domain)
}