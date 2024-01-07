import com.prmto.convention.dependency.coreTestingModule
import com.prmto.convention.dependency.databaseModuleWithApi

plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.core_data"
}

dependencies {
    databaseModuleWithApi()
    coreTestingModule()
}