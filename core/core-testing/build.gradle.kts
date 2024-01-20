import com.prmto.convention.dependency.coreDomainModule

plugins {
    id("mova.layer.domain")
}

android {
    namespace = "com.prmto.core_testing"
}

dependencies {
    coreDomainModule()
    testImplementation(project(":core:core-domain"))
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit)
    implementation(libs.mockwebserver)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.kotlin.codegen)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit)
    implementation(libs.retrofit.coroutines.adapter)
}