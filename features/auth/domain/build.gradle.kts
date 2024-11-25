plugins {
    id("invoicer.library")
}

android {
    namespace = "features.auth.domain"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}