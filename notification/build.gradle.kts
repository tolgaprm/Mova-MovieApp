import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.coreUiModule

plugins {
    id("mova.android.library")
    id("mova.android.hilt")
}

android {
    namespace = "com.prmto.notification"
}

dependencies {
    coreDomainModule()
    coreUiModule()
}