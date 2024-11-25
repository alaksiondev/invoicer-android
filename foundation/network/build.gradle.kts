plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.network"
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.engine.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.negotiation)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}