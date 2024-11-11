package build.logic.plugins

import build.logic.configs.AppConfig
import build.logic.extensions.getPlugin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.utils.named

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            installPlugins(this)
            androidSettings(this)
            configureKotlin(this)
        }
    }

    private fun installPlugins(target: Project) {
        target.pluginManager.apply(
            target.getPlugin(alias = "android-library").pluginId
        )
        target.pluginManager.apply(
            target.getPlugin(alias = "kotlin-android").pluginId
        )
    }

    private fun androidSettings(target: Project) {
        target.extensions.configure<LibraryExtension> {
            compileSdk = AppConfig.compileSdk

            defaultConfig {
                minSdk = AppConfig.minSdk
            }

            compileOptions {
                sourceCompatibility = AppConfig.javaVersion
                targetCompatibility = AppConfig.javaVersion
            }
        }
    }

    private fun configureKotlin(target: Project) {
        // TODO -> Configure jvmTarget
    }
}