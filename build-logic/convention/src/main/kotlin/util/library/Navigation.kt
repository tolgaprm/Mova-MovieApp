package util.library

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.navigation(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("androidx.navigation.fragment").get())
    addImplementation(libs.findLibrary("androidx.navigation.ui").get())
    addAndroidTestImplementation(libs.findLibrary("navigation.testing").get())
}