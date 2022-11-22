package com.prmto.mova_movieapp.data.models.detail.tv

import com.squareup.moshi.Json

data class CreatedBy(
    @Json(name = "credit_id") val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @Json(name = "profile_path") val profilePath: String?
)