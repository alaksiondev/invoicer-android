plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.alaksion.invoicer.features.intermediary.services"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.core)
    implementation(libs.koin.core)
    implementation(libs.datetime)

    implementation(projects.foundation.network)

    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
}