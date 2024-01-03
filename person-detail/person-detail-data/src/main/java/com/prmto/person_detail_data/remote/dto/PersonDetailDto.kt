package com.prmto.person_detail_data.remote.dto

import com.squareup.moshi.Json

data class PersonDetailDto(
    val adult: Boolean?,
    @Json(name = "also_known_as") val alsoKnownAs: List<String>?,
    val biography: String?,
    val birthday: String?,
    @Json(name = "combined_credits") val combinedCreditsDto: CombinedCreditsDto?,
    val deathday: String?,
    val gender: Int?,
    val homepage: String?,
    val id: Int?,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "known_for_department") val knownForDepartment: String?,
    val name: String?,
    @Json(name = "place_of_birth") val placeOfBirth: String?,
    val popularity: Double?,
    @Json(name = "profile_path") val profilePath: String?
)