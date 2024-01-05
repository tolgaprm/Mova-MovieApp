import com.prmto.convention.dependency.authenticationDomainModule
import com.prmto.convention.dependency.coreUiModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.authentication_ui"

}

dependencies {
    coreUiModule()
    authenticationDomainModule()
    implementation(libs.play.services.auth)
}