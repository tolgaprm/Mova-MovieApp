import com.prmto.convention.dependency.coreUiModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.my_list_ui"
}

dependencies {
    coreUiModule()
}