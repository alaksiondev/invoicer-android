plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.auth.impl"
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(projects.foundation.storage.impl)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
