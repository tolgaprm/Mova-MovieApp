package specific_library_plugin

import com.prmto.convention.dependency.room
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply("kotlin-kapt")

            dependencies {
                room(libs)
            }
        }
    }
}