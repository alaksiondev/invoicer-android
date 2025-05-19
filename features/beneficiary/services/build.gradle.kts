plugins {
    id("invoicer.multiplatform.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.alaksion.invoicer.features.beneficiary.services"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.core)
            implementation(libs.koin.core)
            implementation(libs.datetime)
            implementation(projects.foundation.network)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }
    }
}