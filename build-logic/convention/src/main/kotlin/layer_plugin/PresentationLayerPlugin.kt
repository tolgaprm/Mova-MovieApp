package layer_plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.navigationModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import util.Plugins
import util.library.presentationLibraries
import util.libs

class PresentationLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.MOVA_ANDROID_LIBRARY)
                apply(Plugins.MOVA_NAVIGATION)
            }

            extensions.configure<LibraryExtension> {
                dependencies {
                    coreDomainModule()
                    navigationModule()
                    presentationLibraries(libs)
                }
            }
        }
    }
}