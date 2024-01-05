import com.prmto.convention.dependency.coreDataModule
import com.prmto.convention.dependency.exploreDomainModule

plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.explore_data"
}

dependencies {
    coreDataModule()
    exploreDomainModule()
}