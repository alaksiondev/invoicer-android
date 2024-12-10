plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.storage.test"
}

dependencies {
    implementation(projects.foundation.storage.impl)
}
