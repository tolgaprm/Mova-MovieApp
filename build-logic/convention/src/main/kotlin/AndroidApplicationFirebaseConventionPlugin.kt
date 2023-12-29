import com.android.build.api.dsl.ApplicationExtension
import com.prmto.convention.firebaseCommonDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.firebase.crashlytics")
                apply("com.google.gms.google-services")
            }

            extensions.configure<ApplicationExtension> {
                firebaseCommonDependencies(this)
            }
        }
    }
}