plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "foundation.auth.data"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.core)
    implementation(libs.koin.core)
    implementation(projects.foundation.network)
    implementation(projects.foundation.auth.domain)
    implementation(projects.foundation.storage.impl)

    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
    testImplementation(projects.foundation.storage.test)
}