package com.prmto.mova_movieapp.core.domain.models

enum class Sort(val value: String) {
    Popularity(value = "popularity"),
    LatestRelease(value = "release_date")
}

fun Sort.isPopularity(): Boolean {
    return this.value == Sort.Popularity.value
}

fun Sort.isLatestRelease(): Boolean {
    return this.value == Sort.LatestRelease.value
}