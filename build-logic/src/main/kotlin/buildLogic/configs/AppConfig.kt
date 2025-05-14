package buildLogic.configs

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal object AppConfig {
    val compileSdk = 35
    val minSdk = 29
    val targetSdk = 35
    val versionCode = 1
    val versionName = "1.0"
    val appId = "io.github.alaksion.invoicer"

    val javaVersion = JavaVersion.VERSION_11
    val jvmTargetMp = JvmTarget.JVM_11
    val jvmTarget = "11"
}