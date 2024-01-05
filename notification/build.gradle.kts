import com.prmto.convention.dependency.coreDomainModule

plugins {
    id("mova.android.library")
    id("mova.android.hilt")
}

android {
    namespace = "com.prmto.notification"
}

dependencies {
    coreDomainModule()
}