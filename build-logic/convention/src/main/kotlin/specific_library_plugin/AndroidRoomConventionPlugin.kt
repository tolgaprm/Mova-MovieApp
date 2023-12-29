package specific_library_plugin

import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKapt
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply("kotlin-kapt")

            dependencies {
                addImplementation(libs.findLibrary("room.runtime").get())
                addImplementation(libs.findLibrary("room.ktx").get())
                add("annotationProcessor", libs.findLibrary("room.compiler").get())
                addKapt(libs.findLibrary("room.compiler").get())
            }
        }
    }
}