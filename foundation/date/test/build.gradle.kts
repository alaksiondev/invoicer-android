plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.date.test"
}

dependencies {
    implementation(projects.foundation.date.impl)
    implementation(libs.datetime)
}