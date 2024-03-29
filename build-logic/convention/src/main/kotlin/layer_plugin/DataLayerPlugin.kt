package layer_plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.addBuildConfigField
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.dataStore
import com.prmto.convention.dependency.firebase
import com.prmto.convention.dependency.retrofit
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.util.Properties

class DataLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val properties = Properties().apply {
            load(target.rootProject.file("local.properties").inputStream())
        }

        with(target) {
            with(pluginManager) {
                apply("mova.android.library")
                apply("mova.android.hilt")
                apply("mova.android.room")
                apply("mova.android.moshi")
            }

            extensions.configure<LibraryExtension> {
                commonDependenciesForEachModule(this)

                dependencies {
                    coreDomainModule()
                    firebase(libs)
                    retrofit(libs)
                    dataStore(libs)
                }

                defaultConfig {
                    addBuildConfigField(properties, "API_KEY")
                    addBuildConfigField("TMDB_BASE_URL", "https://api.themoviedb.org/3/")
                }
            }
        }
    }
}