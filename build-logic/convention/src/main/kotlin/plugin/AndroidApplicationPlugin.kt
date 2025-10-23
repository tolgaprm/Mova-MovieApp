package plugin

import com.android.build.api.dsl.ApplicationExtension
import com.prmto.convention.dependency.authenticationFeature
import com.prmto.convention.dependency.coreDataModule
import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.exploreFeature
import com.prmto.convention.dependency.homeFeature
import com.prmto.convention.dependency.movieTvDetailFeature
import com.prmto.convention.dependency.myListFeature
import com.prmto.convention.dependency.navigationModule
import com.prmto.convention.dependency.notificationModule
import com.prmto.convention.dependency.personDetailFeature
import com.prmto.convention.dependency.settingsFeature
import com.prmto.convention.dependency.splashModule
import com.prmto.convention.dependency.upcomingFeature
import com.prmto.convention.dependency.workManager
import com.prmto.convention.dependency.workManagerModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import util.Common
import util.Plugins
import util.configureKotlinAndroid
import util.getTargetSdk
import util.getVersionCode
import util.getVersionName
import util.library.dataLaterLibraries
import util.library.presentationLibraries
import util.libs

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.ANDROID_APPLICATION)
                apply(Plugins.KOTLIN_ANDROID)
                apply(Plugins.KOTLIN_PARCELIZE)
                apply(Plugins.MOVA_HILT)
                apply(Plugins.MOVA_ROOM)
                apply(Plugins.MOVA_NAVIGATION)
                apply(Plugins.MOVA_MOSHI)
                apply(Plugins.MOVA_APPLICATION_FIREBASE)
                apply(Plugins.KOTLIN_SERIALIZATION)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this, target)
                namespace = Common.APPLICATION_ID
                defaultConfig {
                    applicationId = Common.APPLICATION_ID
                    targetSdk = libs.getTargetSdk()
                    versionCode = libs.getVersionCode()
                    versionName = libs.getVersionName()
                    testInstrumentationRunner = "com.prmto.mova_movieapp.HiltTestRunner"
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
                myListFeature()
                personDetailFeature()
                navigationModule()
                notificationModule()
                workManagerModule()
                splashModule()

                presentationLibraries(libs)
                dataLaterLibraries(libs)
                workManager(libs)
            }
        }
    }
}