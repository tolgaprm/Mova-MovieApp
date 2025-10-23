plugins {
    id("mova.android.application")
}

android {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(libs.play.services.base)
    implementation(libs.play.services.ads)
}