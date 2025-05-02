plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.auth"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.core)
    implementation(projects.foundation.network)
    implementation(projects.foundation.watchers)
    implementation(projects.foundation.storage.impl)
    implementation(projects.foundation.session)

    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
    testImplementation(projects.foundation.storage.test)
}