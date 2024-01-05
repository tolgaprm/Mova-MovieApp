import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.settingsDomainModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.settings_ui"
}

dependencies {
    coreUiModule()
    settingsDomainModule()
}