import com.prmto.convention.dependency.coreDataModule
import com.prmto.convention.dependency.movieTvDetailDomainModule

plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.data"
}

dependencies {
    coreDataModule()
    movieTvDetailDomainModule()
}