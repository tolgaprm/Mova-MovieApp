plugins {
    id("mova.android.application")
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
    implementation(libs.play.services.base)
}