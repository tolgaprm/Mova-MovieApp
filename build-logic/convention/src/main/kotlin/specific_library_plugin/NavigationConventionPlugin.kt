package specific_library_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Plugins
import util.library.navigation
import util.libs

class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(Plugins.NAVIGATION_SAFE_ARGS)

            dependencies {
                navigation(libs)
            }
        }
    }
}