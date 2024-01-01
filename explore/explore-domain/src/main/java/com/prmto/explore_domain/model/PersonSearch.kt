package com.prmto.explore_domain.model

data class PersonSearch(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val knownForDepartment: String?,
    val knownFor: List<KnownForSearch>
)

data class KnownForSearch(
    val id: Int,
    val overview: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: String,
    val name: String,
    val originalName: String,
    val originalTitle: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)