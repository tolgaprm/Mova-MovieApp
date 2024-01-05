import com.prmto.convention.dependency.coreDataModule
import com.prmto.convention.dependency.upcomingDomainModule

plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.upcoming_data"
}

dependencies {
    coreDataModule()
    upcomingDomainModule()
}