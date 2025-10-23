package util

internal object Common {
    const val APPLICATION_ID = "com.prmto.mova_movieapp"
}

object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"

    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_PARCELIZE = "org.jetbrains.kotlin.plugin.parcelize"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlin.plugin.serialization"

    const val GOOGLE_SERVICES = "com.google.gms.google-services"
    const val HILT = "dagger.hilt.android.plugin"
    const val KSP = "com.google.devtools.ksp"
    const val NAVIGATION_SAFE_ARGS = "androidx.navigation.safeargs"

    // Burada değişiklik yapılacaksa, libs.version.toml dosyasında da değişiklik yapılmalı
    const val GETIR_DRIVE_COMPOSE = "getir.drive.compose"
    const val MOVA_HILT = "mova.android.hilt"
    const val MOVA_ROOM = "mova.android.room"
    const val MOVA_NAVIGATION = "mova.android.navigation"
    const val MOVA_MOSHI = "mova.android.moshi"
    const val MOVA_ANDROID_LIBRARY = "mova.android.library"
    const val MOVA_APPLICATION_FIREBASE = "mova.android.application.firebase"
}