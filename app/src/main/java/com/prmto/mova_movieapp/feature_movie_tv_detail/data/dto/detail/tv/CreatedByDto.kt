package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.CreatedBy
import com.squareup.moshi.Json

data class CreatedByDto(
    @Json(name = "credit_id") val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @Json(name = "profile_path") val profilePath: String?
)

fun List<CreatedByDto>.toListOfCreatedBy(): List<CreatedBy> {
    return map {
        CreatedBy(
            id = it.id,
            name = it.name
        )
    }
}