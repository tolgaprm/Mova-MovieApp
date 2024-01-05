package com.prmto.convention.dependency

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKapt
import com.prmto.convention.dependencyHandler.addKaptAndroidTest
import com.prmto.convention.dependencyHandler.addTestImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.hilt(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("dagger.hilt").get())
    addKapt(libs.findLibrary("dagger.hilt.compiler").get())
    addKapt(libs.findLibrary("hilt.compiler").get())
    addAndroidTestImplementation(libs.findLibrary("hilt.testing").get())
    addKaptAndroidTest(libs.findLibrary("dagger.hilt.compiler").get())
}

internal fun DependencyHandlerScope.room(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("room.runtime").get())
    addImplementation(libs.findLibrary("room.ktx").get())
    add("annotationProcessor", libs.findLibrary("room.compiler").get())
    addKapt(libs.findLibrary("room.compiler").get())
}

internal fun DependencyHandlerScope.retrofit(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("retrofit").get())
    addImplementation(libs.findLibrary("retrofit.coroutines.adapter").get())
    addImplementation(libs.findLibrary("retrofit.converter.moshi").get())
}

internal fun DependencyHandlerScope.dataStore(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("datastore.preferences").get())
}

internal fun DependencyHandlerScope.workManager(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("work.runtime.ktx").get())
    addImplementation(libs.findLibrary("hilt.work").get())
}

internal fun DependencyHandlerScope.moshi(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("moshi").get())
    addImplementation(libs.findLibrary("moshi.kotlin").get())
    addKapt(libs.findLibrary("moshi.kotlin.codegen").get())
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

internal fun DependencyHandlerScope.androidXKtx(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("androidx.ktx").get())
}

internal fun DependencyHandlerScope.firebase(libs: VersionCatalog) {
    addImplementation(platform(libs.findLibrary("firebase.bom").get()))
    addImplementation(libs.findLibrary("firebase.analytics.ktx").get())
    addImplementation(libs.findLibrary("firebase.auth.ktx").get())
    addImplementation(libs.findLibrary("play.services.auth").get())
    addImplementation(libs.findLibrary("firebase.firestore.ktx").get())
    addImplementation(libs.findLibrary("firebase.crashlytics.ktx").get())
    addImplementation(libs.findLibrary("firebase.appcheck.playintegrity").get())
}