package specific_library_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Plugins
import util.library.room
import util.libs

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply(Plugins.KSP)

            dependencies {
                room(libs)
            }
        }
    }
}