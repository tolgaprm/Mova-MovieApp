import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}
var properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}
android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.prmto.mova_movieapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 9
        versionName = "1.1"

        testInstrumentationRunner = "com.prmto.mova_movieapp.HiltTestRunner"
        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        val resourceConfigurations = listOf("en", "tr-rTR", "de-rDE")
        resourceConfigurations.forEach { resConfig ->
            resConfig
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.ktx)

    implementation(libs.play.services.base)

    //dagger - hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.compiler)

    // navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // coroutines for getting off the UI thread
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Work Manager
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)


    // retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.retrofit.converter.moshi)

    // moshi for parsing the JSON format
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    kapt(libs.moshi.kotlin.codegen)

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

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.play.services.ads)
    implementation(libs.firebase.appcheck.playintegrity)


    // Local Unit Tests
    testImplementation(libs.junit)
    implementation(libs.test.core)
    testImplementation(libs.hamcrest)
    testImplementation(libs.core.testing)
    testImplementation(libs.robolectric)
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