import com.prmto.convention.dependency.coreDomainModule

plugins {
    id("mova.android.library")
    id("mova.android.room")
    id("mova.android.hilt")
}

android {
    namespace = "com.prmto.database"
}

dependencies {
    coreDomainModule()
}