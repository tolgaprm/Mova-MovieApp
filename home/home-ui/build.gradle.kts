import com.prmto.convention.dependency.coreUiModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.home_ui"
}

dependencies {
    coreUiModule()
}