package layer_plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.configureCommon
import com.prmto.convention.dependency.addAllUiDependencies
import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class PresentationLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("mova.android.library")
                apply("mova.android.hilt")
                apply("mova.android.navigation")
            }

            extensions.configure<LibraryExtension> {
                commonDependenciesForEachModule(this)

                dependencies {
                    coreDomainModule()
                    addAllUiDependencies(libs)
                }
            }

            extensions.configure<ApplicationExtension> {
                configureCommon(this)
            }
        }
    }
}