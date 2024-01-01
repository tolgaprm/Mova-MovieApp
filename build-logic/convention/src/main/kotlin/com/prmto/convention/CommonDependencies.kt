package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependency.addCommonTestDependencies
import com.prmto.convention.dependency.androidXKtx
import com.prmto.convention.dependency.coroutines
import com.prmto.convention.dependency.paging
import com.prmto.convention.dependency.timber
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.commonDependenciesForEachModule(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            coroutines(libs)
            addCommonTestDependencies(libs)
            androidXKtx(libs)
            timber(libs)
            paging(libs)
        }
    }
}