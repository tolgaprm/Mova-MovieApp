import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.upcomingDomainModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.upcoming_ui"
}

dependencies {
    coreUiModule()
    upcomingDomainModule()
    implementation(project(":notification"))
}