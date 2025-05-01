plugins {
    id("invoicer.library")
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.utils"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.datetime)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}