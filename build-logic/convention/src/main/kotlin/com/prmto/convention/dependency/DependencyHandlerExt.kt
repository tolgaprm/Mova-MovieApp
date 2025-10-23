package com.prmto.convention.dependency

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addTestImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.commonDependencies(libs: VersionCatalog) {
    coroutines(libs)
    addCommonTestDependencies(libs)
    androidCore(libs)
    timber(libs)
    paging(libs)
}

internal fun DependencyHandlerScope.dataStore(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("androidx.datastore").get())
}

internal fun DependencyHandlerScope.workManager(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("work.runtime.ktx").get())
    addImplementation(libs.findLibrary("hilt.work").get())
}

internal fun DependencyHandlerScope.coroutines(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("coroutines.core").get())
    addImplementation(libs.findLibrary("coroutines.android").get())
    addTestImplementation(libs.findLibrary("kotlinx.coroutines.test").get())
    addAndroidTestImplementation(libs.findLibrary("kotlinx.coroutines.test").get())
}

internal fun DependencyHandlerScope.paging(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("paging.runtime.ktx").get())
}

internal fun DependencyHandlerScope.timber(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("timber").get())
}

internal fun DependencyHandlerScope.androidCore(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("androidx.core").get())
}