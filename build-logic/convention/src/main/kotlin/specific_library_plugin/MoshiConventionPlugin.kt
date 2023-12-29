package specific_library_plugin

import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKapt
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MoshiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
            }

            dependencies {
                addImplementation(libs.findLibrary("moshi").get())
                addImplementation(libs.findLibrary("moshi.kotlin").get())
                addKapt(libs.findLibrary("moshi.kotlin.codegen").get())
            }
        }
    }
}