plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.logger.test"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.foundation.logger.impl)
}