plugins {
    id("invoicer.library")
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.analytics"
}

dependencies {
    // Analytics
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)


    // Project
    implementation(projects.foundation.utils)

    // Test
    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
}