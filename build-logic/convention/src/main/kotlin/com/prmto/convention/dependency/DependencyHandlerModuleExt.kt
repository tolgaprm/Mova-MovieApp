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

fun DependencyHandlerScope.coreUiModule() {
    addModule(":core:core-ui")
}

// Upcoming
fun DependencyHandlerScope.upcomingDomainModule() {
    addModule(":upcoming:upcoming-domain")
}

fun DependencyHandlerScope.upcomingDataModule() {
    addModule(":upcoming:upcoming-data")
}

internal fun DependencyHandlerScope.upcomingFeature() {
    upcomingDataModule()
    upcomingDomainModule()
}

// Authentication
fun DependencyHandlerScope.authenticationDataModule() {
    addModule(":authentication:authentication-data")
}

fun DependencyHandlerScope.authenticationDomainModule() {
    addModule(":authentication:authentication-domain")
}

fun DependencyHandlerScope.authenticationUiModule() {
    addModule(":authentication:authentication-ui")
}

internal fun DependencyHandlerScope.authenticationFeature() {
    authenticationDataModule()
    authenticationDomainModule()
    authenticationUiModule()
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

internal fun DependencyHandlerScope.exploreFeature() {
    exploreDataModule()
    exploreDomainModule()
}

// Home
fun DependencyHandlerScope.homeDataModule() {
    addModule(":home:home-data")
}

fun DependencyHandlerScope.homeDomainModule() {
    addModule(":home:home-domain")
}

fun DependencyHandlerScope.homeUiModule() {
    addModule(":home:home-ui")
}

internal fun DependencyHandlerScope.homeFeature() {
    homeDataModule()
    homeDomainModule()
    homeUiModule()
}