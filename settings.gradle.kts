pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mova-MovieApp"
include(":app")
include(":core:core-data")
include(":core:core-domain")
include(":database")
include(":upcoming")
include(":upcoming:upcoming-data")
include(":upcoming:upcoming-domain")
include(":authentication")
include(":authentication:authentication-data")
include(":authentication:authentication-domain")
include(":explore")
include(":explore:explore-domain")
include(":explore:explore-data")
include(":home")
include(":home:home-data")
include(":home:home-domain")
include(":home:home-ui")
include(":core:core-ui")
include(":authentication:authentication-ui")
include(":movie-tv-detail")
include(":movie-tv-detail:data")
include(":movie-tv-detail:domain")
include(":movie-tv-detail:ui")
include(":settings")
include(":settings:settings-domain")
include(":settings:settings-ui")
include(":my-list")
include(":my-list:my-list-ui")
