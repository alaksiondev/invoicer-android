plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.logger.impl"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}