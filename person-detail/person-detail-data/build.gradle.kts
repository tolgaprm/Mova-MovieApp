import com.prmto.convention.dependency.coreDataModule
import com.prmto.convention.dependency.personDetailDomainModule

plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.person_detail_data"
}

dependencies {
    coreDataModule()
    personDetailDomainModule()
}