plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.upcoming_data"
}

dependencies {
    implementation(project(":core:core-data"))
    implementation(project(":upcoming:upcoming-domain"))
}