package com.prmto.mova_movieapp.core.domain.models

enum class Category {
    MOVIE,
    TV,
    SEARCH
}

fun Category.isMovie(): Boolean {
    return this == Category.MOVIE
}

fun Category.isTv(): Boolean {
    return this == Category.TV
}

fun Category.isSearch(): Boolean {
    return this == Category.SEARCH
}