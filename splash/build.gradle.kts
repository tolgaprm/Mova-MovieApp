import com.prmto.convention.dependency.coreTestingModule
import com.prmto.convention.dependency.coreUiModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.splash"
}

dependencies {
    coreUiModule()
    coreTestingModule()
    testImplementation(libs.truth)
}