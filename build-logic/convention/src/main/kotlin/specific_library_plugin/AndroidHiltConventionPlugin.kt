package specific_library_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Plugins
import util.library.hilt
import util.libs

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.HILT)
                apply(Plugins.KSP)
            }

            dependencies {
                hilt(libs)
            }
        }
    }
}