package build.logic.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val Project.versionCatalog: VersionCatalog
    get() {
        return extensions.getByType<VersionCatalogsExtension>().named("libs")
    }

internal fun Project.getPlugin(alias: String): PluginDependency {
    return versionCatalog.findPlugin(alias).get().get()
}