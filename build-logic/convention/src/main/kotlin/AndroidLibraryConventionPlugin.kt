import com.android.build.gradle.LibraryExtension
import com.prmto.convention.dependencyHandler.addCoreLibraryDesugaring
import com.prmto.convention.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

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
                        isCoreLibraryDesugaringEnabled = true
                    }

                    dependencies {
                        addCoreLibraryDesugaring(libs.findLibrary("desugar.jdk.libs").get())
                    }
                }
            }
        }
    }
}