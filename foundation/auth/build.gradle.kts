plugins {
    id("invoicer.multiplatform.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.auth"
}

kotlin {
    sourceSets {
        commonMain.dependencies {

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Ktor
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.core)

            // Libs
            implementation(projects.foundation.network)
            implementation(projects.foundation.watchers)
            implementation(projects.foundation.storage)
            implementation(projects.foundation.session)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }

        androidMain.dependencies {

        }

    }
}

dependencies {
    // Auth Providers
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}