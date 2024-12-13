plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "foundation.invoice.data"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.core)
    implementation(libs.koin.core)
    implementation(projects.foundation.network.client)

    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
}