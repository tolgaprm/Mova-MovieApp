plugins {
    id("mova.android.application")
    id("mova.android.hilt")
    id("mova.android.room")
    id("mova.android.moshi")
    id("mova.android.application.firebase")
    kotlin("android")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {

    defaultConfig {
        applicationId = "com.prmto.mova_movieapp"
        minSdk = 24
        versionCode = 12
        versionName = "2.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core:core-data"))
    implementation(project(":core:core-domain"))
    implementation(project(":upcoming:upcoming-domain"))
    implementation(project(":upcoming:upcoming-data"))
    implementation(project(":authentication:authentication-data"))
    implementation(project(":authentication:authentication-domain"))

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    implementation(libs.play.services.base)

    // navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // coroutines for getting off the UI thread
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Work Manager
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)

    // retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.retrofit.converter.moshi)

    // logging
    implementation(libs.timber)

    // DataStore
    implementation(libs.datastore.preferences)

    //Carousel
    implementation(libs.carouselrecyclerview)

    // Swipe Refresh Layout
    implementation(libs.swiperefreshlayout)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.svg)

    // Shimmer
    implementation(libs.shimmer)

    // Youtube Player
    implementation(libs.youtubeplayer)

    //Paging Library
    implementation(libs.paging.runtime.ktx)

    // Circle Image View
    implementation(libs.circleimageview)

    // Ads
    implementation(libs.play.services.ads)

    // Local Unit Tests
    testImplementation(libs.junit)
    testImplementation(libs.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockk)
    // Flow Testing
    testImplementation(libs.turbine)

    androidTestImplementation(libs.hilt.testing)
    kaptAndroidTest(libs.dagger.hilt.compiler)

    // Instrumented Unit Tests
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.android)
    debugImplementation(libs.fragment.testing)
    androidTestImplementation(libs.navigation.testing)
}