package util

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.getCompileSdk(): Int = findVersion("compileSdk").get().toString().toInt()

fun VersionCatalog.getMinSdk(): Int = findVersion("minSdk").get().toString().toInt()

fun VersionCatalog.getTargetSdk(): Int = findVersion("targetSdk").get().toString().toInt()

fun VersionCatalog.getVersionCode(): Int =
    findVersion("versionMajor").get().toString().toInt() * 10000 +
            findVersion("versionMinor").get().toString().toInt() * 10 +
            findVersion("versionPatch").get().toString().toInt()

fun VersionCatalog.getVersionName(): String =
    "${findVersion("versionMajor").get()}.${findVersion("versionMinor").get()}.${findVersion("versionPatch").get()}"