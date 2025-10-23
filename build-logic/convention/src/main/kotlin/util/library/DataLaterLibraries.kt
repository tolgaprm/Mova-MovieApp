package util.library

import com.prmto.convention.dependency.commonDependencies
import com.prmto.convention.dependency.coreDomainModule
import com.prmto.convention.dependency.dataStore
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.dataLaterLibraries(libs: VersionCatalog) {
    retrofit(libs)
    firebase(libs)
    coreDomainModule()
    dataStore(libs)
    commonDependencies(libs)
}