import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")

                extensions.configure<LibraryExtension> {
                    defaultConfig {
                        testInstrumentationRunner = "com.prmto.mova_movieapp.HiltTestRunner"
                        minSdk = 24
                    }

                    defaultConfig.targetSdk = 33
                    compileSdk = 33

                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_11
                        targetCompatibility = JavaVersion.VERSION_11
                    }
                }
            }
        }
    }
}