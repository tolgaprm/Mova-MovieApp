import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.homeDomainModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.home_ui"
}

dependencies {
    coreUiModule()
    homeDomainModule()
}