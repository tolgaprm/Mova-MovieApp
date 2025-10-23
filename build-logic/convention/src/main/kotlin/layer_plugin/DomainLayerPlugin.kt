package layer_plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.dependency.commonDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import util.Plugins
import util.library.firebase
import util.libs

class DomainLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.MOVA_ANDROID_LIBRARY)
            }

            extensions.configure<LibraryExtension> {
                dependencies {
                    firebase(libs)
                    commonDependencies(libs)
                }
            }
        }
    }
}