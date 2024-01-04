import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.exploreDomainModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.explore_ui"
}

dependencies {
    coreUiModule()
    exploreDomainModule()
}