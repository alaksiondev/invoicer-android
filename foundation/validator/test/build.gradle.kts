plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.validator.test"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}