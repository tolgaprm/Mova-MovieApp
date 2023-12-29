package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addTestImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.commonDependenciesForEachModule(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            addImplementation(libs.findLibrary("androidx.ktx").get())
            addImplementation(libs.findLibrary("timber").get())
            addImplementation(libs.findLibrary("coroutines.core").get())
            addImplementation(libs.findLibrary("coroutines.android").get())
            addImplementation(libs.findLibrary("paging.runtime.ktx").get())

            addTestImplementation(libs.findLibrary("kotlinx.coroutines.test").get())
            addTestImplementation(libs.findLibrary("junit").get())
            addTestImplementation(libs.findLibrary("truth").get())
            addTestImplementation(libs.findLibrary("core.testing").get())
            addTestImplementation(libs.findLibrary("mockk").get())
            addTestImplementation(libs.findLibrary("mockito.core").get())
            addTestImplementation(libs.findLibrary("turbine").get())

            addAndroidTestImplementation(libs.findLibrary("kotlinx.coroutines.test").get())
            addAndroidTestImplementation(libs.findLibrary("truth").get())
            addAndroidTestImplementation(libs.findLibrary("mockk").get())
            addAndroidTestImplementation(libs.findLibrary("core.testing").get())
            addAndroidTestImplementation(libs.findLibrary("turbine").get())
        }
    }
}