plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.validator.impl"
}


dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}