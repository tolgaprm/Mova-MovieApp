package util.library

import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.firebase(libs: VersionCatalog) {
    addImplementation(platform(libs.findLibrary("firebase.bom").get()))
    addImplementation(libs.findLibrary("firebase.analytics.ktx").get())
    addImplementation(libs.findLibrary("firebase.auth.ktx").get())
    addImplementation(libs.findLibrary("play.services.auth").get())
    addImplementation(libs.findLibrary("firebase.firestore.ktx").get())
    addImplementation(libs.findLibrary("firebase.crashlytics.ktx").get())
    addImplementation(libs.findLibrary("firebase.appcheck.playintegrity").get())
}