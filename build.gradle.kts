import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.google.services.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.firebase.crcashlytics.gradle.plugin)
    }
    repositories {
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.andorid.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.navigation.safe.args) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

// Pure Java
tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}