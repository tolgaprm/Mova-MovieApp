plugins {
    id("mova.android.library")
    id("mova.android.navigation")
    id("mova.android.hilt")
}

android {
    namespace = "com.prmto.navigation"
}

dependencies {
    implementation(project(":core:core-domain"))
}