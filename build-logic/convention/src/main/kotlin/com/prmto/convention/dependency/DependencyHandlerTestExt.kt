package com.prmto.convention.dependency

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addTestImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

private fun DependencyHandlerScope.truth(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("truth").get())
    addAndroidTestImplementation(libs.findLibrary("truth").get())
}

private fun DependencyHandlerScope.turbine(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("turbine").get())
    addAndroidTestImplementation(libs.findLibrary("turbine").get())
}

private fun DependencyHandlerScope.junit(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("junit").get())
    addAndroidTestImplementation(libs.findLibrary("junit").get())
    addTestImplementation(libs.findLibrary("junitExt").get())
    addAndroidTestImplementation(libs.findLibrary("junitExt").get())
}

private fun DependencyHandlerScope.mockk(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("mockk").get())
    addAndroidTestImplementation(libs.findLibrary("mockk").get())
}

private fun DependencyHandlerScope.mockito(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("mockito.core").get())
    addAndroidTestImplementation(libs.findLibrary("mockito.core").get())
    addAndroidTestImplementation(libs.findLibrary("mockito.android").get())
}

fun DependencyHandlerScope.addCommonTestDependencies(libs: VersionCatalog) {
    truth(libs)
    turbine(libs)
    junit(libs)
    mockk(libs)
    mockito(libs)
}