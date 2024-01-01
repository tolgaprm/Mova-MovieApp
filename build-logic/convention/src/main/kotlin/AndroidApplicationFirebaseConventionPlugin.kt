import com.prmto.convention.dependency.firebase
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.firebase.crashlytics")
                apply("com.google.gms.google-services")
            }

            dependencies {
                firebase(libs)
            }
        }
    }
}