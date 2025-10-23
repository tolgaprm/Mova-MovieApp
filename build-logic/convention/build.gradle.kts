import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.prmto.mova.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "mova.android.application"
            implementationClass = "plugin.AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "mova.android.library"
            implementationClass = "plugin.AndroidLibraryPlugin"
        }

        register("androidHilt") {
            id = "mova.android.hilt"
            implementationClass = "specific_library_plugin.AndroidHiltConventionPlugin"
        }

        register("androidRoom") {
            id = "mova.android.room"
            implementationClass = "specific_library_plugin.AndroidRoomConventionPlugin"
        }

        register("moshi") {
            id = "mova.android.moshi"
            implementationClass = "specific_library_plugin.MoshiConventionPlugin"
        }

        register("navigation") {
            id = "mova.android.navigation"
            implementationClass = "specific_library_plugin.NavigationConventionPlugin"
        }

        register("androidApplicationFirebase") {
            id = "mova.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }

        register("androidDataLayer") {
            id = "mova.layer.data"
            implementationClass = "layer_plugin.DataLayerPlugin"
        }

        register("androidDomainLayer") {
            id = "mova.layer.domain"
            implementationClass = "layer_plugin.DomainLayerPlugin"
        }

        register("androidUiLayer") {
            id = "mova.layer.ui"
            implementationClass = "layer_plugin.PresentationLayerPlugin"
        }
    }
}