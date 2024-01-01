import com.prmto.convention.dependency.authenticationDomainModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.authentication_ui"

}

dependencies {
    authenticationDomainModule()
}