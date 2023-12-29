package specific_library_plugin

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKapt
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("kotlin-kapt")
            }

            dependencies {
                addImplementation(libs.findLibrary("dagger.hilt").get())
                addKapt(libs.findLibrary("dagger.hilt.compiler").get())
                addKapt(libs.findLibrary("hilt.compiler").get())
                addAndroidTestImplementation(libs.findLibrary("hilt.testing").get())
            }
        }
    }
}