package com.prmto.mova_movieapp.feature_person_detail.data.dto

import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail
import com.squareup.moshi.Json

data class PersonDetailDto(
    val adult: Boolean,
    @Json(name = "also_known_as") val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    @Json(name = "combined_credits") val combinedCreditsDto: CombinedCreditsDto,
    val deathday: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @Json(name = "imdb_id") val imdbId: String,
    @Json(name = "known_for_department") val knownForDepartment: String,
    val name: String,
    @Json(name = "place_of_birth") val placeOfBirth: String,
    val popularity: Double,
    @Json(name = "profile_path") val profilePath: String
)

fun PersonDetailDto.toPersonDetail(): PersonDetail {
    return PersonDetail(
        id = id,
        name = name,
        biography = biography,
        birthday = birthday,
        combinedCredits = combinedCreditsDto.toCombinedCredits(),
        deathday = deathday,
        imdbId = imdbId,
        knownForDepartment = knownForDepartment,
        placeOfBirth = placeOfBirth,
        profilePath = profilePath
    )
}