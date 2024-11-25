plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.network.request"
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(projects.foundation.exception)
    implementation(projects.foundation.network.client)
}
