package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.dataLayerDependencies(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            addImplementation(libs.findLibrary("retrofit").get())
            addImplementation(libs.findLibrary("retrofit.coroutines.adapter").get())
            addImplementation(libs.findLibrary("retrofit.converter.moshi").get())
            addImplementation(libs.findLibrary("retrofit.converter.moshi").get())
            addImplementation(libs.findLibrary("datastore.preferences").get())

        }
    }
}