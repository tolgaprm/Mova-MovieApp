package util.library

import com.prmto.convention.dependency.commonDependencies
import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.presentationLibraries(libs: VersionCatalog) {
    commonDependencies(libs)
    addImplementation(libs.findLibrary("androidx.appcompat").get())
    addImplementation(libs.findLibrary("android.material").get())
    addImplementation(libs.findLibrary("androidx.constraintlayout").get())
    addImplementation(libs.findLibrary("carouselrecyclerview").get())
    coil(libs)
    addImplementation(libs.findLibrary("shimmer").get())
    addImplementation(libs.findLibrary("circleimageview").get())
    addImplementation(libs.findLibrary("youtubeplayer").get())
    addImplementation(libs.findLibrary("play.services.ads").get())
    addImplementation(libs.findLibrary("swiperefreshlayout").get())
}

private fun DependencyHandlerScope.coil(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("coil").get())
    addImplementation(libs.findLibrary("coil.svg").get())
}