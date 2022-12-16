package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.credit

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Cast
import com.squareup.moshi.Json

data class CastDto(
    val id: Int,
    val adult: Boolean,
    val gender: Int?,
    @Json(name = "known_for_department") val knownForDepartment: String,
    val name: String,
    @Json(name = "original_name") val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path") val profilePath: String?,
    val character: String,
    @Json(name = "credit_id") val creditId: String,
    val order: Int
)

fun List<CastDto>.toCast(): List<Cast> {
    return map {
        Cast(
            id = it.id,
            originalName = it.originalName,
            name = it.name,
            profilePath = it.profilePath,
            character = it.character
        )
    }
}