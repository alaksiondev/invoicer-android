import java.util.Properties

plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.serialization)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "foundation.network.client"

    buildTypes {
        release {
            buildConfigField("String", "API_URL", properties.getProperty("PROD_APP_URL"))
        }
        debug {
            buildConfigField("String", "API_URL", properties.getProperty("DEBUG_APP_URL"))
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.engine.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.client.log)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(projects.foundation.exception)
}