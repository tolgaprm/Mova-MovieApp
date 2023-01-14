package com.prmto.mova_movieapp.feature_person_detail.domain.model

data class PersonDetail(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String,
    val combinedCredits: CombinedCredits,
    val deathday: String?,
    val imdbId: String,
    val knownForDepartment: String,
    val placeOfBirth: String,
    val profilePath: String?
)