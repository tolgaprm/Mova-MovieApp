plugins {
    `kotlin-dsl`
}

group = "com.prmto.mova.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "mova.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
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

        register("androidApplicationFirebase") {
            id = "mova.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }

        register("androidLibrary") {
            id = "mova.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidDataLayer") {
            id = "mova.layer.data"
            implementationClass = "layer_plugin.DataLayerPlugin"
        }

        register("androidDomainLayer") {
            id = "mova.layer.domain"
            implementationClass = "layer_plugin.DomainLayerPlugin"
        }

        register("androidPresentationLayer") {
            id = "mova.layer.presentation"
            implementationClass = "layer_plugin.PresentationLayerPlugin"
        }
    }
}