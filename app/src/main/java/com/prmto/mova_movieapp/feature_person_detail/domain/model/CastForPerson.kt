package com.prmto.mova_movieapp.feature_person_detail.domain.model

data class CastForPerson(
    val id: Int,
    val name: String?,
    val originalName: String?,
    val originalTitle: String?,
    val character: String,
    val firstAirDate: String?,
    val mediaType: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double
)
