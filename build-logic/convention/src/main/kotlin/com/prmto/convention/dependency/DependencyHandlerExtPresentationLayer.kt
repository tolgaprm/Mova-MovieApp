package com.prmto.convention.dependency

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

private fun DependencyHandlerScope.addCommonDependencyForUi(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("appcompat").get())
    addImplementation(libs.findLibrary("material").get())
    addImplementation(libs.findLibrary("constraintlayout").get())
}

fun DependencyHandlerScope.navigation(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("navigation.fragment.ktx").get())
    addImplementation(libs.findLibrary("navigation.ui.ktx").get())
    addAndroidTestImplementation(libs.findLibrary("navigation.testing").get())
}

private fun DependencyHandlerScope.carouselRecyclerView(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("carouselrecyclerview").get())
}

private fun DependencyHandlerScope.coil(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("coil").get())
    addImplementation(libs.findLibrary("coil.svg").get())
}

private fun DependencyHandlerScope.shimmer(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("shimmer").get())
}

private fun DependencyHandlerScope.circleImageView(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("circleimageview").get())
}

private fun DependencyHandlerScope.youtubePlayer(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("youtubeplayer").get())
}

private fun DependencyHandlerScope.ads(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("play.services.ads").get())
}

private fun DependencyHandlerScope.swipeRefreshLayout(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("swiperefreshlayout").get())
}

private fun DependencyHandlerScope.espresso(libs: VersionCatalog) {
    addAndroidTestImplementation(libs.findLibrary("espresso.core").get())
}

private fun DependencyHandlerScope.fragmentTesting(libs: VersionCatalog) {
    addAndroidTestImplementation(libs.findLibrary("fragment.testing").get())
}

fun DependencyHandlerScope.addAllUiDependencies(libs: VersionCatalog) {
    addCommonDependencyForUi(libs)
    carouselRecyclerView(libs)
    coil(libs)
    shimmer(libs)
    circleImageView(libs)
    youtubePlayer(libs)
    ads(libs)
    swipeRefreshLayout(libs)
    espresso(libs)
    fragmentTesting(libs)
}