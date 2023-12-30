plugins {
    id("mova.layer.domain")
}

android {
    namespace = "com.prmto.upcoming_domain"
}

dependencies {
    implementation(project(":core:core-domain"))
    implementation(project(":database"))
}