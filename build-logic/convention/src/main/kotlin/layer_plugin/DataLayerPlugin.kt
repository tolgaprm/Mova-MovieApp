package layer_plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.addBuildConfigField
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import util.Plugins
import util.library.dataLaterLibraries
import util.libs
import java.util.Properties

class DataLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val properties = Properties().apply {
            load(target.rootProject.file("local.properties").inputStream())
        }

        with(target) {
            with(pluginManager) {
                apply(Plugins.MOVA_ANDROID_LIBRARY)
                apply(Plugins.MOVA_ROOM)
                apply(Plugins.MOVA_MOSHI)
            }

            extensions.configure<LibraryExtension> {
                dependencies {
                    dataLaterLibraries(libs)
                }

                defaultConfig {
                    addBuildConfigField(properties, "API_KEY")
                    addBuildConfigField("TMDB_BASE_URL", "https://api.themoviedb.org/3/")
                }
            }
        }
    }
}