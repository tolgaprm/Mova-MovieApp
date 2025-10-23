package util.library

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKsp
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.hilt(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("dagger.hilt").get())
    addKsp(libs.findLibrary("dagger.hilt.compiler").get())
    addKsp(libs.findLibrary("hilt.compiler").get())
    addAndroidTestImplementation(libs.findLibrary("hilt.testing").get())
}