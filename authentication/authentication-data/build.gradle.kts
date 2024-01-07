import com.prmto.convention.dependency.authenticationDomainModule
import com.prmto.convention.dependency.coreDataModule

plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.authentication_data"
}

dependencies {
    coreDataModule()
    authenticationDomainModule()
}