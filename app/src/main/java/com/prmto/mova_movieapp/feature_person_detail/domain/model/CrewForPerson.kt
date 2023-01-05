package com.prmto.mova_movieapp.feature_person_detail.domain.model

data class CrewForPerson(
    val id: Int,
    val department: String,
    val firstAirDate: String?,
    val job: String,
    val mediaType: String,
    val name: String?,
    val popularity: Double,

    val originalName: String?,
    val originalTitle: String?,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int
)