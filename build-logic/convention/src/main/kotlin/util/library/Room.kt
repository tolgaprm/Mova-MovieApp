package util.library

import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKsp
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.room(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("room.runtime").get())
    addImplementation(libs.findLibrary("room.ktx").get())
    add("annotationProcessor", libs.findLibrary("room.compiler").get())
    addKsp(libs.findLibrary("room.compiler").get())
}