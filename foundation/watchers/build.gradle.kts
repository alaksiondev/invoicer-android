plugins {
    id("invoicer.multiplatform.library")
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.watchers"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.foundation.ui)
            implementation(libs.coroutines.core)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
    }
}