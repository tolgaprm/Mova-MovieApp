import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.DefaultConfig
import com.prmto.convention.configureCommon
import com.prmto.convention.dependency.addAllUiDependencies
import com.prmto.convention.dependency.addCommonTestDependencies
import com.prmto.convention.dependency.androidXKtx
import com.prmto.convention.dependency.authenticationFeature
import com.prmto.convention.dependency.coreDataModule
import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.coroutines
import com.prmto.convention.dependency.dataStore
import com.prmto.convention.dependency.exploreFeature
import com.prmto.convention.dependency.homeFeature
import com.prmto.convention.dependency.movieTvDetailFeature
import com.prmto.convention.dependency.paging
import com.prmto.convention.dependency.retrofit
import com.prmto.convention.dependency.settingsFeature
import com.prmto.convention.dependency.timber
import com.prmto.convention.dependency.upcomingFeature
import com.prmto.convention.dependency.workManager
import com.prmto.convention.dependencyHandler.addCoreLibraryDesugaring
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.util.Properties

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val properties = Properties().apply {
            load(target.rootProject.file("local.properties").inputStream())
        }

        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("mova.android.hilt")
                apply("mova.android.room")
                apply("mova.android.navigation")
                apply("mova.android.moshi")
                apply("mova.android.application.firebase")
            }

            extensions.configure<ApplicationExtension> {
                configureCommon(this)
                compileSdk = 33
                defaultConfig {
                    targetSdk = 33
                    addBuildConfigField(properties, "API_KEY")
                    addBuildConfigField(properties, "FIREBASE_PROJECT_ID")
                    addBuildConfigField(properties, "FIREBASE_APPLICATION_ID")
                    addBuildConfigField(properties, "FIREBASE_API_KEY")

                    val resourceConfigurations = listOf("en", "tr-rTR", "de-rDE")
                    resourceConfigurations.forEach { resConfig ->
                        resConfig
                    }
                }

                compileOptions {
                    isCoreLibraryDesugaringEnabled = true
                }
            }

            dependencies {
                coreDataModule()
                coreDomainModule()
                coreUiModule()
                upcomingFeature()
                authenticationFeature()
                exploreFeature()
                homeFeature()
                movieTvDetailFeature()
                settingsFeature()

                addAllUiDependencies(libs)
                addCoreLibraryDesugaring(libs.findLibrary("desugar.jdk.libs").get())
                androidXKtx(libs)
                coroutines(libs)
                dataStore(libs)
                paging(libs)
                retrofit(libs)
                timber(libs)
                workManager(libs)
                addCommonTestDependencies(libs)
            }
        }
    }

    private fun DefaultConfig.addBuildConfigField(
        properties: Properties,
        name: String
    ) {
        buildConfigField(
            "String",
            name,
            "\"${properties.getProperty(name)}\""
        )
    }
}