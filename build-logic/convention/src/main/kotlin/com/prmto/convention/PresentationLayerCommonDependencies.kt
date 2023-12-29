package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addDebugImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.presentationLayerCommonDependencies(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            addImplementation(libs.findLibrary("appcompat").get())
            addImplementation(libs.findLibrary("material").get())
            addImplementation(libs.findLibrary("constraintlayout").get())
            addImplementation(libs.findLibrary("navigation.fragment.ktx").get())
            addImplementation(libs.findLibrary("navigation.ui.ktx").get())

            addImplementation(libs.findLibrary("carouselrecyclerview").get())
            addImplementation(libs.findLibrary("swiperefreshlayout").get())
            addImplementation(libs.findLibrary("coil").get())
            addImplementation(libs.findLibrary("coil.svg").get())

            addImplementation(libs.findLibrary("shimmer").get())
            addImplementation(libs.findLibrary("youtubeplayer").get())
            addImplementation(libs.findLibrary("circleimageview").get())


            addAndroidTestImplementation(libs.findLibrary("espresso.core").get())
            addAndroidTestImplementation(libs.findLibrary("navigation.testing").get())
            addDebugImplementation(libs.findLibrary("fragment.testing").get())
        }
    }
}