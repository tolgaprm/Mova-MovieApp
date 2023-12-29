package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion

internal fun configureCommon(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        compileSdk = 33

        defaultConfig {
            minSdk = 26
            testInstrumentationRunner = "com.prmto.mova_movieapp.HiltTestRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildFeatures {
            buildConfig = true
            viewBinding = true
        }
    }
}