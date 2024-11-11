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
    implementation(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("invoicer-library") {
            id = "invoicer.library"
            implementationClass = "build.logic.plugins.LibraryPlugin"
        }
    }
}