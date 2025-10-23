package util.library

import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKsp
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.moshi(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("moshi").get())
    addImplementation(libs.findLibrary("moshi.kotlin").get())
    addKsp(libs.findLibrary("moshi.codegen").get())
}