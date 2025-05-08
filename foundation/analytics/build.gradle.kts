plugins {
    id("invoicer.library")
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.analytics"
}

dependencies {

    // Auth Providers
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Test
    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
}