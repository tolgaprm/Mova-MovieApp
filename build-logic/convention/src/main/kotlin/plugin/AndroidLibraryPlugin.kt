package plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.dependencyHandler.addCoreLibraryDesugaring
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import util.Plugins
import util.configureKotlinAndroid
import util.libs

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.ANDROID_LIBRARY)
                apply(Plugins.KOTLIN_ANDROID)
                apply(Plugins.KOTLIN_PARCELIZE)
                apply(Plugins.KOTLIN_SERIALIZATION)
                apply(Plugins.MOVA_HILT)
                apply(Plugins.KSP)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this, target)
            }

            dependencies {
                addCoreLibraryDesugaring(libs.findLibrary("desugar.jdk.libs").get())
            }
        }
    }
}