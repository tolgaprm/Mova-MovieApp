import com.prmto.convention.dependency.coreDomainModule

plugins {
    id("mova.layer.domain")
}

android {
    namespace = "com.prmto.authentication_domain"
}

dependencies {
    coreDomainModule()
}