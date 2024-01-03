import com.prmto.convention.dependency.coreUiModule
import com.prmto.convention.dependency.movieTvDetailDomainModule

plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.ui"
}

dependencies {
    coreUiModule()
    movieTvDetailDomainModule()
}