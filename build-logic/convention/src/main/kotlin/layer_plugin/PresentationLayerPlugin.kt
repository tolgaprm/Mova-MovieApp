package layer_plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.configureCommon
import com.prmto.convention.presentationLayerCommonDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class PresentationLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("mova.android.library")
                apply("mova.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                commonDependenciesForEachModule(this)
                presentationLayerCommonDependencies(this)
            }

            extensions.configure<ApplicationExtension> {
                configureCommon(this)
            }
        }
    }
}