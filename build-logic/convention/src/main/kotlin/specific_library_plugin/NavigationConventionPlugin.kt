package specific_library_plugin

import com.prmto.convention.dependency.navigation
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("androidx.navigation.safeargs")

            dependencies {
                navigation(libs)
            }
        }
    }
}