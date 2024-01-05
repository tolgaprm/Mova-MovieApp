plugins {
    id("mova.layer.domain")
}

android {
    namespace = "com.prmto.authentication_domain"
}

dependencies {
    implementation(project(":core:core-domain"))
}