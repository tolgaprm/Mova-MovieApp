package layer_plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.firebaseCommonDependencies
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class DomainLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("mova.android.library")
                apply("mova.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                commonDependenciesForEachModule(this)
                firebaseCommonDependencies(this)
                dependencies {
                    addImplementation(platform(libs.findLibrary("firebase.bom").get()))
                }
            }
        }
    }
}