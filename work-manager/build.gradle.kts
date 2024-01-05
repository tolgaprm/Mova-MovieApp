import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.notificationModule

plugins {
    id("mova.android.library")
    id("mova.android.hilt")
}

android {
    namespace = "com.prmto.work_manager"
}

dependencies {
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)
    notificationModule()
    coreDomainModule()
}