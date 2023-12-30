package layer_plugin

import com.android.build.api.dsl.DefaultConfig
import com.android.build.gradle.LibraryExtension
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.dataLayerDependencies
import com.prmto.convention.dependencyHandler.addModule
import com.prmto.convention.firebaseCommonDependencies
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
                firebaseCommonDependencies(this)
                dataLayerDependencies(this)

                dependencies {
                    addModule(":core:core-domain")
                }

                defaultConfig {
                    addTmdbApi(properties)
                }
            }
        }
    }
}

fun DefaultConfig.addTmdbApi(
    properties: Properties
) {
    buildConfigField(
        "String",
        "API_KEY",
        "\"${properties.getProperty("API_KEY")}\""
    )
}