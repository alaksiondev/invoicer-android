package buildLogic.plugins

import buildLogic.configs.AppConfig
import buildLogic.extensions.configureDetekt
import buildLogic.extensions.getPlugin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            installPlugins(this)
            androidSettings(this)
            configureKotlinMultiplatform(this)
            configureDetektPlugin(this)
        }
    }

    private fun installPlugins(target: Project) {
        target.pluginManager.apply(
            target.getPlugin(alias = "android-library").pluginId
        )
        target.pluginManager.apply(
            target.getPlugin(alias = "kotlin-multiplatform").pluginId
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

    private fun configureDetektPlugin(target: Project) {
        target.configureDetekt()
    }

    private fun configureKotlinMultiplatform(target: Project) = with(target) {
        configure<KotlinMultiplatformExtension> {
            androidTarget {
                compilations.all {
                    compileTaskProvider.configure {
                        compilerOptions {
                            jvmTarget.set(AppConfig.jvmTargetMp)
                        }
                    }
                }
            }
            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64()
            ).forEach { iosTarget ->
                iosTarget.binaries.framework {
                    baseName = "Invoicer"
                    isStatic = true
                }
            }
        }
    }
}