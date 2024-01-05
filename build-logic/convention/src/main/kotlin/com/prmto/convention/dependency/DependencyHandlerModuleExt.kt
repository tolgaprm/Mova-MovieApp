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

fun DependencyHandlerScope.upcomingUiModule() {
    addModule(":upcoming:upcoming-ui")
}

internal fun DependencyHandlerScope.upcomingFeature() {
    upcomingDataModule()
    upcomingDomainModule()
    upcomingUiModule()
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

fun DependencyHandlerScope.exploreUiModule() {
    addModule(":explore:explore-ui")
}

internal fun DependencyHandlerScope.exploreFeature() {
    exploreDataModule()
    exploreDomainModule()
    exploreUiModule()
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

// MovieTvDetail
fun DependencyHandlerScope.movieTvDetailDataModule() {
    addModule(":movie-tv-detail:detail-data")
}

fun DependencyHandlerScope.movieTvDetailDomainModule() {
    addModule(":movie-tv-detail:detail-domain")
}

fun DependencyHandlerScope.movieTvDetailUiModule() {
    addModule(":movie-tv-detail:detail-ui")
}

internal fun DependencyHandlerScope.movieTvDetailFeature() {
    movieTvDetailDataModule()
    movieTvDetailDomainModule()
    movieTvDetailUiModule()
}

// Settings
fun DependencyHandlerScope.settingsDomainModule() {
    addModule(":settings:settings-domain")
}

fun DependencyHandlerScope.settingsUiModule() {
    addModule(":settings:settings-ui")
}

internal fun DependencyHandlerScope.settingsFeature() {
    settingsDomainModule()
    settingsUiModule()
}

// MyList
fun DependencyHandlerScope.myListUiModule() {
    addModule(":my-list:my-list-ui")
}

internal fun DependencyHandlerScope.myListFeature() {
    myListUiModule()
}

// Person Detail

fun DependencyHandlerScope.personDetailDataModule() {
    addModule(":person-detail:person-detail-data")
}


fun DependencyHandlerScope.personDetailDomainModule() {
    addModule(":person-detail:person-detail-domain")
}

fun DependencyHandlerScope.personDetailUiModule() {
    addModule(":person-detail:person-detail-ui")
}

internal fun DependencyHandlerScope.personDetailFeature() {
    personDetailDataModule()
    personDetailDomainModule()
    personDetailUiModule()
}

// Navigation
fun DependencyHandlerScope.navigationModule() {
    addModule(":navigation")
}