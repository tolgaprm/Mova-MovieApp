package com.prmto.mova_movieapp.data.models.credit

import com.prmto.mova_movieapp.domain.models.credit.Crew
import com.squareup.moshi.Json

data class CrewDto(
    val id: Int,
    val adult: Boolean,
    val gender: Int?,
    @Json(name = "known_for_department") val knownForDepartment: String,
    val name: String,
    @Json(name = "original_name") val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "credit_id") val creditId: String,
    val department: String,
    val job: String
)

fun List<CrewDto>.toCrew(): List<Crew> {
    return map {
        Crew(
            id = it.id,
            name = it.name,
            originalName = it.originalName,
            profilePath = it.profilePath,
            department = it.department
        )
    }
}