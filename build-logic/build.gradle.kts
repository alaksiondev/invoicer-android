plugins {
    `kotlin-dsl`
    `version-catalog`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.library.gradle.plugin)
    implementation(libs.android.application.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("invoicer-library") {
            id = "invoicer.library"
            implementationClass = "build.logic.plugins.LibraryPlugin"
        }

        create("invoicer-compose") {
            id = "invoicer.compose"
            implementationClass = "build.logic.plugins.ComposePlugin"
        }

        create("invoicer-application") {
            id = "invoicer.application"
            implementationClass = "build.logic.plugins.AppPlugin"
        }
    }
}