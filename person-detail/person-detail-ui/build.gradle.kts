import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.personDetailDomainModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.example.person_detail_ui"
}

dependencies {
    coreUiModule()
    personDetailDomainModule()
}