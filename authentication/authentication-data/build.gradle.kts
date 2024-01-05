plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.authentication_data"
}

dependencies {
    implementation(project(":core:core-data"))
    implementation(project(":authentication:authentication-domain"))
}