package util

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    project: Project
) {
    commonExtension.apply {
        compileSdk = project.libs.getCompileSdk()

        defaultConfig {
            minSdk = project.libs.getMinSdk()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }


        project.extensions.configure<KotlinAndroidProjectExtension> {
            jvmToolchain(17)
        }

        buildFeatures {
            buildConfig = true
            viewBinding = true
        }

        testOptions {
            unitTests.all { it.useJUnitPlatform() }
            unitTests.isReturnDefaultValues = true
        }
    }
}
