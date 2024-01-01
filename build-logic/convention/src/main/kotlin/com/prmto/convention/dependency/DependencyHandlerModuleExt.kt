package com.prmto.convention.dependency

import com.prmto.convention.dependencyHandler.addApi
import com.prmto.convention.dependencyHandler.addModule
import org.gradle.kotlin.dsl.DependencyHandlerScope

// Core
fun DependencyHandlerScope.coreDataModule() {
    addModule(":core:core-data")
}

fun DependencyHandlerScope.coreDomainModule() {
    addModule(":core:core-domain")
}

// Upcoming
fun DependencyHandlerScope.upcomingDomainModule() {
    addModule(":upcoming:upcoming-domain")
}

fun DependencyHandlerScope.upcomingDataModule() {
    addModule(":upcoming:upcoming-data")
}

// Authentication
fun DependencyHandlerScope.authenticationDataModule() {
    addModule(":authentication:authentication-data")
}

fun DependencyHandlerScope.authenticationDomainModule() {
    addModule(":authentication:authentication-domain")
}

//Database
fun DependencyHandlerScope.databaseModuleWithApi() {
    addApi(":database")
}

fun DependencyHandlerScope.databaseModuleWithImpl() {
    addModule(":database")
}

// Explore
fun DependencyHandlerScope.exploreDataModule() {
    addModule(":explore:explore-data")
}

fun DependencyHandlerScope.exploreDomainModule() {
    addModule(":explore:explore-domain")
}
