import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.databaseModuleWithImpl

plugins {
    id("mova.layer.domain")
}

android {
    namespace = "com.prmto.upcoming_domain"
}

dependencies {
    coreDomainModule()
    databaseModuleWithImpl()
}