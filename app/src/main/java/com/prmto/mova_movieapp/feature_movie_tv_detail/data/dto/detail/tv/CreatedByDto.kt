package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv

import com.squareup.moshi.Json

data class CreatedByDto(
    @Json(name = "credit_id") val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @Json(name = "profile_path") val profilePath: String?
)