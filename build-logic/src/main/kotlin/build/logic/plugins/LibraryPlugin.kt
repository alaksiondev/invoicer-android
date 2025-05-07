package build.logic.plugins

import build.logic.configs.AppConfig
import build.logic.extensions.getPlugin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            installPlugins(this)
            androidSettings(this)
            configureKotlin(this)
            configureDetektPlugin(this)
        }
    }

    private fun installPlugins(target: Project) {
        target.pluginManager.apply(
            target.getPlugin(alias = "android-library").pluginId
        )
        target.pluginManager.apply(
            target.getPlugin(alias = "kotlin-android").pluginId
        )
        target.pluginManager.apply(
            target.getPlugin(alias = "detekt").pluginId
        )
    }

    private fun androidSettings(target: Project) {
        target.extensions.configure<LibraryExtension> {
            compileSdk = AppConfig.compileSdk

            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                minSdk = AppConfig.minSdk
            }

            compileOptions {
                sourceCompatibility = AppConfig.javaVersion
                targetCompatibility = AppConfig.javaVersion
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                }

                debug {

                }
            }
        }
    }

    private fun configureKotlin(target: Project) {
        target.tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = AppConfig.jvmTarget
            }
        }
    }

    private fun configureDetektPlugin(target: Project) {
//        target.configureDetekt()
    }
}