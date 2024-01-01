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
