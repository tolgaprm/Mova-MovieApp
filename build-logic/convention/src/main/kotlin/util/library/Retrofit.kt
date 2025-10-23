package util.library

import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.retrofit(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("squareup.okhttp").get())
    addImplementation(libs.findLibrary("squareup.okhttp.loginterceptor").get())
    addImplementation(libs.findLibrary("squareup.retrofit2").get())
    addImplementation(libs.findLibrary("squareup.retrofit2.moshi").get())
    addImplementation(libs.findLibrary("squareup.retrofit2.scalars").get())
}