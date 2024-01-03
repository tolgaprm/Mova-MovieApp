import com.prmto.convention.dependency.coreDomainModule

plugins {
    id("mova.layer.domain")
}

android {
    namespace = "com.prmto.person_detail_domain"
}

dependencies {
    coreDomainModule()
}